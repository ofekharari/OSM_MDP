package MDP;

import java.util.HashMap;
import java.util.Map;
import MDPinterfaces.PrintPi;

/**
 * 
 * @author ofek harari & eldad wiener
 * The class MDPpolicy represents a MDP policy.
 * The class allows to query about states policy and allows to print the policy.
 *
 */
public class MDPpolicy {
	private Map<State,Action> policy = new HashMap<>();
	private PrintPi printPi= new PrintPi() {
		public void printPi(Map<State,Action> pi) {
			System.out.println(pi);
		}
	};

	/**
	 *@required  state != null , action != null
	 *@modified this.
	 *@effects update the action from the state.
	 */	
	public void updatePolicy(State state , Action action) {
		policy.put(state, action);
	}
	
	/**
	 *@required None
	 *@modified None
	 *@effects return the action attached to the state. if the state is not exist or null return null. 
	 */	
	public Action getActionByState(State state) {
		return policy.get(state);
	}

	/**
	 *@required None
	 *@modified None
	 *@effects print policy according to the printing strategy.
	 */	
	public void printPi() {
		printPi.printPi(policy);
	}
	
	/**
	 *@required None
	 *@modified this
	 *@effects set new print strategy.
	 */	
	public void setPrintPi(PrintPi newPrintPi) {
		if(newPrintPi != null) {
			printPi = newPrintPi;
		}
	}
}
