public class Hare extends Animal{
	public Hare(AnimalType type){
		super(type);
	}

	@Override
	public void move(int limit){
		int chance = actionChance();
		if(chance<=2){ return; } //sleep
		else if(chance<=4){ setPos(Math.min(getPos()+9,limit)); } //big hop
		else if(chance<=5){ setPos(Math.max(getPos()-12,1)); } //big slip
		else if(chance<=8){ setPos(Math.min(getPos()+1,limit)); } //small slip
		else if(chance<=10){ setPos(Math.max(getPos()-2,1)); } //small slip
	}
}