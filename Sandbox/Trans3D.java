import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.awt.Polygon;

public class Trans3D extends JPanel{

	private Dimension windowDim = new Dimension(1000,800);

	private Matrix projection = new Matrix(new float[][]{{1,0,0},{0,1,0}});
	private float theta = 0;
	private Matrix xRotation = new Matrix(new float[][]{{1,0,0},{0,(float)Math.cos(theta),-(float)Math.sin(theta)},{0,(float)Math.sin(theta),(float)Math.cos(theta)}});
	private Matrix yRotation = new Matrix(new float[][]{{(float)Math.cos(theta),0,(float)Math.sin(theta)},{0,1,0},{-(float)Math.sin(theta),0,(float)Math.cos(theta)}});
	private Matrix zRotation = new Matrix(new float[][]{{(float)Math.cos(theta),-(float)Math.sin(theta),0},{(float)Math.sin(theta),(float)Math.cos(theta),0},{0,0,1}});

	private final int L = 200;

	private Matrix p1 = new Matrix(new float[][]{{-L},{-L},{L}});
	private Matrix p2 = new Matrix(new float[][]{{L},{-L},{L}});
	private Matrix p3 = new Matrix(new float[][]{{L},{L},{L}});
	private Matrix p4 = new Matrix(new float[][]{{-L},{L},{L}});

	private Matrix p5 = new Matrix(new float[][]{{-L},{-L},{-L}});
	private Matrix p6 = new Matrix(new float[][]{{L},{-L},{-L}});
	private Matrix p7 = new Matrix(new float[][]{{L},{L},{-L}});
	private Matrix p8 = new Matrix(new float[][]{{-L},{L},{-L}});

	private BufferedImage canvas;
	private Graphics g;

	Matrix[] ps = new Matrix[]{p1,p2,p3,p4,p5,p6,p7,p8};

	private Point origin = new Point(windowDim.width/2,windowDim.height/2);

