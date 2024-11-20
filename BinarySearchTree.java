/**
 * @author Eliandro Pizzonia
 * 
 * This class represents a binary search tree
 */
public class BinarySearchTree {
	
	// instance variable representing the root of the BST
	private BSTNode root;
	
	
	/**
	 * The constructor for the class that creates a leaf node as the root of the tree
	 */
	public BinarySearchTree() {
		this.root = new BSTNode(null);
	}
	
	
	/**
	 * @return the root node of this binary search tree
	 */
	public BSTNode getRoot() {
		return root;
	}
	
	
	/**
	 * @param r
	 * @param k
	 * @return the node storing the given key, returns null if the key is not stored in the tree with root r
	 */
	public BSTNode get(BSTNode r, Key k) {
		
		// base case returns leaf node if reached
		if(r.isLeaf()) {
			return r;
		}
		
		else {
			// return node if found with key
			if(r.getRecord().getKey().compareTo(k) == 0) {
				return r;
			}
			// searching the right subtree
			else if(r.getRecord().getKey().compareTo(k) == -1) {
				return get(r.getRightChild(), k);
			}
			// searching the left subtree
			else {
				return get(r.getLeftChild(), k);
			}
		}
	}
	
	
	/**
	 * @param r
	 * @param d
	 * @throws DictionaryException
	 * 
	 * Adds the record to the binary search tree with root r. Throws a DictionaryException if the tree 
	 * already stores a record with the same key as d
	 */
	public void insert(BSTNode r, Record d) throws DictionaryException{
		BSTNode node = this.get(r, d.getKey());
		
		if(node == null) {
			throw new DictionaryException("The record is already in the tree");			
		}
		
		// setting node with record
		node.setRecord(d);
		
		// creating a new left and right child
		BSTNode leftChild = new BSTNode(null);
		BSTNode rightChild = new BSTNode(null);
		
		// setting the left child of the node and the parent of the left child to the node
		node.setLeftChild(leftChild);
		leftChild.setParent(node);
		
		// setting the right child of the node and the parent of the right child to the node
		node.setRightChild(rightChild);
		rightChild.setParent(node);
		
	}
	
	
	/**
	 *@param r
	 *@param k
	 *@throws DictionaryException
	 *
	 *Deletes the node with the given key from the tree with root r. Throws a DictionaryException if the tree 
	 *does not store a record with the given key
	 */
	public void remove(BSTNode r, Key k) throws DictionaryException {
		
		// getting the node to be removed
		BSTNode node = this.get(r, k);
		
		// if node is not found
		if (node == null) {
			throw new DictionaryException("The Tree does not store a record with the given key");
		}

		BSTNode tempParent = node.getParent();
		BSTNode tempChild;
		
		// if node has one child or no children
		if (node.getLeftChild() == null || node.getRightChild() == null) {
			
			// setting tempChild to the child that is not null
			if(node.getLeftChild() != null) {
				tempChild = node.getLeftChild();
			}
			else {
				tempChild = node.getRightChild();
			}
			
			// if the node to be removed is the root
			if (node == this.root) {
				
				// root is set to the tempChild
				this.root = tempChild;
				
				// updating the new roots parent
				if (tempChild != null) {
					tempChild.setParent(null);
				}
			}
			
			// if the node to remove is not the root
			else {
				
				// if the parent of the node has the node as its left child, the parent of the node sets it left child to the left child of
				// the node (replacing the node)
				if (tempParent.getLeftChild() == node) {
					tempParent.setLeftChild(tempChild);
				}
					
				// if the parent of the node has the node as its right child, the parent of the node sets it right child to the right child of
				// the node (replacing the node)
				else {
					tempParent.setRightChild(tempChild);
				}
				
				// if the new child of the parent of the node is not null, its parent reference is updated
				if (tempChild != null) {
					tempChild.setParent(tempParent);
				}
			}
		}
		
		// if the node has two children
		else {
			
			// smallest node in the right subtree
			BSTNode smallestNode = smallest(node.getRightChild());
			
			// replacing the record of the node to be removed with the smallest node and removing the original smallest  node
			if (smallestNode != null) {
				node.setRecord(smallestNode.getRecord());
				remove(node.getRightChild(), smallestNode.getRecord().getKey());
			}
			
			// updating the root if the smallest node is null
			else {
				if (node == this.root) {
					this.root = node.getLeftChild();

					if (this.root != null) {
						this.root.setParent(null);
					}
				}
				
				// updating the reference of the parents child
				else {
					if (tempParent.getLeftChild() == node) {
						tempParent.setLeftChild(node.getLeftChild());
					}
					else {
						tempParent.setRightChild(node.getLeftChild());
					}
					
					// updating the parent reference of the left child
					if (node.getLeftChild() != null) {
						node.getLeftChild().setParent(tempParent);
					}
				}
			}
		}
	}

	
	/**
	 * @param r
	 * @param k
	 * @return the node storing the successor of the given key in the tree with root r, returns null if the successor does not exist
	 */
	public BSTNode successor(BSTNode r, Key k) {	
		
		// finding node with the key
		BSTNode node = get(r, k);
		
		// if the tree is empty or largest node is null 
		if(largest(r) == null) {
			return null;
		}
		
		// if the node is the largest node, it has no successor
		if(node == largest(r)) {
			return null;
		}
		
		// returning null if the key is greater than the largest key
		if(k.compareTo(largest(r).getRecord().getKey()) == 1) {
			
			return null;
		}
		
		// If the node is a leaf find the first ancestor that is greater
		if (node.isLeaf()){
			
			BSTNode parent = node.getParent();
			
			while(parent.getRecord().getKey().compareTo(k) == -1) {
				
				parent = parent.getParent();
			}
			return parent;
			
		}
		
		// the successor is the smallest node in the right subtree if the node has a right child
		if(!node.getRightChild().isLeaf()) {
			return smallest(node.getRightChild());
		}
		
		// if the right child is a leaf , the first ancestor that is greater is found
		if(node.getRightChild().isLeaf()) {
		
			
			BSTNode parent = node.getParent();
			
			while(parent.getRecord().getKey().compareTo(k) == -1) {
				parent = parent.getParent();
			}
			return parent;
		}
		
		return null;
	}	

	
	/**
	 * @param r
	 * @param k
	 * @return the node storing the predecessor of the given key in the tree with root r, returns null if the predecessor does not exist
	 */
	public BSTNode predecessor(BSTNode r, Key k) {
		
		// find the node with the key
		BSTNode node = get(r, k);
		
		// if the tree is empty or smallest node is null 
		if(smallest(r) == null) {
			return null;
		}
		
		// if the node is the smallest node, it has no successor
		if(node == smallest(r)) {
			return null;
		}
		
		// returning null if the key is smaller than the largest key
		if(k.compareTo(smallest(r).getRecord().getKey()) == -1) {
			
			return null;
		}
		
		// If the node is a leaf find the first ancestor that is smaller		
		if (node.isLeaf()){
			
			BSTNode parent = node.getParent();
			
			while(parent.getRecord().getKey().compareTo(k) == 1) {
				
				parent = parent.getParent();
			}
			return parent;
			
		}
		
		// the predecessor is the largest node in the left subtree if the node has a left child
		if(!node.getLeftChild().isLeaf()) {
			return largest(node.getLeftChild());
		}
		
		// if the left child is a leaf , the first ancestor that is smaller is found
		if(node.getLeftChild().isLeaf()) {
			
			BSTNode parent = node.getParent();
			
			while(parent.getRecord().getKey().compareTo(k) == 1) {
				
				parent = parent.getParent();
			}
			return parent;
		}
		
		return null;
	}
	
	
	/**
	 * @param r
	 * @return the node with the smallest key in tree with root r
	 */
	public BSTNode smallest(BSTNode r) {
		
		// if the tree is empty null is returned
		if(r.isLeaf()) {
			return null;
		}
		
		// traverse to the node that is the most left in the tree
		else {
			BSTNode currNode = r;
			while(!currNode.isLeaf()) {
				currNode = currNode.getLeftChild();
			}
			return currNode.getParent();
		}
	}
	
	
	/**
	 * @param r
	 * @return the node with the largest key in tree with root r
	 */
	public BSTNode largest (BSTNode r) {
		
		// if the tree is empty null is returned
		if(r.isLeaf()) {
			return null;
		}
		
		// traverse to the node that is the most right in the tree
		else {
			BSTNode currNode = r;
			while(!currNode.isLeaf()) {
				currNode = currNode.getRightChild();
			}
			return currNode.getParent();
		}
	}		
}