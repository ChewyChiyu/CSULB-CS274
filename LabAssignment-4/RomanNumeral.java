public class RomanNumeral{

	private String rn;

	public RomanNumeral(String rn){
		if(validRomanQuery(rn)){
			this.rn = rn;
		}else{
			this.rn = null;
		}
	}

	/**
		@param rn: String containing Roman Numberal
		@return int: arabic number converted from String roman numberal
	*/
	public int romanToArabic(){
		if(rn==null){ return -1; }
		LinkedList romanList = stringToQueue(rn);
		Stack stackRoman = new Stack();
		int result = 0;
		while(romanList.size()>0){
			String next = ""+romanList.removeAt(0);
			if((stackRoman.size()==0&&romanList.size()==0)){
				result+=singleRomanToArabic(next);
			}else if(stackRoman.size()==0&&romanList.size()>0){
				stackRoman.push(next);
			}else if(singleRomanToArabic(stackRoman.peek())<singleRomanToArabic(next)){
				result+=(singleRomanToArabic(next)-singleRomanToArabic(stackRoman.pop()));
			}else if(romanList.size()>0&&(singleRomanToArabic(stackRoman.peek())>=singleRomanToArabic(next))){
				result+=singleRomanToArabic(stackRoman.pop());
				stackRoman.push(next);
			}else if((singleRomanToArabic(stackRoman.peek())>=singleRomanToArabic(next))){
				result+=singleRomanToArabic(stackRoman.pop());
				result+=singleRomanToArabic(next);
			}
		}
		System.out.println(result);
		return result;
	}

	public LinkedList stringToQueue(String str){
		LinkedList list = new LinkedList();
		for(char c : str.toCharArray()){
			list.add(c);
		}
		return list;
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
		@return String of a valid Roman Numeral
	*/
	public boolean validRomanQuery(String rn){
		//boolean valid = true;
		//checking for correct characters 


		//TODO: MCMMXCIX + DCCCXVIII = MMMDCCCXVII fix

		//regular expression
		return rn.matches("\\bM{0,4}(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})\\b");


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

	public static void main(String[] args){
		RomanNumeral rn = new RomanNumeral("ML");
		System.out.println(rn.romanToArabic());
	}
}