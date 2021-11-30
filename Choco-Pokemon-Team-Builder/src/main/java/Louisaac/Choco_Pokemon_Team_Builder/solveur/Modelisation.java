package Louisaac.Choco_Pokemon_Team_Builder.solveur;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.constraints.Constraint;
import org.chocosolver.solver.exception.InvalidSolutionException;
import org.chocosolver.solver.variables.IntVar;

import Louisaac.Choco_Pokemon_Team_Builder.constraint.AllStrengthsCovered;
import Louisaac.Choco_Pokemon_Team_Builder.constraint.ChosenGeneration;
import Louisaac.Choco_Pokemon_Team_Builder.pokemon.Pokemon;

public class Modelisation
{
	private Model model;

	private IntVar[] team;

	public Modelisation(int teamSize, int[] pokemonList)
	{
		this.model = new Model();

		this.team = this.model.intVarArray(teamSize, pokemonList);

	}

	public void solve()
	{
		this.buildConstraints();
		
		this.launchSolver();
	}

	private void buildConstraints()
	{
		this.team[0].eq(395).post();

		(new Constraint("AllStrengthsCovered", new AllStrengthsCovered(team))).post();
		(new Constraint("ChosenGeneration", new ChosenGeneration(team, 2, 3, 4))).post();
	}
	
	private void launchSolver()
	{
		try
		{
			this.model.getSolver().solve();
			this.model.getSolver().printStatistics();
		}
		catch (InvalidSolutionException e)
		{
			e.printStackTrace();
		}
		finally
		{
			this.printSolution();
		}
	}

	private void printSolution()
	{
		String str = "\n";
		
		for (int i = 0; i < this.team.length; i++)
		{
			str += Pokemon.getPokemon(this.team[i].getValue()) + ", ";
		}
		
		str = str.substring(0, str.length() - 2);
		
		System.out.println(str);
	}
}
