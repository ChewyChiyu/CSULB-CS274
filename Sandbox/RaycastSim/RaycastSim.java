import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import javax.swing.KeyStroke;
import javax.swing.AbstractAction;
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
				p.dy = p.moveSpeed;
			}

		});
		getInputMap().put(KeyStroke.getKeyStroke("S"), "S");
		getActionMap().put("S", new AbstractAction(){

			@Override
			public void actionPerformed(ActionEvent e) {
				p.dy = -p.moveSpeed;
			}

		});
		getInputMap().put(KeyStroke.getKeyStroke("A"), "A");
		getActionMap().put("A", new AbstractAction(){

			@Override
			public void actionPerformed(ActionEvent e) {
				p.dx = p.moveSpeed;
			}

		});
		getInputMap().put(KeyStroke.getKeyStroke("D"), "D");
		getActionMap().put("D", new AbstractAction(){

			@Override
			public void actionPerformed(ActionEvent e) {
				p.dx = -p.moveSpeed;
			}

		});


		getInputMap().put(KeyStroke.getKeyStroke("released W"), "releasedW");
		getActionMap().put("releasedW", new AbstractAction(){

			@Override
			public void actionPerformed(ActionEvent e) {
				p.dy = 0;
			}

		});
		getInputMap().put(KeyStroke.getKeyStroke("released S"), "releasedS");
		getActionMap().put("releasedS", new AbstractAction(){

			@Override
			public void actionPerformed(ActionEvent e) {
				p.dy = 0;
			}

		});
		getInputMap().put(KeyStroke.getKeyStroke("released A"), "releasedA");
		getActionMap().put("releasedA", new AbstractAction(){

			@Override
			public void actionPerformed(ActionEvent e) {
				p.dx = 0;
			}

		});
		getInputMap().put(KeyStroke.getKeyStroke("released D"), "releasedD");
		getActionMap().put("releasedD", new AbstractAction(){

			@Override
			public void actionPerformed(ActionEvent e) {
				p.dx = 0;
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