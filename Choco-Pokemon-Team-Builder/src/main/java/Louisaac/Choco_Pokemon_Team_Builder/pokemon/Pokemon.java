package Louisaac.Choco_Pokemon_Team_Builder.pokemon;

import java.util.List;

public class Pokemon
{
	public static Pokemon[] pokedex = new Pokemon[395];

	public String name;

	public List<Type> types;

	public int gen;

	public Rarity rarity;
	
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
