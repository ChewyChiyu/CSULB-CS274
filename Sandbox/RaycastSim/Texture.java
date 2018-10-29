import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
public class Texture{

	public static BufferedImage gabenTexture;

	public Texture(){
		//load
		load();
	}

	public void load(){
		try{
			gabenTexture =  ImageIO.read(getClass().getResource("gaben.png"));
		}catch(Exception e){e.printStackTrace();}
	}

	public static BufferedImage getSlice(BufferedImage img, int xPos){
		try{
			return img.getSubimage(xPos,0,xPos,img.getHeight());
		}catch(Exception e){e.printStackTrace();}
		return null;
	}
}