import java.util.Scanner;
public class RomanNumCalc{
	
	/**
		Constructor to load self instance of Class
	*/
	public RomanNumCalc(){
		System.out.println(arabicToRoman(2817));
	}

	/**
		@param n: integer below 3999
		@return String: conversion from arabic to roman
	*/
	public String arabicToRoman(int n){
		StringBuilder sb = new StringBuilder();
		int remainder = n;
		int divisor = remainder;
		while(remainder!=0){
			String close = arabicToNearRoman(remainder);
			remainder%=romanToArabic(close);
			divisor/=romanToArabic(close);
			for(int index = 0; index < divisor; index++){
				sb.append(close);
			}
			divisor = remainder;
		}
		return sb.toString();
	}

	/**
		@param rn: String containing Roman Numberal
		@return int: arabic number converted from String roman numberal
	*/
	public int romanToArabic(String rn){
		Queue romanQueue = stringToQueue(rn);
		Queue prevItemQueue = new Queue(); 
		Stack stackRoman = new Stack();
		int result = 0;
		while(romanQueue.size()>0){
			String next = romanQueue.pop();
			if((stackRoman.size()==0&&romanQueue.size()==0)){
				result+=singleRomanToArabic(next);
			}else if(stackRoman.size()==0&&romanQueue.size()>0){
				stackRoman.push(next);
			}else if(singleRomanToArabic(stackRoman.peek())<singleRomanToArabic(next)){
				result+=(singleRomanToArabic(next)-singleRomanToArabic(stackRoman.peek()));
				prevItemQueue.push(stackRoman.pop());
			}else if(romanQueue.size()>0&&(singleRomanToArabic(stackRoman.peek())>=singleRomanToArabic(next))){
				result+=singleRomanToArabic(stackRoman.peek());
				prevItemQueue.push(stackRoman.pop());
				stackRoman.push(next);
			}else if((singleRomanToArabic(stackRoman.peek())>=singleRomanToArabic(next))){
				result+=singleRomanToArabic(stackRoman.peek());
				prevItemQueue.push(stackRoman.pop());
				result+=singleRomanToArabic(next);
				prevItemQueue.push(next);
			}
		}
		return result;
	}

	public Queue stringToQueue(String str){
		Queue queue = new Queue();
		for(char c : str.toCharArray()){
			queue.push(""+c);
		}
		return queue;
	}


	/**
		@param rn: String of single roman numeral
		@return int: arabic int of single roman numeral, -1 if not found
	*/
	public int singleRomanToArabic(String rn){
		switch(rn){
			case "I": return 1;
			case "V": return 5;
			case "X": return 10;
			case "L": return 50;
			case "C": return 100;
			case "D": return 500;
			case "M": return 1000;
		}
		return -1;
	}

	/**
		@param n: integer less than 3999
		@return String: roman numeral closest to integer n
	*/
	public String arabicToNearRoman(int n){
		if(n>=1000){return "M";}
		else if(n>=900){return "CM";}
		else if(n>=500){return "D";}
		else if(n>=400){return "CD";}
		else if(n>=100){return "C";}
		else if(n>=90){return "XC";}
		else if(n>=50){return "L";}
		else if(n>=40){return "XL";}
		else if(n>=10){return "X";}
		else if(n>=9){return "IX";}
		else if(n>=5){return "V";}
		else if(n>=4){return "IV";}
		else {return "I";}
	}

	/**
		@return String[]: proper readable parsed String[] from the user with Scanner Object
	*/
	public String[] validStringQuery(){
		try{
			Scanner scan = new Scanner(System.in);
			String[] query = new String[3]; 
			System.out.println("Please Input First Roman Numeral");
			query[0] = scan.next();
			query[1] = validOperandQuery();
			System.out.println("Please Input Second Roman Numeral");
			query[2] = scan.next();
			return query;
		}catch(Exception e){
			System.out.println("Invalid Input");
			return validStringQuery();
		}
	}

	/**
		@return String[]: proper readable String from the user with Scanner Object
	*/
	public String validOperandQuery(){
		try{
			Scanner scan = new Scanner(System.in);
			System.out.println("Please Input Operand (+,-,*)");
			String input = scan.next();
			if(!input.equals("*")&&!input.equals("-")&&!input.equals("+")){
				System.out.println("Invalid Operand");
				return validOperandQuery();
			}else{
				return input;
			}
		}catch(Exception e){
			System.out.println("Invalid Input");
			return validOperandQuery();
		}
	}

	/**
		@param args: String[] from initial launch
		JVM Instance
	*/
	public static void main(String[] args){
		new RomanNumCalc();
	}





}