/**
	@author Evan Chen
	ADT Queue class that pushes and pops String data type. Upper 
	Bound limited to QUEUE_UPPER_INDEX, initialy set at 100. Push and Pop at
	time complexity constant, space is also held constant
*/
public class Queue{

	public static final int QUEUE_UPPER_INDEX = 100;

	private int queueSize = 0;
	private String[] pile = new String[QUEUE_UPPER_INDEX];


	/**
		@param s: String to add to Queue, auto increment of queueSize
	*/
	public void push(String s){
		if(queueSize<QUEUE_UPPER_INDEX)
			pile[queueSize++] = s;
		else
			System.out.println("Queue Out Of Bounds Exception.");
	}

	/**
		@return String: the String element on top of Queue, at index queueSize with auto decrement
	*/
	public String pop(){
		if(queueSize>0){
			String s = peek();
			for(int index = 1; index < queueSize; index++){
				pile[index-1] = pile[index];
			}
			queueSize--;
			return s;
		}else{
			System.out.println("Queue Out Of Bounds Exception.");
			return null;
		}
	}

	/**
		@return String: the String element on top of Queue, at index 0
	*/
	public String peek(){
		return pile[0];
	}

	/**
		@return String: the String element on bottom of Queue, at index queueSize
	*/
	public String top(){
		return pile[queueSize-1];
	}

	/**
		@return int: returns the queueSize
	*/
	public int size(){
		return queueSize;
	}
	/**
		@return the elements of the Queue in printable format
	*/
	public String toString(){
		StringBuilder sb = new StringBuilder();
		for(int index = 0; index < queueSize; index++){
			sb.append(pile[index]);
			if(index!=queueSize-1){
				sb.append(", ");
			}
		}
		return sb.toString();
	}
}