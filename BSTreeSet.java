package edu.wou.cs260.SpellChecker;

import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;
import java.util.Set;

/**
 * class MyBST contains methods to construct and manage a Binary Search Tree (BST)
 * A BST has a root with two child nodes (left, right) where the left child is less than the parent (root)
 * and the right is greater than the parent node.
 * 
 * @author Stephen Oliver
 * 
 * @version CS260 Lab 3, 07/27/2016 
 * @LabVersion Lab 3B-CS	
 * 
 * @param <T> item of Generic data type 
 */
public class BSTreeSet <T  extends Comparable<T>> implements Set<T>, CompareCount {
	//Fields
	Node root = null;
	int size = 0;
	int lastCompareCount = 0;
	
	//Methods
	
	/**
	 * Main Method used for initial light testing
	 * 
	 * @param args
	 */
	public static void main (String[] args) {
		BSTreeSet<Integer> test = new BSTreeSet<Integer>();
		
		System.out.println("test.isEmpty(true): " + test.isEmpty());
		System.out.println("--------------------------------");
		System.out.println("test.size(0): " + test.size());
		System.out.println("--------------------------------");
		System.out.println("Populating BST...");
		test.add(86);
		test.add(583);
		test.add(23);
		test.add(19);
		test.add(24);
		test.add(17);
		test.add(240);
		test.add(890);
		test.add(91);
		test.add(583); //this isn't added to the tree as 583 already exists
		System.out.println("Populated!");
		System.out.println("--------------------------------");
		System.out.println("test.isEmpty(false): " + test.isEmpty());
		System.out.println("--------------------------------");
		System.out.println("test.size(9): " + test.size());
		System.out.println("--------------------------------");
		System.out.println("test.contains(86) (true): " + test.contains(86));
		System.out.println("test.contains(19) (true): " + test.contains(19));
		System.out.println("test.contains(890) (true): " + test.contains(890));
		System.out.println("test.contains(84) (false): " + test.contains(84));
		System.out.println("test.contains(10) (false): " + test.contains(10));
		System.out.println("test.contains(1000) (false): " + test.contains(1000));
		System.out.println("test.contains(90) (false): " + test.contains(90));
		System.out.println("--------------------------------");
		int sum = 0;
		Iterator<Integer> it = test.iterator( );
		while (it.hasNext( )) {
			sum = sum + it.next( );
		}
		System.out.println("Iterator test, sumation of all Nodes(1973): " + sum);
		System.out.println("--------------------------------");
		System.out.println("Clearing Tree...");
		test.clear();
		System.out.println("Cleared Tree");
		System.out.println("test.size(0): " + test.size());
		System.out.println("test.isEmpty(true): " + test.isEmpty());
		System.out.println("test.contains(86) (false): " + test.contains(86));
		System.out.println("--------------------------------");
	}
	
	@Override
	public boolean add(T t) {
		if (this.isEmpty()) { //if this is an empty tree, new node is the root.
			Node tN = new Node(null, t, null);
			this.root = tN;
			size++; //added a new node to tree, size is 1 greater
			fixHeight(root);
			return true;
		}
		else {
			return addHelper(t, root);
		}
	}
	/*
	 * add Helper handles the add(T) recursion 
	 */
	private boolean addHelper(T t, Node cN) {
		Node tN = new Node(null, t, null);
			if(t.compareTo(cN.item) > 0 && cN.right == null) {
				cN.right = tN;
				size++; //added a new node to tree, size is 1 greater
				fixHeight(root);
				return true;
			}
			else if(t.compareTo(cN.item) < 0 && cN.left == null) {
				cN.left = tN;
				size++; //added a new node to tree, size is 1 greater
				fixHeight(root);
				return true;
			}
			else if (t.compareTo(cN.item) > 0 && cN.right != null) {
				addHelper(t, cN.right);
			}
			else if (t.compareTo(cN.item) < 0 && cN.left != null) {
				addHelper(t, cN.left);
			}
			return false;
	}
	
	@Override
	public void clear() {
		clearHelper(root);
	}
	private void clearHelper(Node r){
		if (r != null) { //Post-Order traverse the tree
			clearHelper(r.left);
			clearHelper(r.right);	
			r.left = null;		//sever (possible) reference to left child, left child removed for Garb. collection
			r.right = null;		//sever (possible) reference to right child, right child removed for Garb. collection
			if (r.equals(root)){ //If r is the tree root
				root = null; 	//make r = null, root (final node) removed for Garbage collection
			}
		}
		size = 0; //tree is cleared, size of tree is now zero...
	}
	
