/**
 * @author Eliandro Pizzonia 
 *
 * This class represents the key of the data items stored in the internal nodes of the binary search tree implementing the ordered dictionary
 */
public class Key {

	// instance variables
	private String label;
	private int type;
	
	
	/**
	 * @param theLabel
	 * @param theType
	 * 
	 * A constructor which initializes a new Key object with the specified parameters
	 */
	public Key(String theLabel, int theType) {
		this.label = theLabel.toLowerCase();
		this.type = theType;
	}
	
	
	/**
	 * @return the String stored in instance variable label
	 */
	public String getLabel() {
		return this.label;
	}
	
	
	/**
	 * @return the value of instance variable type
	 */
	public int getType() {
		return this.type;
	}
	
	
	/**
	 * @param k
	 * @return 0 if this Key object is equal to k, returns -1 if this Key object is smaller than k, and it returns 1 otherwise
	 */
	public int compareTo(Key k){
		
		// checking if both labels and types are equal
		if(this.label.equals(k.label) && this.type == k.type) {
			return 0;
		}
		
		// comparing the types if the labels are equal
		if (this.label.equals(k.label)) {
			
			if (this.type > k.type) {
				return 1;
			}
			else {
				return -1;
			}
		} 
		
		// comparing the labels lexicographically
		else {
			if (this.label.compareTo(k.label) > 0) {
				return 1;
			}
			else {
				return -1;
			}
		}
	}
	
}



