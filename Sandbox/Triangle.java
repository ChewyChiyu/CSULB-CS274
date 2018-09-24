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
	final int SQUARE_WIDTH = 350;
	final Point center = new Point(400,400);
	final Point floor = new Point(400,780);

	int fractalStep = 0;

	public Triangle(){
		panel();
	}

	public void panel(){
		JFrame frame = new JFrame("Fractals");
		frame.add(this);
		frame.setPreferredSize(windowDim);
		final int MIN_STEP = 0;
		final int MAX_STEP = 8;

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
		sierpinskiCarpet(center.x, center.y, 0, fractalStep, SQUARE_WIDTH*4, g);
		//sierpinski(floor.x, floor.y, 0, fractalStep, TRIANGLE_WIDTH, g);
		//tSquare(center.x, center.y, 0, fractalStep, SQUARE_WIDTH, g);
	}



	public void tSquare(int x, int y, int stepCurrent, int stepMax, int sideLength, Graphics g){
		drawCircleAt(x,y,sideLength,g);
		sideLength/=2;
		if(stepCurrent!=stepMax){ 
			tSquare(x-sideLength,y-sideLength,stepCurrent+1,stepMax,sideLength,g);
			tSquare(x+sideLength,y-sideLength,stepCurrent+1,stepMax,sideLength,g);
			tSquare(x-sideLength,y+sideLength,stepCurrent+1,stepMax,sideLength,g);
			tSquare(x+sideLength,y+sideLength,stepCurrent+1,stepMax,sideLength,g);
		}
	} 

	public void sierpinskiCarpet(int x, int y, int stepCurrent, int stepMax, int sideLength, Graphics g){
		if(stepCurrent==stepMax){ //break
			drawSquareAt(x,y,sideLength,g);
		}else{
			sideLength/=3;
			sierpinskiCarpet(x-sideLength/2,y-sideLength/2,stepCurrent+1,stepMax,sideLength,g);
			sierpinskiCarpet(x-sideLength/2,y+sideLength/2,stepCurrent+1,stepMax,sideLength,g);
			sierpinskiCarpet(x-sideLength/2,y,stepCurrent+1,stepMax,sideLength,g);
			sierpinskiCarpet(x+sideLength/2,y-sideLength/2,stepCurrent+1,stepMax,sideLength,g);
			sierpinskiCarpet(x+sideLength/2,y+sideLength/2,stepCurrent+1,stepMax,sideLength,g);
			sierpinskiCarpet(x+sideLength/2,y,stepCurrent+1,stepMax,sideLength,g);
			sierpinskiCarpet(x,y-sideLength/2,stepCurrent+1,stepMax,sideLength,g);
			sierpinskiCarpet(x,y+sideLength/2,stepCurrent+1,stepMax,sideLength,g);
		}
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

	public void drawSquareAt(int x, int y, int sideLength, Graphics g){
		int[] polyX = new int[]{x-sideLength/2,x-sideLength/2,x+sideLength/2,x+sideLength/2};
		int[] polyY = new int[]{y+sideLength/2,y-sideLength/2,y-sideLength/2,y+sideLength/2};
		Polygon squ = new Polygon(polyX,polyY,polyX.length);
		g.fillPolygon(squ);
	}

	public void drawCircleAt(int x, int y, int raidus, Graphics g){
		g.fillOval(x-raidus/2,y-raidus/2,raidus,raidus);
	}
	public static void main(String[] args){
		new Triangle();
	}
}