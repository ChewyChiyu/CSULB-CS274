import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
@SuppressWarnings("serial")
public class Draw extends JPanel{

	private Dimension windowDim = new Dimension(1000,800);

	private final Point center = new Point(500,400);
	private BufferedImage canvas;

	private Thread thread;
	private boolean runFlag;

	private Stick crux,leg1,leg2,leg3;

	public Draw(){
		panel();
		sticks();
		start();
	}


	private void sticks(){
		crux = new Stick(center,new Dimension(10,75),0,10);
		leg1 = new Stick(crux, new Dimension(10,100),Math.PI/3,7);
		leg2 = new Stick(leg1, new Dimension(10,125),Math.PI/2,3);
		leg3 = new Stick(leg2, new Dimension(10,100),Math.PI/4,1);

	}

	private synchronized void start(){
		try{
			Runnable run = () -> update();
			thread = new Thread(run);
			runFlag = true;
			thread.start();
		}catch(Exception e) { }
	}

	private void update(){
		while(runFlag){
		    Point p = leg3.endPoint();
		    Graphics canvasGraphics = canvas.getGraphics();
			canvasGraphics.setColor(Color.BLACK);
			canvasGraphics.fillOval(p.x-5,p.y-5,10,10);
			repaint();
			try{Thread.sleep(1);}catch(Exception e) {}
		}
	}



	public void panel(){
		JFrame frame = new JFrame("Draw");
		frame.add(this);
		frame.setPreferredSize(windowDim);
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try{ canvas = new BufferedImage(windowDim.width, windowDim.height, BufferedImage.TYPE_INT_ARGB); }catch(Exception e){}
	}

	public static void main(String[] args){
		new Draw();
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(canvas,0,0,null);
		try{
			crux.draw(g);
			leg1.draw(g);
			leg2.draw(g);
			leg3.draw(g);
		}catch(Exception e) {} 
	}

}

class Stick{

	public Point loc;
	public Dimension dim;

	public double theta; //radians

	public Stick parent;

	public int sleepTime;

	public static final double deltaTheta = Math.PI/360;

	public Thread clock;

	public Stick(Point loc, Dimension dim, double theta, int sleepTime){
		this.loc = loc;
		this.dim = dim;
		this.theta = theta;
		this.sleepTime = sleepTime;
		start();
	}

	public Stick(Stick parent, Dimension dim, double theta,int sleepTime){
		this.loc = parent.endPoint();
		this.dim = dim;
		this.theta = theta;
		this.parent = parent;
		this.sleepTime = sleepTime;
		start();
	}

	public synchronized void start(){
		Runnable run = () -> update();
		clock = new Thread(run);
		clock.start();
	}

	public Point endPoint(){
		return new Point(loc.x-(int)(dim.height*Math.sin(theta)),loc.y+(int)(dim.height*Math.cos(theta)));
	}

	public void update(){
		while(this!=null){
			if(parent!=null){
				loc = parent.endPoint();
			}
			theta+=Stick.deltaTheta;
			if(theta >= Math.PI*2){
				theta = 0;
			}
			try{ Thread.sleep(sleepTime); }catch(Exception e) { }
		}
	}

	public void draw(Graphics g){
		Graphics2D g2d = (Graphics2D) g;
		AffineTransform a = g2d.getTransform();
		g2d.setColor(Color.BLUE);
		g2d.translate(loc.x, loc.y);
		g2d.rotate(theta);
		g2d.fillRect(-dim.width/2,0,dim.width,dim.height);
		g2d.setTransform(a);
	}

}