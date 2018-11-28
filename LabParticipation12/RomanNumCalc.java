import java.util.Scanner;
import javax.swing.*;
import java.awt.*;

public class RomanNumCalc extends JPanel{
	

	private Dimension windowDim = new Dimension(500,100);

	/**
		Constructor to load self instance of Class
	*/
	public RomanNumCalc(){
		panel();
	}


	public boolean validate(String query){		
		query = query.replace("+",",+,");
		query = query.replace("-",",-,");
		query = query.replace("*",",*,");
		String[] queryArr = query.split(",");
		String[] realQueryArr = new String[3];
		int index = 0;
		for(String s : queryArr){
			if(index>=realQueryArr.length){
				System.out.println(" - Invalid Expression- ");
				return false;
			}
			if(!s.trim().equals("")){
				realQueryArr[index++] = s;
			}
		}
		queryArr = realQueryArr;
			

		if(queryArr.length!=3 || !validRomanQuery(queryArr[0]) || !validRomanQuery(queryArr[2]) || !validOperandQuery(queryArr[1])){
			System.out.println(" - Invalid Expression - ");
			return false;
		}


		return true;
	}


	public String[] toQuery(String query){
		query = query.replace("+",",+,");
		query = query.replace("-",",-,");
		query = query.replace("*",",*,");
		String[] queryArr = query.split(",");
		String[] realQueryArr = new String[3];
		int index = 0;
		for(String s : queryArr){
			if(index>=realQueryArr.length){
				System.out.println(" - Invalid Expression- ");
				return null;
			}
			if(!s.trim().equals("")){
				realQueryArr[index++] = s;
			}
		}
		queryArr = realQueryArr;
			
		if(queryArr.length>3){
			System.out.println(" - Invalid Expression - ");
			return null;
		}
		return queryArr;
	}

	public boolean calculate(String eq){
		if(!validate(eq)){
			return false;
		}
		String result = "";
		String[] query = toQuery(eq);
		String print = "";
		for(String s : query){ print+=(s + " ");}
		switch(query[1]){
			case "-":
				int difference = romanToArabic(query[0])-romanToArabic(query[2]);
				result = arabicToRoman(Math.abs(difference));
				if(difference<0){
					result = "NEGATIVE "+result;
				}else if(difference==0){
					result = "nulla";
				}else if(difference>3999){
					result = "OVERFLOW";
				}
				break;
			case "+":
				int sum = romanToArabic(query[0])+romanToArabic(query[2]);
				result = arabicToRoman(sum);
				if(sum > 3999){
					result = "OVERFLOW";
				}
				break;
			case "*":
				int product = romanToArabic(query[0])*romanToArabic(query[2]);
				result = arabicToRoman(product);
				if(product > 3999){
					result = "OVERFLOW";
				}
				break;
		}
		JOptionPane.showMessageDialog(null, print + "= " + result);
		return true;
	}


	public void panel(){
		JFrame frame = new JFrame("Roman Numeral");
		frame.add(this);
		this.setLayout(new FlowLayout());

		JLabel queryLabel = new JLabel("Please Input In Format Upper Case ex: III + VI ");
		this.add(queryLabel);

		JTextArea queryArea = new JTextArea(1,20);
		this.add(queryArea);

		JButton validate = new JButton("Valiate");
		validate.addActionListener(e -> {
			String query = queryArea.getText();
			if(validate(query)){
				JOptionPane.showMessageDialog(null,"Valid Roman Expression.");
			}else{ 
				JOptionPane.showMessageDialog(null,"Invalid Roman Expression","Bad Expression",JOptionPane.ERROR_MESSAGE);
			}
		});
		this.add(validate);
		JButton execute = new JButton("Execute");
		execute.addActionListener(e -> {
			String query = queryArea.getText();
			if(!calculate(query)){
				JOptionPane.showMessageDialog(null,"Invalid Roman Expression","Bad Expression",JOptionPane.ERROR_MESSAGE);
			}
		});
		this.add(execute);

		frame.setPreferredSize(windowDim);
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		try{
			Scanner scan = new Scanner(System.in);
			System.out.println("Please Input In Format Upper Case ex: III + VI ");
			String raw = scan.nextLine();
			String[] rawArr = raw.split(" ");
			boolean valid = true;
			if(rawArr.length>3){ valid = false; }
			else if(!validOperandQuery(rawArr[1])||!validRomanQuery(rawArr[0])||!validRomanQuery(rawArr[2])){ valid = false; }
			if(valid){
				return rawArr;
			}else{
				System.out.println("Invalid Expression");
				return validStringQuery();
			}
		}catch(Exception e){
			System.out.println("Invalid Expression");
			return validStringQuery();
		}
	}

	/**
		@return String[]: proper readable String from the user with Scanner Object
	*/
	public boolean validOperandQuery(String op){
		
		if(!op.equals("*")&&!op.equals("-")&&!op.equals("+")){
			System.out.println("Invalid Operand");
			return false;
		}else{
			return true;
		}
	}	

	/**
		@return String of a valid Roman Numeral
	*/
	public boolean validRomanQuery(String rn){
		return rn.matches("\\bM{0,4}(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})\\b");
		// boolean valid = true;
		// //checking for correct characters
		// char prev =  ' ';
		// int inARow = 0;
		// int ascending = 0;
		// for(char s : rn.toCharArray()){
		// 	if(s!='I'&&s!='V'&&s!='X'&&s!='L'&&s!='C'&&s!='D'&&s!='M'){valid = false; break;}
		// 	else if(inARow>=1&&(singleRomanToArabic(""+s)>singleRomanToArabic(""+prev))){valid=false; break;}
		// 	if(prev!=' '&&s==prev){ inARow++; }else{inARow=0;}
		// 	if(prev!=' '&&(singleRomanToArabic(""+s)>singleRomanToArabic(""+prev))){ascending++;}else{ascending=0;}
		// 	if(ascending>=2){ valid = false; break;}
		// 	if(inARow>2){ valid = false; break;}
		// 	if(inARow>=1&&(s!='I'&&s!='X'&&s!='C'&&s!='M')){valid = false; break;}
		// 	else if(prev!=' '&&(singleRomanToArabic(""+s)>singleRomanToArabic(""+prev)*10)){valid=false; break;}
		// 	else if(prev!=' '&&(singleRomanToArabic(""+s)>singleRomanToArabic(""+prev))&&(prev!='I'&&prev!='X'&&prev!='C'&&prev!='M')){valid=false; break;}
		// 	prev = s;
		// }
		// if(valid){
		// 	return true;
		// }else{
		// 	System.out.println("Invalid Roman Numeral");
		// 	return false;
		// }
	}

	/**
		@param args: String[] from initial launch
		JVM Instance
	*/
	public static void main(String[] args){
		new RomanNumCalc();
	}





}