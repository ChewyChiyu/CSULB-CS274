/**
	@author Evan Chen
	LinkedList Data structure with Nodes, holds chars.
*/
public class LinkedList{

	private Node head;
	private Node tail = head;	

	private int size = 0;
	/**
		@param char: String to append to end of tail Node
	*/
	public void add(char c){
		Node next = new Node(c);
		if(tail==null){
			head = next;
		}
		else{
			tail.link(next);
		}
		size++;
		tail = next;
	}

	/**
		@param c: char to append at index of list
		@param index: int of index
	*/
	public void add(char c, int index){
		if(index<0||index>size()){
			return;
		}else if(size == 0 || index == size){
			add(c);
		}else if(index == 0){
			Node n = new Node(c);
			n.link(head);
			head = n;
			size++;
		}else{
			Node n = new Node(c);
			Node before = getNodeAt(index-1);
			Node after = getNodeAt(index);
			before.link(n);
			n.link(after);
			size++;
		}
		
	}

	/**
		@return char: head val char
	*/
	public char getHead(){
		return head.getVal();
	}
	/**
		@return char: tail val char
	*/
	public char getTail(){
		return tail.getVal();
	}
	/**
		@return int: size of linked list, or tail index
	*/
	public int size(){
		return size;
	}	

	/**
		@return String: tail val String, decrements list
	*/
	public char pop(){
		if(tail==null){ return ' '; }
		char re = getTail();
		size--;
		if(size==0){
			head = null;
			tail = head;
		}
		else{
			tail = getNodeAt(size-1);
			tail.dropChild();
		}
		return re;
	}

	/**
		@return char: get card at index i, null if not applicable
	*/
	public char getIndex(int i){
		if(tail==null||i>=size()){ return ' '; }
		Node next = head;
		for(int index = 0; index <= i; index++){
			if(index==i){ 
				System.out.println(next);
				return next.getVal(); 
			}
			next = next.getChild(); 
		}
		return ' ';
	}

	/**
		@return Node: get Node at index i, null if not applicable
	*/
	private Node getNodeAt(int i){
		if(tail==null||i>size()){ return null; }
		Node next = head;
		for(int index = 0; index <= i; index++){
			if(index==i){ return next; }
			next = next.getChild(); 
		}
		return null;
	}

	/**
		@return String: readable string of linked list
	*/
	public String toString(){
		StringBuilder br = new StringBuilder();
		Node start = head;
		if(start!=null){
			br.append(""+start.getVal()+"\n");
			while(start.getChild()!=null){
				br.append(""+start.getChild().getVal()+"\n");
				start = start.getChild();
			}
		}
		return br.toString();
	}
	/**	
		@param int: index at which to remove char node from
	*/
	public char removeAt(int index){
		if(tail!=null&&index>size()||index<0){ return ' '; }
		else if(index==size()){ return pop(); }
		else if(index==0){
			char re = getIndex(index);
			head = getNodeAt(1);
			size--;
			return re;
		}else{
			Node before = getNodeAt(index-1);
			char re = getIndex(index);
			Node after = getNodeAt(index+1);
			before.link(after);
			size--;
			return re;
		}
	}
}
	
/**
	Node class for linked List
*/
class Node{

	private Node child;

	private char val;

	/**
		@param char: the main body of the node
	*/
	public Node(char val){
		this.val = val;
	}

	/**
		@return char: getter of char object
	*/
	public char getVal(){
		return val;
	}

	/**
		@return char: getter of child node
	*/
	public Node getChild(){
		return child;
	}

	/**
		@return String: gets readable char of object
	*/
	public String toString(){
		return ""+val;
	}

	/**
		turns child into null
	*/
	public void dropChild(){
		child = null;
	}

	/**
		@param Node: links node to end of this
	*/
	public void link(Node n){
		child = n;
	}	

}