package MyMDPmaze;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import MDP.Action;
import MDP.State;
import MDPinterfaces.PrintPi;

public class MyMazePrintPi implements PrintPi {
	private final int len;
	private final int high;
	private Map<Action, String> actionMap = new HashMap<>();
	private ArrayList<ArrayList<Character>> gameBoard;
	
	public MyMazePrintPi(int len , int high, ArrayList<ArrayList<Character>> gameBoard) {
		this.len = len;
		this.high = high;
		this.gameBoard = gameBoard;
		actionMap.put(new Action("up"), "u");
		actionMap.put(new Action("down"), "d");
		actionMap.put(new Action("right"), "r");
		actionMap.put(new Action("left"), "l");
	}
	
	@Override
	public void printPi(Map<State, Action> pi) {
		int line = 1;
		for(int y = high -1 ; y >= 0 ; --y) {
			ArrayList<Character> gameLine = gameBoard.get(line);
			for(int x = 0 ; x < len ; x++) {
				char ch = gameLine.get((x * 2) + 3);
				if ((ch == ' ') || (ch == '_')) {
					gameLine.set((x * 2) + 3, (actionMap.get(pi.get(new MyMazeState(x, y)))).charAt(0));
				}
			}
			line++;
		}
		for(ArrayList<Character> gameLine : gameBoard) {
			for(Character c: gameLine) {
				System.out.print(c);
			}
			System.out.println("");
		}
		System.out.println("");
	}
	
	

}
