import java.awt.Color;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.awt.image.*;
import javax.imageio.ImageIO;

public class Barrier extends MazeElement{
	private int a = 0;
    private boolean active = true;
    private int health = 5;
	public static final String[] IMG_STRS = {"barrier.png"};
	private BufferedImage[] images;

	public Barrier(Location loc, int size){
		super(loc, size);

		images = new BufferedImage[IMG_STRS.length];
		for(int x = 0; x < IMG_STRS.length; x++)//incomplete but it scans through then ill do move and
			try {  // Loads a placeholder image
				images[x] = ImageIO.read(new File(IMG_STRS[x]));
			} catch (IOException e) {
				System.out.println("Image: " + images[x] + " not loaded");
			}
	}
	@Override
	public BufferedImage getImg(){
		return images[a];
	}
    public void setActive(boolean t) {
        active = t;
    }
    public boolean isActive() {
        return active;
    }
    public void attacked() {
        health--;
    }
    public int getHealth() {
        return health;
    }

	
	

}