public class RaceSim{

	public RaceSim(){
		Hare h = new Hare(AnimalType.HARE);
		Tortoise t = new Tortoise(AnimalType.TORTOISE);
		int fin = 70;
		race(h,t,fin);
	}

	public void race(Animal a, Animal b, int finish){
		System.out.println("BANG !!!!! \nAND THEY'RE OFF !!!!!");
		while(a.getPos()<finish&&b.getPos()<finish){
			a.move(finish);
			b.move(finish);
			if(a.getPos()==b.getPos()){ System.out.println(" OUCH!! " + b.getPos()); }
		}
		System.out.println( new String((b.getPos()>=a.getPos())?"YAY!!! Tortoise Wins!!":"Hare Wins.") );
	}	


	public static void main(String[] args){
		new RaceSim();
	}
}