	public Trans3D(){
		panel();
		final int R = 10;
		try{
			boolean flip1 = false, flip2 = false, flip3 = true;
			while(true){
				newCanvas();
				ArrayList<Matrix> draws = new ArrayList<Matrix>();
				ArrayList<Float> zS = new ArrayList<Float>();
				for(Matrix p : ps){
					p = xRotation.multi_by(p);
					p = yRotation.multi_by(p);
					p = zRotation.multi_by(p);
					Matrix m = projection.multi_by(p);
					draws.add(m);
					zS.add(p.to_array()[2]);
					g.fillOval((int)m.to_array()[0]+origin.x,(int)m.to_array()[1]+origin.y,R,R);
				}
				g.setColor(Color.BLACK);
				g.drawLine((int)draws.get(0).to_array()[0]+origin.x,(int)draws.get(0).to_array()[1]+origin.y,(int)draws.get(1).to_array()[0]+origin.x,(int)draws.get(1).to_array()[1]+origin.y);
				g.drawLine((int)draws.get(1).to_array()[0]+origin.x,(int)draws.get(1).to_array()[1]+origin.y,(int)draws.get(2).to_array()[0]+origin.x,(int)draws.get(2).to_array()[1]+origin.y);
				g.drawLine((int)draws.get(2).to_array()[0]+origin.x,(int)draws.get(2).to_array()[1]+origin.y,(int)draws.get(3).to_array()[0]+origin.x,(int)draws.get(3).to_array()[1]+origin.y);
				g.drawLine((int)draws.get(3).to_array()[0]+origin.x,(int)draws.get(3).to_array()[1]+origin.y,(int)draws.get(0).to_array()[0]+origin.x,(int)draws.get(0).to_array()[1]+origin.y);

				g.drawLine((int)draws.get(4).to_array()[0]+origin.x,(int)draws.get(4).to_array()[1]+origin.y,(int)draws.get(5).to_array()[0]+origin.x,(int)draws.get(5).to_array()[1]+origin.y);
				g.drawLine((int)draws.get(5).to_array()[0]+origin.x,(int)draws.get(5).to_array()[1]+origin.y,(int)draws.get(6).to_array()[0]+origin.x,(int)draws.get(6).to_array()[1]+origin.y);
				g.drawLine((int)draws.get(6).to_array()[0]+origin.x,(int)draws.get(6).to_array()[1]+origin.y,(int)draws.get(7).to_array()[0]+origin.x,(int)draws.get(7).to_array()[1]+origin.y);
				g.drawLine((int)draws.get(7).to_array()[0]+origin.x,(int)draws.get(7).to_array()[1]+origin.y,(int)draws.get(4).to_array()[0]+origin.x,(int)draws.get(4).to_array()[1]+origin.y);


				g.drawLine((int)draws.get(0).to_array()[0]+origin.x,(int)draws.get(0).to_array()[1]+origin.y,(int)draws.get(4).to_array()[0]+origin.x,(int)draws.get(4).to_array()[1]+origin.y);
				g.drawLine((int)draws.get(1).to_array()[0]+origin.x,(int)draws.get(1).to_array()[1]+origin.y,(int)draws.get(5).to_array()[0]+origin.x,(int)draws.get(5).to_array()[1]+origin.y);
				g.drawLine((int)draws.get(2).to_array()[0]+origin.x,(int)draws.get(2).to_array()[1]+origin.y,(int)draws.get(6).to_array()[0]+origin.x,(int)draws.get(6).to_array()[1]+origin.y);
				g.drawLine((int)draws.get(3).to_array()[0]+origin.x,(int)draws.get(3).to_array()[1]+origin.y,(int)draws.get(7).to_array()[0]+origin.x,(int)draws.get(7).to_array()[1]+origin.y);

				//chain p1234
				int[] polyX = new int[]{(int)draws.get(0).to_array()[0]+origin.x,(int)draws.get(1).to_array()[0]+origin.x,(int)draws.get(2).to_array()[0]+origin.x,(int)draws.get(3).to_array()[0]+origin.x};
				int[] polyY = new int[]{(int)draws.get(0).to_array()[1]+origin.y,(int)draws.get(1).to_array()[1]+origin.y,(int)draws.get(2).to_array()[1]+origin.y,(int)draws.get(3).to_array()[1]+origin.y};
				Polygon poly1 = new Polygon(polyX,polyY,polyX.length);
				Face f1 = new Face(poly1,Color.RED,1);

				//chain p5678
				polyX = new int[]{(int)draws.get(4).to_array()[0]+origin.x,(int)draws.get(5).to_array()[0]+origin.x,(int)draws.get(6).to_array()[0]+origin.x,(int)draws.get(7).to_array()[0]+origin.x};
				polyY = new int[]{(int)draws.get(4).to_array()[1]+origin.y,(int)draws.get(5).to_array()[1]+origin.y,(int)draws.get(6).to_array()[1]+origin.y,(int)draws.get(7).to_array()[1]+origin.y};
				Polygon poly2 = new Polygon(polyX,polyY,polyX.length);
				Face f2 = new Face(poly2,Color.BLUE,1);

				//chain p4378
				polyX = new int[]{(int)draws.get(3).to_array()[0]+origin.x,(int)draws.get(2).to_array()[0]+origin.x,(int)draws.get(6).to_array()[0]+origin.x,(int)draws.get(7).to_array()[0]+origin.x};
				polyY = new int[]{(int)draws.get(3).to_array()[1]+origin.y,(int)draws.get(2).to_array()[1]+origin.y,(int)draws.get(6).to_array()[1]+origin.y,(int)draws.get(7).to_array()[1]+origin.y};
				Polygon poly3 = new Polygon(polyX,polyY,polyX.length);
				Face f3 = new Face(poly3,Color.GREEN,1);

				//chain p1485
				polyX = new int[]{(int)draws.get(0).to_array()[0]+origin.x,(int)draws.get(3).to_array()[0]+origin.x,(int)draws.get(7).to_array()[0]+origin.x,(int)draws.get(4).to_array()[0]+origin.x};
				polyY = new int[]{(int)draws.get(0).to_array()[1]+origin.y,(int)draws.get(3).to_array()[1]+origin.y,(int)draws.get(7).to_array()[1]+origin.y,(int)draws.get(4).to_array()[1]+origin.y};
				Polygon poly4 = new Polygon(polyX,polyY,polyX.length);
				Face f4 = new Face(poly4,Color.YELLOW,1);
					
				//chain p1265
				polyX = new int[]{(int)draws.get(0).to_array()[0]+origin.x,(int)draws.get(1).to_array()[0]+origin.x,(int)draws.get(5).to_array()[0]+origin.x,(int)draws.get(4).to_array()[0]+origin.x};
				polyY = new int[]{(int)draws.get(0).to_array()[1]+origin.y,(int)draws.get(1).to_array()[1]+origin.y,(int)draws.get(5).to_array()[1]+origin.y,(int)draws.get(4).to_array()[1]+origin.y};
				Polygon poly5 = new Polygon(polyX,polyY,polyX.length);
				Face f5 = new Face(poly5,Color.ORANGE,1);

				//chain p2673
				polyX = new int[]{(int)draws.get(1).to_array()[0]+origin.x,(int)draws.get(5).to_array()[0]+origin.x,(int)draws.get(6).to_array()[0]+origin.x,(int)draws.get(2).to_array()[0]+origin.x};
				polyY = new int[]{(int)draws.get(1).to_array()[1]+origin.y,(int)draws.get(5).to_array()[1]+origin.y,(int)draws.get(6).to_array()[1]+origin.y,(int)draws.get(2).to_array()[1]+origin.y};
				Polygon poly6 = new Polygon(polyX,polyY,polyX.length);
				Face f6 = new Face(poly6,Color.PINK,1);
				

				if(zS.get(3)>zS.get(7)){
					f1.draw(g);
				}else{
					f2.draw(g);
				}
				if(zS.get(3)>zS.get(2)){
					f4.draw(g);
				}else{
					f6.draw(g);
				}
				if(zS.get(3)>zS.get(0)){
					f3.draw(g);
				}else{
					f5.draw(g);
				}
				
				repaint();
				updateTheta();
				updateRotations();
				Thread.sleep(10); 
			}
		}catch(Exception e){ e.printStackTrace();}
	}

