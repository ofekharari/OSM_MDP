package MyMDPimp;


import java.util.Arrays;

import MDP.State;

public class MyState extends State {
	private boolean terminationState ;
	private final int xCord;
	private final int yCord;
	
	public MyState(int xCord, int yCord, boolean termination) {
		this.xCord = xCord;
		this.yCord = yCord;
		this.terminationState = termination;
	}
	
	/**
	 *@requires None
	 *@modified None
	 *@effects return true if its termination state.
	 */
	public boolean isTerminationState() {
		return terminationState;
	}
	
	/**
	 *@requires None
	 *@modified None
	 *@effects  return description of State (str).
	 */
	@Override
	public String toString() {
		return "The state info is :\n " + 
				"\tThe state cords are : (" +  Integer.toString(xCord) + "," + Integer.toString(yCord) + ")\n" +
				"\tIs terminate state : " + terminationState; 
	}

	/**
	 *@requires None
	 *@modified this
	 *@effects  mark the state as termination state and returns the prev state.
	 */
	public boolean setTerminationState() {
		boolean prev = terminationState;
		terminationState = true;
		return prev;
	}

	/**
	 *@requires None
	 *@modified this
	 *@effects  mark the state as regular state and returns the prev state.
	 */
	public boolean setRegularState() {
		boolean prev = terminationState;
		terminationState = false;
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
        if (!(o instanceof MyState)) { 
            return false; 
        } 
          
        // typecast o to Complex so that we can compare data members  
        MyState rhs = (MyState) o; 
          
        // Compare the data members and return accordingly  
        return (Arrays.equals(this.get_cords(), rhs.get_cords()));
	}

	@Override
	public int hashCode() {
		return xCord + yCord; 
	}

	public int[] get_cords() {
		int []array = new int[]{xCord,yCord};
		return array;
	}

}
