import java.util.Scanner;
import java.util.ArrayList;
public class BlackJack extends CardGame{

	private int balance;
	private Scanner player;

	private ArrayList<Card> playerHand, computerHand;

	public final int MAX_HAND = 21;

	public BlackJack(){
		super(new Deck());
		player = new Scanner(System.in);
		playerHand = new ArrayList<Card>();
		computerHand = new ArrayList<Card>();
		balance = 100;
		menu();
	}

	public int alterBalance(int inc){
		if(balance+inc<0){ return -1; }
		balance+=inc;
		return balance;
	}

	public void dealHand(ArrayList<Card> hand){
		hand.add(getDeck().deal());
	}

	public void showHand(ArrayList<Card> hand, boolean hide){
		for(Card c : hand){
			if(hide){
				hide = false;
				System.out.println("? of ?????");
				continue;
			}
			System.out.println(c);
		}
	}

	public void clearHand(ArrayList<Card> hand){
		hand.clear();
	}

	public int handValue(ArrayList<Card> hand){
		int val = 0;
		boolean hasAce = false;
		for(Card c : hand){
			if(c.getRank()==1){ //card is ace
				hasAce = true;
			}else{
				val+= c.getRank();
			}
		}
		if(hasAce){
			if(val+11>MAX_HAND){
				val++;
			}else{
				val+=11;
			}
		}
		return val;
	}

	@Override
	public void play(){
		System.out.println("\n");
		if(balance==0){ 
			System.out.println("No more money can't play exiting script :(");
			return;
		}else{
			System.out.println("You have $"+balance+". How much do you want to bet?");
			int bet = 0;
			try{
				bet = player.nextInt();
				if(bet>balance){
				   System.out.println("invalid bet, auto bet 1$");
				   bet = 1;
				}
			}catch(Exception e){ 
				System.out.println("invalid bet, auto bet 1$");
				bet = 1;
			}
			System.out.println("Your hand value is " + handValue(playerHand));
			showHand(playerHand,false);
			System.out.println("Dealer hand");
			showHand(computerHand,true);
			int playerScore = playerTurn();
			if(playerScore==-1){ //overflow
				System.out.println("player loses due to overflow");
				balance-=bet;
			}else{ //resume with computer turn
				System.out.println("Dealer hand");
				showHand(computerHand,false);
				int computerScore = computerTurn();
				computeWinner(playerScore,computerScore,bet);
			}
		}
		clearHand(playerHand);
		clearHand(computerHand);	
		//see if need new cards if less than 20
		if(getDeck().deckSize()<20){
			System.out.println("Auto Deck Refresh");
			refreshDeck();
			getDeck().shuffle();
		}	
	}

	public void computeWinner(int player, int computer, int bet){
		System.out.println("\n");
		//verbose hands
		System.out.println("Your hand value is " + handValue(playerHand));
		showHand(playerHand,false);
		System.out.println("\n");
		System.out.println("Dealer hand value is " + handValue(computerHand));
		showHand(computerHand,false);
		System.out.println("\n");
		boolean playerWin = (computer == -1 || player > computer);
		if(playerWin){
			balance+=bet;
		}else{
			balance-=bet;
		}
		System.out.println(new String((playerWin)?"Player Wins":"Dealer Wins"));
		System.out.println("\n\n\n");
	}

	public int computerTurn(){ // dumb dumb AI
		System.out.println("\n");
		boolean shouldDraw = true;
		int handValue = handValue(computerHand);
		while(shouldDraw){
			Card nextCard = getDeck().deal();
			if(nextCard==null){ 
				System.out.println("no more cards, abborting");
				return handValue;
			}else{
				System.out.println("Dealer get card :" + nextCard);
				computerHand.add(nextCard);
				handValue = handValue(computerHand);
				System.out.println("Dealer hand value :" + handValue);
			}
			if(handValue>MAX_HAND){
				System.out.println("overflow");
				return -1;
			}

			//should draw logic
			if(handValue==MAX_HAND){
				return handValue;
			}else{ //sort of random draw
				double prob = (double)(MAX_HAND-handValue)/(double)MAX_HAND;
				if(Math.random() > prob){
					shouldDraw = false;
				}
			}

		}
		return handValue;
	}

	public int playerTurn(){ // has to be player
		System.out.println("\n");
		System.out.println("Would you like to take a card? y/n");
		int handValue = handValue(playerHand);
		while(player.next().equalsIgnoreCase("y")){
			Card nextCard = getDeck().deal();
			if(nextCard==null){ 
				System.out.println("no more cards, abborting");
				return handValue;
			}else{
				System.out.println("Player get card :" + nextCard);
				playerHand.add(nextCard);
				handValue = handValue(playerHand);
				System.out.println("Player hand value :" + handValue);
			}
			if(handValue>MAX_HAND){
				System.out.println("overflow");
				return -1;
			}
		}
		return handValue;
	}

	@Override
	public void menu(){
		System.out.println("Welcome to BlackJack! Balance:" + balance);
		System.out.println("1- Create a new deck"+"\n"+
"2- Deal 4 cards and show the number of remainder cards"+"\n"+
"3- Shuffle the card and show the cards."+"\n"+
"4- Play the Blackjack game"+"\n"+"5- Exit Application"+"\n"+"6- Restart Application");
		switch(player.next()){
			case "1": 
			refreshDeck();
			System.out.println("refreshed deck");
			menu();
			break;
			case "2": // deal 2 cards per player / show deck len
			if(playerHand.isEmpty()){
				if(getDeck().deckSize()<=4){
					System.out.println("no cards left . . . please reload deck ");
				}else{
					dealHand(playerHand);
					dealHand(playerHand);
					dealHand(computerHand);
					dealHand(computerHand);
					System.out.println(getDeck().deckSize() + " cards in deck left");
				}
			}else{
				System.out.println("Cards already handed out, please play");
			}
			menu();
			break;
			case "3": 
			getDeck().shuffle();
			System.out.println(getDeck());
			System.out.println("shuffled deck");
			menu();
			break;
			case "4": 
			if(!playerHand.isEmpty())
				play();
			else
				System.out.println("hands are empty, please first deal cards");
			menu();
			break;
			case "5":
				System.out.println("Exiting, thank you for playing!");
			break;
			case "6":
				new BlackJack();
			break;
			default: 
			System.out.println("invalid input");
			menu(); 
			break;
		}
	}

}