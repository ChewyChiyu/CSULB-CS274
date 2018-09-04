public abstract class CardGame{

	private Deck deck;

	public CardGame(Deck deck){
		this.deck = deck;
	}

	abstract void play();
	abstract void menu();

	public Deck getDeck(){
		return deck;
	}

	public void refreshDeck(){
		deck = new Deck();
	}

}