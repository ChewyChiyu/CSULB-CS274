import java.util.Scanner;
/**
	@author Evan Chen
	Pushes and Pops 5 random Card objects from Deck from Stack
*/
public class CardStack{

	/**
		Creates new Deck, Stack object, runs loop 5 incrememts and adds random card
		from shuffled Deck to Stack, Pops all objects in stack call
	*/
	public CardStack(){
		System.out.println("Welcone to Stack Testing Program");
		while(query()){
			run();
		}
		System.out.println("Thank you and Goodbye");
	}

	public boolean query(){
		try{
			Scanner scan = new Scanner(System.in);
			System.out.print("Would you like another run (Y/N)?");
			String s = scan.next();
			System.out.println();
			if(s.equalsIgnoreCase("y")||s.equalsIgnoreCase("n")){
				return s.equalsIgnoreCase("y");
			}else{
				System.out.println("Invalid Input");
				return query();
			}
		}catch(Exception e){
			System.out.println("Invalid Input");
			return query();
		}
	}

	public void run(){
		Deck deck = new Deck();
		deck.shuffle();
		Stack<Card> stack = new Stack<Card>();
		for(int index = 0; index < 5; index++){
			push(stack,deck.deal());
		}
		System.out.println();
		for(int index = 1; index <= 5; index++){
			System.out.println(index+"- "+stack.pop()+"- we have "+stack.size()+" cards left");
		}
	}

	/**
		@param Stack<Card> stack: stack of cards
		@param Card c: Card to add to stack
	*/
	public void push(Stack<Card> stack, Card c){
		stack.push(c);
		System.out.println(stack.size()+"- pushing "+c);
	}

	/** JVM Initializer */
	public static void main(String[] args){
		new CardStack();
	}
}
/**
	Self scaling generic Stack data structire. Always starts with one value
*/
class Stack<E>{


	private E[] arr = (E[])new Object[1]; // at least one value	

	/**	
		@param E element: Generic element e to be added in stack, self increment++
	*/
	public void push(E element){
		E[] next = (E[])new Object[arr.length+1];
		for(int index = 0; index < arr.length; index++){
			next[index] = arr[index];
		}
		next[next.length-1] = element;
		arr = next;
	}

	/**	
		@return E: element on top of stack;
	*/
	public E pop(){
		E back = arr[arr.length-1];
		E[] next = (E[])new Object[arr.length-1];
		for(int index = 0; index < next.length; index++){
			next[index] = arr[index];
		}
		arr = next;
		return back;
	}
	 
	public int size(){ return arr.length-1; }
}

/*
dhcp-39-146-249:sandbox evan$ javac CardStack.java
dhcp-39-146-249:sandbox evan$ javac CardStack.java
dhcp-39-146-249:sandbox evan$ java CardStack
1 pushing 8 of spades
2 pushing 2 of hearts
3 pushing jack of clubs
4 pushing 4 of hearts
5 pushing 10 of hearts
dhcp-39-146-249:sandbox evan$ javac CardStack.java
dhcp-39-146-249:sandbox evan$ java CardStack
1- pushing 5 of hearts
2- pushing queen of hearts
3- pushing 7 of hearts
4- pushing king of hearts
5- pushing queen of clubs
1- queen of clubs- we have 4 cards left
2- king of hearts- we have 3 cards left
3- 7 of hearts- we have 2 cards left
4- queen of hearts- we have 1 cards left
5- 5 of hearts- we have 0 cards left
dhcp-39-146-249:sandbox evan$ 
*/