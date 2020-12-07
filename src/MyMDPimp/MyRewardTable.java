package MyMDPimp;

import MDP.Action;
import MDP.State;
import MDPinterfaces.RewardTable;

public class MyRewardTable implements RewardTable {
	State special;
	State medium;
	State bad;
	
	
	
	public MyRewardTable(State special ,State medium , State bad) {
		this.special = special;
		this.medium = medium;
		this.bad = bad;
	}
	
	@Override
	public double getRewardByActionAndStates(Action action, State srcState, State dstState) {
		if(dstState.equals(special)){
			return 10;
		}
		if(dstState.equals(medium)){
			return 5;
		}
		if(dstState.equals(bad)){
			return -4.5;
		}
		return 0.5;
	}

}
