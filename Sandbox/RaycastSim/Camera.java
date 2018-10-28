import java.awt.*;
public class Camera{

	public double angleSpan;
	public double sweepAngle;
	public double maxRange;
	public Camera(double angleSpan, double sweepAngle, double maxRange){
		this.angleSpan = angleSpan;
		this.sweepAngle = sweepAngle;
		this.maxRange = maxRange;
	}

	public void draw(Player p, Map m, Graphics2D g2d, Dimension dim){
		// sweep from [p.theta-angleSpan/2,p.theta+angleSpan/2]
		double xBuffer = 0;
		double xDBuffer = (int)(dim.width/(angleSpan/sweepAngle))+1;
		double originY = dim.height/2;

		//horizion line y > dim.height/2 // ground
		for(int h = dim.height/2; h < dim.height; h++){
			g2d.setColor(getGradientCrim(h,dim.height/2,dim.height));
			g2d.fillRect(0,h,dim.width,1);
		}

		//horizion line y < dim.height/2 // sky
		for(int h = dim.height/2; h > 0; h--){
			g2d.setColor(getGradientBlue(h,dim.height/2,0));
			g2d.fillRect(0,h,dim.width,1);
		}

		for(double angle = p.thetaX-angleSpan/2; angle <= p.thetaX+angleSpan/2; angle+=sweepAngle){
			Ray r = new Ray(p.x,p.y,angle);
			RayDetail rayLen = r.stretch(m,maxRange);
			
			double stretchLen = (((-dim.height)/maxRange)*(rayLen.mag)+dim.height);
			g2d.setColor(((rayLen.hitToken)?getGradientRed(Math.abs(rayLen.mag),0,maxRange):getGradientBlack(Math.abs(rayLen.mag*Math.sin(angle)),0,maxRange)));
			g2d.fillRect((int)xBuffer,(int)(originY-stretchLen/2),(int)(xDBuffer),(int)(stretchLen));
			xBuffer+=xDBuffer;
		}
	}

	public Color getGradientCrim(double x, double min, double max){
		int g = (int)(((x-min)/(max-min))*255);
		return new Color(g,120,40);
	}

	public Color getGradientBlue(double x, double min, double max){
		int g = (int)(((x-min)/(max-min))*255);
		return new Color(0,0,g);
	}

	public Color getGradientBlack(double x, double min, double max){
		int g = (int)(((x-min)/(max-min))*255);
		return new Color(g,g,g);
	}
	public Color getGradientRed(double x, double min, double max){
		int g = (int)(((x-min)/(max-min))*255);
		return new Color(g,0,0);
	}
}