import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
public class ColorGradientTester extends JPanel
{
	private Dimension windowDim = new Dimension(1000,800);
	private BufferedImage canvas;
	private Graphics g;
	private Color c;
	public ColorGradientTester()
	{
		panel();
	}

	public void panel()
	{
		JFrame frame = new JFrame("Color Gradient Tester");
		JPanel back = new JPanel();
		back.setPreferredSize(windowDim);
		frame.add(back);
		final int MIN = 0, MAX = 100;
		JSlider cX = new JSlider(JSlider.VERTICAL,MIN, MAX, 0);
		cX.addChangeListener(e -> {
			JSlider source = (JSlider)e.getSource();
			c = (ColorGradient.getGrad(new Color[]{Color.RED,Color.BLUE,Color.RED,Color.BLUE,Color.RED,Color.BLUE,Color.RED,Color.BLUE},(double)source.getValue(),MIN,MAX));
			repaint();
		});
		back.add(cX);;
		back.add(this);
		this.setPreferredSize(new Dimension((int)(windowDim.width*.8),windowDim.height));
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		newCanvas();
		repaint();
	}

	public void newCanvas()
	{
		try
		{ 
			canvas = new BufferedImage(windowDim.width, windowDim.height, BufferedImage.TYPE_INT_ARGB);
			g = canvas.getGraphics();
			g.setColor(Color.BLACK);
		}catch(Exception e){}
	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.setColor(c);
		g.fillRect(0,0,windowDim.width,windowDim.height);
	}
	public static void main(String[] args)
	{
		new ColorGradientTester();
	}
}