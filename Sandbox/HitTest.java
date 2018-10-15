import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import javax.swing.KeyStroke;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class HitTest extends JPanel{
	private Thread thread;

	private boolean isRun;

	private Dimension windowDim = new Dimension(1200,1000);
	private BufferedImage canvas;

	private final int SLEEP_TIME = 5;

	private Polygon triangle;
	private Cab cab;

	private HitLine[] lines = new HitLine[]{new HitLine(new Point(300,200),new Point(300,750)),
											new HitLine(new Point(500,700),new Point(1000,700)),
											new HitLine(new Point(300,400),new Point(700,700)),
											new HitLine(new Point(300,400),new Point(800,200))};


												
	public HitTest(){
		panel();
		load();
		keys();
		start();
	}

	public void keys(){
		getInputMap().put(KeyStroke.getKeyStroke("A"), "A");		
		getActionMap().put("A", new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e) {
				cab.dx = -cab.speed;
			}
		});
		getInputMap().put(KeyStroke.getKeyStroke("released A"), "rA");		
		getActionMap().put("rA", new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e) {
				cab.dx = 0;
			}
		});
		getInputMap().put(KeyStroke.getKeyStroke("D"), "D");		
		getActionMap().put("D", new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e) {
				cab.dx = cab.speed;
			}
		});
		getInputMap().put(KeyStroke.getKeyStroke("released D"), "rD");		
		getActionMap().put("rD", new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e) {
				cab.dx = 0;
			}
		});
		getInputMap().put(KeyStroke.getKeyStroke("W"), "W");		
		getActionMap().put("W", new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e) {
				cab.dy = -cab.speed;
			}
		});
		getInputMap().put(KeyStroke.getKeyStroke("released W"), "rW");		
		getActionMap().put("rW", new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e) {
				cab.dy = 0;
			}
		});
		getInputMap().put(KeyStroke.getKeyStroke("S"), "S");		
		getActionMap().put("S", new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e) {
				cab.dy = cab.speed;
			}
		});
		getInputMap().put(KeyStroke.getKeyStroke("released S"), "rS");		
		getActionMap().put("rS", new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e) {
				cab.dy = 0;
			}
		});
	}

	public void load(){
		//points for cab
		cab = new Cab(new Point(100,100),new Dimension(70,70));
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
		while(isRun){
			cab.move(lines);
			repaint();
			try{ Thread.sleep(SLEEP_TIME);}catch(Exception e){}
		}
	}

	public void panel(){
		JFrame frame = new JFrame("Hit Test");
		frame.add(this);
		frame.setPreferredSize(windowDim);
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		newCanvas();
	}

	private void newCanvas(){
		try{ canvas = new BufferedImage(windowDim.width, windowDim.height, BufferedImage.TYPE_INT_ARGB); }catch(Exception e){}
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(canvas,0,0,null);
		try{
			cab.draw(g);
			for(HitLine hl : lines){
				hl.draw(g);
			}
		}catch(Exception e) {}
	}

	public static void main(String[] args){
		new HitTest();
	}
}
class Cab{

	public Point loc;
	public Dimension dim;

	public int dx = 0,dy = 0;

	public int speed = 3;

	public boolean canMove = true;

	private Point testP = new Point(0,0);									

	public Cab(Point spawn, Dimension size){
		loc = spawn;
		dim = size;
	}

	public void move(HitLine[] hls){
		boolean canMoveDx = true, canMoveDy = true;
		for(HitLine hl : hls){
			//vertical/horizontal line test
			if(hl.p2.x==hl.p1.x||hl.p2.y==hl.p1.y){
				if((loc.x+dx<hl.p2.x&&loc.x+dx+dim.width>hl.p1.x)&&(loc.y<hl.p2.y&&loc.y+dim.height>hl.p1.y)){
					canMoveDx = false;
				}
				if((loc.x<hl.p2.x&&loc.x+dim.width>hl.p1.x)&&(loc.y+dy<hl.p2.y&&loc.y+dy+dim.height>hl.p1.y)){
					canMoveDy = false;
				}
			}else{ // slope contact movement
				double slope = (double)(hl.p2.y-hl.p1.y)/(double)(hl.p2.x-hl.p1.x);
				boolean inBounds = (loc.x+dx<hl.p2.x&&loc.x+dx+dim.width>hl.p1.x)&&(loc.y+dy<hl.p2.y&&loc.y+dy+dim.height>hl.p1.y);
				if(slope<0){
					inBounds = (loc.x+dx<hl.p2.x&&loc.x+dx+dim.width>hl.p1.x)&&(loc.y+dy<hl.p1.y&&loc.y+dy+dim.height>hl.p2.y);
				}
				if(inBounds){
					double hitY = slope*(loc.x+dx-hl.p2.x)+hl.p2.y;
					double topLeft = hitY;
					double topRight = slope*(loc.x+dim.width+dx-hl.p2.x)+hl.p2.y;
					testP = new Point(loc.x,(int)hitY);
					if(dx>0){
						if(topRight>loc.y&&topLeft<loc.y+dim.height&&slope>0){
							canMoveDx = false;
							loc.y+=slope*speed;
						}
						if(topLeft>loc.y&&topRight<loc.y+dim.height&&slope<0){
							canMoveDx = false;
							loc.y+=slope*speed;
						}

					}
					if(dx<0){
						if(topLeft<loc.y+dim.height&&topRight>loc.y&&slope>0){
							canMoveDx = false;
							loc.y-=slope*speed;
						}
						if(topLeft>loc.y&&topRight<loc.y+dim.height&&slope<0){
							canMoveDx = false;
							loc.y-=slope*speed;
						}
					}
					if(dy<0){
						if(topLeft>loc.y+dy&&topRight<loc.y+dy+dim.height&&slope<0){
							canMoveDy = false;
							loc.x-=slope*speed;
						}	
						if(topRight>loc.y+dy&&topLeft<loc.y+dy+dim.height&&slope>0){
							canMoveDy = false;
							loc.x-=slope*speed;
						}
					}	
					if(dy>0){
						if(topLeft>loc.y+dy&&topRight<loc.y+dy+dim.height&&slope<0){
							canMoveDy = false;
							loc.x+=slope*speed;
						}
						if(topRight>loc.y+dy&&topLeft<loc.y+dy+dim.height&&slope>0){
							canMoveDy = false;
							loc.x+=slope*speed;
						}
					}

				}
				
			}
		}
		if(canMoveDx){ loc.x+=dx;}
		if(canMoveDy){ loc.y+=dy;}
		
	}


	public void draw(Graphics g){
		g.fillRect(loc.x,loc.y,dim.width,dim.height);
		g.setColor(Color.RED);
		g.fillOval(testP.x,testP.y,4,4);
		g.setColor(Color.BLACK);
	}
}
class HitLine{
	public Point p1, p2;
	public HitLine(Point p1, Point p2){
		this.p1 = p1;
		this.p2 = p2;
	}
	public void draw(Graphics g){
		g.drawLine(p1.x,p1.y,p2.x,p2.y);
	}
}