import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
public class SortVisual extends JPanel{
	private Dimension windowDim = new Dimension(1000,800);
	private BufferedImage canvas;
	private Graphics g;
	private final int STICK_WIDTH = 5;
	private Stick[] sticks = new Stick[windowDim.width/STICK_WIDTH];
	private final int SLEEP_TIME = 1;
	public SortVisual(){
		panel();
		run();
	}

	public void run(){
		loadSticks();
		bubbleSim();
		loadSticks();
		insertionSim();
		loadSticks();
		mergeSim();
		loadSticks();
		shellSim();
		drawSticks();
	}


	public void shellSim(){
		int len = sticks.length;
		for(int gap = len/2; gap >0; gap /=2){
			for(int i = gap; i < len; i++){
				Stick t = sticks[i];
				int j;
				for(j = i; j >= gap && sticks[j-gap].h<t.h;j-=gap){
					sticks[j] = sticks[j-gap];
					try{Thread.sleep(SLEEP_TIME);}catch(Exception e) {}
					drawSticks();
				}
				sticks[j] = t;
			}
		}
	}

	public void mergeSim(){
		//clone arr
		Stick[] clone = new Stick[sticks.length];
		for(int index = 0; index < sticks.length; index++){
			clone[index] = new Stick(sticks[index].h,sticks[index].c);
		}
		int len = sticks.length-1;
		mergeSort(0,len,clone);
	}


	public void mergeSort(int low, int up, Stick[] clone){
		if(low < up){
			int mid = low + (up - low)/2;
			mergeSort(low,mid,clone);
			mergeSort(mid+1,up,clone);
			mergeParts(low,mid,up,clone);
		}
	}

	public void mergeParts(int low, int mid, int up, Stick[] clone){
		for(int index = low; index <= up; index++){
			clone[index] = sticks[index];
		}
		int i = low;
		int j = mid+1;
		int k = low;
		while(i<=mid&&j<=up){
			try{Thread.sleep(SLEEP_TIME);}catch(Exception e) {}
			drawSticks();
			if(clone[i].h>=clone[j].h){
				sticks[k] = clone[i];
				i++;
			}else{
				sticks[k] = clone[j];
				j++;
			}
			k++;
		}
		while(i<=mid){
			sticks[k] = clone[i];
			k++;
			i++;
		}
	}

	public void bubbleSim(){
		boolean keepSort = true;
		int len = sticks.length;
		while(keepSort){
			keepSort = false;
			for(int index = 0; index < len-1; index++){
				if(sticks[index].h<sticks[index+1].h){
					try{Thread.sleep(SLEEP_TIME);}catch(Exception e) {}
					swap(index,index+1);
					keepSort = true;
					drawSticks();
				}
			}
		}

	}

	public void insertionSim(){
		int len = sticks.length;
		for(int index = 1; index < len; index++){
			int j = index;
			while(j>0&&sticks[j].h>sticks[j-1].h){
				try{Thread.sleep(SLEEP_TIME);}catch(Exception e) {}
				swap(j,j-1);
				j--;
				drawSticks();
			}
		}
	}

	public void loadSticks(){
		for(int index = 0; index < sticks.length; index++){
			int stickHeight = windowDim.height-(int)(index*STICK_WIDTH/1.3);
			sticks[index] = new Stick(stickHeight,gradientRed(index));
		}
		scramble();
	}
	public void drawSticks(){
		newCanvas();
		int x_buffer = windowDim.width;
		int y_buffer = windowDim.height;
		for(int index = 0; index < sticks.length; index++){
			g.setColor(sticks[index].c);
			g.fillRect(x_buffer,y_buffer-sticks[index].h,STICK_WIDTH,sticks[index].h);
			x_buffer-=STICK_WIDTH;
		}
		repaint();
	}	

	public Color randColor(){
		return new Color((int)(Math.random()*256),(int)(Math.random()*256),(int)(Math.random()*256));
	}

	public Color gradientRed(int n){
		final int MAX_RED = 255;
		double grad = (double)n/(double)sticks.length;
		return new Color((int)(grad*MAX_RED),0,0);
	}

	public void swap(int a, int b){
		Stick n = sticks[a];
		sticks[a] = sticks[b];
		sticks[b] = n;
	}

	public void scramble(){
		final int UPPER_BOUND = 1000;
		for(int index = 0; index < UPPER_BOUND; index++){
			int rand1 = (int)(Math.random()*sticks.length);
			int rand2 = (int)(Math.random()*sticks.length);
			swap(rand1,rand2);
		}
	}
	public void panel(){
		JFrame frame = new JFrame("Sort Visual");
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
		new SortVisual();
	}
}
class Stick{
	public int h;
	public Color c;
	public Stick(int h, Color c){
		this.h = h;
		this.c = c;
	}
}