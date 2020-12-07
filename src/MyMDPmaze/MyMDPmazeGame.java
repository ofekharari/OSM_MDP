package MyMDPmaze;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import MDP.Action;
import MDP.MDPcalculator;
import MDP.MDPpolicy;
import MyMDPimp.MyRewardTable;

public class MyMDPmazeGame {
	private static double gamma = 0.97;
	public class Pair{
		private final int num1;
		private final int num2;
		
		public Pair(int num1 , int num2) {
			this.num1 = num1;
			this.num2 = num2;
		}
		@Override
		public boolean equals(Object o) {
	        // If the object is compared with itself then return true   
	        if (o == this) { 
	            return true; 
	        } 
	        /* Check if o is an instance of Pair or not 
	          "null instanceof [type]" also returns false */
	        if (!(o instanceof Pair)) { 
	            return false; 
	        } 
	          
	        // typecast o to Complex so that we can compare data members  
	        Pair rhs = (Pair) o; 
	          
	        // Compare the data members and return accordingly  
	        return ((this.num1 == rhs.num1) && (this.num2 == rhs.num2));
	    }
		
		@Override
		public int hashCode() {
			return num1+num2;
		}
	}
	/*
	public void simpleNxNmazeGame(int dim) {
		MyMazeStatesSet world = new MyMazeStatesSet();
		int len = dim;
		int high = dim;
		for(int i = 0; i < len; i++) {
			for(int j = 0; j < high; j++) {
				world.addState(new MyMazeState(i,j,j != high-1, j != 0 , i != len-1 , i != 0, false));
			}
		}
		world.setProbTable(new MyMazeProbTable());
		world.setRewardTable(new MyRewardTable(new MyMazeStateName (len - 1, high - 1),
											   null ,
											   new MyMazeStateName(len - 1, high - 2)));
		world.getState(new MyMazeStateName (len - 1, high - 1)).setTerminationState();
		world.getState(new MyMazeStateName (len - 1, high - 2)).setTerminationState();
		
	
		world.addAction(new Action("up"));
		world.addAction(new Action("left"));
		world.addAction(new Action("down"));
		world.addAction(new Action("right"));
		
		for(int i = 0; i < 1; i++) {
			world.setSeed(i);
			double totalReward = 0;
			MDPcalculator MyMDP = new MDPcalculator(world, 0.98);
			MDPpolicy policy = MyMDP.calcPiStar();
			policy.setPrintPi(new MyMazePrintPi(5, 5, null));//TODO fix it
			policy.printPi();
			StateName curState = new MyMazeStateName(0,0);
			int iter = 0;
			while(world.getState(curState).isTerminationState() == false) {
				//System.out.println("In iteration : " + iter );
				Action nextAction = policy.getActionByState(curState);
				StateName nextState = world.getNextStateByAction(nextAction, curState);
				totalReward += world.getRewardByActionAndStates(nextAction, curState, nextState);
				//System.out.println( "\t The current state is : " + curState);
				//System.out.println( "\t The next action is : " + nextAction);
				//System.out.println( "\t The next state is : " + nextState);
				curState = nextState;
				//System.out.println("\t The total reward is : " + totalReward);
				iter ++;
				//if (iter > 20) break;
			}
			System.out.println("The total reward is : " + totalReward);
		}
		
	}
	*/
	
