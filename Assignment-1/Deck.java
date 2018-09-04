public class Deck{

	private Card[] cards;
	public final int MAX_CARDS = 52, SHUFFLE_SCALE = 10;

	public Deck(){
		loadCards();
	}

	private void loadCards(){
		cards = new Card[MAX_CARDS];
		int deckIndex = 0;
		for(int suite = 1; suite <= Card.MAX_SUITE; suite++){
			for(int rank = 1; rank <= Card.MAX_RANK; rank++){
				int color = (suite == 2 || suite == 3)?1:2; // diamonds or hearts
				cards[deckIndex++] = new Card(rank,suite,color);
			}
		}
	}

	public void shuffle(){
		for(int shuffle = 0; shuffle < SHUFFLE_SCALE; shuffle++){
			for(int index = 0; index < cards.length; index++){
				int randIndex1 = (int)(Math.random()*cards.length), randIndex2 = (int)(Math.random()*cards.length);
				swap(randIndex1,randIndex2);
			}
		}
	}



	public void swap(int cardIndex1, int cardIndex2){
		Card tempCard = cards[cardIndex2];
		cards[cardIndex2] = cards[cardIndex1];
		cards[cardIndex1] = tempCard;
	}

	public Card deal(){ //deals from index 0
		if(deckSize()==0){ return null; }
		Card[] deltaCards = new Card[deckSize()-1];
		Card dealCard = cards[0]; 
		for(int index = 0; index < deltaCards.length; index++){
			deltaCards[index] = cards[index+1];
		}
		cards = deltaCards;
		return dealCard;
	}

	public Card[] getCards(){ return cards; }

	public int deckSize(){ return cards.length; }

	public String toString(){
		StringBuilder sb = new StringBuilder();
		for(Card c : cards){
			sb.append(c.toString());
			sb.append("\n");
		}
		return sb.toString();
	}

	public static void main(String[] args){
		Deck d = new Deck();
		System.out.println(d);
		d.shuffle();
		System.out.println(d);
		Card c  = d.deal();
		System.out.println(c + " this is a card");
		System.out.println(d.deckSize());
	}

}