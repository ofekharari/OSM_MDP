package MDP;


import java.util.Iterator;

import MDPinterfaces.StatesSet;

/**
 * 
 * @author ofek harari & eldad wiener
 * The class MDPcalculator represents a MDP solver.
 * The class receives set of states, gamma and epsilon. provides an optimal policy.
 *
 */
public class MDPcalculator {
	private final double gamma;
	private double epsilon;
	private StatesSet world;
	private static boolean DEBUG_MODE = false;
	
	/**
	 *@requires world != null , 0 <= gamma, epsilon <=1  .
	 *@modified this
	 *@effects create new MDPcalculator with world, gamma and epsilon.
	 */		
	public MDPcalculator(StatesSet world, double gamma , double epsilon) {
		this.epsilon = epsilon;
		this.gamma = gamma;
		this.world = world;
	}
	
	/**
	 *@requires world != null , 0 <= gamma <=1  .
	 *@modified this
	 *@effects create new MDPcalculator with world and gamma. epsilon = 0.
	 */	
	public MDPcalculator(StatesSet world, double gamma) {
		this.epsilon = 0;
		this.gamma = gamma;
		this.world = world;
	}
	
	/**
	 *@requires  0 <= epsilon <=1  .
	 *@modified None
	 *@effects return optimal policy for the set of states and the given epsilon .
	 */	
	public MDPpolicy calcPiStar(double epsilon) {
		double tmpEps = this.epsilon;
		this.epsilon = epsilon;
		MDPpolicy policy = calcPiStar();
		this.epsilon = tmpEps;
		return policy;
	}
	
	/**
	 *@requires None
	 *@modified None
	 *@effects return optimal policy for the set of states .
	 */	
	public MDPpolicy calcPiStar() {
		
		MDPpolicy policy = new MDPpolicy();
		MDPvalue value = new MDPvalue();
		value.initValue(world.getStates());
		int iter = 0;
		while(!value.isVconverge(epsilon)) {
			value.updateOldValues();
			Iterator<? extends State>srcIter = world.getStates();
			while(srcIter.hasNext()) {
				State srcState = srcIter.next();
				for(Action action : world.getActions()) {
					double sum = 0;
					Iterator<? extends State>dstIter = world.getStates();
					while(dstIter.hasNext()) {
						State dstState = dstIter.next();
						double curProb = world.getProbByActionAndStates(action, srcState, dstState);
						if(curProb == -1) {
							sum = Double.NEGATIVE_INFINITY;
							break;
						}
						double curReward = world.getRewardByActionAndStates(action, srcState, dstState);
						double curVOld = value.getStateOldVal(dstState);
						sum += curProb * (curReward + (gamma * curVOld));
					}
					if(sum > value.getStateNewVal(srcState) || value.isFirstState(srcState)) {
						value.setStateNewVal(srcState, sum);
						value.setFirstState(srcState, false);
						policy.updatePolicy(srcState, action);
					}
				}
			}
			if(DEBUG_MODE) {
				System.out.println("the iteration is : " + iter);
			}
			iter++;
		}
		if(DEBUG_MODE) {
			System.out.println("the number of iteration for calc Pi* is : " + iter);
		}
		return policy;
	}
}