	@Override
	public boolean contains(Object o) {
		if (o == null) {
			throw new NullPointerException();
		}
		return containsHelper(o, root);
	}
	private boolean containsHelper(Object o, Node r) {
		if(r == null) {
			return false;
		}
		else if (r.item.equals(o)) {
			lastCompareCount++;
			return true;
		}
		else if(r.item.compareTo((T)o) > 0) {
			return containsHelper(o, r.left);	
		}
		else {
			return containsHelper(o, r.right);
		}
	}
	
	
	@Override
	public boolean isEmpty() {
		if (this.root == null) {
			return true;
		}
		else {
		return false;
		}
	}
	
	@Override
	public Iterator<T> iterator() {
		Iterator<T> itr = new BSTreeIterator();
		return itr;
	}

	@Override
	public int size() {
		return size;
	}
	
	@Override
	public int getLastCompareCount() {
		return lastCompareCount;
	}
	
	/** 
	 * Get the height set correctly for each node by getting the greatest height of its two
	 * children, adding one to that value and storing the result into the height field of the
	 * given Node parameter.
	 * 
	 * Will be used after an item is added to correct the height for all elements of the new version
	 * of the tree. 
	 * 
	 * @param r The BST Node that is currently having its height value corrected
	 */
	public void fixHeight(Node r) {
		if (r==null) {
			throw new NullPointerException();
		}
		fixHeightHelper(r);
	}
	private void fixHeightHelper(Node r) {
		if (r != null) {
			fixHeightHelper(r.left);
			r.height = maxHeight(r.left, r.right) + 1;
			fixHeightHelper(r.right);
		}
	}
	
	/**
	 * return the largest height value of a parent's two child nodes
	 * 
	 * @param left parent's left child (null if no left)
	 * @param right parent's right child (null if no right)
	 */
	public int maxHeight(Node left, Node right) {
		if (right == null && left == null) {
			return -1;
		}
		else if (right == null) {
			return left.height;
		}
		else if (left == null) {
			return right.height;
		}
		else if (right.height > left.height) {
			return right.height;
		}
		else {
			return left.height;
		}
		
	}
	
	//-------- Internal Classes --------//
	/**
	 * Node class is used to create/manage instances of Node objects.
	 * A node in a BST has left and right child nodes, a data item, and a height value.
	 * 
	 * @author Mitch Fry, Stephen Oliver
	 * 
	 * @version CS260 Lab 3, 07/27/2016 
	 * @LabVersion Lab 3B-CS		
	 * 
	 * @param <T> item of Generic data type 
	 */
	class Node {
		//Fields
		T item;
		int height;
		Node left, right = null;
		
		//Methods
		
		//Constructors (3)
		Node() {
			this(null, null, null);
		}
		
		Node(T item) {
			this(null, item, null);
		}
		
		Node(Node left, T item, Node right) {
			this.left = left;
			this.item = item;
			this.right = right;
			height = 0;
		}	
	}
	
	/**
	 * The iterator class for BSTtree which iterates through the BST in a breadth first manner.
	 * @author Stephen Oliver
	 * @version CS260 Lab 3, 07/27/2016 
	 * @LabVersion Lab 3B-CS	
	 */
	class BSTreeIterator implements Iterator<T>{
		//Fields
		Queue<Node> queue;
		
		//Methods
		
		// constructor, initialize the queue with tree root
		BSTreeIterator () {
			queue = new DLList<Node>();
			queue.add(root);
		}
		
		@Override
		public boolean hasNext() {
			if (queue.isEmpty() != true){
				return true;
			}
			return false;
		}

		@Override
		public T next() {
			Node tN;
			tN = queue.remove();
			if (tN.left != null) {
				queue.add(tN.left);
			}
			if (tN.right != null) {
				queue.add(tN.right);
			}
			return tN.item;
		}	
	}
//-------- UNUSED METHODS FROM IMPLEMENTING Set<T> --------//
	@Override
	public boolean addAll(Collection<? extends T> arg0) {
		// Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> arg0) {
		// Auto-generated method stub
		return false;
	}

	@Override
	public boolean remove(Object arg0) {
		// Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> arg0) {
		// Auto-generated method stub
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> arg0) {
		// Auto-generated method stub
		return false;
	}

	@Override
	public Object[] toArray() {
		// Auto-generated method stub
		return null;
	}

	@SuppressWarnings("hiding")
	@Override
	public <T> T[] toArray(T[] arg0) {
		// Auto-generated method stub
		return null;
	}
}
