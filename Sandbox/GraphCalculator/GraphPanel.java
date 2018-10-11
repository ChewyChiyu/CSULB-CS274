import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import javax.swing.*;

public class GraphPanel extends JPanel{

	private Dimension graphDim;
	private int tickScale = 15, tickDelta = 5;
	private Point origin;
	private BufferedImage canvas;
	private ArrayList<Function> functions = new ArrayList<Function>();


	public GraphPanel(GraphCalculatorGUI gui){
		panel(gui);
	}
	public void panel(GraphCalculatorGUI gui){
		graphDim = new Dimension(900,gui.getDim().height);
		origin = new Point(graphDim.width/2,graphDim.height/2);
		this.setPreferredSize(graphDim);
		clear();
		repaint();
	}

	public void clearFunctions(){
		functions.clear();
		clear();
	}

	public void clear(){
		try{ canvas = new BufferedImage(graphDim.width, graphDim.height, BufferedImage.TYPE_INT_ARGB); }catch(Exception e){}
		drawBack(canvas.getGraphics());
		drawFunctions(canvas.getGraphics());
		repaint();
	}

	public void drawFunctions(Graphics g){
		Graphics2D g2d = (Graphics2D) g;
		AffineTransform a = g2d.getTransform();
		g2d.setColor(Color.BLACK);
		g2d.translate(origin.x, origin.y);
		for(Function f : functions){
			drawFunction(f,g);
		}
		g2d.fillRect(-5,-5,10,10);
		g2d.setTransform(a);
	}

	public void addFunction(Function f){
		functions.add(f);
		clear();
	}

	public void drawFunction(Function f, Graphics g){
		g.setColor(Color.RED);
		Point prev = null;
		for(int x = (graphDim.width/tickScale); x >=0 ; x--){
			g.fillRect(-x*tickScale,-(int)(f.inputNegative((double)x)*tickScale),5,5);
			if(prev!=null)
				g.drawLine(-x*tickScale,-(int)(f.inputNegative((double)x)*tickScale),prev.x,prev.y);
			prev = new Point(-x*tickScale,-(int)(f.inputNegative((double)x)*tickScale));
		} 
		prev = null;
		for(int x = 0; x < (graphDim.width/tickScale); x++){
			g.fillRect(x*tickScale,-(int)(f.inputPositive((double)x)*tickScale),5,5);
			if(prev!=null)
				g.drawLine(x*tickScale,-(int)(f.inputPositive((double)x)*tickScale),prev.x,prev.y);
			prev = new Point(x*tickScale,-(int)(f.inputPositive((double)x)*tickScale));
		} 
		
	}

	public void drawBack(Graphics g){
		g.setColor(Color.BLACK);
		for(int y = 0; y < graphDim.height; y+=tickScale){
			g.drawLine(0,y,graphDim.width,y);
		}
		for(int x = 0; x < graphDim.width; x+=tickScale){
			g.drawLine(x,0,x,graphDim.height);
		}
		//axis
		g.fillRect(0,(graphDim.height/2)+1,graphDim.width,2);
		g.fillRect((graphDim.width/2)-1,0,2,graphDim.height);

	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(canvas,0,0,null);	
	}

	public void scale(int s){
		tickScale = tickDelta*s;
		clear();
	}

}