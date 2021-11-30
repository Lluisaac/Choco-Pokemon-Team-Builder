package Louisaac.Choco_Pokemon_Team_Builder.pokemon;

import java.util.List;

public class Pokemon
{
	public static Pokemon[] pokedex = new Pokemon[395];

	public String name;

	public List<Type> types;

	public int gen;

	public Rarity rarity;

	static
	{
		pokedex[0] = new Pokemon("Louis", 1, Rarity.MYTHICAL, Type.NORMAL, Type.BUG);
		pokedex[1] = new Pokemon("Isaac", 1, Rarity.MYTHICAL, Type.POISON);
		pokedex[2] = new Pokemon("Kevin", 1, Rarity.NORMAL, Type.GROUND);
		pokedex[3] = new Pokemon("Durand", 2, Rarity.LEGENDARY, Type.PSYCHIC, Type.DARK);
		pokedex[4] = new Pokemon("Delahaye", 3, Rarity.LEGENDARY, Type.FIRE, Type.DRAGON);
		pokedex[394] = new Pokemon("Pingoleon", 4, Rarity.NORMAL, Type.WATER, Type.STEEL);
	}
	
	public Pokemon(String name, int gen, Rarity rarity, Type... types)
	{
		super();
		this.name = name;
		this.types = List.of(types);
		this.gen = gen;
		this.rarity = rarity;
	}

	public static Pokemon getPokemon(int index)
	{
		return pokedex[index - 1];
	}

	public boolean isStrongAgainst(Type defensive)
	{
		for (Type type : this.types)
		{
			if (type.getOffensiveModifier(defensive) == DamageModifier.WEAKNESS)
			{
				return true;
			}
		}

		return false;
	}
	
	@Override
	public String toString()
	{
		return this.name;
	}
}
