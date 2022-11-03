import java.awt.Color;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.awt.image.*;
import javax.imageio.ImageIO;

public class Explorer extends MazeElement{
	private int dir;
	private int steps;
	public static final String[] IMG_STRS = {"expUp.png", "expRight.png", "expDown.png", "expLeft.png"};
	private BufferedImage[] images;

	public Explorer(Location loc, int size){
		super(loc, size);
		steps = 0;
		dir = 1; //east starting

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
		return images[dir];
	}
	public String getSteps(){
		return(""+steps);
	}
	@Override
	public void move(int key, char[][] maze){
		if(key == 39){//rightarrow
			steps++;
			dir++;
			if(dir == 4)
				dir = 0;

		}if(key == 37){//leftarrow
			steps++;
			dir--;
			if(dir == -1)
				dir = 3;

		}if(key == 38) {
			int r = getLoc().getRow();
			int c = getLoc().getCol();
			if(dir == 1) {
				if(c+1 < maze[r].length && maze[r][c+1] == ' ')
					getLoc().set(r, c+1);
			}
			if(dir == 2) {
				if(r+1 < maze.length && maze[r+1][c] == ' ')
					getLoc().set(r+1, c);
			}
			if(dir == 3) {
				if(c-1 > 0 && maze[r][c-1] == ' ')
					getLoc().set(r, c-1);
			}
			if(dir == 0) {
				if(r-1 > 0 && maze[r-1][c] == ' ')
					getLoc().set(r-1, c);
			}
		}

	}
}