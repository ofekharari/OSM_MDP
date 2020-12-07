package OSM;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


import MDP.Action;
import MDP.MDPcalculator;
import MDP.MDPpolicy;
import OSM.OSMrewardTable.payingPolicy;
import OSM.OSMstate.fork;

/**
 * 
 * @author ofek harari & eldad wiener
 * The class OSMmdpGame represents an Optimal Selfish Mining MDP Game.
 * The class provides the upper and lower bounds for OSM MDP problem .
 * Based on Optimal Selfish Mining Strategies in Bitcoin paper by Ayelet Sapirshtein, Yonatan Sompolinsky and Aviv Zohar.
 *
 */
public class OSMmdpGame {
	private static int seed = 0;
	private static int NUM_ITER = 10000;
	private static int MAX_ADOPT = 10000;
	private static int CONST_NUM_ITER = 1000;
	private static int CONST_MAX_ADOPT = 1000;
	private Random rand = new Random(seed);
	private OSMstatesSet world = new OSMstatesSet();
	private MDPcalculator MyMDP;
	private static boolean DEBUG_MODE = false; 
	
	/**
	 * 
	 * The inner class OSMmdpPolicyBounds holds the revenue and the policy for over pay and under pay.
	 *
	 */
	public class PolicyBounds{
		public double upperBound;
		public double lowerBound;
		public MDPpolicy upperBoundPolicy;
		public MDPpolicy lowerBoundPolicy;
		
		/**
		 *@requires upperPolicy != null , lowerPolicy != null .
		 *@modified this.
		 *@effects create PolicyBounds.
		 */
		public PolicyBounds(double upper , double lower , MDPpolicy upperPolicy , MDPpolicy lowerPolicy) {
			this.upperBound = upper;
			this.lowerBound = lower;
			this.upperBoundPolicy = upperPolicy;
			this.lowerBoundPolicy = lowerPolicy;
		}
		@Override
		public String toString() {
			return "The upperBound is : " + upperBound + " , the lowerBound is : " + lowerBound ;
		}
	}
	
	/**
	 *@requires T is natural , 0 <= alpha, gamma <= 1 .
	 *@modified None
	 *@effects  create set of states with the specific parameters.
	 */
	public void createStateSet (int T , double alpha , double gamma) {
		world = new OSMstatesSet();
		for(int i = 0; i <= T; i++) {
			for(int j = 0; j <= T; j++) {
				if(i == 0 && j == 0) {
					continue;
				}
				for (fork myfork : fork.values()) {
					if(DEBUG_MODE) System.out.println(myfork);
					if((j == 0 && myfork != fork.IRRELEVANT) || (i == 0 && myfork == fork.ACTIVE) ) {
						continue;
					}
					world.addState(new OSMstate(i, j, myfork));
				}
			}
		}
		world.setProbTable(new OSMprobTable(alpha, gamma, T));
		world.addAction(new Action("Adopt"));
		world.addAction(new Action("Override"));
		world.addAction(new Action("Wait"));
		world.addAction(new Action("Match"));
	}
	
