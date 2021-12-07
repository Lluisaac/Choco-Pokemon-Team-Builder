package Louisaac.Choco_Pokemon_Team_Builder.main;

import java.io.IOException;

import Louisaac.Choco_Pokemon_Team_Builder.api.PokedexLoader;
import Louisaac.Choco_Pokemon_Team_Builder.pokemon.Pokemon;
import Louisaac.Choco_Pokemon_Team_Builder.solveur.Modelisation;

public class Main
{
	private static final int TEAM_SIZE = 6;

	public static void main(String[] args) throws IOException, InterruptedException
	{
		PokedexLoader pokedex = new PokedexLoader();
		pokedex.load();
		Modelisation model = new Modelisation(TEAM_SIZE, Pokemon.pokedex.size());
		model.solve();
	}
}
