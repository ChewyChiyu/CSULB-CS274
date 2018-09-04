public class Card{

	private int rank, suite, color;

	public static int MAX_RANK = 13, MAX_COLOR = 2, MAX_SUITE = 4;

	public Card(int rank, int suite, int color){
		this.rank = rank;
		this.suite = suite;
		this.color = color;
	}

	public int getRank(){ return rank; }
	public int getSuite(){ return suite; }
	public int getColor(){ return color; }

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