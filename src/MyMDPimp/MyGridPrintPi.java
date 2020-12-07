package MyMDPimp;

import java.util.HashMap;
import java.util.Map;

import MDP.Action;
import MDP.State;
import MDPinterfaces.PrintPi;

public class MyGridPrintPi implements PrintPi {
	private final int len;
	private final int high;
	private Map<Action, String> actionMap = new HashMap<>();
	
	public MyGridPrintPi(int len , int high) {
		this.len = len;
		this.high = high;
		actionMap.put(new Action("up"), "U");
		actionMap.put(new Action("down"), "D");
		actionMap.put(new Action("right"), "R");
		actionMap.put(new Action("left"), "L");
	}
	
	@Override
	public void printPi(Map<State, Action> pi) {
		for(int y = high -1 ; y >= 0 ; --y) {
			for(int x = 0 ; x < len ; x++) {
				System.out.print("|" + actionMap.get(pi.get(new MyState(x, y,false))));
			}
			System.out.println("|");
			if(y > 0) {
				for(int x = 0 ; x < len ; x++) {
					System.out.print("--");
				}
				System.out.println("-");
			}
		}
	}

}
