/*
	@author Evan Chen
	Classic Dice War game written in the java programming language. 
	I/O scanner and console respectively. 
	Game stores wins and games played, allowing the player to look up stats of games.

*/
import java.util.Scanner;

public class DiceWar{


	//class respective variables, not inheritable
	private int games, wins;
	private Scanner player;

	/*
		Default constructor of program
		initializes private vars and runs menu()
	*/
	public DiceWar(){
		games = 0;
		wins = 0;
		player = new Scanner(System.in);
		menu();
	}


	/*
		Core option menu of the game, uses scanner input
		and handles input with switch statement. Invalid inputs handled with
		default statement.
	*/
	void menu(){
		System.out.println("1- Play Dice War Game\n2- Show Stats\n3- Quit");
		switch(player.next()){
			case "1":
				play();
				break;
			case "2":
				System.out.println(games + " games played " + wins + " won");
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


	/*
		Core game loop of the dice war game. Invalid inputs
		cause automatic exit of the loop.
	*/
	void play(){
		
		System.out.println("This is a game between you and the computer \nPress N/n if you want to quit, any key to start:");
		if(!player.next().equalsIgnoreCase("n")){
			roll();
			while(player.next().equalsIgnoreCase("y")){ //i'm assuming the player presses y/n correctly...
				roll();
			}
		}
		System.out.println("Goodbye!");
	}


	/*
		Core gameplay happening here. Computation of winner and rolls happen here
	*/
	void roll(){
		games++;
		int d1 = randDiceRoll(), d2 = randDiceRoll(), p = d1+d2;
		System.out.println("Your dice's sum is " + (d1+d2) + "(=" + d1 + "+" + d2 + ")");
		d1 = randDiceRoll();
		d2 = randDiceRoll();
		System.out.println("The computer's sum is " + (d1+d2) + "(=" + d1 + "+" + d2 + ")");
		if(p==d1+d2){
			System.out.println("It's a TIE");
		}else if(p>d1+d2){
			System.out.println("YES you won");
			wins++;
		}else{
			System.out.println("Yike! You loss");
		}
		System.out.println("Would you like to play again y/n");
	}

	/*
		@return int: random integer from 1-6 inclusive
	*/
	int randDiceRoll(){
		return (int)(Math.random()*6)+1;
	}


	//main method for launching JVM instance
	public static void main(String[] args){
		new DiceWar();
	}


}
/*

dhcp-39-146-192:LabParticipation2 evan$ javac DiceWar.java
dhcp-39-146-192:LabParticipation2 evan$ java DiceWar
1- Play Dice War Game
2- Show Stats
3- Quit
1
This is a game between you and the computer 
Press N/n if you want to quit, any key to start:
y
Your dice's sum is 7(=2+5)
The computer's sum is 8(=4+4)
Yike! You loss
Would you like to play again y/n
y
Your dice's sum is 8(=5+3)
The computer's sum is 9(=3+6)
Yike! You loss
Would you like to play again y/n
y
Your dice's sum is 3(=1+2)
The computer's sum is 6(=2+4)
Yike! You loss
Would you like to play again y/n
y
Your dice's sum is 9(=6+3)
The computer's sum is 3(=2+1)
YES you won
Would you like to play again y/n
y
Your dice's sum is 9(=5+4)
The computer's sum is 7(=5+2)
YES you won
Would you like to play again y/n
n
Goodbye!
1- Play Dice War Game
2- Show Stats
3- Quit
2
5 games played 2 won
1- Play Dice War Game
2- Show Stats
3- Quit
3


*/