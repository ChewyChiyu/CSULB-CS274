import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
@SuppressWarnings("serial")
public class Firework extends JPanel{


	private Dimension windowDim = new Dimension(1000,800);
	private final int LAUNCH_LEVEL = windowDim.height; 
	private final int SLEEP_TIME = 20;

	private Thread cycle;
	private boolean isRun;


	private ArrayList<Rocket> rockets;

	public Firework(){
		panel();
		start();
		loadRockets();

	}

	public void loadRockets(){
		rockets.add(new Rocket(new Point((int)(Math.random()*windowDim.width),LAUNCH_LEVEL)));
	}

	public void start(){
		isRun = true;
		Runnable run = () -> update();
		cycle = new Thread(run);
		cycle.start();
		rockets = new ArrayList<Rocket>();
	}

	public void update(){
		while(isRun){
			repaint();
			for(int index = 0; index < rockets.size(); index++){
				Rocket r = rockets.get(index);
				r.update();
				if(r.shouldDelete()){
					rockets.remove(r);
					index--;
				}
			}
			if(Math.random()<0.3&&Math.random()<0.2){ // (1/30)*(1/20) chance
				loadRockets();
			}
			try{ Thread.sleep(SLEEP_TIME); }catch(Exception e){}
		}
	}

	public void panel(){
		JFrame frame = new JFrame("Firework Display");
		frame.add(this);
		frame.setPreferredSize(windowDim);
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBackground(Color.BLACK);
	}


	public void paintComponent(Graphics g){
		super.paintComponent(g);
		for(int index = 0; index < rockets.size(); index++){
			Rocket r = rockets.get(index);
			r.draw(g);	
		}
	}

	public static void main(String[] args){
		new Firework();
	}

}

class Rocket{


	private ArrayList<Particle> particles;
	private boolean launch;
	private Point launchPoint;


	final int launchParticleNum = 1, launchTime = 40;
	final Dimension launchDim = new Dimension(1,6);
	final Color launchColor = Color.WHITE;
	final Point launchVelocity = new Point(0,-25+(int)(Math.random()*-10));

	final int explodeParticleNum = 800, explodeTime = 20+(int)(Math.random()*10);
	private Color explodeColor;
	final Dimension explodeDim = new Dimension(1,1);

	private boolean exploded;

	public Rocket(Point launchPoint){
		this.launchPoint = launchPoint;
		particles = new ArrayList<Particle>();
		launch();
	}	

	public void launch(){
		launch = true;
		exploded = false;
		loadLaunch();
	}

	public void loadLaunch(){
		for(int index = 0; index < launchParticleNum; index++){
			Particle p = new Particle(launchTime,launchDim,launchColor,new Point2D.Double(launchPoint.x,launchPoint.y),new Point2D.Double(launchVelocity.x,launchVelocity.y));
			particles.add(p);
		}
	}

	public void explode(Point2D center){
		exploded = true;
		explodeColor = randColor();
		for(int index = 0; index < explodeParticleNum; index++){
			Point2D explodeVelocity = new Point2D.Double(((Math.random()<.3)?1:-1)*Math.random()*((Math.random()<.3)?5:3),((Math.random()<.3)?1:-1)*Math.random()*((Math.random()<.3)?10:7));
			Particle p = new Particle(explodeTime,explodeDim,explodeColor,new Point2D.Double(center.getX(),center.getY()),explodeVelocity);
			particles.add(p);
		}
	}


	public Color randColor(){
		switch((int)(Math.random()*5)){
			case 0:
				return Color.RED;
			case 1:
				return Color.BLUE;
			case 2:
				return Color.ORANGE;
			case 3:
				return Color.PINK;
			case 4:
				return Color.YELLOW;
		}
		return null;
	}

	public void update(){
		if(!launch){ return; }
		for(int index = 0; index < particles.size(); index++){
			Particle p = particles.get(index);
			p.update();
			if(p.shouldDelete()){
				if(particles.size()==1&&!exploded){ //explode
					explode(particles.get(0).getPos());
				}
				particles.remove(p);
				index--;
			}
		}
	}

	public void draw(Graphics g){
		for(int index = 0; index < particles.size(); index++){
			Particle p = particles.get(index);
			p.draw(g);
		}
	}

	public boolean shouldDelete(){
		return particles.isEmpty();
	}

}

class Particle{

	private int lifeTime;
	private Dimension dim;
	private Color color;
	private Point2D velocity;
	private Point2D position;

	private double gravity, terminalVelocity;
	
	public Particle(int lifeTime, Dimension dim, Color color, Point2D position, Point2D velocity){
		this.lifeTime = lifeTime;
		this.dim = dim;
		this.color = color;
		this.position = position;
		this.velocity = velocity;
		gravity = 0.001;
		terminalVelocity = 1;
	}

	public void update(){
		lifeTime--;
		position.setLocation(position.getX()+velocity.getX(),position.getY()+velocity.getY());
		if(velocity.getY()<terminalVelocity){
			velocity.setLocation(velocity.getX(),velocity.getY()+terminalVelocity);
		}
	}

	public boolean shouldDelete(){
		return lifeTime<=0;
	}

	public Point2D getPos(){
		return position;
	}

	public void draw(Graphics g){
		if(Math.random()<0.17&&velocity!=null&&velocity.getY()>0){
			g.setColor(Color.WHITE);
		}else{
			g.setColor(color);
		}
		
		g.fillRect((int)position.getX(),(int)position.getY(),dim.width,dim.height);
	}
}
