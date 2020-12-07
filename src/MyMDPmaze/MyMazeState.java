package MyMDPmaze;


import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import MDP.State;

public class MyMazeState extends State {
	public enum directions {UP, DOWN, RIGHT, LEFT};
	private final int xCord;
	private final int yCord;
	private Map<directions, Boolean> passTable = new HashMap<>();
	private boolean terminationState;
	
	public MyMazeState(int xCord, int yCord ,boolean up ,boolean down ,boolean right ,boolean left, boolean term) {
		this.xCord = xCord;
		this.yCord = yCord;
		this.terminationState = term;
		passTable.put(directions.UP, up);
		passTable.put(directions.DOWN, down);
		passTable.put(directions.RIGHT, right);
		passTable.put(directions.LEFT, left);
	}
	
	
	public MyMazeState(int xCord, int yCord) {
		this.xCord = xCord;
		this.yCord = yCord;
		this.terminationState = false;
		passTable.put(directions.UP, false);
		passTable.put(directions.DOWN, false);
		passTable.put(directions.RIGHT, false);
		passTable.put(directions.LEFT, false);
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
        if (!(o instanceof MyMazeState)) { 
            return false; 
        } 
          
        // typecast o to Complex so that we can compare data members  
        MyMazeState rhs = (MyMazeState) o; 
          
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
	
	public Map<directions, Boolean> getDirections(){
		return passTable;
	}
	
	@Override
	public Object clone() {
		MyMazeState clonedObj = (MyMazeState) super.clone();
		clonedObj.passTable = new HashMap<>();
		clonedObj.passTable.putAll(this.passTable);
		return clonedObj;
	}

}
