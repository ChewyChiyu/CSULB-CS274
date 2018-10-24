import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;

public class PointGradient extends JPanel{

	private final int SCALE = 4;


	private Dimension windowDim = new Dimension(255*SCALE,255*SCALE);
	private BufferedImage canvas;



	public PointGradient(){
		panel();
		input();
	}

	public void input(){
		 	
		addMouseMotionListener(new MouseMotionAdapter() { 
        	public void mouseMoved(MouseEvent e) {
            	alterCanvas(e.getPoint(),canvas);
       	 	}
    	});

	}


	public void panel(){
		JFrame frame = new JFrame("Point Gradient");
		frame.add(this);
		frame.setPreferredSize(windowDim);
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try{ canvas = new BufferedImage(windowDim.width, windowDim.height, BufferedImage.TYPE_INT_ARGB); }catch(Exception e){}
	}

	public void alterCanvas(Point origin, BufferedImage canvas){
		final Dimension pixel = new Dimension(5,5);
		Graphics g = canvas.getGraphics();
		for(int row = 1; row < canvas.getHeight(); row+=pixel.width){
			for(int col = 1; col < canvas.getWidth(); col+=pixel.height){
				
				//color
				double dist = (Math.sqrt(distanceSquare(row,col,origin.x,origin.y))/(windowDim.width/2))/SCALE;
					
				double red = 0, blue = 0, green = 0;
				
				red = row/4;
				blue = (col)/4;
				green = dist*255;
				Color gradient = new Color((int)red,(int)green,(int)blue);
				g.setColor(gradient);
				g.fillRect(row,col,pixel.width,pixel.height);
			}
		}
		repaint();
	}

	

	public double distanceSquare(double x1, double y1, double x2, double y2){
		return (x2-x1)*(x2-x1)+(y2-y1)*(y2-y1);
	}	

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(canvas,0,0,null);
	}

	public static void main(String[] args){
		new PointGradient();
	}
}