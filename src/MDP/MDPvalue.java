package MDP;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 
 * @author ofek harari & eldad wiener
 * The class MDPvalue represents the values of the states.
 * The class allows to get and update the values of the states of the MDP problem.
 *
 */
public class MDPvalue {
	private Map<State,Value> valueMap = new HashMap<>();
	/**
	 * 
	 * The inner class inside MDPvalue holds specific value . 
	 *
	 */
	class Value {
		private double Vold = -1;
		private double Vnew = 0;
		boolean first = true;
		
		@Override
		public String toString() {
			String str = "The value is : " + Double.toString(Vold);
			return str ;
		}
	}
	
	/**
	 *@required  statesIter != null . 
	 *@modified this .
	 *@effects initialize value for each state .
	 */	
	public void initValue(Iterator<? extends State> statesIter){
		while(statesIter.hasNext()) {
			State state = statesIter.next();
			valueMap.put(state, new Value());
		}
	}
	
	/**
	 *@required None . 
	 *@modified this .
	 *@effects for every state update its old value to the new .
	 */	
	public void updateOldValues() {
		for(Value value : valueMap.values()) {
			value.Vold = value.Vnew;
			value.first = true;
		}
	}
	
	/**
	 *@required State != null . 
	 *@modified None .
	 *@effects return the old value of state .
	 */	
	public double getStateOldVal(State State) {
		return valueMap.get(State).Vold;
	}
	
	/**
	 *@required State != null . 
	 *@modified None .
	 *@effects return the new value of state .
	 */	
	public double getStateNewVal(State State) {
		return valueMap.get(State).Vnew;
	}
	
	/**
	 *@required State != null . 
	 *@modified None .
	 *@effects return if the value of the state updated before .
	 */	
	public boolean isFirstState(State State) {
		return valueMap.get(State).first;
	}
	
	/**
	 *@required State != null . 
	 *@modified this .
	 *@effects update new value of the state .
	 */	
	public void setStateNewVal(State State, double newVal) {
		valueMap.get(State).Vnew = newVal;
	}
	
	/**
	 *@required State != null . 
	 *@modified this .
	 *@effects marks that state's value update at least once  .
	 */	
	public void setFirstState(State State, boolean first) {
		valueMap.get(State).first = first;
	}
		
	/**
	 *@required None . 
	 *@modified None .
	 *@effects checks if the distance (norm 2) between new and old values is less then epsilon .
	 */
	public boolean isVconverge(double epsilon) {
		double sum = 0;
		for(Value value : valueMap.values()) {
			sum+= Math.pow(value.Vold - value.Vnew , 2);
		}
		sum = Math.sqrt(sum);
		if (sum <= epsilon) {
			return true;
		}
		return false;
	}
}
