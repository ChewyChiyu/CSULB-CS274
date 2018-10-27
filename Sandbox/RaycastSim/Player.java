public class Player{

	public double x,y,dx,dy;
	public double moveSpeed;
	public double thetaX,dthetaX;
	public Player(double x, double y, double thetaX, double dthetaX){
		this.x = x;
		this.y = y;
		this.thetaX = thetaX;
		this.dthetaX = dthetaX;
		moveSpeed = 0.1;
		dx = 0;
		dy = 0;
	}

	public void turnRightX(){ 
		thetaX+=dthetaX;
		if(thetaX>=Math.PI*2){ thetaX = 0; }
	}

	public void turnLeftX(){ 
		thetaX-=dthetaX;
		if(thetaX<=-Math.PI*2){ thetaX = 0; }
	}

	public void move(Map m){
		if((int)(x+dx)<1||(int)(x+dx)>m.getM()[0].length-2){
			return;
		}
		if((int)(y+dy)<1||(int)(y+dy)>m.getM().length-2){
			return;
		}
		if(m.getM()[(int)y][(int)(x+dx)]==0){
			x+=dx;
		}
		if(m.getM()[(int)(y+dy)][(int)x]==0){
			y+=dy;
		}
	}


}