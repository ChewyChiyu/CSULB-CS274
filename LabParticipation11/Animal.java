public abstract class Animal{
	private int pos = 0; // default
	private AnimalType type;
	public Animal(AnimalType t){
		type = t;
	}

	public int getPos(){ return pos; }
	public void setPos(int p){ pos = p; }
	public int actionChance(){ return (int)(Math.random()*10) + 1; }
	public AnimalType getType(){ return type; }

	public abstract void move(int limit);

	public String toString(){ return type + " is at " + pos; }

}