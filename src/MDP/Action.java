package MDP;

/**
 * 
 * @author ofek harari & eldad wiener
 * The class Action represents an action in MDP problem.
 * The class must support comparison between two actions (equals or not).
 *
 */
public class Action {

	private final String action;
	
	/**
	 *@requires action != null
	 *@modified this
	 *@effects create new Action.
	 */
	public Action(String action) {
		this.action = action;
	}
	
	@Override
	public boolean equals(Object o) {

        // If the object is compared with itself then return true   
        if (o == this) { 
            return true; 
        } 
        
        /* Check if o is an instance of Action or not 
          "null instanceof [type]" also returns false */
        if (!(o instanceof Action)) { 
            return false; 
        } 
          
        // typecast o to Complex so that we can compare data members  
        Action rhs = (Action) o; 
          
        // Compare the data members and return accordingly  
        return this.action.equals(rhs.action);
    } 
	
	
	@Override
	public int hashCode() {
		
		return action.hashCode();
	}
	
	@Override
	public String toString() {
		
		return "The action is : " + action;
	}
		
}