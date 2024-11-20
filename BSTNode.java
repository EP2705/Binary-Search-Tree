/**
 * @author Eliandro Pizzonia
 * 
 * This class represents a node of the binary search tree
 */
public class BSTNode {
	
	// instance variables to store the record, leftchild, rightchild, and parent of a node in the BST
	private Record record;
	private BSTNode leftChild;
	private BSTNode rightChild;
	private BSTNode parent;

	
	/**
	 *@param item 
	 * 
	 * The constructor for the class
	 */
	public BSTNode(Record item) {
		this.record = item;
	}
	
	
	/**
	 * @return the Record object stored in this node
	 */
	public Record getRecord() {
		return record;
	}
	
	
	/**
	 * @param d
	 * 
	 * Stores the given record in this node
	 */
	public void setRecord(Record d) {
		this.record = d;
	}
	
	
	/**
	 * @return the left child
	 */
	public BSTNode getLeftChild() {
		return this.leftChild;
	}
	
	
	/**
	 * @return the right child
	 */
	public BSTNode getRightChild() {
		return this.rightChild;
	}
	
	
	/**
	 * @return the parent
	 */
	public BSTNode getParent() {
		return this.parent;
	}
	
	
	/**
	 * @param u
	 * 
	 * Sets the left child to the specified value
	 */
	public void setLeftChild(BSTNode u) {
		this.leftChild = u;
	}
	
	
	/**
	 * @param u
	 * 
	 * Sets the right child to the specified value
	 */
	public void setRightChild(BSTNode u) {
		this.rightChild = u;
	}
	
	
	/**
	 * @param u
	 * 
	 * Sets the parent to the specified value
	 */
	public void setParent(BSTNode u) {
		this.parent = u;
	}
	
	
	/**
	 * @return true if this node is a leaf, false otherwise
	 * 
	 * A node is a leaf if both of its children are null
	 */
	public boolean isLeaf() {
		return this.getLeftChild() == null && this.getRightChild() == null;
	}
		
}

