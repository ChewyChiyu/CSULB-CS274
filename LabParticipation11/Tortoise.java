public class Tortoise extends Animal{
	public Tortoise(AnimalType type){
		super(type);
	}

	@Override
	public void move(int limit){
		int chance = actionChance();
		if(chance<=5){ setPos(Math.min(getPos()+3,limit));}
		else if(chance<=7){ setPos(Math.max(getPos()-6,1));}
		else if(chance<=10){ setPos(Math.min(getPos()+1,limit)); }
	}
}