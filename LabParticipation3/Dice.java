/*
	@author Evan Chen
	The purpose of this class is to create a 
	"Dice" Object, one of six sides with each side
	with a its respective dice value. 
*/
public class Dice{

	//private class vars
	private final int SIDES;

	public final static int DICE_WAR_SIDES = 6;

	//Constructor @param int sides: number of sides on dice, lower bound 1
	public Dice(int sides){
		SIDES = sides;
	}

	//Rolls dice @return int random from 1-SIDES
	public int roll(){
		return (int)(Math.random()*SIDES)+1;
	}

	/*Rolls dice n times and returns sum @param int n: times rolled,
		@return int sum of n dice rolls
	*/
	public int sumRoll(int n){
		int sum = 0;
		for(int rollIndex = 0; rollIndex < n; rollIndex++){
			sum+=roll();
		}
		return sum;
	}

}