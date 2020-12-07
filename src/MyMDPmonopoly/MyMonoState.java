package MyMDPmonopoly;

import MDP.State;

public class MyMonoState extends State {
	private final int index;
	private boolean isTerminate;
	
	public MyMonoState(int index , boolean isTerminate) {
		this.index = index;
		this.isTerminate = isTerminate;
	}
	
	public MyMonoState(int index) {
		this.index = index;
		this.isTerminate = false;
	}
	
	/**
	 *@requires None
	 *@modified None
	 *@effects return the index of the state.
	 */
	public int get_ind() {
		return index;
	}

	/**
	 *@requires None
	 *@modified None
	 *@effects return true if its termination state.
	 */
	public boolean isTerminationState() {
		return isTerminate;
	}
	
	/**
	 *@requires None
	 *@modified None
	 *@effects  return description of State (str).
	 */
	@Override
	public String toString() {
		String str = "The state index is : " + Integer.toString(get_ind()) + 
					", Terminate is : " + isTerminate;
		return str ;
	}

	/**
	 *@requires None
	 *@modified this
	 *@effects  mark the state as termination state and returns the prev state.
	 */
	public boolean setTerminationState() {
		boolean prev = isTerminate;
		isTerminate = true;
		return prev;
	}

	/**
	 *@requires None
	 *@modified this
	 *@effects  mark the state as regular state and returns the prev state.
	 */
	public boolean setRegularState() {
		boolean prev = isTerminate;
		isTerminate = false;
		return prev;
	}

	@Override
	public boolean equals(Object o) {
	    // If the object is compared with itself then return true   
        if (o == this) { 
            return true; 
        } 
        /* Check if o is an instance of MyStateName or not 
          "null instanceof [type]" also returns false */
        if (!(o instanceof MyMonoState)) { 
            return false; 
        } 
          
        // typecast o to Complex so that we can compare data members  
        MyMonoState rhs = (MyMonoState) o; 
          
        // Compare the data members and return accordingly  
        return (this.index == rhs.index);
	}

	@Override
	public int hashCode() {
		return get_ind(); 
	}

	
	@Override
	public Object clone() {
		MyMonoState clonedObj = (MyMonoState) super.clone();
		return clonedObj;
	}

}
