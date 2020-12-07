package MyMDPmonopoly;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import MDP.Action;
import MDP.State;
import MDPinterfaces.RewardTable;

public class MyMonoRewardTable implements RewardTable {
	private Random rand;
	private Map<MyMonoState, Double> rewardMap = new HashMap<>();
	
	public MyMonoRewardTable(int statesNum , int seed) {
		this.rand = new Random(seed);
		System.out.print("the reward table is : \n[" );
		for(int i = 0 ; i< statesNum ; i++) {
			double value = (rand.nextDouble() * 100) - 50;
			System.out.print("(" + i + ", " + String.format("%.2f", value) + ")," );
			rewardMap.put(new MyMonoState(i, false), value);
		}
		System.out.println("(" + statesNum + ", END GAME)]");
		rewardMap.put(new MyMonoState(statesNum, true), 0.0);
	}
	
	@Override
	public double getRewardByActionAndStates(Action action, State srcState, State dstState) {
		return rewardMap.get(dstState);
	}
}
