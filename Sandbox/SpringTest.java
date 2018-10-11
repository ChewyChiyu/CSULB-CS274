import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class SpringTest extends JPanel{

	private Dimension windowDim = new Dimension(800,800);
	private Point center = new Point(windowDim.width/2,windowDim.height/3);
	private double dy = 1;
	private double dx = 0;
	private Thread thread;
	private boolean isRun;
	private boolean isTracking;

	private final int SLEEP_TIME = 5;

	private BufferedImage canvas ;

	public SpringTest(){
		panel();
		input();
		start(dy,dx);
	}

	public void input(){
		 addMouseListener(new MouseAdapter() {

                @Override
                public void mousePressed(MouseEvent e) {
                    isTracking = true;
                    isRun = false;
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    isTracking = false;
                    start(e.getY()-center.y,e.getX()-center.x);
               		newCanvas();
               	}
            });
		addMouseMotionListener(new MouseMotionAdapter() { 
        	public void mouseDragged(MouseEvent e) {
            	if(isTracking){ 
               		dy = e.getY()-center.y;
               		dx = e.getX()-center.x;
               		repaint();
            	}
        }
    	});

	}

	public void start(double dy, double dx){
		try{
			Runnable run = () -> update(dy,dx);
			thread = new Thread(run);
			isRun = true;
			isTracking = false;
			thread.start();
		}catch(Exception e){}
	}

	public void update(double y, double x){
		double vY = 0,vX=0;
		double aY = 0,aX=0;
		double k = .2;
		double m = .5;
		double b = 0.09;
		final double GRAVITY = -200*m;
		while(isRun){
			aY = (-(k/m)*y)-((b/m)*vY)-GRAVITY;
			aX = (dx/dy)*((-(k/m)*y)-((b/m)*vY))-(aX*0.7);
			vY+=(aY*0.1);
			vX+=(aX*0.1);
			y+=(vY*0.1);
			x+=(vX*0.1);
			dy = y;
			dx = x;
			drawPoint();
			repaint();
			try{ Thread.sleep(SLEEP_TIME);}catch(Exception e){}
		}
	}

	public void drawPoint(){
		Graphics g = canvas.getGraphics();
		g.setColor(Color.RED);
		g.fillOval((int)(center.x+dx)-2,(int)(center.y+dy)-2,4,4);	
	}

	public void panel(){
		JFrame frame = new JFrame("Spring Simulation");
		frame.add(this);
		frame.setPreferredSize(windowDim);
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		newCanvas();
	}

	public void newCanvas(){
		try{ canvas = new BufferedImage(windowDim.width, windowDim.height, BufferedImage.TYPE_INT_ARGB); }catch(Exception e){}
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(canvas,0,0,null);
		g.fillOval(center.x-3,center.y-3,6,6);
		g.drawLine(center.x,center.y,(int)(center.x+dx),(int)(center.y+dy));
		g.fillOval((int)(center.x+dx)-50,(int)(center.y+dy)-50,100,100);	
		System.out.println(dx);
	}

	public static void main(String[] args){
		new SpringTest();
	}
}