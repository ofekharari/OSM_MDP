package MDPinterfaces;

import MDP.Action;
import MDP.State;

/**
 * 
 * @author ofek harari & eldad wiener
 * The interface RewardTable is necessary for every class who wants to define reward map for transition between states and actions.
 *
 */
public interface RewardTable {
	/**
	 *@effects return reward to move from src state to dst state with action.
	 */	
	public double getRewardByActionAndStates(Action action, State srcState, State dstState);
}
