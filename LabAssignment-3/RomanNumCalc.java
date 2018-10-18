import java.util.Scanner;
public class RomanNumCalc{
	
	/**
		Constructor to load self instance of Class
	*/
	public RomanNumCalc(){
		String[] query = validStringQuery();
		String result = "";
		for(String s : query){ System.out.print(s + " "); }
		switch(query[1]){
			case "-":
				int n = romanToArabic(query[0])-romanToArabic(query[2]);
				result = arabicToRoman(Math.abs(n));
				if(n<0){
					result = "-"+result;
				}else if(n==0){
					result = "nulla";
				}
				break;
			case "+":
				result = arabicToRoman(romanToArabic(query[0])+romanToArabic(query[2]));
				break;
			case "*":
				result = arabicToRoman(romanToArabic(query[0])*romanToArabic(query[2]));
				break;
		}
		System.out.print("= " + result);
		System.out.println();
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
		Stack stackRoman = new Stack();
		int result = 0;
		while(romanQueue.size()>0){
			String next = romanQueue.pop();
			if((stackRoman.size()==0&&romanQueue.size()==0)){
				result+=singleRomanToArabic(next);
			}else if(stackRoman.size()==0&&romanQueue.size()>0){
				stackRoman.push(next);
			}else if(singleRomanToArabic(stackRoman.peek())<singleRomanToArabic(next)){
				result+=(singleRomanToArabic(next)-singleRomanToArabic(stackRoman.pop()));
			}else if(romanQueue.size()>0&&(singleRomanToArabic(stackRoman.peek())>=singleRomanToArabic(next))){
				result+=singleRomanToArabic(stackRoman.pop());
				stackRoman.push(next);
			}else if((singleRomanToArabic(stackRoman.peek())>=singleRomanToArabic(next))){
				result+=singleRomanToArabic(stackRoman.pop());
				result+=singleRomanToArabic(next);
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
		String[] query = new String[3]; 
		System.out.println("Please Input First Roman Numeral");
		query[0] = validRomanQuery();
		query[1] = validOperandQuery();
		System.out.println("Please Input Second Roman Numeral");
		query[2] = validRomanQuery();
		return query;
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
		@return String of a valid Roman Numeral
	*/
	public String validRomanQuery(){
		try{
			Scanner scan = new Scanner(System.in);
			String input = scan.next();
			boolean valid = true;
			//checking for correct characters
			char prev =  ' ';
			int inARow = 0;
			int ascending = 0;
			for(char s : input.toCharArray()){
				if(s!='I'&&s!='V'&&s!='X'&&s!='L'&&s!='C'&&s!='D'&&s!='M'){valid = false;}
				if(inARow>=1&&(singleRomanToArabic(""+s)>singleRomanToArabic(""+prev))){valid=false;}
				if(prev!=' '&&s==prev){ inARow++; }else{inARow=0;}
				if(prev!=' '&&(singleRomanToArabic(""+s)>singleRomanToArabic(""+prev))){ascending++;}else{ascending=0;}
				if(ascending>=2){ valid = false; }
				if(inARow>2){ valid = false; }
				if(inARow>=1&&(s!='I'&&s!='X'&&s!='C'&&s!='M')){valid = false;}
				if(prev!=' '&&(singleRomanToArabic(""+s)>singleRomanToArabic(""+prev)*10)){valid=false;}
				if(prev!=' '&&(singleRomanToArabic(""+s)>singleRomanToArabic(""+prev))&&(prev!='I'&&prev!='X'&&prev!='C'&&prev!='M')){valid=false;}
				prev = s;
			}
			if(valid){
				return input;
			}else{
				System.out.println("Invalid Roman Numeral");
				return validRomanQuery();
			}
		}catch(Exception e){
			System.out.println("Invalid Input");
			return validRomanQuery();
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