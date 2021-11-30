package Louisaac.Choco_Pokemon_Team_Builder.pokemon;

public enum Type
{
	STEEL, FIGHTING, DRAGON, WATER, ELECTRIK, FAIRY, FIRE, ICE, BUG, NORMAL, GRASS, POISON, PSYCHIC, ROCK, GROUND, GHOST, DARK, FLYING;

	private static final DamageModifier[][] typeArray = new DamageModifier[Type.values().length][Type.values().length];

	static
	{
		for (int i = 0; i < typeArray.length; i++)
		{
			for (int j = 0; j < typeArray[i].length; j++)
			{
				typeArray[i][j] = DamageModifier.NORMAL;
			}
		}
	}

	public static void setDamageModifier(Type offensive, Type defensive, DamageModifier modifier)
	{
		typeArray[offensive.ordinal()][defensive.ordinal()] = modifier;
	}

	public DamageModifier getOffensiveModifier(Type other)
	{
		return typeArray[this.ordinal()][other.ordinal()];
	}

	public DamageModifier getDefensiveModifier(Type other)
	{
		return typeArray[other.ordinal()][this.ordinal()];
	}
}
