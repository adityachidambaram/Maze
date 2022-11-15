import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.awt.image.*; //load images
import javax.imageio.ImageIO; //load images
public class MazeProject extends JPanel implements KeyListener, ActionListener
{
	//actionlistener controls time
	//keylistener assists with processing the keys the user is clicking

	private JFrame frame;
	private int size = 35, width = 1500, height = 1000;
	private char[][] maze; //main array
	private Timer t;
	private MazeElement finish; //declared finish line
	private Key keys; //creating keys
	private Barrier block; //creating keys
	private int bR;
	private int bC;
	private Explorer explorer; //declared the starting line/moving character
	private boolean levelComplete = false;
	//private boolean continued = false;


	public MazeProject(){
		//Maze variables
		setBoard("maze0.txt");
		frame=new JFrame("A-Mazing Program");
		frame.setSize(width,height);
		frame.add(this);
		frame.addKeyListener(this);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		t = new Timer(500, this);  // will trigger actionPerformed every 500 ms
		t.start();
	}

	// All Graphics handled in this method.  Don't do calculations here
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2=(Graphics2D)g;
		g2.setColor(Color.BLACK);
		g2.fillRect(0,0,frame.getWidth(),frame.getHeight());

		for(int r=0;r<maze.length;r++){
			for(int c=0;c<maze[0].length;c++){

				g2.setColor(Color.GRAY);
				if (maze[r][c]=='#')
					g2.fillRect(c*size+size,r*size+size,size,size); //Wall

				else
					g2.drawRect(c*size+size,r*size+size,size,size);  //Open


				//Paint MazeElements
				Location here = new Location(r,c);
				if (here.equals(finish.getLoc())){
					g2.drawImage(finish.getImg(), c*size+size,r*size+size,size,size,null, this);
				}//for finish object
				if(here.equals(explorer.getLoc())){
					g2.drawImage(explorer.getImg(), c*size+size,r*size+size,size,size,null, this);
				}//for explorer object
				if(here.equals(keys.getLoc()) && keys.isActive()){
					g2.drawImage(keys.getImg(), c*size+size,r*size+size,size,size,null, this);
				}//for keys object
				if(here.equals(block.getLoc()) && block.isActive()){
					g2.drawImage(block.getImg(), c*size+size,r*size+size,size,size,null, this);
				}//for keys object



			}
		}

		// Display at bottom of page
		int hor = size;
		int vert = maze.length*size+ 2*size;
		g2.setFont(new Font("Arial",Font.BOLD,20));
		g2.setColor(Color.PINK);
		g2.drawString("Total Steps: " + explorer.getSteps() ,hor,vert);
		g2.drawString("Wall's Health: " + block.getHealth() ,hor,vert+40);
		if(levelComplete) {
			g2.setColor(Color.RED);
			g2.drawString("Level is Completed - press \"enter\" to continue" ,hor,vert+80);
		}
		else {
			g2.setColor(Color.BLACK);
			g2.drawString("Level is Completed - press \"enter\" to continue" ,hor,vert+80);
		}

		

	}


	public void keyPressed(KeyEvent e){
		System.out.println(e.getKeyCode()); //prints out key user pressed in console
		// Call explorer method
		explorer.move(e.getKeyCode(),maze); //pass the maze so you can see if there is a wall or a space
		repaint();

		if(explorer.intersects(finish)) {
			levelComplete = true;
		}
		if(levelComplete && e.getKeyCode() == 10) {
			setBoard("maze1.txt");
			explorer.setSteps(0);
			levelComplete = false;
		}


		if(explorer.intersects(keys)) {
			keys.setActive(false);
			block.setActive(false);
			maze[bR][bC] = ' ';
		}


		if(e.getKeyCode() == 38) {
			int r = explorer.getLoc().getRow();
			int c = explorer.getLoc().getCol();

			if(explorer.getDir() == 1) {
				if(c+1 < maze[r].length && maze[r][c+1] == 'W') {
					block.attacked();
				}
					
			}
		}
		if(block.getHealth() == 0) {
			block.setActive(false);
			maze[bR][bC] = ' ';
		}
		
		
	}

	/*** empty methods needed for interfaces **/
	public void keyReleased(KeyEvent e){}
	public void keyTyped(KeyEvent e){}
	public void actionPerformed(ActionEvent e) {} //where to put things for timer

	public ArrayList<String> list = new ArrayList<String>();


	public void setBoard(String fileName) {
        try {
            BufferedReader input = new BufferedReader(new FileReader(fileName));
            ArrayList<ArrayList<Character>> grid = new ArrayList<ArrayList<Character>>();
            String text;

            while((text = input.readLine()) != null) {
                char[] textArr = text.toCharArray();
                ArrayList<Character> line = new ArrayList<Character>();

                for(int i = 0; i < textArr.length; i++)
                    line.add(textArr[i]);

                grid.add(line);
            }

            char[][] temp = new char[grid.size()][grid.get(0).size()];
            for(int i = 0; i < grid.size(); i++) {
                for(int j = 0; j < grid.get(0).size(); j++) {
                    temp[i][j] =  grid.get(i).get(j);

                    if (temp[i][j] == 'F') {
                        finish = new MazeElement(new Location(i,j),size,"finish.png");
						temp[i][j] = ' ';
                    }

                    if (temp[i][j] == 'E') {
                        explorer = new Explorer(new Location(i,j), size);
                        temp[i][j] = ' ';
                    }

					if (temp[i][j] == 'K') {
						keys = new Key(new Location(i,j),size);
						temp[i][j] = ' ';
					}

					if (temp[i][j] == 'W') {
						block = new Barrier(new Location(i,j),size);
						bR = i;
						bC = j;
						temp[i][j] = 'W';
					}

                }
            }

            maze = temp;

        } catch (IOException io) {
            System.err.println("Error reading file => " + io);
        }
    }


	public static void main(String[] args){
		MazeProject app=new MazeProject();
	}
}