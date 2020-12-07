package MyMDPmonopoly;

import MDP.Action;
import MDP.MDPcalculator;
import MDP.MDPpolicy;

public class MyMDPmonoGame {
	public static double gamma = 0.98;
	public static int CUBES_NUM = 3; 
	public static int CUBE_SIDE = 3;
	
	public void simpleMonoGame(int numStates, int seed) {
		MyMonoStatesSet world = new MyMonoStatesSet();
		for(int i = 0 ; i <= numStates; i++) {
			world.addState(new MyMonoState(i,i == numStates));
		}
		world.setProbTable(new MyMonoProbTable(CUBES_NUM ,CUBE_SIDE));
		world.setRewardTable(new MyMonoRewardTable(numStates , seed));
		for (int i = 1; i <= CUBES_NUM; i++) {
			world.addAction(new Action(Integer.toString(i)));
		}
		for(int i = 0; i < 1; i++) {
			world.setSeed(i);
			double totalReward = 0;
			MDPcalculator MyMDP = new MDPcalculator(world, gamma);
			MDPpolicy policy = MyMDP.calcPiStar();
			policy.setPrintPi(new MyMonoPrintPi());
			policy.printPi();
			MyMonoState curState = new MyMonoState(0);
			int iter = 0;
			while(((MyMonoState)world.findState(curState)).isTerminationState() == false) {
				System.out.println("In iteration : " + iter );
				Action nextAction = policy.getActionByState(curState);
				MyMonoState nextState = (MyMonoState)world.getNextStateByAction(nextAction, curState);
				totalReward += world.getRewardByActionAndStates(nextAction, curState, nextState);
				System.out.println( "\t The current state is : " + curState);
				System.out.println( "\t The next action is : " + nextAction);
				System.out.println( "\t The next state is : " + nextState);
				curState = nextState;
				//System.out.println("\t The total reward is : " + totalReward);
				iter ++;
				//if (iter > 20) break;
			}
			System.out.println("The total reward is : " + totalReward);
		}
	}

	public static void main(String[] args) {
		MyMDPmonoGame game = new MyMDPmonoGame();
		CUBES_NUM = 2;
		game.simpleMonoGame(16,1);
	}
	
}

