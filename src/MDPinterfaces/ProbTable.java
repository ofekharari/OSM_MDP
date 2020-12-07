package MDPinterfaces;

import MDP.Action;
import MDP.State;

/**
 * 
 * @author ofek harari & eldad wiener
 * The interface ProbTable is necessary for every class who wants to define probability map for transition between states.
 * Based on Optimal Selfish Mining Strategies in Bitcoin paper by Ayelet Sapirshtein, Yonatan Sompolinsky and Aviv Zohar.
 *
 */
public interface ProbTable {
	/**
	 *@effects return probability to move from src state to dst state with action.
	 */	
	public double getProbByActionAndStates(Action action, State srcState, State dstState);
}
