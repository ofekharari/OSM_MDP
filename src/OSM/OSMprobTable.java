package OSM;

import java.util.HashMap;
import java.util.Map;

import MDP.Action;
import MDP.State;
import MDPinterfaces.ProbTable;
import OSM.OSMstate.fork;

/**
 * 
 * @author ofek harari & eldad wiener
 * The class OSMprobTable represents the probability map of OSM MDP Game.
 * The class holds : set of action , T , alpha and gamma.
 * Based on Optimal Selfish Mining Strategies in Bitcoin paper by Ayelet Sapirshtein, Yonatan Sompolinsky and Aviv Zohar.
 *
 */
public class OSMprobTable implements ProbTable {
	private final double alpha;
	private final double gamma;
	private final int T;
	private Map<Action, String> actionMap = new HashMap<>();
	
	/**
	 *@requires None.
	 *@modified this.
	 *@effects  create new OSMprobTable state.
	 */
	public OSMprobTable(double alpha , double gamma , int T) {
		this.alpha = alpha;
		this.gamma = gamma;
		this.T = T;
		actionMap.put(new Action("Adopt"), "Adopt");
		actionMap.put(new Action("Override"), "Override");
		actionMap.put(new Action("Wait"), "Wait");
		actionMap.put(new Action("Match"), "Match");
	}
	
	/**
	 *@requires action,srcState,dstState != null.
	 *@modified None
	 *@effects return probability to move from src state to dst state with action.
	 */
	@Override
	public double getProbByActionAndStates(Action action, State srcState, State dstState) {
		int aSrc = ((OSMstate)srcState).getChainsParams()[0];
		int hSrc = ((OSMstate)srcState).getChainsParams()[1];
		fork forkSrc = ((OSMstate)srcState).getFork();
		if(aSrc == T || hSrc == T) {
			if(actionMap.get(action) == "Adopt") {
				if(dstState.equals(new OSMstate(1, 0, fork.IRRELEVANT))) {
					return alpha;
				}
				else if (dstState.equals(new OSMstate(0, 1, fork.IRRELEVANT))) {
					return 1 - alpha;
				}
			}
			else {
				return -1;
			}
		}
		if(actionMap.get(action) == "Adopt") {
			if(dstState.equals(new OSMstate(1, 0, fork.IRRELEVANT))) {
				return alpha;
			}
			else if (dstState.equals(new OSMstate(0, 1, fork.IRRELEVANT))) {
				return 1 - alpha;
			}
			else {
				return 0;
			}
		}
		else if((actionMap.get(action) == "Override")) {
			if(aSrc > hSrc) {
				if(dstState.equals(new OSMstate(aSrc - hSrc, 0, fork.IRRELEVANT))){
					return alpha;
				}
				else if(dstState.equals(new OSMstate(aSrc - hSrc - 1, 1, fork.RELEVANT))) {
					return 1 - alpha;
				}
				else {
					return 0;
				}
			}
		}
		else if((actionMap.get(action) == "Match") && (aSrc >= hSrc && forkSrc == fork.RELEVANT) ||
				(actionMap.get(action) == "Wait") && (forkSrc == fork.ACTIVE)) {
			if(dstState.equals(new OSMstate(aSrc + 1 , hSrc , fork.ACTIVE))) {
				return alpha;
			}
			else if(dstState.equals(new OSMstate(aSrc - hSrc , 1 , fork.RELEVANT))) {
				return gamma * (1 - alpha);
			}
			else if(dstState.equals(new OSMstate(aSrc , hSrc + 1 , fork.RELEVANT))) {
				return (1 - gamma) * (1 - alpha);
			}
			else {
				return 0;
			}
		}
		else if((actionMap.get(action) == "Wait") && (forkSrc == fork.RELEVANT || forkSrc == fork.IRRELEVANT)) {
			if(dstState.equals(new OSMstate(aSrc + 1 , hSrc , fork.IRRELEVANT))) {
				return alpha;
			}
			else if(dstState.equals(new OSMstate(aSrc , hSrc + 1 , fork.RELEVANT))) {
				return 1 - alpha;
			}
			else {
				return 0;
			}
		}
		// non feasible state action
		return -1;
	}
}
	