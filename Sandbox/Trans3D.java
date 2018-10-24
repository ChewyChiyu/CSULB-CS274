import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

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
			while(true){
				newCanvas();
				ArrayList<Matrix> draws = new ArrayList<Matrix>();
				for(Matrix p : ps){
					p = xRotation.multi_by(p);
					p = yRotation.multi_by(p);
					p = zRotation.multi_by(p);
					Matrix m = projection.multi_by(p);
					draws.add(m);
					g.fillOval((int)m.to_array()[0]+origin.x,(int)m.to_array()[1]+origin.y,R,R);
				}
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

				repaint();
				updateTheta()
				updateRotations();
				Thread.sleep(10); 
			}
		}catch(Exception e){ e.printStackTrace();}
	}

	public void updateTheta(){
		theta+=Math.PI/360;
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