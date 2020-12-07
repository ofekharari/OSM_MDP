package MDP;


/*
 * The abstract class State represent a state in the MDP problem .   
 * Each class which inherit from State will define his state variables,
 * and implement the methods that provides the state for the user .
 */

public abstract class State implements Cloneable {
	
	  @Override
	  public abstract boolean equals(Object other);
	  
	  @Override
	  public abstract int hashCode();
	  
	  @Override
	  public Object clone() {
			State clonedObj = null;
			try {
				clonedObj = (State) super.clone();
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
			return clonedObj;
	  }
	  
}

