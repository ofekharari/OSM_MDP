package MDPinterfaces;

import java.util.Map;

import MDP.Action;
import MDP.State;

/**
 * 
 * @author ofek harari & eldad wiener
 * The interface PrintPi is necessary for every class who wants to print the MDP policy in special form (it's a print policy) .
 *
 */
public interface PrintPi {
	public void printPi(Map<State,Action> pi);
}
