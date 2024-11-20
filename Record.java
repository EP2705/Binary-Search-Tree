/**
 * @author Eliandro Pizzonia 
 * 
 * This class represents the records to be stored in the internal nodes of the binary search tree
 */
public class Record {
	
	// instance variables to store the key and data
	private Key theKey;
	private String data;
	
	
	/**
	 * @param k
	 * @param theData
	 * 
	 * A constructor which initializes a new Record object with the specified parameters.
	 */
	public Record(Key k, String theData) {
		this.theKey = k;
		this.data = theData;
	}
	
	
	/**
	 * @return theKey
	 */
	public Key getKey() {
		return this.theKey;
	}
	
	
	/**
	 * @return data
	 */
	public String getDataItem() {
		return this.data;
	}
}
