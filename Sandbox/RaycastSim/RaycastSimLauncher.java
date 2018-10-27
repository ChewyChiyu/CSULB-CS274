import java.util.*;
import java.awt.*;
public class RaycastSimLauncher{

	private Dimension windowDim = new Dimension(1000,800);

	private Map m = new Map(new int[][]{ {0,0,0,0,0,0,0},
										 {0,0,0,0,0,0,0},
										 {0,0,0,0,0,0,0},
										 {0,0,0,3,0,0,0},
										 {0,0,3,0,3,0,0},
										 {0,0,0,0,0,0,0},
										 {0,0,0,0,0,0,0} });

	private int pX = 3, pY = 1;
	private double pTheta = Math.PI/2, pDTheta = Math.PI/60;
	private Player p = new Player(pX,pY,pTheta,pDTheta);


	private double cameraSpan = Math.PI;
	private double sweepAngle = Math.PI/360;
	private double maxRange = Math.max(m.getM().length,m.getM()[0].length);
	private double cStretch = 35;
	private Camera c = new Camera(cameraSpan,sweepAngle,maxRange,cStretch);


	public RaycastSimLauncher(){
		new RaycastSim(windowDim,c,m,p);
	}

	public static void main(String[] args){
		new RaycastSimLauncher();
	}
}