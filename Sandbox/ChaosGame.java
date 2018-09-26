import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class ChaosGame extends JPanel{

	private Dimension windowDim = new Dimension(1000,800);
	private BufferedImage canvas;
	private ArrayList<Point> points;

	private final int MAX_NUM_POINTS = 30;
	private final int SLEEP_TIME = 15;

	private Thread thread;
	private boolean shouldRun;

	private Point current, previous;

	public ChaosGame(){
		panel();
		vars();
		start();
		repaint();
	}

	public void start(){
		try{
			shouldRun = true;
			Runnable run = () -> update();
			thread = new Thread(run);
			thread.start();
		}catch(Exception e){}
	}

	public void update(){
		while(shouldRun){
			Point towards = randomPointFrom(points);
			previous = current;
			current = halfWay(current,towards);
			Graphics g = canvas.getGraphics();
			g.setColor(Color.BLACK);
			g.drawLine(current.x,current.y,previous.x,previous.y);
			repaint();
			try{ Thread.sleep(SLEEP_TIME); }catch(Exception e) {}
		}
	}

	public Point randomPointFrom(ArrayList<Point> points){
		return points.get((int)(Math.random()*points.size()));
	}


	public Point halfWay(Point from, Point towards){
		return new Point(from.x+(towards.x-from.x)/2,from.y+(towards.y-from.y)/2);
	}

	public void vars(){
		points = new ArrayList<Point>();
		for(int index = 0; index < MAX_NUM_POINTS; index++){
			points.add(new Point((int)(Math.random()*windowDim.width),(int)(Math.random()*windowDim.height)));
		}
		current = randomPointFrom(points);
		previous = current;
	}

	public void panel(){
		JFrame frame = new JFrame("Chaos Game");
		frame.add(this);
		frame.setPreferredSize(windowDim);
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try{ canvas = new BufferedImage(windowDim.width, windowDim.height, BufferedImage.TYPE_INT_ARGB); }catch(Exception e){}
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		try{
			g.drawImage(canvas,0,0,null);
			drawPoints(g);
		}catch(Exception e) { }
	}

	public void drawPoints(Graphics g){
		g.setColor(Color.BLACK);
		for(Point p : points){
			g.fillOval(p.x,p.y,5,5);
		}
		g.setColor(Color.RED);
		g.fillOval(current.x,current.y,7,7);
		g.fillOval(previous.x,previous.y,7,7);
		g.drawLine(current.x,current.y,previous.x,previous.y);

	}

	public static void main(String[] args){
		new ChaosGame();
	}

}