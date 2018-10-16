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

public class Laser extends JPanel{

	private Dimension windowDim = new Dimension(1000,1000);

	private Point2D origin = new Point2D.Double(windowDim.width/2,windowDim.height/2);

	private Line2D[] lines = new Line2D[]{	new Line2D.Double(100,100,600,100),
											new Line2D.Double(800,100,800,600),
											new Line2D.Double(100,100,500,600),
											new Line2D.Double(100,300,100,600),
											new Line2D.Double(100,700,700,700) };

	
	private BufferedImage canvas;
	private final int EXTRA_RANGE = 2000;


	public Laser(){
		panel();
		mouse();
	}

	public void mouse(){
		addMouseMotionListener(new MouseMotionAdapter() { 
        	public void mouseMoved(MouseEvent e) {
            	drawLaser(new Point2D.Double(e.getX(),e.getY()));
            	repaint();
        	}
    	});
	}

	public void drawLaser(Point2D.Double mousePoint){
		newCanvas();
		Graphics g = canvas.getGraphics();
		boolean forward = (mousePoint.getX()>origin.getX());
		Line2D laser = extendLine(getLine(origin,mousePoint),EXTRA_RANGE,forward);
		Line2D prev = laser;
		g.setColor(Color.RED);
		for(int index = 0; index < 30; index++){
			Line2D intersectLine = null;
			for(Line2D line : lines){
				if(laser.intersectsLine(line)){
					if(intersectLine==null||intersectLine.ptLineDist(laser.getP1())>line.ptLineDist(laser.getP1())){
						if(prev!=null&&!prev.equals(line))
							intersectLine = line;
					}
				}
			}
			if(intersectLine!=null){
				laser = truncate(laser,intersectLine);
				drawLine(laser,g);
				laser = reflect(laser,intersectLine,forward);
			}else{
				drawLine(laser,g);
			}
			prev = intersectLine;
		}
		drawLine(laser,g);
		repaint();
	}

	

	public Line2D reflect(Line2D laser, Line2D line,boolean forward){
		double reciprocal = -getSlope(laser);
		if(getSlope(line)==0){ //horizontal
			if(laser.getX2()<laser.getX1()){
				forward = false;
			}else{
				forward = true;
			}
		}else if(getSlope(line)!=1000000){ // dy/dx != 0 || N/A
			double theta = (Math.atan(getSlope(line))-Math.atan(getSlope(laser)));
			double sai = -(Math.PI-2*theta);
			double x2 = laser.getX1()*Math.cos(sai)-laser.getY1()*Math.sin(sai);
			double y2 = laser.getY1()*Math.cos(sai)+laser.getX1()*Math.sin(sai);
			Graphics g = canvas.getGraphics();
			Line2D reflect = getLine(laser.getP2(),new Point2D.Double(x2,y2));
			if(laser.getX2()<laser.getX1()){
				if((getSlope(reflect)<0)||((getSlope(reflect)>0)&&(getSlope(laser)<0))){
					forward = true;
				}else{
					forward = false;
				}
			}else{
				if((getSlope(reflect)<0)||((getSlope(reflect)>0)&&(getSlope(laser)<0))){
					forward = false;
				}else{
					forward = true;
				}
			}
			return extendLine(reflect,EXTRA_RANGE,forward);
		}else{ //vertical
			if(laser.getX2()<laser.getX1()){
				forward = true;
			}else{
				forward = false;
			}
		}
		Point2D p2 = new Point2D.Double(laser.getX2()+5,laser.getY2()+reciprocal*5);
		Line2D reflect = getLine(laser.getP2(),p2);
		return extendLine(reflect,EXTRA_RANGE,forward);
	 }

	public Line2D truncate(Line2D laser, Line2D line){
		double x = getXInter(laser,line);
		double y = laser.getY1()+(x-laser.getX1())*getSlope(laser);
		return new Line2D.Double(laser.getX1(),laser.getY1(),x,y);
	}

	public double getXInter(Line2D laser, Line2D line){
		return (((-getSlope(line)*line.getX1()) + line.getY1() + (getSlope(laser)*laser.getX1()) - laser.getY1())/(getSlope(laser)-getSlope(line)));
	}

	public double getSlope(Line2D line){
		if((line.getX2()-line.getX1())==0){
			return 1000000;
		}
		return (line.getY2()-line.getY1())/(line.getX2()-line.getX1());
	}

	public Line2D getLine(Point2D p1, Point2D p2){
		return new Line2D.Double(p1.getX(),p1.getY(),p2.getX(),p2.getY());
	}

	public Line2D extendLine(Line2D line, int dL, boolean forward){
		if(forward){
			return new Line2D.Double(line.getP1().getX(),line.getP1().getY(),line.getP1().getX()+dL,line.getP1().getY()+(getSlope(line)*dL));
		}else{
			return new Line2D.Double(line.getP1().getX(),line.getP1().getY(),line.getP1().getX()-dL,line.getP1().getY()-(getSlope(line)*dL));
		}
	}

	public double distance(Point2D p1, Point2D p2){
		return Math.sqrt((p2.getY()-p1.getY())*(p2.getY()-p1.getY())+((p2.getX()-p1.getX()))*(p2.getX()-p1.getX()));
	}

	public void panel(){
		JFrame frame = new JFrame("Laser Simulation");
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
		//laser head
		g.fillOval((int)origin.getX(),(int)(origin.getY()),10,10);
		//draw lines
		drawLines(g);
	}

	public void drawLines(Graphics g){
		g.setColor(Color.BLACK);
		for(Line2D line : lines){
			drawLine(line,g);
		}
	}
	
	public void drawLine(Line2D line, Graphics g){
		g.drawLine((int)line.getX1(),(int)line.getY1(),(int)line.getX2(),(int)line.getY2());
	}

	public static void main(String[] args){
		new Laser();
	}
}