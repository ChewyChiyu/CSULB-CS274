/*
	@author Evan Chen
	Classic Dice War game written in the java programming language. 
	I/O scanner and console respectively. 
	Game stores wins and games played, allowing the player to look up stats of games.
	
*/
import java.util.Scanner;

public class DiceWarGame{


	//class respective variables, not inheritable
	private Scanner prompt;
	private Player[] players; //user if player at index 0, mandate at 2
	private int gamesPlayed;

	public final int DICE_PER_PLAYER = 2;
	/*
		Default constructor of program
		initializes private vars and runs menu()
		@param int numPlayers: number of players in game
	*/
	public DiceWarGame(int numPlayers){
		prompt = new Scanner(System.in);
		players = new Player[numPlayers];
		gamesPlayed = 0;
		createPlayers();
		menu();
	}

	//creates the player objects
	private void createPlayers(){
		for(int playerIndex = 0; playerIndex < players.length; playerIndex++){
			players[playerIndex] = new Player(DICE_PER_PLAYER);
		}
	}

	/*
		Core option menu of the game, uses scanner input
		and handles input with switch statement. Invalid inputs handled with
		default statement.
	*/
	void menu(){
		System.out.println("1- Play Dice War Game\n2- Show Stats\n3- Quit");
		switch(prompt.next()){
			case "1":
				play();
				break;
			case "2":
				showStats(players);
				break;
			case "3":
				System.exit(0);
				break;
			default:
				System.out.println("invalid option");
				break;
		}
		menu();
	}

	//shows the score of all players and games played @param Player[] players that play
	public void showStats(Player[] players){
		for(int playerIndex = 0; playerIndex < players.length; playerIndex++){
			System.out.println("Player " + playerIndex + " has " + players[playerIndex].getTotalPoint() + " points.");
		}
		System.out.println(gamesPlayed + " games played. ");
	}

	/*
		Core game loop of the dice war game. Invalid inputs
		cause automatic exit of the loop. This is the users Player object
	*/
	void play(){
		
		System.out.println("This is a game between you and the computer \nPress N/n if you want to quit, any key to start:");
		if(!prompt.next().equalsIgnoreCase("n")){
			roll();
			while(prompt.next().equalsIgnoreCase("y")){ //i'm assuming the player presses y/n correctly...
				roll();
			}
		}
		System.out.println("Goodbye!");
	}


	/*
		Core gameplay happening here. Computation of winner and rolls happen here
		int[] is held to determine winner(s) of round
	*/
	void roll(){
		gamesPlayed++;

		int[] tempScoreArray = new int[players.length];
		int highestRoll = 0;
		for(int playerIndex = 0; playerIndex < players.length; playerIndex++){
			tempScoreArray[playerIndex] = players[playerIndex].rollAllDice();
			highestRoll = Math.max(highestRoll,tempScoreArray[playerIndex]);
		}
		//tempScoreIndex == player.length
		for(int tempScoreIndex = 0; tempScoreIndex < tempScoreArray.length; tempScoreIndex++){
			if(tempScoreArray[tempScoreIndex] == highestRoll){
				System.out.println("Player at index " + tempScoreIndex + " won/tied with " + highestRoll);
				players[tempScoreIndex].incrementPoints();
			}else{
				System.out.println("Player at index " + tempScoreIndex + " lost with " + tempScoreArray[tempScoreIndex]);
			}
		}
		System.out.println("Would you like to play again y/n");
	}

	


	//main method for launching JVM instance
	public static void main(String[] args){
		new DiceWarGame(10); //assuming 2 players
	}


}