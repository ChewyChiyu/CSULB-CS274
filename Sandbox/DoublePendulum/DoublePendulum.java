import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;
public class DoublePendulum extends JPanel{

	private Dimension windowDim = new Dimension(800,800);
	private BufferedImage canvas;
	private Graphics gCanvas;


	private Point2D origin = new Point2D.Double(windowDim.width/2,windowDim.height/3);


	private double m1 = 20, m2 = 10;
	private double theta1 = Math.PI/2, theta2 = Math.PI/3;
	private double l1 = 120, l2 = 240;

	private double r1 = m1+20, r2 = m2+20;

	private final double G = 1.8;

	private boolean keepRun, pause;
	private final int SLEEP_TIME = 10;

	private boolean h1Press = false, h2Press = false;

	public DoublePendulum(){
		panel();
		repaint();
		input();
		start();
	}	

	public void input(){
		addMouseListener(new MouseAdapter() {

                @Override
                public void mousePressed(MouseEvent e) {
                	final int BUFFER_SCALE = 5;
                   	if(distanceSquared(getH1Point().getX(),getH1Point().getY(),e.getX(),e.getY())<r1*r1*BUFFER_SCALE){
                   		h1Press = true;
                   		pause();
                   	}else if(distanceSquared(getH2Point().getX(),getH2Point().getY(),e.getX(),e.getY())<r2*r2*BUFFER_SCALE){
                   		h2Press = true;
                   		pause();
                   	}

                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    
                    if(h1Press||h2Press){
                    	h1Press = false;
                 		h2Press = false;
                        pause();
                        newCanvas();
                    }
               		
            }
        });
		addMouseMotionListener(new MouseMotionAdapter() { 
        	public void mouseDragged(MouseEvent e) {
            		
            		if(h1Press){
            			if(e.getX()>origin.getX())
            				theta1 = Math.acos((e.getY()-origin.getY())/Math.sqrt(distanceSquared(e.getX(),e.getY(),origin.getX(),origin.getY())));
            			else
            				theta1 = -Math.acos((e.getY()-origin.getY())/Math.sqrt(distanceSquared(e.getX(),e.getY(),origin.getX(),origin.getY())));
            		}else if(h2Press){
            			Point2D h1 = getH1Point();
            			if(e.getX()>h1.getX())
            				theta2 = Math.acos((e.getY()-h1.getY())/Math.sqrt(distanceSquared(e.getX(),e.getY(),h1.getX(),h1.getY())));
            			else
            				theta2 = -Math.acos((e.getY()-h1.getY())/Math.sqrt(distanceSquared(e.getX(),e.getY(),h1.getX(),h1.getY())));
            		}
               		repaint();
            	
        	}
    	});
	}

	public double distanceSquared(double x1, double y1, double x2, double y2){
		return (y2-y1)*(y2-y1)+(x2-x1)*(x2-x1);
	}

	public void start(){
		keepRun = true;
		pause = false;
		run();
	}	

	public void pause(){
		pause = !pause;
	}

	public void stop(){
		keepRun = false;
	}

	public void run(){
		double dTheta1 = 0;
		double dTheta2 = 0;
	 	final double D_SPEED = 0.0005;
		while(keepRun){
			System.out.println("dT1: " + dTheta1 + " dT2: " + dTheta2);
			if(pause){ dTheta1 = 0; dTheta2 = 0; continue; }
			double fixDTheta1 = D_SPEED*((-l1*G)*(m1+m2)*Math.sin(theta1)-m2*l1*l2*dTheta1*dTheta2*Math.sin(theta1-theta2));
			double fixDTheta2 = D_SPEED*(m2*l1*l2*dTheta1*dTheta2*Math.sin(theta1-theta2)-l2*m2*G*Math.sin(theta2));
			dTheta1+=(D_SPEED*fixDTheta1);
			theta1+=(dTheta1);
			dTheta2+=(D_SPEED*fixDTheta2);
			theta2+=(dTheta2);


			if(theta1>Math.PI*2){ theta1 = 0; }
			if(theta2>Math.PI*2){ theta2 = 0; }

			repaint();
			try{ Thread.sleep(SLEEP_TIME);}catch(Exception e){e.printStackTrace();}
		}

	}

	public void panel(){
		JFrame frame = new JFrame("Double Pendulum");
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
		try{ 
			canvas = new BufferedImage(windowDim.width, windowDim.height, BufferedImage.TYPE_INT_ARGB);
			gCanvas = canvas.getGraphics();
			gCanvas.setColor(Color.RED);
		}catch(Exception e){}
	}


	public Point2D getH1Point(){
		return new Point2D.Double(origin.getX()+l1*Math.sin(theta1),origin.getY()+l1*Math.cos(theta1));
	}

	public Point2D getH2Point(){
		return new Point2D.Double(getH1Point().getX()+l2*Math.sin(theta2),getH1Point().getY()+l2*Math.cos(theta2));
	}

	public void penPos(Graphics g){
		Point2D hFix = getH1Point();
		g.drawLine((int)(origin.getX()),(int)(origin.getY()),(int)(hFix.getX()),(int)(hFix.getY()));
		g.fillOval((int)(hFix.getX()-r1/2),(int)(hFix.getY()-r1/2),(int)r1,(int)r1);
		Point2D h2Fix =  getH2Point();
		g.drawLine((int)(hFix.getX()),(int)(hFix.getY()),(int)(h2Fix.getX()),(int)(h2Fix.getY()));
		g.fillOval((int)(h2Fix.getX()-r2/2),(int)(h2Fix.getY()-r2/2),(int)r2,(int)r2);
		gCanvas.fillOval((int)(h2Fix.getX()),(int)(h2Fix.getY()),3,3);
	}
	

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(canvas,0,0,null);
		//drawing pendulum components
		//origin
		g.fillOval((int)origin.getX()-5,(int)origin.getY()-5,10,10);
		penPos(g);
	}

	public static void main(String[] args){
		new DoublePendulum();
	}
}