	/**
	 *@required  (0 < epsilonN < 1) , (0 < epsilonM < 8*alpha) , (T is natural) , (0 <= alpha,gamma <= 1).
	 *@modified this.
	 *@effects calculate the upper and lower bounds of the OSM problem.
	 */	
	public PolicyBounds calcBounds(int T , double alpha , double gamma , double epsilonM , double epsilonN, boolean eps_adapt) {
		//create the optimal selfish mining world
		createStateSet(T, alpha , gamma);
		//init the binary search params
		double low = 0;
		double high = 1;
		double rho;
		MyMDP = new MDPcalculator(world, 0.999);
		MDPpolicy lowerBoundPolicy;
		MDPpolicy upperBoundPolicy;
		int iter = 0;
		double currEpsilonM = epsilonM;
		if (eps_adapt) {
			currEpsilonM = 0.1; 
		} 
		do {
			if ((iter % 5 == 0) && eps_adapt) {
				currEpsilonM /= 10;
			}
			rho = (low + high)/2;
			if(DEBUG_MODE) {
				System.out.println("number of iter is : " + iter);
				System.out.println("\t high is : " + high + " , low is : " + low + " , rho is : " + rho);
			}

			world.setRewardTable(new OSMrewardTable(rho,T,alpha,payingPolicy.Mrho));
			lowerBoundPolicy = MyMDP.calcPiStar(currEpsilonM/8);
			double v = calcV(alpha,lowerBoundPolicy);
			if(v > 0) {
				low = rho;
			}
			else {
				high = rho;
			}
			iter++;
		} while((high - low) >= (epsilonM/8));
		int T_n = (int) (T);
		calcBounds_itr_num = iter;
		double lowerBound = rho - epsilonM;
		double rhoTag = ((low - (epsilonM/4)) > 0)? (low - (epsilonM/4)) : 0;
		
		createStateSet(T_n, alpha , gamma);
		MyMDP = new MDPcalculator(world, 0.98);
		world.setRewardTable(new OSMrewardTable(rhoTag,T_n,alpha,payingPolicy.Nrho));
		
		upperBoundPolicy = MyMDP.calcPiStar(epsilonN);
		calcBounds_itr_num++;
		double v = calcV(alpha,upperBoundPolicy);
		double upperBound = (rhoTag + 2*(v + epsilonN));
		return new PolicyBounds(upperBound , lowerBound , upperBoundPolicy , lowerBoundPolicy);
	}

	/**
	 *@requires policy != null , 0 <= alpha <= 1 .
	 *@modified None
	 *@effects  return the expected revenue of the policy .
	 */
	private double calcV(double alpha, MDPpolicy policy) {
		double expReward = 0;
		for(int i = 0; i < NUM_ITER; i++) {
			double totalReward = 0;
			OSMstate curState = null;
			if(rand.nextDouble() < alpha ) {
				curState = new OSMstate(1, 0, fork.IRRELEVANT);
			}
			else {
				curState = new OSMstate(0, 1, fork.IRRELEVANT);
			}
			int adoptCounter = 0;
			int steps = 0;
			while(adoptCounter < MAX_ADOPT) {
				Action nextAction = policy.getActionByState(curState);
				OSMstate nextState = (OSMstate)world.getNextStateByAction(nextAction, curState);
				double curRward = world.getRewardByActionAndStates(nextAction, curState, nextState);
				totalReward +=curRward;
				curState = nextState;
				steps ++;
				if (nextAction.equals(new Action("Adopt"))){
					adoptCounter++;
				}
			}
			expReward += totalReward/steps;
		}
		expReward /= NUM_ITER;
		if(DEBUG_MODE) {
			System.out.println("the expectation is : " + expReward);
		}
		return expReward;
	}

