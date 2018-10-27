import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.FlowLayout;

public class Fractal extends JPanel{

	private Dimension windowDim = new Dimension(1000,800);
	private BufferedImage canvas;
	private Graphics g;

	private double cA = 0, cB = 0;
	private final int MIN_B = -1000, MAX_B = 1000;

	public Fractal(){
		panel();
		run();
	}

	public void run(){
		double lowerBound = -1.5, upperBound = 2, minX = 0, maxX = windowDim.width, minY = minX, maxY = maxX;
		double limit = 40, cycles = 70;
		for(double x = minX; x <= maxX; x++){
			for(double y = minY; y <= maxY; y++){
				 double mX = map(x,minX,maxX,lowerBound,upperBound);
				 double mY = map(y,minY,maxY,lowerBound,upperBound);
				 
				 double a = mX;
				 double b = mY;
				 for(int cycle = 0; cycle < cycles; cycle++){
				 	double iA = (a*a)-(b*b);
					double iB = (2*b*a);
					a = iA + cA;
					b = iB + cB;
				 	if(Math.abs(a*b)>=limit){ //diverges
				 		g.setColor(boundGradient(Math.abs(a*b),limit,limit*5));
						break;
				 	}
				 }
				 if(Math.abs(a*b)<limit){ //converges
				 	g.setColor(gradient(Math.abs(a*b),0,limit));
				 }

				 g.fillRect((int)x,(int)y,1,1);
			}
		}
		repaint();
	}

	public Color boundGradient(double x, double minX, double maxX){
		double n = (x < maxX)? x : maxX;
		double d = ((n-minX)/(maxX-minX))*255;
		return new Color(0,20,255-(int)d);
	}

	public Color gradient(double x, double minX, double maxX){
		double g = ((x-minX)/(maxX-minX))*255;
		if(g<0.3333333){
			return new Color(0,255-(int)g,255-(int)g);
		}else if(g<0.66666666){
			return new Color(255-(int)g,255-(int)g,0);
		}else{
			return new Color(255-(int)g,0,255-(int)g);
		}
		
	}

	public double map(double x, double minX, double maxX, double targetMin, double targetMax){
		return ((targetMax-targetMin)/(maxX-minX))*(x-minX)+targetMin;
	}

	public void panel(){
		JFrame frame = new JFrame("Fractal Sim");


		JPanel back = new JPanel();
		back.setPreferredSize(windowDim);
		back.setLayout(new FlowLayout());
		frame.add(back);
		

		JSlider cX = new JSlider(JSlider.VERTICAL,MIN_B, MAX_B, 0);
		cX.addChangeListener(e -> {
			JSlider source = (JSlider)e.getSource();
			cA = (double)source.getValue()/MIN_B;
			run();
		});
		cX.setPreferredSize(new Dimension(20,windowDim.height));
		back.add(cX);


		JSlider cY = new JSlider(JSlider.VERTICAL,MIN_B, MAX_B, 0);
		cY.addChangeListener(e -> {
			JSlider source = (JSlider)e.getSource();
			cB = (double)source.getValue()/MIN_B;
			run();
		});
		cY.setPreferredSize(new Dimension(20,windowDim.height));
		back.add(cY);

		back.add(this);
		Dimension fPDim = new Dimension(800,windowDim.height);
		this.setPreferredSize(fPDim);

		frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		newCanvas();
		repaint();
	}

	public void newCanvas(){
		try{ 
			canvas = new BufferedImage(windowDim.width, windowDim.height, BufferedImage.TYPE_INT_ARGB);
			g = canvas.getGraphics();
			g.setColor(Color.BLACK);
		}catch(Exception e){}
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(canvas,0,0,null);
	}

	public static void main(String[] args){
		new Fractal();
	}
}