package MyMDPmonopoly;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import MDP.Action;
import MDP.State;
import MDPinterfaces.ProbTable;

public class MyMonoProbTable implements ProbTable {
	private List<Map<Integer , Integer>> combList = new ArrayList<>();
	private Map<Action,Integer> actionMap = new HashMap<>();
	private final int sidesPerCube;
	
	
	
	public MyMonoProbTable(int cubesNum, int sidesPerCube) {
		this.sidesPerCube = sidesPerCube;
		for(int i = 0; i < cubesNum ; i++) {
			actionMap.put(new Action(Integer.toString(i+1)), i+1);
			Map<Integer , Integer> curCombMap = new HashMap<>(); 
			combList.add(curCombMap);
			for(int j = 1 ; j <= sidesPerCube ; j++) {
				if(i == 0) {
					curCombMap.put(j, 1);
				}
				else {
					for(Map.Entry<Integer , Integer> entry : combList.get(i-1).entrySet()) {
						int newKey = j + entry.getKey();
						if (curCombMap.get(newKey) == null) {
							curCombMap.put(newKey, entry.getValue());
						}
						else {
							int oldKey = curCombMap.get(newKey);
							curCombMap.replace(newKey, oldKey + entry.getValue());
						}
					}
				}
			}
		}
		//System.out.println(combList);
	}
	
	@Override
	public double getProbByActionAndStates(Action action, State src, State dst) {
		if(action == null || src == null  || dst == null) {
			return 0;
		}
		MyMonoState srcState = (MyMonoState) src;
		MyMonoState dstState = (MyMonoState) dst;
		boolean termiante = dstState.isTerminationState();
		int distance = dstState.get_ind() - srcState.get_ind();
		int curCubesNum = actionMap.get(action);
		if(curCubesNum == 0) {
			return 0;
		}
		Map<Integer , Integer> currMap = combList.get(curCubesNum - 1);
		Integer currComb = currMap.get(distance);
		if(currComb == null) {
			currComb = 0;
		}
		if(termiante == false) {
			return currComb/(Math.pow(sidesPerCube, curCubesNum));
		}
		int combSum = 0;
		for(Map.Entry<Integer , Integer> entry : currMap.entrySet()) {
			if(entry.getKey() >= distance) {
				combSum += entry.getValue();
			}
		}
		return combSum/(Math.pow(sidesPerCube, curCubesNum));
	}
}
