package OSM;

import MDP.State;

/**
 * 
 * @author ofek harari & eldad wiener
 * The class OSMstate represents a state of OSM MDP problem.
 * The class holds: a, h and fork. fork is {RELEVANT , IRRELEVANT , ACTIVE}.
 * Based on Optimal Selfish Mining Strategies in Bitcoin paper by Ayelet Sapirshtein, Yonatan Sompolinsky and Aviv Zohar.
 *
 */
public class OSMstate extends State {
	private final int a;
	private final int h;
	enum fork { RELEVANT , IRRELEVANT , ACTIVE };
	private final fork myFork;
	
	/**
	 *@requires None.
	 *@modified this.
	 *@effects  create new OSM state.
	 */
	public OSMstate(int a, int h , fork myFork) {
		this.a = a;
		this.h = h;
		this.myFork = myFork;
	}
	
	/**
	 *@requires None.
	 *@modified None.
	 *@effects  returns: a and h of the state.
	 */
	public int[] getChainsParams() {
		int []array = new int[]{a,h};
		return array;
	}
	
	/**
	 *@requires None.
	 *@modified None.
	 *@effects  returns myFork of the state.
	 */
	public fork getFork() {
		return myFork;
	}
	
	/**
	 *@requires None
	 *@modified None
	 *@effects  return description of State name (str).
	 */
	@Override
	public String toString() {
		String str = "The state chains are : (" + Integer.toString(a) + 
					 						"," + Integer.toString(h) +
					 						"," + myFork + ")";      
		return str ;
	}

	/**
	 *@requires None
	 *@modified None
	 *@effects  return if the object o equals(a,h,fork are equals) to the state.
	 */
	@Override
	public boolean equals(Object o) {
        // If the object is compared with itself then return true   
        if (o == this) { 
            return true; 
        } 
        /* Check if o is an instance of MyStateName or not 
          "null instanceof [type]" also returns false */
        if (!(o instanceof OSMstate)) { 
            return false; 
        } 
          
        // typecast o to Complex so that we can compare data members  
        OSMstate rhs = (OSMstate) o; 
          
        // Compare the data members and return accordingly  
        return ((this.a == rhs.a) && (this.h == rhs.h) &&
        		 this.myFork == rhs.myFork);
    }

	@Override
	public int hashCode() {
		return a + h; 
	}

	@Override
	public Object clone() {
		OSMstate clonedObj = (OSMstate) super.clone();
		return clonedObj;
	}
}
