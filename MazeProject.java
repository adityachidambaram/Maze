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
	private int size = 60, width = 1500, height = 1000;
	private char[][] maze; //main array
	private Timer t;
	private MazeElement finish; //declared finish line
	private Explorer explorer; //declared the starting line/moving character

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

			}
		}

		// Display at bottom of page
		int hor = size;
		int vert = maze.length*size+ 2*size;
		g2.setFont(new Font("Arial",Font.BOLD,20));
		g2.setColor(Color.PINK);
		g2.drawString("PRINT STUFF HERE  ",hor,vert);
	}


	public void keyPressed(KeyEvent e){
		System.out.println(e.getKeyCode()); //prints out key user pressed in console
		// Call explorer method
		explorer.move(e.getKeyCode(),maze); //pass the maze so you can see if there is a wall or a space
		repaint();
	}

	/*** empty methods needed for interfaces **/
	public void keyReleased(KeyEvent e){}
	public void keyTyped(KeyEvent e){}
	public void actionPerformed(ActionEvent e) {} //where to put things for timer

	public ArrayList<String> list = new ArrayList<String>();


	public void setBoard(String fileName){

		File name = new File("maze0.txt");

		try{

			BufferedReader input = new BufferedReader(new FileReader(name));
			String text,output="";

			while((text=input.readLine())!= null){

				String[] temp = text.split("\n");
				for(int i=0; i<temp.length; i++){
					list.add(temp[i]);
				}

				char[][] temp2D = new char[list.size()][list.get(0).length()];
				for(int r=0; r<list.size(); r++){

					for(int c=0; c<list.get(0).length(); c++){

						temp2D[r][c] = list.get(r).charAt(c);
					}
					System.out.println();
				}

				maze = temp2D;

				for(int r=0; r<maze.length; r++){
					for(int c=0; c<maze[0].length; c++){

						char symbol = maze[r][c];
						if(symbol != ' ' && symbol != '#'){

							if(symbol == 'E')
								explorer = new Explorer(new Location(r, c), size); //explorer

							if(symbol == 'F')
								finish = new MazeElement(new Location(r,c),size,"finish.png"); //finish line

							maze[r][c] = ' ';
						}
						else{

							maze[r][c] = symbol;
						}
					}
				}
			}

		}
		catch (IOException io){

			System.err.println("Error reading file => "+io);
			io.printStackTrace(System.out);
		}
	}

	public static void main(String[] args){
		MazeProject app=new MazeProject();
	}
}