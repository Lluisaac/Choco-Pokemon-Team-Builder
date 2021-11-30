package Louisaac.Choco_Pokemon_Team_Builder.main;

import Louisaac.Choco_Pokemon_Team_Builder.solveur.Modelisation;

public class Main
{
	private static final int TEAM_SIZE = 6;

	public static void main(String[] args)
	{
		Modelisation model = new Modelisation(TEAM_SIZE, 1000); //TODO Mettre la taille du pokédex à la place du "1000"
		model.solve();
	}
}
