package MyMDPimp;

import java.util.ArrayList;

import MDP.Action;
import MDP.MDPcalculator;
import MDP.MDPpolicy;

public class MyMDPgame {
	private static double gamma = 0.98;
	
	public void myGame(int high, int width, MyState initState, ArrayList<MyState>termStatesArr,
						MyState goodState, MyState mediumState, MyState badState) {
		MyStatesSet world = new MyStatesSet();
		for(int i = 0; i < high; i++) {
			for(int j = 0; j < width; j++) {
				MyState state = createState(i, j);
				if(termStatesArr.contains(state)) {
					world.addState(new MyState(i,j,true));
				}
				else {
					world.addState(new MyState(i,j,false));
				}
			}
		}
		world.setProbTable(new MyProbTable2(high,width));
		world.setRewardTable(new MyRewardTable(goodState, mediumState, badState));
		world.addAction(new Action("up"));
		world.addAction(new Action("left"));
		world.addAction(new Action("down"));
		world.addAction(new Action("right"));
		
		for(int i = 0; i < 1; i++) {
			world.setSeed(i);
			double totalReward = 0;
			MDPcalculator MyMDP = new MDPcalculator(world , gamma);
			MDPpolicy policy = MyMDP.calcPiStar();
			policy.setPrintPi(new MyGridPrintPi(high, width));
			policy.printPi();
			MyState curState = initState;
			int iter = 0;
			while(((MyState)world.findState(curState)).isTerminationState() == false) {
				System.out.println("In iteration : " + iter );
				Action nextAction = policy.getActionByState(curState);
				MyState nextState = (MyState) world.getNextStateByAction(nextAction, curState);
				totalReward += world.getRewardByActionAndStates(nextAction, curState, nextState);
				System.out.println( "\tThe current state is : " + curState);
				//System.out.println( "\t The next action is : " + nextAction);
				System.out.println( "\tThe next state is : " + nextState);
				curState = nextState;
				//System.out.println("\t The total reward is : " + totalReward);
				iter ++;
			}
			System.out.println("The total reward is : " + totalReward);
		}
	}
	
	public static MyState createState(int xCord, int yCord) {
		return new MyState(xCord, yCord, false);
	}

	public static void main(String[] args) {
		MyMDPgame game = new MyMDPgame();
		ArrayList<MyState> termStatesArr = new ArrayList<>();
		termStatesArr.add(createState(3, 3));
		termStatesArr.add(createState(2, 2));
		game.myGame(4, 4, createState(0, 0),termStatesArr, createState(3, 3),createState(2, 1),createState(2, 2));
	}
}

