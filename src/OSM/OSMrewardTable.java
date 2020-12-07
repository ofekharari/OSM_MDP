package OSM;

import java.util.HashMap;
import java.util.Map;

import MDP.Action;
import MDP.State;
import MDPinterfaces.RewardTable;
import OSM.OSMstate.fork;

/**
 * 
 * @author ofek harari & eldad wiener
 * The class OSMrewardTable represents the reward map of OSM MDP Game.
 * The class holds : set of action , T , alpha and payingPolicy.
 * Based on Optimal Selfish Mining Strategies in Bitcoin paper by Ayelet Sapirshtein, Yonatan Sompolinsky and Aviv Zohar.
 *
 */
public class OSMrewardTable implements RewardTable {
	private double rho;
	private Map<Action, String> actionMap = new HashMap<>();
	private final int T;
	private final double alpha;
	enum payingPolicy { Mrho , Nrho };
	private final payingPolicy policy;
	
	/**
	 *@requires None.
	 *@modified this.
	 *@effects  create new OSMrewardTable state.
	 */
	public OSMrewardTable(double rho, int T, double alpha, payingPolicy policy) {
		this.rho = rho;
		this.T = T;
		this.alpha = alpha;
		this.policy = policy;
		actionMap.put(new Action("Adopt"), "Adopt");
		actionMap.put(new Action("Override"), "Override");
		actionMap.put(new Action("Wait"), "Wait");
		actionMap.put(new Action("Match"), "Match");
	}
	
	/**
	 *@requires None.
	 *@modified None.
	 *@effects  calculate the reward.
	 */
	private double calReward(int x , int y) {
		return (1 - rho) * x - rho * y;
	}
	
	/**
	 *@requires None.
	 *@modified None.
	 *@effects  calculate TruncatingState reward.
	 */
	private double calcTruncatingStateReward(int aSrc , int hSrc) {
		if(policy == payingPolicy.Mrho) {
			return calReward(0, hSrc);
		}
		else {
			if(aSrc >= hSrc) {
				return (1 - rho)*((alpha * (1 - alpha))/(Math.pow(1 - (2*alpha), 2))) +
						0.5*(((aSrc-hSrc)/(1-2*alpha))+ aSrc + hSrc);
			}
			else {
				return (1 - Math.pow((alpha/(1-alpha)), hSrc - aSrc))*(-1 * rho * hSrc) + 
						Math.pow((alpha/(1-alpha)), hSrc - aSrc) * (1 - rho) * 
						((alpha*(1-alpha)/Math.pow(1-(2*alpha), 2)) + ((hSrc - aSrc)/(1-2*alpha)));               
			}
		}

	}
	
	/**
	 *@requires action,srcState,dstState != null.
	 *@modified None.
	 *@effects  return reward to move from src state to dst state with action
	 */
	@Override
	public double getRewardByActionAndStates(Action action, State srcState, State dstState) {
		
		int aSrc = ((OSMstate)srcState).getChainsParams()[0];
		int hSrc = ((OSMstate)srcState).getChainsParams()[1];
		fork forkSrc = ((OSMstate)srcState).getFork();
		if(aSrc == T || hSrc == T) {
			return calcTruncatingStateReward(aSrc , hSrc);
		}
		if(actionMap.get(action) == "Adopt") {
			return calReward(0, hSrc);
		}
		else if((actionMap.get(action) == "Override")) {
			return calReward(hSrc + 1, 0 );
		}
		else if((actionMap.get(action) == "Match") && (aSrc >= hSrc && forkSrc == fork.RELEVANT) ||
				(actionMap.get(action) == "Wait") && (forkSrc == fork.ACTIVE)) {
			if(dstState.equals(new OSMstate(aSrc + 1 , hSrc , fork.ACTIVE))) {
				return calReward(0,0);
			}
			else if(dstState.equals(new OSMstate(aSrc - hSrc , 1 , fork.RELEVANT))) {
				return calReward(hSrc,0);
			}
			else if(dstState.equals(new OSMstate(aSrc , hSrc + 1 , fork.RELEVANT))) {
				return calReward(0,0);
			}
		}
		else if((actionMap.get(action) == "Wait") && (forkSrc == fork.RELEVANT || forkSrc == fork.IRRELEVANT)) {
			return calReward(0,0);
		}
		// non feasible state action
		return Integer.MIN_VALUE;
	}
}
