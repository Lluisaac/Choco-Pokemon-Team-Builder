package Louisaac.Choco_Pokemon_Team_Builder.main;

import Louisaac.Choco_Pokemon_Team_Builder.solveur.Modelisation;

public class Main
{
	public static void main(String[] args)
	{
		Modelisation model = new Modelisation(6, new int[] {1, 2, 3, 4, 5, 395});
		model.solve();
	}
}
