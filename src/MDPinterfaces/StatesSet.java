package MDPinterfaces;

import java.util.Collection;
import java.util.Iterator;

import MDP.Action;
import MDP.State;

/**
 * 
 * @author ofek harari & eldad wiener
 * The interface StatesSet is necessary for every class who wants to define set of states for MDP problem.
 * Based on Optimal Selfish Mining Strategies in Bitcoin paper by Ayelet Sapirshtein, Yonatan Sompolinsky and Aviv Zohar.
 *
 */
public interface StatesSet {
	
	/**
	 *@effects add new state to the states set. 
	 */
	public boolean addState(State state);
	
	/**
	 *@effects set new probTable.
	 */
	public void setProbTable(ProbTable newProbTalbe);
	
	/**
	 *@effects return probability to move from src state to dst state with action.
	 */	
	public double getProbByActionAndStates(Action action, State srcState, State dstState);
	
	/**
	 *@effects set new rewardTable.
	 */
	public void setRewardTable(RewardTable newRewordTable);
	
	/**
	 *@effects return the reward that received after moving from srcState to dstState with action.
	 */	
	public double getRewardByActionAndStates(Action action, State srcState, State dstState);
	
	/**
	 *@effects return iterator to States set.
	 */	
	public Iterator<? extends State> getStates();
	
	/**
	 *@effects return collection of Actions.
	 */	
	public Collection<? extends Action> getActions();
	
	/**
	 *@effects add action to List.
	 */	
	public void addAction(Action action);
	
	/**
	 *@effects return next state by given action and current state according to the probabilities. 
	 */	
	public State getNextStateByAction(Action action , State curState);
	
	/**
	 *@effects set seed.
	 */	
	public void setSeed(long seed);
	
	/**
	 *@effects find state in the state set.
	 */	
	public State findState(State curState);
	
	
	
}
