import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class RecamanSequence extends JPanel{

	private Dimension windowDim = new Dimension(1000,800);
	private Point origin = new Point(0,windowDim.height/2);

	private BufferedImage canvas;
	private Graphics2D g2d;

	private final int SLEEP = 10;

	public RecamanSequence(){
		panel();
		sequence();
	}
	public void sequence(){
		final int MAX_STEP = 1000;
		final int SCALE = 1;
		int n = 0;
		int i = 1;
		double scale = 1;
		g2d.scale(1/scale,1/scale);
		HashSet<Integer> prev = new HashSet<Integer>();
		while(n<=windowDim.width*scale){
			// if n = n-i if n-i > 0 else n = n + i
			prev.add(n);
			if(n-i>0&&!prev.contains(n-i)){ 
				n = n-i;
				g2d.drawArc(origin.x+n*SCALE,(int)(scale*origin.y-i*SCALE/2),i*SCALE,i*SCALE,0,-180);
			}else{
				n = n+i;
				g2d.drawArc(origin.x+n*SCALE,(int)(scale*origin.y-i*SCALE/2),i*SCALE,i*SCALE,0,180);
			}
			i++;
			try{Thread.sleep(SLEEP);}catch(Exception e){}
			repaint();
		}
		System.out.println("DONE");
	}
	public void panel(){
		JFrame frame = new JFrame("Recaman Sequence");
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
			g2d = (Graphics2D) canvas.getGraphics();
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                          RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.setColor(Color.BLACK);
		}catch(Exception e){}
	}
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(canvas,0,0,null);
	}
	public static void main(String[] args){
		new RecamanSequence();
	}
}	