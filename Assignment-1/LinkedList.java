/**
	@author Evan Chen
	LinkedList Data structure with Nodes, holds Card Objects.
*/
public class LinkedList{

	private Node head;
	private Node tail = head;	


	/**
		@param Card: card to append to end of tail Node
	*/
	public void add(Card c){
		Node next = new Node(c);
		if(tail==null){
			head = next;
		}
		else{
			tail.link(next);
		}
		tail = next;
	}


	/**
		@return Card: head val card
	*/
	public Card getHead(){
		return head.getVal();
	}
	/**
		@return Card: tail val card
	*/
	public Card getTail(){
		return tail.getVal();
	}
	/**
		@return int: size of linked list, or tail index
	*/
	public int size(){
		return tail.getIndex();
	}	

	/**
		@return Card: tail val card, decrements list
	*/
	public Card pop(){
		Node start = head;
		
		if(start!=null){
			if(size()==0){
				Card str = start.getVal();
				head = null;
				tail = start;
				tail.dropChild();
				return str;
			}	
			while(start.getChild()!=null){
				if(start.getIndex()+1==size()){
					Card str = start.getChild().getVal();
					tail = start;
					tail.dropChild();
					return str;
				}
				start = start.getChild();
			}
		}
		return null;
	}

	/**
		@return Card: get card at index i, null if not applicable
	*/
	public Card getIndex(int i){
		if(tail==null||i>size()){ return null; }
		Node next = head;
		for(int index = 0; index <= i; index++){
			if(index==i){ return next.getVal(); }
			next = next.getChild(); 
		}
		return null;
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
			br.append(start.getVal()+"\n");
			while(start.getChild()!=null){
				br.append(start.getChild().getVal()+"\n");
				start = start.getChild();
			}
		}
		return br.toString();
	}
	/**	
		@param int: index at which to remove card node from
	*/
	public Card removeAt(int index){
		if(tail!=null&&index>size()||index<0){ return null; }
		else if(index==0||index==size()){ return pop(); }
		else{
			Node before = getNodeAt(index-1);
			Card re = getIndex(index);
			Node after = getNodeAt(index+1);
			for(int i = index+1; i < size(); i++){
				getNodeAt(i).setIndex(getNodeAt(i).getIndex()-1);
			}
			before.link(after);
			return re;
		}
	}
}
	
/**
	Node class for linked List
*/
class Node{

	private Node child;

	private Card val;

	private int index = 0;

	/**
		@param Card: the main body of the node
	*/
	public Node(Card val){
		this.val = val;
	}

	/**
		@return Card: getter of card object
	*/
	public Card getVal(){
		return val;
	}

	/**
		@return Card: getter of child node
	*/
	public Node getChild(){
		return child;
	}

	/**
		@return String: gets readable string of object
	*/
	public String toString(){
		return val.toString();
	}

	/**
		turns child into null
	*/
	public void dropChild(){
		child = null;
	}

	/**
		@param int: setting index in linkedlist 
	*/
	public void setIndex(int i){
		index = i;
	}

	/**
		@return int: gets index primitive
	*/
	public int getIndex(){
		return index;
	}

	/**
		@param Node: links node to end of this
	*/
	public void link(Node n){
		child = n;
		n.setIndex(index+1);
	}	


}