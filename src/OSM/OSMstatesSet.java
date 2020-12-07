package OSM;

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
import OSM.OSMrewardTable.payingPolicy;

/**
 * 
 * @author ofek harari & eldad wiener
 * The class OSMstatesSet represents a set of state of OSM MDP problem.
 * The class holds: set of states , set of actions , probability table and reward table.
 * Based on Optimal Selfish Mining Strategies in Bitcoin paper by Ayelet Sapirshtein, Yonatan Sompolinsky and Aviv Zohar.
 *
 */
public class OSMstatesSet implements StatesSet {
	private long SEED = 0;
	private Random rand = new Random(SEED);
	private Set <OSMstate> statesSet = new HashSet<>();
	private Set<Action> actionSet = new HashSet<>();
	private ProbTable probTable;
	private RewardTable rewardTable;
	private static boolean DEBUG_MODE = false;
	
	
	/**
	 *@requires None
	 *@modified this
	 *@effects create myStatesSet instance with default probTable 
	 *		   (for every Action action: P(src,src,action) = 1)
	 */
	public OSMstatesSet() {
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
				MyState state = (MyState) findState(dstState);
				assert state != null:"not exist state in function getRewardByActionAndStates";
				if(state.isTerminationState()) {
					return 10;
				}
				return -1;
			}			
		};
	}
	
	/**
	 *@requires curState != null
	 *@modified this
	 *@effects  find state in the state set.
	 */
	@Override
	 public State findState (State curState) {
		for(OSMstate state : statesSet) {
			if (state.equals(curState)) {
				return state;
			}
		}
		return null;
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
	 *@requires action,srcState,dstState != null
	 *@modified None
	 *@effects return probability to move from src state to dst state with action.
	 */
	@Override
	public double getProbByActionAndStates(Action action, State srcState, State dstState) {
		OSMstate sState = (OSMstate) findState(srcState);
		OSMstate dState = (OSMstate) findState(dstState);
		return probTable.getProbByActionAndStates(action, sState, dState);	
	}
	
	/**
	 *@requires newRewardTable != null
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
	 *@requires action,srcState,dstState != null
	 *@modified None
	 *@effects return the reward that received after moving from srcState to dstState with action.
	 */
	@Override
	public double getRewardByActionAndStates(Action action, State srcState, State dstState) {
		return rewardTable.getRewardByActionAndStates(action, srcState, dstState);
	}
	
	/**
	 *@requires state != null
	 *@modified this
	 *@effects create new state with state name and adding to the map. if the name already exist then replace the old with the new
	 */
	@Override
	public boolean addState(State state) {
		if (state == null) {
			return false;
		}
		statesSet.add((OSMstate)state.clone());
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
	 *@requires attion != null
	 *@modified this
	 *@effects if action is not null : add action to the actionSet.
	 */
	@Override
	public void addAction(Action action) {
		if(action != null) {
			actionSet.add(action);
		}	
	}
	
	/**
	 *@requires action != null , curState != null
	 *@modified None
	 *@effects return next state by given action and current state according to the probabilities.
	 */
	@Override
	public State getNextStateByAction(Action action , State curState) {
		if(action == null || curState == null) {
			return null;
		}
		double range = 0;
		double randNum = rand.nextDouble();
		if(DEBUG_MODE) {
			System.out.println("\t the random number is : " + randNum);
		}
		Iterator<? extends State>iter = getStates();
		while(iter.hasNext()) {
			State dstState = iter.next();
			double curProb = getProbByActionAndStates(action, curState, dstState);
			if(curProb == 0) continue;
			if((randNum >= range) && (randNum <= (curProb + range))) {
				if(DEBUG_MODE) {
					System.out.println("\t the current prob is : " + curProb);
				}
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
		for(OSMstate state : statesSet) {
			str += state.toString() + "\n";		
		}	
		return str;
	}
}