	public void NxMmazeGame(int len, int high, MyMazeState initState) {
		MyMazeStatesSet world = new MyMazeStatesSet();
		int factor = len*high;
		Random rand = new Random();
		Set <Pair> blocks = new HashSet<>();
		for(int i = 0; i < len; i++) {
			for(int j = 0; j < high; j++) {
				for(int k = 0; k < 2; k++) {
					double number = rand.nextDouble();
					switch (k) {
					case 0 : //up
						if(number > 0.8) { //TODO change to 0.8
							//System.out.println(" BLOCK UP (" + i + " , " + j + ")");
							blocks.add(new Pair(i*factor + j, i*factor + j + 1));
						}
						break;
					case 1 : //right
						if(number > 0.8) { //TODO change to 0.8
							//System.out.println(" BLOCK RIGHT (" + i + " , " + j + ")");
							blocks.add(new Pair(i*factor + j, (i + 1)*factor +j));
						}
						break;
					}
				}
			}
		}
		blocks.remove(new Pair((len-2)*factor + high-1 , (len-1)*factor + high-1));
		//System.out.println(blocks.contains(new Pair((len-2)*factor + high-1 , (len-1)*factor + high-1)));
		ArrayList<ArrayList<Character>> gameBoard = new ArrayList<>();
		gameBoard.add(new ArrayList<Character>());
		int line = 0;
		gameBoard.get(line).add(' ');
		gameBoard.get(line).add(' ');
		gameBoard.get(line).add(' ');
		System.out.print("  ");
		for(int i = 0; i < len; i++) {
			System.out.print(" _");
			gameBoard.get(line).add('_');
			gameBoard.get(line).add(' ');
		}
		System.out.println("");
		gameBoard.add(new ArrayList<Character>());
		line++;
		for(int j = high - 1; j >= 0; --j) {
			System.out.print(j + " ");
			gameBoard.get(line).add((char)('0' + j));
			gameBoard.get(line).add(' ');
			for(int i = 0; i < len; i++) {
				boolean blockUp = (j != high - 1) && !blocks.contains(new Pair(i * factor + j, i * factor + j + 1));
				boolean blockDown = (j != 0) && !blocks.contains(new Pair(i * factor + j - 1, i * factor + j));
				boolean blockRight = (i != len - 1) && !blocks.contains(new Pair(i * factor + j , (i + 1) * factor + j));
				boolean blockLeft = (i != 0) && !blocks.contains(new Pair((i - 1) * factor + j , i * factor + j));
				if(!blockLeft) {
					System.out.print("|");
					gameBoard.get(line).add('|');
				}else {
					System.out.print(" ");
					gameBoard.get(line).add(' ');
				}
				if(!blockDown) {
					System.out.print("_");
					gameBoard.get(line).add('_');
				}else {
					System.out.print(" ");
					gameBoard.get(line).add(' ');
				}
				if(i == len-1) {
					System.out.println("|");
					gameBoard.get(line).add('|');
					gameBoard.add(new ArrayList<Character>());
					line++;
				}
				world.addState(new MyMazeState(i,j, blockUp, blockDown, blockRight , blockLeft, false));
			}
		}
		gameBoard.get(line).add(' ');
		gameBoard.get(line).add(' ');
		gameBoard.get(line).add('-');
		for(int i = 0; i < len; i++) {
			gameBoard.get(line).add('-');
			gameBoard.get(line).add('-');
		}
		gameBoard.add(new ArrayList<Character>());
		line++;
		gameBoard.get(line).add(' ');
		gameBoard.get(line).add(' ');
		gameBoard.get(line).add(' ');
		for(int i = 0; i < len; i++) {
			gameBoard.get(line).add((char)('0' + i));
			gameBoard.get(line).add(' ');
		}
		System.out.print("  ");
		for(int i = 0; i < len; i++) {
			System.out.print(" " + i);
		}
		System.out.println("\n");
		gameBoard.get(1).set((4 * 2) + 3, 'V');
		gameBoard.get(2).set((4 * 2) + 3,'X');
		gameBoard.get(6).set(3, '^');
		for(ArrayList<Character> gameLine : gameBoard) {
			for(Character c: gameLine) {
				System.out.print(c);
			}
			System.out.println("");
		}
		System.out.println("");
		world.setProbTable(new MyMazeProbTable());
		world.setRewardTable(new MyRewardTable(new MyMazeState (len - 1, high - 1),
											   new MyMazeState (len / 2, high / 2) ,
											   new MyMazeState(len - 1, high - 2)));
		((MyMazeState)world.findState(new MyMazeState (len - 1, high - 1))).setTerminationState();
		((MyMazeState)world.findState(new MyMazeState (len - 1, high - 2))).setTerminationState();
		
		world.addAction(new Action("up"));
		world.addAction(new Action("left"));
		world.addAction(new Action("down"));
		world.addAction(new Action("right"));
		
		for(int i = 0; i < 3; i++) {
			world.setSeed(i+15);
			double totalReward = 0;
			MDPcalculator MyMDP = new MDPcalculator(world, gamma);
			MDPpolicy policy = MyMDP.calcPiStar();
			policy.setPrintPi(new MyMazePrintPi(5, 5, gameBoard));
			policy.printPi();
			MyMazeState curState = initState;
			int iter = 0;
			while(((MyMazeState)world.findState(curState)).isTerminationState() == false) {
				System.out.println("In iteration : " + iter );
				Action nextAction = policy.getActionByState(curState);
				MyMazeState nextState = (MyMazeState)world.getNextStateByAction(nextAction, curState);
				totalReward += world.getRewardByActionAndStates(nextAction, curState, nextState);
				System.out.println( "\t The current state is : " + curState);
				//System.out.println( "\t The next action is : " + nextAction);
				System.out.println( "\t The next state is : " + nextState);
				curState = nextState;
				//System.out.println("\t The total reward is : " + totalReward);
				iter ++;
				//if (iter > 20) break;
			}
			System.out.println("The total reward is : " + totalReward);
		}
	}
	
	/*
	public void mySimple3on3Game() {
		MyStatesSet world = new MyStatesSet();
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				world.createState(new MyStateName(i,j));
			}
		}
		world.setProbTable(new MyProbTable(3,3));
		world.setRewardTable(new MyRewardTable(new MyStateName (2,2), new MyStateName (1,1) , new MyStateName (2,1)));
		world.getState(new MyStateName(2,2)).setTerminationState();
		world.getState(new MyStateName(2,1)).setTerminationState();
		
	
		world.addAction(new Action("up"));
		world.addAction(new Action("left"));
		world.addAction(new Action("down"));
		world.addAction(new Action("right"));
		
		for(int i = 0; i < 1; i++) {
			world.setSeed(i);
			double totalReward = 0;
			MDPcalculator MyMDP = new MDPcalculator(world , 0.5);
			MyMDP.setPrintPi(new MyGridPrintPi(3, 3));
			MyMDP.printPi();
			StateName curState = new MyStateName(0,0);
			int iter = 0;
			while(world.getState(curState).isTerminationState() == false) {
				//System.out.println("In iteration : " + iter );
				Action nextAction = MyMDP.getActionByState(curState);
				StateName nextState = world.getNextStateByAction(nextAction, curState);
				totalReward += world.getRewardByActionAndStates(nextAction, curState, nextState);
				//System.out.println( "\t The current state is : " + curState);
				//System.out.println( "\t The next action is : " + nextAction);
				//System.out.println( "\t The next state is : " + nextState);
				curState = nextState;
				//System.out.println("\t The total reward is : " + totalReward);
				iter ++;
				//if (iter > 20) break;
			}
			System.out.println("The total reward is : " + totalReward);
		}
		
	}*/
	
	public static void main(String[] args) {
		MyMDPmazeGame game = new MyMDPmazeGame();
		game.NxMmazeGame(5,5,new MyMazeState(0, 1));
	}
	
}

