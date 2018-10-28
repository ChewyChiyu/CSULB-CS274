import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import javax.swing.KeyStroke;
import javax.swing.AbstractAction;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;
import java.awt.AWTException; 
import java.awt.Robot; 
import java.awt.image.BufferedImage;
public class RaycastSim extends JPanel{

	private Camera c;
	private Map m;
	private Player p;
	private Dimension dim;

	private boolean isRun;

	private final int SLEEP_TIME = 10;

	public RaycastSim(Dimension dim, Camera c, Map m, Player p){
		this.c = c;
		this.m = m;
		this.p = p;
		this.dim = dim;
		panel();
		input();
		start();
	}

	public void input(){

		getInputMap().put(KeyStroke.getKeyStroke("W"), "W");
		getActionMap().put("W", new AbstractAction(){

			@Override
			public void actionPerformed(ActionEvent e) {
				p.dy = p.moveSpeed*Math.sin(p.thetaX);
				p.dx = p.moveSpeed*Math.cos(p.thetaX);
			}

		});
		getInputMap().put(KeyStroke.getKeyStroke("S"), "S");
		getActionMap().put("S", new AbstractAction(){

			@Override
			public void actionPerformed(ActionEvent e) {
				p.dy = -p.moveSpeed*Math.sin(p.thetaX);
				p.dx = -p.moveSpeed*Math.cos(p.thetaX);
			}

		});
		getInputMap().put(KeyStroke.getKeyStroke("A"), "A");
		getActionMap().put("A", new AbstractAction(){

			@Override
			public void actionPerformed(ActionEvent e) {
				p.dy = p.moveSpeed*Math.sin(p.thetaX-Math.PI/2);
				p.dx = p.moveSpeed*Math.cos(p.thetaX-Math.PI/2);
			}

		});
		getInputMap().put(KeyStroke.getKeyStroke("D"), "D");
		getActionMap().put("D", new AbstractAction(){

			@Override
			public void actionPerformed(ActionEvent e) {
				p.dy = p.moveSpeed*Math.sin(p.thetaX+Math.PI/2);
				p.dx = p.moveSpeed*Math.cos(p.thetaX+Math.PI/2);
			}

		});


		getInputMap().put(KeyStroke.getKeyStroke("released W"), "released");
		getInputMap().put(KeyStroke.getKeyStroke("released S"), "released");
		getInputMap().put(KeyStroke.getKeyStroke("released A"), "released");
		getInputMap().put(KeyStroke.getKeyStroke("released D"), "released");
		getActionMap().put("released", new AbstractAction(){

			@Override
			public void actionPerformed(ActionEvent e) {
				p.dy = 0;
				p.dx = 0;
			}

		});
		
		getInputMap().put(KeyStroke.getKeyStroke("Q"), "quit");
		getActionMap().put("quit", new AbstractAction(){

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}

		});

		addMouseMotionListener(new MouseMotionAdapter() { 
        	public void mouseMoved(MouseEvent e) { //handling mouse movement with robot
        		try{
        			Robot mouse = new Robot();
        			if(e.getX()<dim.width/2){
        				p.turnLeftX();
        			}else if(e.getX()>dim.width/2){
        				p.turnRightX();
        			}
        			mouse.mouseMove(dim.width/2,dim.height/2);
        		}catch(Exception a){}
        	}
    	});
	}



	public void start(){
		 isRun = true;
		 run();
	}

	public void stop(){
		isRun = false;
	}

	public void run(){
		while(isRun){
			p.move(m);
			repaint();
			try{ Thread.sleep(SLEEP_TIME); }catch(Exception e) {}
		}
	}	

	public void panel(){
		JFrame frame = new JFrame("Raycast Sim");
		//turn cursor transparent
		BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
		frame.getContentPane().setCursor(blankCursor);
		frame.add(this);
		frame.setPreferredSize(dim);
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		repaint();
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		c.draw(p,m,(Graphics2D)g,dim); 
	}

}