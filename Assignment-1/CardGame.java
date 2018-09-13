/*
	@author Evan Chen
	This is an abstract template class for CardGames
	Methods for forced inhertiance of non abstract classes
	are play() and menu(); 
*/
public abstract class CardGame{

	// Core deck of card game
	private Deck deck;


	/*
		Constructor:
		@param Deck deck: specific deck pass into card game for proper load. 
	*/
	public CardGame(Deck deck){
		this.deck = deck;
	}


	/*
		abstract play() and menu() for templates of all card games, each card game that
		inherits from CardGame that is not abstract themselves will have to implement some
		form of play() or menu()
 	*/
	abstract void play();
	abstract void menu();

	/*
		getter of Deck object in CardGame Class
	*/
	public Deck getDeck(){
		return deck;
	}

	/*
		mutator of Deck object with only one mutation option not controled by user
	*/
	public void refreshDeck(){
		deck = new Deck();
	}

}