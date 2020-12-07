/*
package MyMDPimp;

import java.util.HashMap;
import java.util.Map;

import MDP.Action;
import MDPinterfaces.ActionsRules;

public class MySquareGridRules implements ActionsRules {
	public static final int HEIGHT_DIM = 5;
	public static final int WIDTH_DIM = 5;
	public static final int TOTAL_STATE_NUM = HEIGHT_DIM * WIDTH_DIM;
	public Map <String, Action> actionsMap = new HashMap<>();  
	
	public MySquareGridRules() {
		actionsMap.put("up",new Action("up"));
		actionsMap.put("down",new Action("down"));
		actionsMap.put("right",new Action("right"));
		actionsMap.put("left",new Action("left"));
	}
	@Override
	public int getNextStateByAction(Action action, int srcStateID) {
		if ( action.equals(actionsMap.get("up")) ) {
			if (srcStateID < (TOTAL_STATE_NUM - WIDTH_DIM) ) {
				return srcStateID + WIDTH_DIM;
			} else { // highest row
				return srcStateID;
			}
		} else if(action.equals(actionsMap.get("down"))) {
			if (srcStateID < WIDTH_DIM ) { // lowest row 
				return srcStateID; 
			} else {
				return srcStateID - WIDTH_DIM;
			}
		} else if(action.equals(actionsMap.get("right"))) {
			if ( (srcStateID % WIDTH_DIM) == (WIDTH_DIM -1) ) { // far right col
				return srcStateID;
			} else { 
				return srcStateID + 1;
			}
		} else if(action.equals(actionsMap.get("left"))) {
			if ( (srcStateID % WIDTH_DIM) == 0 ) { // far left col
				return srcStateID;
			} else { 
				return srcStateID - 1;
			}
		} 
		return -1;
	}

}
*/ 