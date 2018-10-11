/**
	@author Evan Chen
	This class is the card object that stores rank, suite, and color as integers
	upper bound limits at 13 rank 2 color and suite 4 inclusive, lower bound at 0 inclusive
*/
public class Card{

	//private integers to store rank, suite, color
	private int rank, suite, color;

	//upper bound exclusive max limits
	public static int MAX_RANK = 13, MAX_COLOR = 2, MAX_SUITE = 4;

	/**
		Constructor:
		@param int rank, int suite, int color: value of card to convert into user friendly
		and computational friendly text
	*/
	public Card(int rank, int suite, int color){
		this.rank = rank;
		this.suite = suite;
		this.color = color;
	}

	/** 
	basic getters for rank, suite color. @return rank, suite color ints 
	*/
	public int getRank(){ return rank; }
	public int getSuite(){ return suite; }
	public int getColor(){ return color; }


	/** 
	converting rank, suite integers into user friendly output @return verbose of rank and suite 
	*/
	public String rankToString(){
		String str = "";
		switch(rank){
			case 1:  str = "ace"; break;
			case 11: str = "jack"; break;
			case 12: str = "queen"; break;
			case 13: str = "king"; break;
			default: str = ""+rank; break;
		}
		return str;
	}

	public String suiteToString(){
		String str = "";
		switch(suite){
			case 1: str = "clubs"; break;
			case 2: str = "diamonds"; break;
			case 3: str = "hearts"; break;
			case 4: str = "spades"; break;
		}
		return str;
	}

	public String colorToString(){
		return new String((color==1)?"red":"black");
	}
	
	public String toString(){ 
		//return "rank: " + rankToString() + " suite: " + suiteToString() + " color: " + colorToString(); 
		return rankToString() + " of " + suiteToString();
	}

}