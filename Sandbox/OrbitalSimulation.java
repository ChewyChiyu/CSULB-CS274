import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
@SuppressWarnings("serial")
public class OrbitalSimulation extends JPanel{

	private Thread thread;

	private boolean isRun;
	private boolean isTracking;

	private Dimension windowDim = new Dimension(1200,1000);
	private BufferedImage canvas;

	private final int SLEEP_TIME = 20;

	private final Point origin = new Point(windowDim.width/2,windowDim.height/2);

	private Planet p1, p2, p3;

	private Planet currentSelect;

	private final int SCALE = 4;

	public OrbitalSimulation(){
		panel();
		input();
		initialize();
		start();
	}

	private void initialize(){
		p1 = new Planet(new Point(origin.x*SCALE+200,origin.y*SCALE+200),50,15);
		p2 = new Planet(new Point(origin.x*SCALE+300,origin.y*SCALE),100,300);
		p3 = new Planet(new Point(origin.x*SCALE-500,origin.y*SCALE-200),300,1000);
	}

	private void input(){
		addMouseListener(new MouseAdapter() {

                @Override
                public void mousePressed(MouseEvent e) {
                	currentSelect = planetAtLoc(e.getX()*SCALE,e.getY()*SCALE);
                    if(currentSelect!=null){
                    	isTracking = true;
                	}else{
                		initialize();
                	}
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    isTracking = false;
               		newCanvas();
            }
        });
		addMouseMotionListener(new MouseMotionAdapter() { 
        	public void mouseDragged(MouseEvent e) {
            	if(isTracking){ 
            		currentSelect.moveTo((int)(e.getX()*SCALE),(int)(e.getY()*SCALE));
               		repaint();
            	}
        	}
    	});
	}

	private void panel(){
		JFrame frame = new JFrame("Orbital Simulation");
		frame.add(this);
		frame.setPreferredSize(windowDim);
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		newCanvas();
	}	

	private Planet planetAtLoc(int x, int y){
		if(p1.x+p1.getRadius()/2>x&&
			p1.x-p2.getRadius()<x&&
			p1.y+p1.getRadius()/2>y&&
			p1.y-p2.getRadius()<y){
			return p1;
		}else if(p2.x+p2.getRadius()/2>x&&
			p2.x-p2.getRadius()<x&&
			p2.y+p2.getRadius()/2>y&&
			p2.y-p2.getRadius()<y){
			return p2;
		}else if(p3.x+p3.getRadius()/2>x&&
			p3.x-p3.getRadius()<x&&
			p3.y+p3.getRadius()/2>y&&
			p3.y-p3.getRadius()<y){
			return p3;
		}else{
			return null;
		}
	}

	private void newCanvas(){
		try{ canvas = new BufferedImage(windowDim.width, windowDim.height, BufferedImage.TYPE_INT_ARGB); }catch(Exception e){}
	}

	private synchronized void start(){
		try{
			Runnable run = () -> update();
			thread = new Thread(run);
			isRun = true;
			thread.start();
		}catch(Exception e) {}
	}

	private void update(){
		final double GRAVITY_CONST = 3;
		while(isRun){
			System.out.println(distanceBetween(p1,p2));
			if(isTracking){ continue; }
			double p1p3Distance = distanceBetween(p1,p3);
			if(!(p1p3Distance<(p1.getRadius()/2+p3.getRadius()/2))){ 
				p1.vX += (GRAVITY_CONST*p3.getMass()*(p3.x-p1.x))/(p1p3Distance*p1p3Distance);
				p1.vY += (GRAVITY_CONST*p3.getMass()*(p3.y-p1.y))/(p1p3Distance*p1p3Distance);
				p3.vX += (GRAVITY_CONST*p1.getMass()*(p1.x-p3.x))/(p1p3Distance*p1p3Distance);
				p3.vY += (GRAVITY_CONST*p1.getMass()*(p1.y-p3.y))/(p1p3Distance*p1p3Distance);
			}
			double p2p3Distance = distanceBetween(p2,p3);
			if(!(p2p3Distance<(p2.getRadius()/2+p3.getRadius()/2))){ 
				p2.vX += (GRAVITY_CONST*p3.getMass()*(p3.x-p2.x))/(p2p3Distance*p2p3Distance);
				p2.vY += (GRAVITY_CONST*p3.getMass()*(p3.y-p2.y))/(p2p3Distance*p2p3Distance);
				p3.vX += (GRAVITY_CONST*p2.getMass()*(p2.x-p3.x))/(p2p3Distance*p2p3Distance);
				p3.vY += (GRAVITY_CONST*p2.getMass()*(p2.y-p3.y))/(p2p3Distance*p2p3Distance);
			}



			markCanvas();

			p1.move();
			p2.move();
			p3.move();

			repaint();
			try{ Thread.sleep(SLEEP_TIME);}catch(Exception e) {}
		}
	}

	public void markCanvas(){
		Graphics g = canvas.getGraphics();
		g.setColor(Color.RED);
		g.fillOval((int)p1.x/SCALE,(int)p1.y/SCALE,3,3);
		g.setColor(Color.BLUE);
		g.fillOval((int)p2.x/SCALE,(int)p2.y/SCALE,3,3);
		g.setColor(Color.GREEN);
		g.fillOval((int)p3.x/SCALE,(int)p3.y/SCALE,3,3);
	}

	public double distanceBetween(Planet p1, Planet p2){
		return Math.sqrt((p2.x-p1.x)*(p2.x-p1.x)+(p2.y-
			   p1.y)*(p2.y-p1.y));
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(canvas,0,0,null);
		try{
			p1.draw(g,SCALE);
			p2.draw(g,SCALE);
			p3.draw(g,SCALE);
		}catch(Exception e) {e.printStackTrace();}
	}

	public static void main(String[] args){
		new OrbitalSimulation();
	}
}
class Planet{
	private double radius;
	private double mass;
	public double x,y;
	public double vX,vY;

	public Planet(Point loc, double radius, double mass){
		x = loc.x;
		y = loc.y;
		this.radius = radius;
		this.mass = mass;
	}

	public void impulse(double vX, double vY){
		this.vX+=vX;
		this.vY+=vY;
	}

	public void move(){
		x+=vX;
		y+=vY;
	}

	public void moveTo(double x, double y){
		this.x = x;
		this.y = y;
	}

	public double getMass(){ return mass; }
	public double getRadius(){ return radius; }

	public void draw(Graphics g, int scale){
		g.fillOval((int)(x-radius/2)/scale,(int)(y-radius/2)/scale,(int)radius/scale,(int)radius/scale);
	}
}