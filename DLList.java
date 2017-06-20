package edu.wou.cs260.SpellChecker;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import java.util.NoSuchElementException;
import java.util.Queue;

/**
 *  DLList<T> is a custom linked list structure NOT implementing java List
 *  Since DLList is generic it can be used to store any data type.
 *  DLList is used to create Doubly Linked Lists.
 * 
 * @author Stephen K Oliver
 * @version CS260 Lab 2, 07/13/2016 
 * @LabVersion Lab 2-CS
 */
public class DLList<T> implements List<T>, CompareCount, Queue<T>{
	private DLLNode head, tail, curNode;
	private int lastCompareCount = 0;
	
	
	/**
	 * Main method used as entry point for initial testing of DLList class features
	 */
	public static void main (String[ ] args) {
		/*Queue<Integer> qTest = new DLList<Integer>();
		
		System.out.println("Empty Queue.isEmpty(true): " + qTest.isEmpty());
		System.out.println("Empty Queue.peek(null): " + qTest.peek());
		System.out.println("Empty Queue.poll(null): " + qTest.poll());
		System.out.println("----------------------------------------------");
		System.out.println("Enqueue 10 to tail(true): " + qTest.offer(10));
		System.out.println("{10} Queue.peek(10): " + qTest.peek());
		System.out.println("Empty Queue.isEmpty(false): " + qTest.isEmpty());
		System.out.println("Enqueue 10 to tail(true): " + qTest.offer(20));
		System.out.println("{10,20} Queue.peek(10): " + qTest.peek());
		System.out.println("Enqueue 10 to tail(true): " + qTest.offer(30));
		System.out.println("{10,20,30} Queue.peek(10): " + qTest.peek());
		System.out.println("Enqueue 10 to tail(true): " + qTest.offer(40));
		System.out.println("{10,20,30,40} Queue.peek(10): " + qTest.peek());
		Iterator<Integer> itr = qTest.iterator();
		int sum = 0;
		while(itr.hasNext()) {
			sum += itr.next();
		}
		System.out.println("QUEUE ITERATOR TEST(100): " + sum);
		System.out.println("----------------------------------------------");
		System.out.println("{10,20,30,40}QUEUE.peek(10): " +qTest.peek());
		System.out.println("{10,20,30,40}QUEUE.poll(10): " +qTest.poll());
		System.out.println("{20,30,40}QUEUE.peek(20): " +qTest.peek());
		System.out.println("{20,30,40}QUEUE.poll(20): " +qTest.poll());
		System.out.println("{30,40}QUEUE.peek(30): " +qTest.peek());
		System.out.println("{30,40}QUEUE.poll(30): " +qTest.poll());
		System.out.println("{40}QUEUE.peek(40): " +qTest.peek());
		System.out.println("{40}QUEUE.poll(40): " +qTest.poll());*/
		
	}
		
	/**
	 * Add element to the nth index of the list (addToEnd)
	 * I.E. the new element becomes the new tail of the list
	 * 
	 * @param d generic data element to be added to the list
	 */
	public boolean add(T d) {
		DLLNode tN;
		
		if (d == null) {
			throw new NullPointerException();
		}
		
		if (tail == null) {
			tN = new DLLNode(d);
			head = tN;
		}
		else {
			tN = new DLLNode(tail, d, null);
			tail.next = tN;
		}
		tail = tN;
		
		return true;
	}
	
	/**
	 * Add an element a given position in the list.
	 * 
	 * @param pos An integer indicating at what position in the list the element should be placed
	 * @param d generic data element to be added to the list
	 */
	public void add(int pos, T d) {
		curNode = head;
		DLLNode pNode = null;
		DLLNode tN;
		
		if (d == null) {
			throw new NullPointerException();
		}
		
		else if (pos < 0 || pos > this.size()) {
			throw new IndexOutOfBoundsException();
		}
		
		
		for (int i = 0; i < pos; i++) {
			curNode = curNode.next;
		}
		if (this.isEmpty()){
			this.add(d);
		}
		else if (pos == this.size()) {
			this.add(d);
		}
		else if (curNode.equals(head)){			//TOP
			tN = new DLLNode(null, d, curNode);
			curNode.previous = tN;
			head = tN;
		}
		else if (curNode.equals(tail)){		//BOTTOM
			tN = new DLLNode(curNode, d, null);
			curNode.next = tN;
			tail = tN;
		}
		else {								//MIDDLE
			pNode = curNode.previous;
			tN = new DLLNode(pNode, d, curNode);
			pNode.next = tN;
			curNode.previous = tN;
		}
	}
	
	/**
	 * Remove all elements for a list
	 * 
	 */
	public void clear() {
		int k = this.size();
		for(int i = 0; i < k; i++) {
			this.remove(0);
		}
	}
	
