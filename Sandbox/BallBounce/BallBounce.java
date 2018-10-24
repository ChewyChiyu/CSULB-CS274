import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
public class BallBounce extends JPanel{


	private Dimension windowDim = new Dimension(1000,800);

	private Point2D origin = new Point2D.Double(windowDim.width/2,windowDim.height/2);

	private BufferedImage canvas;

	private ArrayList<Ball> balls;

	private Ball current = null;
	private Ball load = null;

	private Point2D cursor = null;

	private final int MIN_RADIUS = 40;
	private final int MAX_RADIUS = 200;

	private Thread thread;
	private boolean isRun;

	private final int SLEEP_TIME = 3;

	private final double VECTOR_SCALE = 0.2;

	private final double FRICTION = 0.03;

	private final double GRAVITY = 0.8;

	private final double WALL_ELA = 1.0;

	private final double MAX_BALLS = 100;


	public BallBounce(){
		clearBalls();
		panel();
		mouse();
		start();
	}	

	public void start(){
		try{
			Runnable run = () -> update();
			thread = new Thread(run);
			isRun = true;
			thread.start();
		}catch(Exception e) {}
	}

	public void update(){
		while(isRun){
			int len = balls.size();
			for(int index = 0; index < len; index++){
				Ball b = balls.get(index);
				checkContact(b);
				wallBounce(b);
				checkContact(b);
			}
			for(int index = 0; index < len; index++){
				Ball b = balls.get(index);
				b.move();
				applyFriction(b);
			}
		
			repaint();
			try{ Thread.sleep(SLEEP_TIME); }catch(Exception e) {} 
		}
	}

	public void checkContact(Ball b){
		int len = balls.size();
		for(int index = 0; index < len; index++){
			Ball b2 = balls.get(index);
			if(b.equals(b2)||!b.active||!b2.active){
				continue;
			}

			if(b.contactBall(b2)){
				double bMass = b.radius/2;
				double b2Mass = b2.radius/2;
				double bDx = b.dx;
				double bDy = b.dy;
				double b2Dy = b2.dy;
				double b2Dx = b2.dx;
			
				
				double xDist = b.x - b2.x;
				double yDist = b.y - b2.dy;
			 	double distSquared = xDist*xDist + yDist*yDist;
				double xVelocity = b2Dx - bDx;
				double yVelocity = b2Dy - bDy;
				double dotProduct = xDist*xVelocity + yDist*yVelocity;
				 //Neat vector maths, used for checking if the objects moves towards one another.
				if(dotProduct > 0){
				double collisionScale = dotProduct / distSquared;
				double xCollision = xDist * collisionScale;
				double yCollision = yDist * collisionScale;
				 //The Collision vector is the speed difference projected on the Dist vector,
				//thus it is the component of the speed difference needed for the collision.
				double combinedMass = bMass + b2Mass;
				double collisionWeightA = 2 * b2Mass / combinedMass;
				double collisionWeightB = 2 * bMass / combinedMass;
				b.dx += collisionWeightA * xCollision;
				b.dy += collisionWeightA * yCollision;
				b2.dx -= collisionWeightB * xCollision;
				b2.dy -= collisionWeightB * yCollision;
				b2.move();
					}
				}
			
				

			
			
		}
	}

	public void applyFriction(Ball b){
		b.dx-=(b.dx*FRICTION);
		b.dy-=(b.dy*FRICTION);
	}

	public void wallBounce(Ball b){
		if(b.x-b.radius+b.dx<0){ b.dx = -b.dx*WALL_ELA; }
		if(b.x+b.radius+b.dx>windowDim.width){ b.dx = -b.dx*WALL_ELA; }
		if(b.y+b.radius+b.dy>=windowDim.height){
			b.dy = -(b.dy*WALL_ELA);
		}else if(b.active){
			b.dy+=GRAVITY;
		}
	}

	public void wrapAround(Ball b){
		if(b.x+b.radius+b.dx<0&&b.dx<0){ b.x = windowDim.width+b.radius*2; }
		if(b.x-b.radius+b.dx>windowDim.width&&b.dx>0){ b.x = -b.radius*2; }
		if(b.y+b.radius+b.dy<0&&b.dy<0){ b.y = windowDim.height+b.radius*2; }
		if(b.y-b.radius+b.dy>windowDim.height&&b.dy>0){ b.y = -b.radius*2; }
	}

