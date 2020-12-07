package MyMDPmonopoly;

import java.util.Map;

import MDP.Action;
import MDP.State;
import MDPinterfaces.PrintPi;

public class MyMonoPrintPi implements PrintPi {
	/*private final int statesNum;
	private final int cubesNum;
	
	public MyMonoPrintPi(int statesNum ,int cubesNum) {
		this.statesNum = statesNum;
		this.cubesNum = cubesNum;
	}
	*/
	@Override
	public void printPi(Map<State, Action> pi) {
		System.out.println("Pi mapping is :  ");
		for(Map.Entry<State , Action> entry : pi.entrySet()) {
			System.out.println("\tfor state " + entry.getKey() + " " + entry.getValue());
		}
	}
}
