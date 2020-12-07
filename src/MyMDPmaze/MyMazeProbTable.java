package MyMDPmaze;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import MDP.Action;
import MDP.State;
import MDPinterfaces.ProbTable;
import MyMDPmaze.MyMazeState.directions;

public class MyMazeProbTable implements ProbTable {
	enum Relation {SELF , NEIGHBOR_U , NEIGHBOR_R, NEIGHBOR_L, NEIGHBOR_D, OTHER};
	private Map<Action, Relation> actionRelation = new HashMap<>();
	private Map<Action, directions> action2Directin = new HashMap<>();
	
	public MyMazeProbTable() {
		actionRelation.put(new Action("up"), Relation.NEIGHBOR_U);
		actionRelation.put(new Action("down"), Relation.NEIGHBOR_D);
		actionRelation.put(new Action("right"), Relation.NEIGHBOR_R);
		actionRelation.put(new Action("left"), Relation.NEIGHBOR_L);
		
		action2Directin.put(new Action("up") , directions.UP);
		action2Directin.put(new Action("down"), directions.DOWN);
		action2Directin.put(new Action("right"), directions.RIGHT);
		action2Directin.put(new Action("left"), directions.LEFT);
	}
	
	@Override
	public double getProbByActionAndStates(Action action, State src, State dst) {
		MyMazeState srcState = (MyMazeState) src;
		MyMazeState dstState = (MyMazeState) dst;
		boolean isTerminated = srcState.isTerminationState();
		Relation relation = getRelation(srcState, dstState);
		if (relation == Relation.OTHER) {
			return 0;
		}
		if(isTerminated) {
			if(relation == Relation.SELF) {
				return 1;
			}
			return 0;
		}
		
		int blocks = 0;
		for(Boolean pass : srcState.getDirections().values()) {
			if(!pass) {
				blocks++;
			}
		}
		if(relation != Relation.SELF) {
			if(actionRelation.get(action) == relation) return 0.7;
			return 0.1;
			
		}
		directions dir = action2Directin.get(action);
		if(srcState.getDirections().get(dir) == true ) return 0.1 * blocks;
		return 0.7 + (0.1 * (blocks - 1));
	}
	
	private Relation getRelation(MyMazeState src, MyMazeState dst) {
		int[] cordSrc = src.get_cords();
		int[] cordDst = dst.get_cords();
		if(Arrays.equals(cordSrc, cordDst)) {
			return Relation.SELF;
		}
		int deltaX = (cordSrc[0] - cordDst[0]);
		int deltaY = (cordSrc[1] - cordDst[1]);
		if(Math.abs(deltaX) > 1 || Math.abs(deltaY) > 1 || (deltaX != 0 && deltaY != 0)) {
			return Relation.OTHER;
		}
		if(deltaX > 0) {
			if(src.getDirections().get(directions.LEFT)) {
				return Relation.NEIGHBOR_L;
			}
		}
		else if(deltaX < 0) {
			if(src.getDirections().get(directions.RIGHT)) {
				return Relation.NEIGHBOR_R;
			}
		}
		else if(deltaY > 0) {
			if(src.getDirections().get(directions.DOWN)) {
				return Relation.NEIGHBOR_D;
			}
		}
		else if (src.getDirections().get(directions.UP)) {
			return Relation.NEIGHBOR_U;
		}
		return Relation.OTHER;
	}
}
