package Louisaac.Choco_Pokemon_Team_Builder.api;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import Louisaac.Choco_Pokemon_Team_Builder.pokemon.DamageModifier;
import Louisaac.Choco_Pokemon_Team_Builder.pokemon.Pokemon;
import Louisaac.Choco_Pokemon_Team_Builder.pokemon.Rarity;
import Louisaac.Choco_Pokemon_Team_Builder.pokemon.Type;

public class PokedexLoader
{
	
	private static final int MAX_INDEX = 898;
	private HttpClient client;
	
	public PokedexLoader()
	{
		this.client = HttpClient.newHttpClient();
	}

	public void load() throws IOException, InterruptedException
	{
		this.loadTypes();
		this.loadPokemons();
	}

	private void loadTypes() throws IOException, InterruptedException
	{
		for (int i = 0; i < 18; i++)
		{			
			loadType(i + 1);
		}
	}

	private void loadType(int index) throws IOException, InterruptedException
	{
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("https://pokeapi.co/api/v2/type/" + index + "/"))
				.build();
		HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
		JSONObject type = new JSONObject(response.body());
		String name = type.getString("name").toUpperCase();
		JSONObject damageRelations = type.getJSONObject("damage_relations");
		
		this.setDamageModifiers(name, damageRelations, "no_damage_to", DamageModifier.IMMUNITY);
		this.setDamageModifiers(name, damageRelations, "double_damage_to", DamageModifier.WEAKNESS);
		this.setDamageModifiers(name, damageRelations, "half_damage_to", DamageModifier.RESISTANCE);
	}

	private void setDamageModifiers(String offensiveName, JSONObject damageRelations, String relationName, DamageModifier modifier)
	{
		JSONArray noDamage = damageRelations.getJSONArray(relationName);
		
		for (Object otherType : noDamage)
		{
			String otherTypeName = ((JSONObject) otherType).getString("name").toUpperCase();
			Type.setDamageModifier(offensiveName, otherTypeName, modifier);
		}
	}

	private void loadPokemons() throws IOException, InterruptedException
	{
		int pokemonAmount = MAX_INDEX;
		
		for (int i = 0; i < pokemonAmount; i++)
		{
			this.loadPokemon(i + 1);
		}
	}

	private void loadPokemon(int index) throws IOException, InterruptedException
	{
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("https://pokeapi.co/api/v2/pokemon/" + index + "/"))
				.build();
		HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
		JSONObject rawPokemon = new JSONObject(response.body());
		
		if (rawPokemon.getBoolean("is_default"))
		{			
			String name = rawPokemon.getString("name");
			List<Type> types = this.getTypesOf(rawPokemon);
			
			Pokemon pokemon = loadPokemonSpecies(rawPokemon.getJSONObject("species").getString("name"), name, types);
			
			Pokemon.add(pokemon);
		}
	}

	private Pokemon loadPokemonSpecies(String speciesName, String name, List<Type> types) throws IOException, InterruptedException
	{
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("https://pokeapi.co/api/v2/pokemon-species/" + speciesName + "/"))
				.build();
		HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
		JSONObject rawPokemonSpecies = new JSONObject(response.body());
		
		String[] splitUrl = rawPokemonSpecies.getJSONObject("generation").getString("url").split("/");
		int gen = Integer.parseInt(splitUrl[splitUrl.length - 1]);
		
		Rarity rarity = Rarity.NORMAL;
		
		if (rawPokemonSpecies.getBoolean("is_legendary"))
		{
			rarity = Rarity.LEGENDARY;
		}
		else if (rawPokemonSpecies.getBoolean("is_mythical"))
		{
			rarity = Rarity.MYTHICAL;
		}
		
		return new Pokemon(name, gen, rarity, types.toArray(new Type[0]));
	}

	private List<Type> getTypesOf(JSONObject rawPokemon)
	{
		List<Type> types = new ArrayList<>();
		
		JSONArray rawTypes = rawPokemon.getJSONArray("types");
		
		for (Object rawType : rawTypes)
		{
			String name = ((JSONObject) rawType).getJSONObject("type").getString("name").toUpperCase();
			types.add(Type.valueOf(name));
		}
		
		return types;
		
	}
}