	public int getLastCompareCount() {
		return lastCompareCount;
	}
	
	/**
	 * Check to see if the list has the given element stored
	 * We do not assume that the list is sorted.
	 * 
	 * @param o Object to be searched for
	 * @return Return true if the object is found
	 */
	public boolean contains(Object o) {
		lastCompareCount = 0;
		DLLNode c = head;
		if (o == null) {
			throw new NullPointerException();
		}
		while (c != null) {
			if(c.data.equals(o)) {
				return true;
			}
			lastCompareCount++;
			c = c.next;
		}
		return false;
	}
	
	/**
	 * Get the element at the specified location if the requested location is within the list.
	 * 
	 * @param pos The given location of the node in the list to return
	 * @return The element at the requested location in the list.
	 */
	public T get(int pos){
		DLLNode curNode = head;
		
		if (pos < 0 || pos >= this.size()){
			throw new IndexOutOfBoundsException();
		}
		
		for (int i = 0; i < pos; i++) {
			curNode = curNode.next;
		}
		
		return curNode.data;
	}
	
	/**
	 * Discover if the list contains any elements
	 * 
	 * @return Return true is the list contains no elements (isEmpty?)
	 */
	public boolean isEmpty() {
		if (this.size() == 0)
			return true;
		return false;
	}
	
	/**
	 * Returns an iterator over the elements in this list in proper sequence.
	 * 
	 * @return an iterator over the elements in this list in proper sequence
	 */
	@Override
	public Iterator<T> iterator() {
		return new DLLIterator();
	}
	
	/**
	 * Remove the FIRST node found which holds the data specified.
	 * We do not assume that the list is sorted.
	 * 
	 * @param d generic data element to be removed
	 * @return true if element is removed
	 */
	public boolean remove(Object o) {
		DLLNode tN = head;
		for (int i = 0; i < this.size(); i++) {
			if(o.equals(tN.data)) { //GOT IT!
				this.remove(i);		//KILLED IT!
				return true;		//END
			}
			tN = tN.next;			// move to the next node
		}
		return false;			//Obj not found, end
	}
	
	/**
	 * Remove the node at the specified position in the list
	 * 
	 * @param pos The position in the list
	 * @return 
	 */
	public T remove(int pos) {
		 curNode = head;
		 DLLNode nNode;
		 DLLNode pNode;
		 T d;
		
		 if (pos < 0 || pos >= this.size()) {
			 throw new IndexOutOfBoundsException();
		 }
		 
		for (int i = 0; i < pos; i++){ //get into position
			curNode = curNode.next;
		}
		if (this.size() == 1) { //if the list is of only one node
			head = null;
			tail = null;
			d = curNode.data;
			
		}
		else if (curNode.equals(head)) {
			nNode = curNode.next;
			head = nNode;
			nNode.previous = null;
			d = curNode.data;
			
			curNode.next = null;
			curNode.previous = null;
			curNode = null;
		}
		else if (curNode.equals(tail)) {
			pNode = curNode.previous;
			tail = pNode;
			pNode.next = null;
			d = curNode.data;
			
			curNode.next = null;
			curNode.previous = null;
			curNode = null;
		}
		else {
			DLLNode p = curNode.previous;
			DLLNode n = curNode.next;
			p.next = n;
			n.previous = p;
			d = curNode.data;
			
			curNode.next = null;
			curNode.previous = null;
			curNode = null;
			
		}
		return d;
	}
	
	/**
	 * determine the size (length) of the list
	 * 
	 * @return Return an integer representing the size (length) of the list.
	 */
	public int size() {
		int size = 0;
		
		if (head==null && tail==null)
			return size;
		
		for(DLLNode c = head; c.next != null; c = c.next) { //So long as the current node has a next node
			size++;											   //Add 1 to the list size
		}
		size++;
		return size;
	}
	
	/**
	 * Prints every element's data field in the list.
	 */
	public void printList() {
		DLLNode c;
		
		System.out.println(">>>>>>>>>>>>>>>>>");
		if (this.isEmpty()) {
			//Do nothing....
		}
		else {
		for(c = head; c.next != null; c = c.next) {
			System.out.println("ELEMENT DATA: " + c.data);
		}
		System.out.println("ELEMENT DATA: " + c.data);
		}
	}
	
	/*
	 * Queue implementations...
	 */
	
	//Add(T t) is implemented earlier in code
	
	/**
	 * Retrieves, but does not remove, the head of this queue.
	 * 
	 * @return The data from element retrieved 
	 */
	@Override
	public T element() {
		DLLNode tN = head;
		if(this.isEmpty()){
			throw new NoSuchElementException();
		}
		return tN.data;
	}

