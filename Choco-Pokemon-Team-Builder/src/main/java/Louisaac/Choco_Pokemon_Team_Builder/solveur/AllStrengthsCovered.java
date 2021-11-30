package Louisaac.Choco_Pokemon_Team_Builder.solveur;

import org.chocosolver.solver.constraints.Propagator;
import org.chocosolver.solver.constraints.PropagatorPriority;
import org.chocosolver.solver.exception.ContradictionException;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.util.ESat;

import Louisaac.Choco_Pokemon_Team_Builder.pokemon.Pokemon;
import Louisaac.Choco_Pokemon_Team_Builder.pokemon.Type;

public class AllStrengthsCovered extends Propagator<IntVar>
{
	
	public AllStrengthsCovered(IntVar[] team)
	{
		super(team, PropagatorPriority.LINEAR, false);
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
		for (int i = 0; i < this.getVars().length; i++)
		{
			if (!this.getVar(i).isInstantiated())
			{
				return ESat.UNDEFINED;
			}
		}
		
		for (Type type : Type.values())
		{
			boolean typeCovered = false;
			
			for (IntVar var : this.getVars())
			{
				if (Pokemon.getPokemon(var.getValue()).isStrongAgainst(type))
				{
					typeCovered = true;
				}
			}
			
			if (!typeCovered)
			{
				return ESat.FALSE;
			}
		}

		return ESat.TRUE;
	}
}
