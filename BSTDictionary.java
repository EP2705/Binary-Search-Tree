/**
 * @author Eliandro Pizzonia
 * 
 * This class implements an ordered dictionary using a binary search tree
 */
public class BSTDictionary implements BSTDictionaryADT {

	// creating a new BST object
	private BinarySearchTree bstTree = new BinarySearchTree();
	
	
	/**
	 * @param k 
	 * @return the Record with key k, or null if the Record is not in the dictionary
	 */
	@Override
	public Record get(Key k) {
		return searchRecord(bstTree.getRoot(), k);
	}
	
	
	/**
	 * @param d
	 * @throws DictionaryException
	 * 
	 * Inserts d into the ordered dictionary. It throws a DictionaryException if a Record with the same Key as d is already 
	 * in the dictionary
	 */
	@Override 
	public void put(Record d) throws DictionaryException {
		
		// insert new record as the root if the tree is empty
		if(bstTree.getRoot() == null) {
			bstTree.insert(null, d);
		}
		// if a record with the same key already exists in the tree, an exception is thrown
		else if(searchRecord(bstTree.getRoot(),d.getKey()) != null) {
			throw new DictionaryException("Record with the same Key as d is already in the dictionary");
		}
		// otherwise a node with the record is inserted in the tree
		else {
			bstTree.insert(bstTree.getRoot(),d);
		}
	}

	
	/**
	 * @param k
	 * @throws DictionaryException
	 * 
	 * Removes the Record with Key k from the dictionary. It throws a DictionaryException if the Record is not in the dictionary
	 */
	@Override 
	public void remove(Key k) throws DictionaryException {
		
		// getting the record of the node to be removed
		Record removeNode = get(k);
		
		// if the record is not found, an exception is thrown
		if(removeNode == null) {
			throw new DictionaryException("Record is not in the dictionary");
		}
		
		// otherwise the record is removed from the tree
		else {
			bstTree.remove(bstTree.getRoot(), removeNode.getKey());
		}
	}	
	
	
	/**
	 * @param k
	 * @return the successor of k (the Record from the ordered dictionary with smallest Key larger than k); 
	 * it returns null if the given Key has no successor. The given Key DOES NOT need to be in the dictionary. 
	 */
	@Override
	public Record successor(Key k) {
		
		//finding the successor node
		BSTNode tempNode = bstTree.successor(bstTree.getRoot(), k);
		
		// if no successor node is found
		if(tempNode == null) {
			return null;
		}
		
		// otherwise the record of the successor node is returned
		else {
			return tempNode.getRecord();
		}
	}
	
	
	/**
	 *@param k
	 *@return the predecessor of k (the Record from the ordered dictionary with largest key smaller than k; 
	 *it returns null if the given Key has no predecessor. The given Key DOES NOT need to be in the dictionary.
	 */
	@Override
	public Record predecessor(Key k) {
		
		// finding the predecessor node
		BSTNode tempNode = bstTree.predecessor(bstTree.getRoot(), k);
		
		// if no predecessor node is found
		if(tempNode == null) {
			return null;
		}
		
		// otherwise the record of the predecessor node is returned
		else {
			return tempNode.getRecord();
		}
	}
	
	
	/**
	 * @return the Record with smallest key in the ordered dictionary. Returns null if the dictionary is empty
	 */
	@Override
	public Record smallest() {
		
		// getting the node with the smallest key
		BSTNode smallestNode = bstTree.smallest(bstTree.getRoot());
		
		// return null if the tree is empty
		if(smallestNode == null){
			return null;
		}
		
		// otherwise the record of the smallest node is returned 
		else {
			return smallestNode.getRecord();
		}
	}

	
	/**
	 * @return the Record with largest key in the ordered dictionary. Returns null if the dictionary is empty
	 */
	@Override
	public Record largest() {
		
		// finding the node with the largest key
		BSTNode largestNode = bstTree.largest(bstTree.getRoot());
		
		// returning null if the node is if the tree is empty
		if(largestNode == null) {
			return null;
			}
		
		// otherwise the record of the largest node is returned
		else {
			return largestNode.getRecord();
		}
	}
	
	
	/**
	 * @param r
	 * @param k
	 * @return the record from the specified key
	 * 
	 * private helper method to search for a record with the specific key in the tree with root r
	 */
	private Record searchRecord(BSTNode r, Key k) {
		
		// returns null if the subtree is empty
		if(r.getLeftChild() == null || r.getRightChild() == null) {
			return null;
		}
		
		// returns the roots record if the key matches the roots key
		else if(k.compareTo(r.getRecord().getKey()) == 0) {
			return r.getRecord();
		}
		
		// searches the right subtree if the key is greater than the roots key
		else if(k.compareTo(r.getRecord().getKey()) == 1) {
			return searchRecord(r.getRightChild(), k);
		}
		
		// searches the left subtree if the key is smaller than the roots key
		else {
			return searchRecord(r.getLeftChild(), k);
		}
	}
}