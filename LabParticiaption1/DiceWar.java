
import java.util.Scanner;

public class DiceWar{


	public DiceWar(){
		play(new Scanner(System.in));
	}

	void play(Scanner player){
		System.out.println("This is a game between you and the computer \nPress N/n if you want to quit, any key to start:");
		if(!player.next().equalsIgnoreCase("n")){
			roll();
			while(player.next().equalsIgnoreCase("y")){ //i'm assuming the player presses y/n correctly...
				roll();
			}
		}
		System.out.println("Goodbye!");
	}

	void roll(){
		int d1 = randDiceRoll(), d2 = randDiceRoll(), p = d1+d2;
		System.out.println("Your dice's sum is " + (d1+d2) + "(=" + d1 + "+" + d2 + ")");
		d1 = randDiceRoll();
		d2 = randDiceRoll();
		System.out.println("The computer's sum is " + (d1+d2) + "(=" + d1 + "+" + d2 + ")");
		if(p==d1+d2){
			System.out.println("It's a TIE");
		}else if(p>d1+d2){
			System.out.println("YES you won");
		}else{
			System.out.println("Yike! You loss");
		}
		System.out.println("Would you like to play again y/n");
	}

	int randDiceRoll(){
		return (int)(Math.random()*6)+1;
	}

	public static void main(String[] args){
		new DiceWar();
	}


}

/*
dhcp-39-145-236:LabParticiaption1 evan$ javac DiceWar.java
dhcp-39-145-236:LabParticiaption1 evan$ java DiceWar
This is a game between you and the computer 
Press N/n if you want to quit, any key to start:
n
Goodbye!
dhcp-39-145-236:LabParticiaption1 evan$ javac DiceWar.java
dhcp-39-145-236:LabParticiaption1 evan$ java DiceWar
This is a game between you and the computer 
Press N/n if you want to quit, any key to start:
a
Your dice's sum is 9(=6+3)
The computer's sum is 8(=2+6)
YES you won
Would you like to play again y/n
y
Your dice's sum is 4(=2+2)
The computer's sum is 6(=4+2)
Yike! You loss
Would you like to play again y/n
y
Your dice's sum is 8(=2+6)
The computer's sum is 9(=3+6)
Yike! You loss
Would you like to play again y/n
y
Your dice's sum is 6(=3+3)
The computer's sum is 7(=3+4)
Yike! You loss
Would you like to play again y/n
y
Your dice's sum is 11(=6+5)
The computer's sum is 7(=1+6)
YES you won
Would you like to play again y/n
y
Your dice's sum is 9(=4+5)
The computer's sum is 5(=1+4)
YES you won
Would you like to play again y/n
y
Your dice's sum is 11(=5+6)
The computer's sum is 7(=4+3)
YES you won
Would you like to play again y/n
y
Your dice's sum is 10(=6+4)
The computer's sum is 11(=5+6)
Yike! You loss
Would you like to play again y/n
y
Your dice's sum is 6(=2+4)
The computer's sum is 6(=3+3)
It's a TIE
Would you like to play again y/n
y
Your dice's sum is 7(=2+5)
The computer's sum is 10(=5+5)
Yike! You loss
Would you like to play again y/n
y
Your dice's sum is 4(=2+2)
The computer's sum is 3(=2+1)
YES you won
Would you like to play again y/n
n
Goodbye!
dhcp-39-145-236:LabParticiaption1 evan$ 
*/