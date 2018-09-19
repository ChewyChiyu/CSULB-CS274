import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JSlider;
import java.awt.Polygon;
import java.awt.Point;

public class Triangle extends JPanel{

	final Dimension windowDim = new Dimension(800,800);
	final int TRIANGLE_WIDTH = 750;
	final Point center = new Point(400,750);
	int fractalStep = 0;

	public Triangle(){
		panel();
	}

	public void panel(){
		JFrame frame = new JFrame("Triangle");
		frame.add(this);
		frame.setPreferredSize(windowDim);
		final int MIN_STEP = 0;
		final int MAX_STEP = 10;

		JSlider fractalStepSlider = new JSlider(JSlider.HORIZONTAL,
				MIN_STEP, MAX_STEP, MIN_STEP);
		fractalStepSlider.setMajorTickSpacing(1);
		fractalStepSlider.setPaintTicks(true);
		fractalStepSlider.addChangeListener(e -> {
			JSlider source = (JSlider) e.getSource();
			fractalStep = source.getValue();
			repaint();
		});
		this.add(fractalStepSlider);
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		sierpinski(center.x, center.y, 0, fractalStep, TRIANGLE_WIDTH, g);
	}

	public void sierpinski(int x, int y, int stepCurrent, int stepMax, int sideLength, Graphics g){
		if(stepCurrent==stepMax){ //break
			drawTriangleAt(x,y,sideLength,g);
		}else{
			sideLength/=2;
			sierpinski(x-sideLength/2,y,stepCurrent+1,stepMax,sideLength,g);
			sierpinski(x+sideLength/2,y,stepCurrent+1,stepMax,sideLength,g);
			sierpinski(x,y-sideLength,stepCurrent+1,stepMax,sideLength,g);
		}
	} 

	public void drawTriangleAt(int x, int y,int sideLength, Graphics g){
		int[] polyX = new int[]{x-sideLength/2,x,x+sideLength/2};
		int[] polyY = new int[]{y,y-sideLength,y};
		Polygon tri = new Polygon(polyX,polyY,polyX.length);
		g.fillPolygon(tri);
	}

	public static void main(String[] args){
		new Triangle();
	}
}