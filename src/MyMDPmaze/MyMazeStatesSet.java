package MyMDPmaze;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import MDP.Action;
import MDP.State;
import MDPinterfaces.ProbTable;
import MDPinterfaces.RewardTable;
import MDPinterfaces.StatesSet;
import MyMDPimp.MyState;

public class MyMazeStatesSet implements StatesSet {
	private long SEED = 0;
	private Random rand = new Random(SEED);
	private Set <MyMazeState> statesSet = new HashSet<>();
	private Set<Action> actionSet = new HashSet<>();
	private ProbTable probTable;
	private RewardTable rewardTable;
	
	
	/**
	 *@requires None
	 *@modified this
	 *@effects create myStatesSet instance with default probTable 
	 *		   (for every Action action: P(src,src,action) = 1)
	 */
	public MyMazeStatesSet() {
		this.probTable = new ProbTable() {
			public double getProbByActionAndStates(Action action, State srcState, State dstState) {
				if(srcState.equals(dstState)) {
					return 1;
				}
				return 0;
			}			
		};
		this.rewardTable = new RewardTable() {
			public double getRewardByActionAndStates(Action action, State srcState, State dstState) {
				MyMazeState state = (MyMazeState) findState(dstState);
				assert state != null:"not exist state in function getRewardByActionAndStates";
				if(state.isTerminationState()) {
					return 10;
				}
				return -1;
			}			
		};
	}
	
	/**
	 *@requires None
	 *@modified this
	 *@effects if newProbTable isnt null: set new probability mapping for the states.
	 */
	@Override
	public void setProbTable(ProbTable newProbTalbe) {
		if(newProbTalbe != null) {
			probTable = newProbTalbe;	
		}
	}

	/**
	 *@requires None
	 *@modified None
	 *@effects return probability to move from src state to dst state with action.
	 */
	@Override
	public double getProbByActionAndStates(Action action, State srcState, State dstState) {
		MyMazeState sState = (MyMazeState) findState(srcState);
		MyMazeState dState = (MyMazeState) findState(dstState);
		return probTable.getProbByActionAndStates(action, sState, dState);	
	}
	
	/**
	 *@requires None
	 *@modified None
	 *@effects set new rewardTable.
	 */
	@Override
	public void setRewardTable(RewardTable newRewardTable) {
		if(newRewardTable != null) {
			rewardTable = newRewardTable;
		}
	}
	/**
	 *@requires None
	 *@modified None
	 *@effects return the reward that received after moving from srcState to dstState with action.
	 */
	@Override
	public double getRewardByActionAndStates(Action action, State srcState, State dstState) {
		return rewardTable.getRewardByActionAndStates(action, srcState, dstState);
	}
	
	/**
	 *@requires None
	 *@modified this
	 *@effects create new state with state name and adding to the map. if the name already exist then replace the old with the new
	 */
	@Override
	public boolean addState(State state) {
		if (state == null) {
			return false;
		}
		statesSet.add((MyMazeState)state.clone());
		return true;
	}
	
	/**
	 *@requires None
	 *@modified None
	 *@effects return collection of States names.
	 */
	@Override
	public Iterator<? extends State> getStates() {
		return statesSet.iterator();
	}

	/**
	 *@requires None
	 *@modified None
	 *@effects return collection of Actions.
	 */
	@Override
	public Collection<? extends Action> getActions() {
		return Collections.unmodifiableSet(actionSet);
	}
	
	/**
	 *@requires None
	 *@modified this
	 *@effects if action isnt null : add action to the actionSet.
	 */
	@Override
	public void addAction(Action action) {
		if(action != null) {
			actionSet.add(action);
		}	
	}
	
	/**
	 *@requires None
	 *@modified None
	 *@effects return next state by given action and current state according to the probabilities.
	 */
	@Override
	public State getNextStateByAction(Action action , State curState) {
		if(action == null || curState == null) {
			return null;
		}
		double range = 0;
		double number = rand.nextDouble();
		//System.out.println("\t the random number is : " + number);
		Iterator<? extends State>iter = getStates();
		while(iter.hasNext()) {
			State dstState = iter.next();
			double curProb = getProbByActionAndStates(action, curState, dstState);
			if(curProb == 0) continue;
			if((number >= range) && (number <= (curProb + range))) {
				//System.out.println("\t the current prob is : " + curProb);
				return dstState;
			}
			range += curProb;
		}
		return null;
	}
	
	/**
	 *@requires None
	 *@modified this
	 *@effects  set seed.
	 */
	@Override
	public void setSeed(long seed) {
		this.SEED = seed;
	}
	
	/**
	 *@requires None
	 *@modified None
	 *@effects  return description of States (str).
	 */
	@Override
	public String toString() {
		String str = "The states are :\n";
		for(MyMazeState state : statesSet) {
			str += state.toString() + "\n";		
		}	
		return str;
	}
	
	/**
	 *@requires None
	 *@modified this
	 *@effects return state by state name. if not exists return null.
	 */
	@Override
	 public State findState (State curState) {
		for(MyMazeState state : statesSet) {
			if (state.equals(curState)) {
				return state;
			}
		}
		return null;
	}
	
	
	/*
	public static void main(String[] args) {
			
		MyStatesSet world = new MyStatesSet();
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				world.createState(new MyStateName(i,j));
			}
		}
		world.setProbTable(new MyProbTable(4,4));
		world.setRewardTable(new MyRewardTable());
		world.getState(new MyStateName(3,3)).setTerminationState();
		//world.setProbTable(new newProbTalbe);

		world.addAction(new Action("up"));
		world.addAction(new Action("left"));
		//world.addAction(new Action("down"));
		//world.addAction(new Action("right"));
		for(StateName srcStateName : world.getStatesName()){
			System.out.println("srcStateName = " + srcStateName);
			for(Action action : world.getActions()){
				System.out.println("\taction = " + action);
				for(StateName dstStateName : world.getStatesName()){
					double prob = world.getProbByActionAndStates(action, srcStateName, dstStateName);
					double reward = world.getRewardByActionAndStates(action, srcStateName, dstStateName);
					System.out.println("\t\tThe probability to move to " + dstStateName + " is : " + prob 
							+ " and the reward is : " + reward);
					
				}
			}
		}
		
		System.out.println(world);
		
	}
	*/
}
