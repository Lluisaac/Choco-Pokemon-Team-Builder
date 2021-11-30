package Louisaac.Choco_Pokemon_Team_Builder.constraint;

import java.util.List;

import org.chocosolver.solver.constraints.Propagator;
import org.chocosolver.solver.constraints.PropagatorPriority;
import org.chocosolver.solver.exception.ContradictionException;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.util.ESat;

import Louisaac.Choco_Pokemon_Team_Builder.pokemon.Pokemon;

public class ChosenGeneration extends Propagator<IntVar>
{
	
	private List<Integer> avaliableGenerations;
	
	public ChosenGeneration(IntVar[] team, Integer... generations)
	{
		super(team, PropagatorPriority.LINEAR, false);
		this.avaliableGenerations = List.of(generations);
	}

	@Override
	public void propagate(int evtmask) throws ContradictionException
	{
		if (this.isEntailed().equals(ESat.FALSE))
		{
			throw new ContradictionException();
		}
	}

	@Override
	public ESat isEntailed()
	{		
		for (IntVar var : this.getVars())
		{
			if (var.isInstantiated() && !this.avaliableGenerations.contains(Pokemon.getPokemon(var.getValue()).gen))
			{
				return ESat.FALSE;
			}
		}
		
		for (IntVar var : this.getVars())
		{
			if (!var.isInstantiated())
			{
				return ESat.UNDEFINED;
			}
		}

		return ESat.TRUE;
	}
}