	public void mouse(){
		addMouseListener(new MouseAdapter() {

                @Override
                public void mousePressed(MouseEvent e) {
                	current = getBallAt(e.getX(),e.getY());
                	if(current == null && balls.size() < MAX_BALLS){
                		load = createBallAt(e.getX(),e.getY());
                	}
                	if(current!=null){
            			cursor = new Point2D.Double(e.getX(),e.getY());
            		}
                }

                @Override
                public void mouseReleased(MouseEvent e) {

                   if(load!=null &&( load.radius < MIN_RADIUS || load.radius > MAX_RADIUS || load.isTouchingBall(balls))){
                   		balls.remove(load);
                   }
                   if(current!=null){
                   	 	current.impulse((current.x-cursor.getX())*VECTOR_SCALE,(current.y-cursor.getY())*VECTOR_SCALE);
                   }
                   if(load!=null){
                   	  	load.active = true;
                   }
                   current = null;
                   load = null;
                   cursor = null;
          	  }
        });
		addMouseMotionListener(new MouseMotionAdapter() { 
        	public void mouseDragged(MouseEvent e) {
            		if(load!=null){
            			load.radius = (int) Math.sqrt(load.distanceSquareToPoint(e.getX(),e.getY()));
            		}
            		if(current!=null){
            			cursor = new Point2D.Double(e.getX(),e.getY());
            		}
        	}
    	});
	}

	public Ball createBallAt(double x, double y){
		Ball b = new Ball(x,y,MIN_RADIUS);
		balls.add(b);
		return b;
	}

	public Ball getBallAt(double x, double y){
		int len = balls.size();
		for(int index = 0; index < len; index++){
			Ball b = balls.get(index);
			if(b.containsPoint(x,y)){
				return b;
			}
		}
		return null;
	}

	public void clearBalls(){
		balls = new ArrayList<Ball>();
	}

	public void panel(){
		JFrame frame = new JFrame("Ball Simulation");
		frame.add(this);
		frame.setPreferredSize(windowDim);
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		newCanvas();
		repaint();
	}

	public void newCanvas(){
		try{ canvas = new BufferedImage(windowDim.width, windowDim.height, BufferedImage.TYPE_INT_ARGB); }catch(Exception e){}
	}


	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		//draw canvas
		g.drawImage(canvas,0,0,null);
		drawBalls(g);
		//draw cursor
		if(cursor!=null){
			g.setColor(Color.BLUE);
			g.drawLine((int)cursor.getX(),(int)cursor.getY(),(int)current.x,(int)current.y);
		}
	}

	public void drawBalls(Graphics g){
		int len = balls.size();
		for(int index = 0; index < len; index++){
			balls.get(index).draw(g);
		}
	}

	public static void main(String[] args){
		new BallBounce();
	}
}
class Ball{


	public double x,y,dx,dy;

	public int radius;

	public boolean active = false;

	public boolean hit = false;

	public double prevDy = 0;

	public final double MAX_VEL = 20;


	public Ball(double x, double y, int radius){
		this.x = x;
		this.y = y;
		this.radius = radius;
		dx = 0;
		dy = 0;
	}

	public void impulse(double dx, double dy){
		this.dx+=dx;
		this.dy+=dy;
	}

	public double distanceSquareToBall(Ball b){
		return ((b.x+b.dx-x-dx)*(b.x+b.dx-x-dx)+(b.y+b.dy-y-dy)*(b.y+b.dy-y-dy));
	}

	public double distanceSquareToPoint(double x, double y){
		return ((this.x-x)*(this.x-x)+(this.y-y)*(this.y-y));
	}

	public boolean contactBall(Ball b){
		return ((b.radius+radius)*(b.radius+radius) >= distanceSquareToBall(b));
	}

	public boolean containsPoint(double x, double y){
		return (radius*radius) >= distanceSquareToPoint(x,y); 
	}	


	public boolean isTouchingBall(ArrayList<Ball> balls){
		int len = balls.size();
		for(int index = 0; index < len; index++){
			Ball b = balls.get(index);
			if(!equals(b)&&contactBall(b)){
				return true;
			}
			
		}
		return false;
	}

	public void move(){

		if(MAX_VEL<dx){ dx = MAX_VEL; }
		if(-MAX_VEL>dx){ dx = -MAX_VEL; }
		if(MAX_VEL<dy){ dy = MAX_VEL; }
		if(-MAX_VEL>dy){ dy = -MAX_VEL; }
		this.x += dx*0.1;
		this.y += dy*0.1;
	}

	public void draw(Graphics g){
		g.setColor(getColorGradient());
		g.fillOval((int)x-radius,(int)y-radius,radius*2,radius*2);
	}

	public Color getColorGradient(){
		double gradient = getGradient(Math.abs(dy)+Math.abs(dx))*255;
		if(gradient>255){
			gradient = 255;
		}
		return new Color((int)gradient,(int)(255-gradient),0);
	}

	public double getGradient(double x){
		final double MIN = 0;
		final double MAX = MAX_VEL;
		return (x-MIN)/(MAX-MIN);
	}
}
 