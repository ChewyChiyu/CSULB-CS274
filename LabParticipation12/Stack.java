/**
	@author Evan Chen
	ADT Stack class that pushes and pops String data type. Upper 
	Bound limited to STACK_UPPER_INDEX, initialy set at 100. Push and Pop at
	time complexity constant, space is also held constant
*/
public class Stack{

	public final static int STACK_UPPER_INDEX = 100;

	private String[] pile = new String[STACK_UPPER_INDEX];

	private int stackSize = 0;




	/**
		@param s: String to add to Stack, auto increment of stackSize
	*/
	public void push(String s){
		if(stackSize<STACK_UPPER_INDEX)
			pile[stackSize++] = s;
		else
			System.out.println("Stack Index Out Of Bounds Exception.");
	}

	/**
		@return String: the String element on top of Stack, at index stackSize with auto decrement
	*/
	public String pop(){
		if(stackSize>0)
			return pile[--stackSize];
		else
			System.out.println("Stack Index Out Of Bounds Exception.");
			return null;
	}

	/**
		@return String: the String element on top of Stack, at index stackSize
	*/
	public String peek(){
		return pile[stackSize-1];
	}

	/**
		@return int: returns the stackSize
	*/
	public int size(){	
		return stackSize;
	}

	/**
		@return the elements of the Stack in printable format
	*/
	public String toString(){
		StringBuilder sb = new StringBuilder();
		for(int index = 0; index < stackSize; index++){
			sb.append(pile[index]);
			if(index!=stackSize-1){
				sb.append(", ");
			}
		}
		return sb.toString();
	}
}