	/**
	 *@requires  (T is natural) , (0 <= alpha,gamma <= 1) , 0 < rho < 1 ,  payPolicy != null .
	 *@modified None
	 *@effects  return the rewards array.
	 */
	public double[] getrewardsArr(int T , double alpha , double gamma , double rho , payingPolicy payPolicy) {
		Random rand = new Random(seed);
		OSMstatesSet world = new OSMstatesSet();
		for(int i = 0; i <= T; i++) {
			for(int j = 0; j <= T; j++) {
				if(i == 0 && j == 0) {
					continue;
				}
				for (fork myfork : fork.values()) {
					if(DEBUG_MODE) System.out.println(myfork);
					if((j == 0 && myfork != fork.IRRELEVANT) || (i == 0 && myfork == fork.ACTIVE) ) {
						continue;
					}
					world.addState(new OSMstate(i, j, myfork));
				}
			}
		}
		world.setProbTable(new OSMprobTable(alpha, gamma, T));
		world.setRewardTable(new OSMrewardTable(rho,T,alpha,payPolicy));
		//world.getState(new OSMstateName(2,2,fork.RELEVANT)).setTerminationState();
		
		world.addAction(new Action("Adopt"));
		world.addAction(new Action("Override"));
		world.addAction(new Action("Wait"));
		world.addAction(new Action("Match"));
		
		double [] rewardsArr  = new double [NUM_ITER+1];
		double expReward = 0;
		MDPcalculator MyMDP = new MDPcalculator(world, 0.98 , 0.000000000001);
		long startTime = System.currentTimeMillis();
		if(DEBUG_MODE) {
			System.out.println("start calcPistar");
		}
		MDPpolicy policy = MyMDP.calcPiStar();
		long afterCalcTime = System.currentTimeMillis() - startTime;
		if(DEBUG_MODE) {
			System.out.println("Time to calc policy is : " + afterCalcTime);
		}
		world.setSeed(seed);
		
		for(int i = 0; i < NUM_ITER; i++) {
			double totalReward = 0;
			OSMstate curState = null;
			if(rand.nextDouble() < alpha ) {
				curState = new OSMstate(1, 0, fork.IRRELEVANT);
			}
			else {
				curState = new OSMstate(0, 1, fork.IRRELEVANT);
			}
			int adoptCounter = 0;
			int steps = 0;
			while(adoptCounter < MAX_ADOPT) {
				Action nextAction = policy.getActionByState(curState);
				OSMstate nextState = (OSMstate)world.getNextStateByAction(nextAction, curState);
				double curRward = world.getRewardByActionAndStates(nextAction, curState, nextState);
				totalReward +=curRward;
				curState = nextState;
				steps ++;
				if (nextAction.equals(new Action("Adopt"))){
					adoptCounter++;
				}
			}
			//System.out.println("for iterations " + i + " the number of steps is : " + steps);
			rewardsArr[i] = totalReward/steps;
			expReward += rewardsArr[i];
		}
		rewardsArr [NUM_ITER] = expReward/NUM_ITER;
		long endTime = System.currentTimeMillis() - afterCalcTime;
		if(DEBUG_MODE) {
			System.out.println("Time to find expection is : " + endTime);
		}
		return rewardsArr;	
	}
	
	public static void main(String[] args) {
		double epsilon = 0.1;
		boolean printWorldStats = true;
		int T = 10;
		int EPS = 5;
		boolean[] epsilon_adaptive = {true, false};
		for (int j = 1; j <= 10; j += 9) {
			NUM_ITER = CONST_NUM_ITER*j;
			MAX_ADOPT = CONST_MAX_ADOPT*j;
			System.out.println("*******NUM_ITER=" + NUM_ITER + ".****MAX_ADOPT=" + MAX_ADOPT + ".****************\n");
			for (boolean eps_state : epsilon_adaptive) {
				epsilon =  Math.pow(10, -EPS);
				System.out.println("************************************\n");
				System.out.println("------------epsilon=" + epsilon + "-------------\n");
				System.out.println("************************************\n");
				System.out.println("-----------------------------------\n");
				System.out.println("----------------T=" + T + "----------------\n");
				System.out.println("-----------------------------------\n");
				long startTime = System.currentTimeMillis();
				long estimatedTime = 0;
				OSMmdpGame game = new OSMmdpGame();
				double alpha = (double)1/3;
				String upper = "";
				String lower = "";
				PolicyBounds bounds = game.calcBounds(T , alpha , 0 , epsilon , 0.00001, eps_state);
				estimatedTime = System.currentTimeMillis() - startTime;
				upper = Double.toString(bounds.upperBound);
				lower = Double.toString(bounds.lowerBound);
				System.out.println("for alpha = " + alpha + ":");
				System.out.println("\tLower bound = " + lower);
				System.out.println("\tUpper bound = " + upper);
				System.out.println("Total run time (sec) = " + (estimatedTime/1000.)+ "\n");
			}
		}

	}
}

