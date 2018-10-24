/**
	@author Evan Chen
	This is a deck class that incorporates Card class
	Basic bounderies of the deck include a max limiter of 52 cards initial and
	shuffle scale of 10
	
*/
public class Deck{


	/**
	 private class objects, shuffle scale of 10 describes a loop through the deck 10 times 
	 */
	private LinkedList cards;
	public final int MAX_CARDS = 52, SHUFFLE_SCALE = 10;

	/**
	 basic constructor, calls loadCards after creation 
	 */
	public Deck(){
		loadCards();
	}


	/**
		Loads cards into LinkedList cards after creation, 
		cards holds max 52 card objects. Creation of each card is handled in the 
		Card class by passing in paramenters of integers represeting the rank and suite and color
		of each individual card. No two cards are the same.
	*/
	private void loadCards(){
		cards = new LinkedList();
		int deckIndex = 0;
		for(int suite = 1; suite <= Card.MAX_SUITE; suite++){
			for(int rank = 1; rank <= Card.MAX_RANK; rank++){
				int color = (suite == 2 || suite == 3)?1:2; // diamonds or hearts
				cards.add(new Card(rank,suite,color));
			}
		}
	}

	/**
		method shuffles the  cards in cards array. Swaps one random card to end of list randomly on each increment of the loop SHUFFLE_SCALE
		times.
	*/
	public void shuffle(){
		for(int shuffle = 0; shuffle < SHUFFLE_SCALE; shuffle++){
			for(int index = 0; index < cards.size(); index++){
				int randIndex1 = (int)(Math.random()*cards.length);
				cards.add(cards.removeAt(randIndex1));
			}
		}
	}

	/**
		creates temp array to copy new array onto with deckSize()-1 size left after
		removal of one card.

		@return Card: returns card object from cards[0]

	*/
	public Card deal(){ //deals from tail
		if(deckSize()==0){ return null; }
		return cards.pop();
	}

	/** 
	basic getter of cards @return  LinkedList which is cards
	*/
	public LinkedList getCards(){ return cards; }

	/**
	basic getter of cards.length @return cards.length
	*/
	public int deckSize(){ return cards.size(); }


	/**
	 loops linkedlist and prints individual cards, string builder to build return string 
	 */
	public String toString(){
		return cards.toString();
	}

}