/*
	@author Evan Chen
	The purpose of this class is to create a "Player" object
	which includes 2 Dice objects in an array to be called upon for futher
	instruction by DiceWarGame. Totaling of the dice rolls will be implemented as well.
*/
public class Player{

	//private player vars
	private Dice[] dice;
	private int points;
	/*
		Constructor: @param int numDice: number of dice the player will have in Dice[]
	*/
	public Player(int numDice){
		dice = new Dice[numDice];
		points = 0;
		createDice();
	}

	//Creates dice from dice array length
	private void createDice(){
		for(int diceIndex = 0; diceIndex < dice.length; diceIndex++){
			dice[diceIndex] = new Dice(Dice.DICE_WAR_SIDES);
		}
	}

	/*@return int returns sum of the rolled Dices in dice array
	  @param int numDice: num of dices to be rolled
	*/
	public int rollDices(int numDice){
		if(numDice<=0){ return 0; }
		if(numDice>dice.length){ numDice = dice.length; }
		int sum = 0;
		for(int diceIndex = 0; diceIndex < numDice; diceIndex++){
			sum+=dice[diceIndex].roll();
		}
		return sum;
	}

	public int rollAllDice(){
		return rollDices(dice.length);
	}

	//@return int points that the player has
	public int getTotalPoint(){
		return points;
	}

	public void incrementPoints(){
		points++;
	}




}