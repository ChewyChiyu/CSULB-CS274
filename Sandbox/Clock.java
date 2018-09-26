import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Date;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
@SuppressWarnings("deprecation")
public class Clock extends JPanel{

	private Dimension windowDim = new Dimension(800,800);
	private Point center = new Point(400,400);


	private Thread cycle;
	private boolean shouldRun;
	private final int SLEEP_TIME = 1000; //1 sec
	
	private final double deltaRadian = Math.PI/6;
	private final double startRadian = Math.PI;

	private Date date;

	private final int RADIUS = 600;
	private final int LEG = RADIUS/2;

	private double hourAngle, minuteAngle, secondAngle;

	public Clock(){
		date = new Date(System.currentTimeMillis());
		recalibrate();
		panel();
		start();
	}


	public void start(){
		Runnable run = () -> update();
		shouldRun = true;
		cycle = new Thread(run);
		cycle.start();
	}


	public void update(){
		while(shouldRun){
			date = new Date(System.currentTimeMillis());
			recalibrate();
			repaint();			
			try{ Thread.sleep(SLEEP_TIME); }catch(Exception e){}
		}
	}

	public void recalibrate(){

		//initial calibration

		secondAngle = startRadian + (deltaRadian/5)*date.getSeconds();
		minuteAngle = startRadian + (deltaRadian/5)*date.getMinutes() + ((secondAngle-startRadian)/(startRadian*2)*(deltaRadian/5));
		hourAngle = startRadian + deltaRadian*date.getHours() + ((minuteAngle-startRadian)/(startRadian*2)*deltaRadian);
	}


	public void panel(){
		JFrame frame = new JFrame("Clock");
		frame.add(this);
		frame.setPreferredSize(windowDim);
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		drawBack(g);
	}

	public void drawBack(Graphics g){
		g.setColor(Color.BLACK);
		
		double start = startRadian - deltaRadian*2;
		for(int index = 1; index <= 12; index++){
			g.drawString(""+index,center.x-(int)(LEG*Math.cos(start)),center.y-(int)(LEG*Math.sin(start)));
			start+=deltaRadian;
		}
		drawHand(RADIUS/4,hourAngle,g);
		drawHand(RADIUS/3,minuteAngle,g);
		drawHand(RADIUS/2,secondAngle,g);
	}

	public void drawHand(int length,double angle,Graphics g){
		Graphics2D g2d = (Graphics2D) g;
		AffineTransform a = g2d.getTransform();
		g2d.setColor(Color.BLACK);
		g2d.translate(center.x, center.y);
		g2d.rotate(angle);
		g2d.fillRect(-1,0,2,length);
		g2d.setTransform(a);
	}


	public static void main(String[] args){
		new Clock();
	}
}