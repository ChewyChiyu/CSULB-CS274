import java.awt.*;
public class Camera{

	public double angleSpan;
	public double sweepAngle;
	public double maxRange;
	public double cStretch;
	public Camera(double angleSpan, double sweepAngle, double maxRange, double cStretch){
		this.angleSpan = angleSpan;
		this.sweepAngle = sweepAngle;
		this.maxRange = maxRange;
		this.cStretch = cStretch;
	}

	public void draw(Player p, Map m, Graphics2D g2d, Dimension dim){
		// sweep from [p.theta-angleSpan/2,p.theta+angleSpan/2]
		double xBuffer = 0;
		double xDBuffer = (int)(dim.width/(angleSpan/sweepAngle))+1;
		double originY = dim.height/2;

		for(double angle = p.thetaX-angleSpan/2; angle <= p.thetaX+angleSpan/2; angle+=sweepAngle){
			Ray r = new Ray(p.x,p.y,angle);
			double rayLen = r.stretch(m,maxRange);
			double stretchLen = (maxRange-rayLen*Math.sin(angle))*cStretch;
			g2d.setColor(getGradient(rayLen,0,maxRange));
			g2d.fillRect((int)xBuffer,(int)(originY-stretchLen/2),(int)(xDBuffer),(int)(stretchLen));
			xBuffer+=xDBuffer;
		}
	}

	public Color getGradient(double x, double min, double max){
		int g = (int)(((x-min)/(max-min))*255);
		return new Color(g,g,g);
	}

}