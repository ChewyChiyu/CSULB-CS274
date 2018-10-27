public class Ray{
	public double iX, iY,dir;
	public double fX, fY;
	public Ray(double iX, double iY, double dir){
		this.iX = iX;
		this.iY = iY;
		this.fX = iX;
		this.fY = iY;
		this.dir = dir;
	}
	public double mag(){
		return Math.sqrt((fX-iX)*(fX-iX)+(fY-iY)*(fY-iY));
	}
	public double stretch(Map m,double max){
		double dX = Math.cos(dir), dY = Math.sin(dir);
		final double SCALE = 0.005;
		while(fX<m.getM()[0].length-1&&fX>0&&fY<m.getM().length-1&&fY>0&&m.getM()[(int)(fY)][(int)(fX)]==0){
			fX+=Math.cos(dir)*SCALE;
			fY+=Math.sin(dir)*SCALE;
			if(mag()>max){
				return max;
			}
		}
		return mag();
	}

}