	/**
	 * Inserts the specified element into this queue if it is possible 
	 * to do so immediately without violating capacity restrictions.
	 * (Enqueue)
	 * 
	 * @param t
	 * @return True upon success of adding element
	 */
	@Override
	public boolean offer(T t) {
		if (this.add(t)) {
			return true;
		}
		return false;
	}

	/**
	 * Retrieves, but does not remove, the head of this queue, or returns null if this queue is empty.
	 * 
	 * @return data of head of queue, null if empty
	 */
	@Override
	public T peek() {
		if (this.isEmpty()) {
			return null;
		}
		else  {
			return this.element();
		}
	}

	/**
	 * Retrieves and removes the head of this queue, or returns null if this queue is empty.
	 * (Dequeue)
	 * 
	 * @return data of head of queue, null if empty
	 */
	@Override
	public T poll() {
		if (this.isEmpty()) {
			return null;
		}
		return this.remove();
	}

	/**
	 * Retrieves and removes the head of this queue.
	 * 
	 * @return Data from element to be removed
	 */
	@Override
	public T remove() {
		if (this.isEmpty()) {
			throw new NoSuchElementException();
		}
		T d = this.get(0);
		this.remove(0);
		
		return d;
	}
	
	
	/**
	 *  DLLNode is a custom linked list Node structure NOT implementing java.util.List
	 *  DLLNode uses generic typing for stored data in the node and itself as a reference
	 *  to what is next in the Double linked list.
	 *  
	 *  @author Mitchel Fry, Jie Liu & Stephen Oliver
	 *  @version  07/09/2016
	 */
	class DLLNode {
	// fields
		DLLNode previous;
		T data;
		DLLNode next;
		
	//Methods
		//3 constructors
		
		//base constructor
		DLLNode() {
			this(null, null, null);
		}
		
		 DLLNode(T d) {
				this(null, d, null);
			 }

	
		//Constructor adding new element.
		DLLNode(DLLNode p, T d, DLLNode n) {
			previous = p;
			data = d;
			next = n;
		}
	}
	
	class DLLIterator implements Iterator<T> {
		//----Fields----//
		DLLNode cN = head;
		int c = 0;
		
		/**
		 * Method hasNext() checks if the current node has another node next and returns true if there is another
		 * node in the list and false if the current node (cN) is the last node in the list
		 * 
		 * @return boolean value indicating if there is or is not a node after the current node
		 */
		@Override
		public boolean hasNext() {
			if (cN.next== null){
				return false; //there is no more stuff
			}
			return true;  //there is more stuff
		}

		/**
		 *  Method next() grabs the data from the current node to return, then sets the current node to the
		 *  next node in the list.
		 *  
		 * @return data on the node
		 */
		@Override
		public T next() {
			T stuff;
			if (cN != null) {
				if (c == 0) { 		  //if we ran this only once since creation of iterator (pos 1)
					stuff = cN.data;  //get the current node's data
					c++; 			  //add to the counter to show not first run anymore
				}			  	      //Do NOT move to next node yet
				else  {				  //We got the first element, now we can move on
					cN = cN.next;     //set current node to the next node
					stuff = cN.data;  //get the next node's data
				}
				return stuff;		  //return node data
			}
			else {
				throw new NoSuchElementException();
			}
		}
	}
	/* -------------------------------------------------------------------------------------------------------------------------
	 * The following Method Stubs were added to appease the Eclipse Gods, for this project/Lab we will not
	 * be writing body code for them.
	 ---------------------------------------------------------------------------------------------------------------------------*/
	public boolean addAll(Collection<? extends T> c) {
		//Auto-generated method stub
		return false;
	}

	
	public boolean addAll(int index, Collection<? extends T> c) {
		//Auto-generated method stub
		return false;
	}

	
	public boolean containsAll(Collection<?> c) {
		//Auto-generated method stub
		return false;
	}

	
	public int indexOf(Object o) {
		//Auto-generated method stub
		return 0;
	}

	
	public int lastIndexOf(Object o) {
		//Auto-generated method stub
		return 0;
	}

	
	public ListIterator<T> listIterator() {
		//Auto-generated method stub
		return null;
	}

	
	public ListIterator<T> listIterator(int index) {
		//Auto-generated method stub
		return null;
	}

	
	public boolean removeAll(Collection<?> c) {
		//Auto-generated method stub
		return false;
	}

	
	public boolean retainAll(Collection<?> c) {
		//Auto-generated method stub
		return false;
	}

	
	public T set(int index, T element) {
		//Auto-generated method stub
		return null;
	}

	
	public List<T> subList(int fromIndex, int toIndex) {
		//Auto-generated method stub
		return null;
	}

	
	public Object[] toArray() {
		//Auto-generated method stub
		return null;
	}

	
	@SuppressWarnings("hiding")
	public <T> T[] toArray(T[] a) {
		//Auto-generated method stub
		return null;
	}	
}