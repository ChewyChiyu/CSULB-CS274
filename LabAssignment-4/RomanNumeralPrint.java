/*
	@author Evan Chen
	Manipulation of linkedlist into arrangements of String characters into Roman Numeral
	Menu included
*/
import java.util.Scanner;
public class RomanNumeralPrint{

	/**
		Basic constructor
	*/

	private LinkedList list;

	public RomanNumeralPrint(){
		list = new LinkedList();
		String prompt = "1- Create an empty list \n2- Insert a node to the list in any position \n3- Remove a node to a list \n4- Display the size of the list \n5- Display the information store at a particular node \n6- Show list's data \n7- Process Roman number expression \n8- Quit";
		menu(prompt);
	}

	public void menu(String prompt){
		System.out.println(prompt);
		switch(validNumQuery(1,8)){
			case 1: //Create an empty list
				System.out.println(" - Creating new list - ");
				list = new LinkedList();
				break;
			case 2: //Insert a node into any position
				System.out.println(" - Input Element of insertion - ");
				char insert = validRomanChar("IVXLCDM");
				System.out.println(" - Input Index of insertion - ");
				list.add(insert,validNumQuery(0,list.size()));
				System.out.println(" - Successful Addition - ");
				break;
			case 3: //Remove a node in a list
				System.out.println(" - Input Element of deletion - ");
				System.out.println(" - Removed " + list.removeAt(validNumQuery(0,list.size())) + " - ");
				break;
			case 4: //Display size of list
				System.out.println(" - List is " + (list.size()) + " long - ");
				break;
			case 5: //Display info at int index node
				System.out.println(" - Input Element of Query - ");
				System.out.println(" - INFO @ Index :"+ list.getIndex(validNumQuery(0,list.size())) + " - ");
				break;
			case 6: //show list data
				System.out.println(" - Printing list " + "\n" + list + " - ");
				break;
			case 7: //process roman numeral 
				process();
				break;
			case 8: //quit
				System.out.println(" - Exiting Program - ");
				System.exit(0);
				break;
		}
		menu(prompt);
	}


	/*
		Process linked list into Roman Numeral
	*/
	public void process(){
		Stack input = new Stack();
		for(int index = list.size()-1; index >= 0 ; index--){
			input.push(""+list.getIndex(index));
		}
		String query = "";
		for(int index = 0; index < list.size(); index++){
			query+=input.pop();
		}
		System.out.println(query);
		RomanNumeral rn = new RomanNumeral(query);
		int n = rn.romanToArabic();
		if(n!=-1){
			System.out.println(" - List Roman Numeral " + n + " - ");
		}else{
			System.out.println(" - Invalid Roman Numeral - ");
		}
	}

	/**
		@param min: int min inclusive of query
		@param max: int max inclusive of query
		@return int: user input of query
	*/
	public int validNumQuery(int min, int max){
		try{
			Scanner scan = new Scanner(System.in);
			int input = scan.nextInt();
			if(input>max||input<min){
				System.out.println(" - Invalid Input Range Error - ");
				return validNumQuery(min,max);
			}else{
				return input;
			}
		}catch(Exception e){
			System.out.println(" - Invalid Input - ");
			return validNumQuery(min,max);
		}
	}

	/**
		@param validChars: String contains valid roman nums
		@return char: valid roman numeral char
	*/
	public char validRomanChar(String validChars){
		try{
			Scanner scan = new Scanner(System.in);
			char input = scan.next().charAt(0);
			if(!validChars.contains(""+input)){
				System.out.println(" - Invalid Input Range Error - ");
				return validRomanChar(validChars);
			}else{
				return input;
			}
		}catch(Exception e){
			System.out.println(" - Invalid Input - ");
			return validRomanChar(validChars);
		}
	}

	/**
		JVM instance
	*/
	public static void main(String[] args){
		new RomanNumeralPrint();
	}
}