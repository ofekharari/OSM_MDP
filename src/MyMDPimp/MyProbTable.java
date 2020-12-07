package MyMDPimp;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import MDP.Action;
import MDP.State;
import MDPinterfaces.ProbTable;

public class MyProbTable implements ProbTable {
	enum Relation {SELF , NEIGHBOR_U , NEIGHBOR_R, NEIGHBOR_L, NEIGHBOR_D, OTHER};
	enum Location {REGULAR , CORNER_UL, CORNER_UR, CORNER_DL, CORNER_DR, EDGE_U, EDGE_L, EDGE_R, EDGE_D};
	private final int len;
	private final int high;
	private Map<Action, Relation> actionRelation = new HashMap<>();
	
	
	public MyProbTable(int len , int high) {
		this.len = len;
		this.high = high;
		actionRelation.put(new Action("up"), Relation.NEIGHBOR_U);
		actionRelation.put(new Action("down"), Relation.NEIGHBOR_D);
		actionRelation.put(new Action("right"), Relation.NEIGHBOR_R);
		actionRelation.put(new Action("left"), Relation.NEIGHBOR_L);
	}
	
	@Override
	public double getProbByActionAndStates(Action action, State srcState, State dstState) {
		boolean isTerminated = ((MyState)srcState).isTerminationState();
		Relation relation = getRelation((MyState)srcState, (MyState)dstState);
		if (relation == Relation.OTHER) {
			return 0;
		}
		if(isTerminated) {
			if(relation == Relation.SELF) {
				return 1;
			}
			return 0;
		}
		Location location = getLocation((MyState)srcState);
		if(location == Location.REGULAR) {
			if(relation == Relation.SELF) {
				return 0;
			}
		}
		else if(location == Location.EDGE_D) {
			if(relation == Relation.SELF) {
				if(actionRelation.get(action) == Relation.NEIGHBOR_D) return 0.7;
				return 0.1;
			}
		}
		else if(location == Location.EDGE_U) {
			if(relation == Relation.SELF) {
				if(actionRelation.get(action) == Relation.NEIGHBOR_U) return 0.7;
				return 0.1;
			}
		}
		else if(location == Location.EDGE_L) {
			if(relation == Relation.SELF) {
				if(actionRelation.get(action) == Relation.NEIGHBOR_L) return 0.7;
				return 0.1;
			}
		}
		else if(location == Location.EDGE_R) {
			if(relation == Relation.SELF) {
				if(actionRelation.get(action) == Relation.NEIGHBOR_R) return 0.7;
				return 0.1;
			}
		}
		else if (location == Location.CORNER_DL) {
			if(relation == Relation.SELF) {
				if(actionRelation.get(action) == Relation.NEIGHBOR_L || 
				   actionRelation.get(action) == Relation.NEIGHBOR_D) return 0.8;
				return 0.2;
			}
		}
		else if (location == Location.CORNER_DR) {
			if(relation == Relation.SELF) {
				if(actionRelation.get(action) == Relation.NEIGHBOR_R || 
				   actionRelation.get(action) == Relation.NEIGHBOR_D) return 0.8;
				return 0.2;
			}
		}
		else if (location == Location.CORNER_UL) {
			if(relation == Relation.SELF) {
				if(actionRelation.get(action) == Relation.NEIGHBOR_L || 
				   actionRelation.get(action) == Relation.NEIGHBOR_U) return 0.8;
				return 0.2;
			}
		}
		else if (location == Location.CORNER_UR) {
			if(relation == Relation.SELF) {
				if(actionRelation.get(action) == Relation.NEIGHBOR_R || 
				   actionRelation.get(action) == Relation.NEIGHBOR_U) return 0.8;
				return 0.2;
			}
		}
		if(actionRelation.get(action) == relation) return 0.7;
		return 0.1;
	}
	
	private Relation getRelation(MyState srcState, MyState dstState) {
		int[] cordSrc = srcState.get_cords();
		int[] cordDst = dstState.get_cords();
		if(Arrays.equals(cordSrc, cordDst)) {
			return Relation.SELF;
		}
		int deltaX = (cordSrc[0] - cordDst[0]);
		int deltaY = (cordSrc[1] - cordDst[1]);
		if(Math.abs(deltaX) > 1 || Math.abs(deltaY) > 1 || (deltaX != 0 && deltaY != 0)) {
			return Relation.OTHER;
		}
		if(deltaX > 0)return Relation.NEIGHBOR_L;
		if(deltaX < 0)return Relation.NEIGHBOR_R;
		if(deltaY > 0)return Relation.NEIGHBOR_D;
		return Relation.NEIGHBOR_U;
	}
	
	private Location getLocation(MyState state) {
		int[] cordSrc = state.get_cords();
		boolean xIsFrame = (cordSrc[0] == 0 || cordSrc[0] == len-1);
		boolean yIsFrame = (cordSrc[1] == 0 || cordSrc[1] == high-1);
		if (xIsFrame && yIsFrame) {
			if(cordSrc[0] == 0) {
				if(cordSrc[1] == 0) return Location.CORNER_DL;
				return Location.CORNER_UL;
			}
			else if(cordSrc[1] == 0)return Location.CORNER_DR;
			return Location.CORNER_UR;
		}	
		if(xIsFrame || yIsFrame){
			if(cordSrc[0] == 0) return Location.EDGE_L;
			if(cordSrc[0] == len-1) return Location.EDGE_R;
			if(cordSrc[1] == 0) return Location.EDGE_D;
			return Location.EDGE_U;
		}
		return Location.REGULAR;
	}

}