	public void updateTheta(){
		theta+=Math.PI/(360*2);
		if(theta>Math.PI*2){
			theta = 0;
		}
	}

	public void updateRotations(){
		 xRotation = new Matrix(new float[][]{{1,0,0},{0,(float)Math.cos(theta),-(float)Math.sin(theta)},{0,(float)Math.sin(theta),(float)Math.cos(theta)}});
		 yRotation = new Matrix(new float[][]{{(float)Math.cos(theta),0,(float)Math.sin(theta)},{0,1,0},{-(float)Math.sin(theta),0,(float)Math.cos(theta)}});
		 zRotation = new Matrix(new float[][]{{(float)Math.cos(theta),-(float)Math.sin(theta),0},{(float)Math.sin(theta),(float)Math.cos(theta),0},{0,0,1}});
	}

	public void panel(){
		JFrame frame = new JFrame("3D Simulation");
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
			g = canvas.getGraphics();
			g.setColor(Color.BLACK);
		}catch(Exception e){}
	}
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(canvas,0,0,null);
	}
	public static void main(String[] args){
		new Trans3D();
	}
}

class Face{
	public Polygon p;
	public Color c;
	public float z;
	public Face(Polygon p, Color c, float z){
		this.p = p;
		this.c = c;
		this.z = z;
	}

	public void draw(Graphics g){
		g.setColor(c);
		g.fillPolygon(p);
		g.setColor(Color.BLACK);
	}
}