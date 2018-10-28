import java.util.*;
import java.awt.*;
public class RaycastSimLauncher{

	private Dimension windowDim = new Dimension(1000,800);

	private Map m = new Map(new int[][]{ {1,1,1,1,1,1,1,1,1,1},
										 {1,0,0,0,0,0,0,0,0,1},
										 {1,0,0,0,0,0,0,0,0,1},
										 {1,0,0,0,0,0,0,0,0,1},
										 {1,0,0,3,0,3,0,0,0,1},
										 {1,0,0,0,0,0,0,0,0,1},
										 {1,0,0,3,0,3,0,0,0,1},
										 {1,0,0,0,0,0,0,0,0,1},
										 {1,0,0,0,0,0,0,0,0,1},
										 {1,1,1,1,1,1,1,1,1,1} });

	private int pX = 3, pY = 1;
	private double pTheta = Math.PI/2, pDTheta = Math.PI/40;
	private Player p = new Player(pX,pY,pTheta,pDTheta);


	private double cameraSpan = Math.PI/2;
	private double sweepAngle = Math.PI/300;
	private double maxRange = 10;
	private Camera c = new Camera(cameraSpan,sweepAngle,maxRange);


	public RaycastSimLauncher(){
		new RaycastSim(windowDim,c,m,p);
	}

	public static void main(String[] args){
		new RaycastSimLauncher();
	}
}