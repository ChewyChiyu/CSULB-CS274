
import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.geom.Point2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;

public class PendulumTest extends JPanel{
		
	private Dimension windowDim = new Dimension(800,800);
	private BufferedImage canvas;

	private Point2D center = new Point2D.Double(400,600); 

	private double x = 0.0, y = 0.0;
	final double STRING_LEN = 40;

	private Thread thread;
	private boolean shouldRun;
	private boolean isTracking;
	private final int SCALE = 14;
	private final int SLEEP_TIME = 1;
	public PendulumTest(){
		panel();
		input();
		start();
	}

	public void input(){
		addMouseListener(new MouseAdapter() {

                @Override
                public void mousePressed(MouseEvent e) {
                    isTracking = true;
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
            		x = (e.getX()-center.getX())/SCALE;
            		y = STRING_LEN-Math.tan(Math.acos(x/STRING_LEN))*x;
               		repaint();
            	}
        	}
    	});
	}

	public void start(){
		try{
			Runnable run = () -> update();
			shouldRun = true;
			isTracking = false;
			thread = new Thread(run);
			thread.start();
		}catch(Exception e){}
	}

	public void update(){
		final int MAX_TIME = 60;
		final double DELTA_TIME = 0.01;
		final double INITIAL_X = 22;
		final double GRAVITY = -4;
		final double AIR_RES = 0.04;
		x = INITIAL_X;
		y = STRING_LEN-Math.tan(Math.acos(x/STRING_LEN))*x;
		double m = 1;
		double vX = 0;
		while(shouldRun){
			if(!isTracking){
				vX += ((((y/x)*(GRAVITY*m))-AIR_RES*vX)*DELTA_TIME)/m;
				x+=vX*DELTA_TIME;
				y = STRING_LEN-Math.tan(Math.acos(x/STRING_LEN))*x;
				repaint();
			}
			try{ Thread.sleep(SLEEP_TIME);}catch(Exception e){}
		}
	}

	public static void main(String[] args){
		new PendulumTest();
	}

	public void panel(){
		JFrame frame = new JFrame("Pendulum Test");
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
		//draw canvas
		g.drawImage(canvas,0,0,null);
		//draw pendulum head
		g.setColor(Color.BLACK);
		g.drawLine((int)(center.getX()+x*SCALE),(int)(center.getY()-y*SCALE),(int)center.getX(),(int)(center.getY()-STRING_LEN*SCALE));
		g.fillOval((int)(center.getX()+x*SCALE)-25,(int)(center.getY()-y*SCALE)-25,50,50);
	}
}