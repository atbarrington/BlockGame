/**
 * Block GUI
 */

/**
 * @author Andrew Tyler Barrington
 *
 */

import java.awt.*;
import java.awt.event.*;

import javax.sound.sampled.*;
import javax.sound.*;
import javax.swing.*;
import javax.swing.border.*;

import sun.audio.*;

import java.io.*;
import java.util.Scanner;

//import Screen.Block;

public class BlockGUI implements KeyListener, Runnable {
	
	public static final int NUM_ROWS = 23;
	public static final int NUM_COLS = 10;
	private static int timer = 1000;
	private static int speed;
	private static boolean rush;
	private static int minutes;
	private static int seconds;
	private static long score;
	private static long lowestScore;
	private static String textScore;
	private static String clockDisplay;
	private static boolean pause;
	private static boolean pauseSwitch;
	//private static boolean music;
	private static int thread;
	
	Block game;
	private static highscore gameHighscore;
	Thread fallTimer;
	Thread clockTimer;
	
	JFrame window;
	JPanel screen;
	JPanel miniScreen;
	JPanel[][] grid;
	JPanel[][] nextPiece;
	JLabel scoreBoard;
	JLabel clock;
	JLabel pauseLight;
	
	
	public BlockGUI( ) 
	{	
		game = new Block();
		window = new JFrame("");
		window.setLayout(null);
		window.setSize(700, 775);
		window.setBackground(Color.BLACK);
		window.setResizable(false);
		buildMenu();
		pause = false;
		pauseSwitch = true;
		gameHighscore = new highscore();
		thread = 1;
		//music = true;
		
		screen = new JPanel(new GridLayout(NUM_ROWS-3, NUM_COLS));
		screen.setBounds(10, 20, 350, 700);
		screen.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		
		grid = new JPanel[NUM_ROWS][NUM_COLS];
		for (int i=0; i<NUM_ROWS; i++)
			for (int j=0; j<NUM_COLS; j++) {
				grid[i][j] = new JPanel();
				grid[i][j].setBackground(Color.LIGHT_GRAY);
				if (i > 2)
					screen.add(grid[i][j]);
			}
		
		Font font = new Font("Times", Font.BOLD, 20);
		JLabel miniScreenDisplay = new JLabel("Next Piece");
		miniScreenDisplay.setBounds(425, 40, 150, 40);
		miniScreenDisplay.setFont(font);
		miniScreenDisplay.setForeground(Color.WHITE);
		miniScreen = new JPanel(new GridLayout(6, 6));
		miniScreen.setBounds(405, 75, 150, 150);
		miniScreen.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		
		nextPiece = new JPanel[6][6];
		for (int i=0; i<6; i++) 
			for (int j=0; j<6; j++) {
				nextPiece[i][j] = new JPanel();
				nextPiece[i][j].setBackground(Color.LIGHT_GRAY);
				miniScreen.add(nextPiece[i][j]);
			}
			
		score = 0;
		textScore = Long.toString(score);
		Font scoreFont = new Font("Verdana", Font.BOLD, 40);
		scoreBoard = new JLabel(textScore);	
		scoreBoard.setBounds(405, 300, 250, 100);
		Border spacing = new EmptyBorder(0, 0, 0, 10);
		Border frame = new BevelBorder(BevelBorder.RAISED);
		scoreBoard.setBorder(BorderFactory.createCompoundBorder(frame, spacing));
		scoreBoard.setHorizontalAlignment(SwingConstants.RIGHT);
		scoreBoard.setFont(scoreFont);
		scoreBoard.setForeground(Color.GREEN);
		scoreBoard.setBackground(Color.LIGHT_GRAY);
		
		clock = new JLabel("00:00");
		clock.setFont(scoreFont);
		clock.setBounds(460, 385, 250, 100);
		clock.setForeground(Color.WHITE);
		
		pauseLight = new JLabel("GAME PAUSED");
		pauseLight.setBounds(50, 170, 1000, 200);
		pauseLight.setVisible(false);
		Font pauseFont = new Font("Braggadocio", Font.BOLD, 60);
		pauseLight.setFont(pauseFont);
		pauseLight.setForeground(Color.RED);
		pauseLight.setBackground(Color.GREEN);

		JLayeredPane layer = new JLayeredPane();
		layer.setBounds(0, 0, 700, 775);
		layer.add(pauseLight, 0);
		layer.add(screen, 1);
		window.add(clock);
		window.add(layer);
		window.add(miniScreenDisplay);
		window.add(miniScreen);
		window.add(scoreBoard);
		window.addKeyListener(this);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
		
		fallTimer = new Thread(this);
		fallTimer.start();
		long pause = System.nanoTime();
		while (System.nanoTime() - pause < 1000000) {}
		clockTimer = new Thread(this);
		clockTimer.start();
		pause = System.nanoTime();
	}
	
	public void buildMenu() {
		// File bar
		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		
		// New game option
		JMenuItem newGame = new JMenuItem("New Game");
		newGame.setMnemonic(KeyEvent.VK_N);
		
		newGame.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		
		// View Highscore option
		JMenuItem highscore = new JMenuItem("High Scores");
		highscore.setMnemonic(KeyEvent.VK_H);
		
		// Exit game option
		JMenuItem exit = new JMenuItem("Exit");
		exit.setMnemonic(KeyEvent.VK_X);
		exit.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_W, ActionEvent.CTRL_MASK));
		
		newGame.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						newGame();
						repaintScreen();
					}
				}
		);
		
		
		highscore.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						pause = true;
						
						//gameHighscore = new highscore();
						
						lowestScore = gameHighscore.getScore(4);
						
						JFrame highscore = new JFrame("High Scores");
						highscore.setLayout(null);
						highscore.setSize(400, 300);
						highscore.setLocationRelativeTo(null);
						highscore.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
						
						JLabel name1 = new JLabel(gameHighscore.getName(0));
						JLabel score1 = new JLabel(Long.toString(gameHighscore.getScore(0)));
						name1.setBounds(50, 30, 200, 50);
						score1.setBounds(300, 30, 100, 50);
						JLabel name2 = new JLabel(gameHighscore.getName(1));
						JLabel score2 = new JLabel(Long.toString(gameHighscore.getScore(1)));
						name2.setBounds(50, 60, 200, 50);
						score2.setBounds(300, 60, 100, 50);
						JLabel name3 = new JLabel(gameHighscore.getName(2));
						JLabel score3 = new JLabel(Long.toString(gameHighscore.getScore(2)));
						name3.setBounds(50, 90, 200, 50);
						score3.setBounds(300, 90, 100, 50);
						JLabel name4 = new JLabel(gameHighscore.getName(3));
						JLabel score4 = new JLabel(Long.toString(gameHighscore.getScore(3)));
						name4.setBounds(50, 120, 200, 50);
						score4.setBounds(300, 120, 100, 50);
						JLabel name5 = new JLabel(gameHighscore.getName(4));
						JLabel score5 = new JLabel(Long.toString(gameHighscore.getScore(4)));
						name5.setBounds(50, 150, 200, 50);
						score5.setBounds(300, 150, 100, 50);
						
						highscore.add(name1);
						highscore.add(score1);
						highscore.add(name2);
						highscore.add(score2);
						highscore.add(name3);
						highscore.add(score3);
						highscore.add(name4);
						highscore.add(score4);
						highscore.add(name5);
						highscore.add(score5);
						highscore.setVisible(true);
					    
						repaintScreen();
					}
				}
				
		);
		
		
		exit.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(window, 
						"Thanks for playing!");
				System.exit(0);
			}
		});
		
		JMenu helpMenu = new JMenu("Help");
		helpMenu.setMnemonic(KeyEvent.VK_H);
		
		JMenuItem help = new JMenuItem("How to Play");
		help.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						pause = true;
				
						JLabel helpScreen = new JLabel("<html>Arrow Keys:<br><pre>   Left   ---    Move Left<br>   Right  ---    Move Right<br>   Down   ---    Send Piece to the Bottom<br>Space     ---     Rotate Piece<br>p         ---    Pause Game<br></pre>");
						helpScreen.setPreferredSize(new Dimension(100, 200));
						helpScreen.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 0));
						
					    JDialog myDialog = new JDialog();
					    myDialog.setContentPane(helpScreen);
					    myDialog.setIconImage(null);
					    myDialog.setFocusableWindowState(false);
					    myDialog.setBounds(100, 300, 500, 200);
					    myDialog.setVisible(true);
					    
						repaintScreen();
					}
				}
		);
		
		fileMenu.add(newGame);
		fileMenu.add(highscore);
		fileMenu.addSeparator();
		fileMenu.add(exit);
				
		helpMenu.add(help);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(fileMenu);
		menuBar.add(helpMenu);
		window.setJMenuBar(menuBar);
	}
	
	
	public void keyPressed(KeyEvent e) {
		
		int dr=0;
		
		if (e.getKeyCode() == KeyEvent.VK_P) {
			if (pause)
				pause = false;
			else
				pause = true;
		}
		
		/**
		if (e.getKeyCode() == KeyEvent.VK_M) {
			if (music)
				music = false;
			else
				music = true;
		}
		*/
		
		if (!pause) {
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				dr = -1;
			}
			else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				dr = 1;
			}
			else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				
				if (!pause)
					rush = true;
			}
			else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				game.rotatePiece();
			}
			
			if (dr != 0) {
				game.movePiece(dr);
			}
		}
		
		repaintScreen();
	}
	
	public void keyReleased(KeyEvent e){}
	public void keyTyped(KeyEvent e){}
	
	// ~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~  MAIN  *~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~ \\
	public static void main(String[] args) 
	{
		new BlockGUI();
	}
	
	public void newGame() {
		pause = false;
		game = new Block();
		minutes = 0;
		seconds = 0;
		score = 0;
		speed = 1;
		pauseSwitch = true;
	}
	
	public void repaintScreen() {
		
		scoreBoard.setText(textScore);
		
		if (pause) {
			if (pauseSwitch) {
				pauseLight.setVisible(true);
				pauseSwitch = false;
			}
			else {
				pauseLight.setVisible(false);
				pauseSwitch = true;
			}
			
			pauseLight.repaint();
		}	
		else
			pauseLight.setVisible(false);
		
		for (int i=0; i<NUM_ROWS; i++)
			for (int j=0; j<NUM_COLS; j++) {

				if (game.isEmpty(i, j)) {
					grid[i][j].setBackground(Color.LIGHT_GRAY);
					grid[i][j].setBorder(BorderFactory.createEtchedBorder(Color.GRAY, Color.LIGHT_GRAY));		// cool optional border
					grid[i][j].setBorder(BorderFactory.createEmptyBorder());
				}
				else if (game.isTall(i, j)) {
					grid[i][j].setBackground(Color.CYAN);
					grid[i][j].setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
				}
				else if (game.isEll(i, j)) {
					grid[i][j].setBackground(Color.RED);
					grid[i][j].setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
				}
				else if (game.isBackEll(i, j)) {
					grid[i][j].setBackground(Color.GREEN);
					grid[i][j].setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
				}
				else if (game.isHorn(i, j)) {
					grid[i][j].setBackground(Color.MAGENTA);
					grid[i][j].setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
				}
				else if (game.isEgypt(i, j)) {
					grid[i][j].setBackground(Color.BLUE);
					grid[i][j].setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
				}
				else if (game.isSnake(i, j)) {
					grid[i][j].setBackground(Color.ORANGE);
					grid[i][j].setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
				}
				else if (game.isSquare(i, j)) {
					grid[i][j].setBackground(Color.YELLOW);
					grid[i][j].setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
				}
				
				if (game.isDead(i, j))
					grid[i][j].setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
			
				grid[i][j].repaint();
			}
		
		if (pause) {
			if (pauseSwitch)
				pauseLight.setVisible(true);
			else
				pauseLight.setVisible(false);
			
			pauseLight.repaint();
		}	
		else
			pauseLight.setVisible(false);
		
		for (int i=0; i<6; i++)
			for (int j=0; j<6; j++) {
				nextPiece[i][j].setBackground(Color.LIGHT_GRAY);
				nextPiece[i][j].setBorder(BorderFactory.createEmptyBorder());
			}
		double next = game.next();
		if (next < .14) {
			nextPiece[1][3].setBackground(Color.CYAN);
			nextPiece[1][3].setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			nextPiece[2][3].setBackground(Color.CYAN);
			nextPiece[2][3].setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			nextPiece[3][3].setBackground(Color.CYAN);
			nextPiece[3][3].setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			nextPiece[4][3].setBackground(Color.CYAN);
			nextPiece[4][3].setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		}
		else if (next < .28) {
			nextPiece[1][2].setBackground(Color.RED);
			nextPiece[1][2].setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			nextPiece[2][2].setBackground(Color.RED);
			nextPiece[2][2].setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			nextPiece[3][2].setBackground(Color.RED);
			nextPiece[3][2].setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			nextPiece[3][3].setBackground(Color.RED);
			nextPiece[3][3].setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		}
		else if (next < .42) {
			nextPiece[1][3].setBackground(Color.GREEN);
			nextPiece[1][3].setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			nextPiece[2][3].setBackground(Color.GREEN);
			nextPiece[2][3].setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			nextPiece[3][3].setBackground(Color.GREEN);
			nextPiece[3][3].setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			nextPiece[3][2].setBackground(Color.GREEN);
			nextPiece[3][2].setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		}
		else if (next < .56) {
			nextPiece[3][3].setBackground(Color.MAGENTA);
			nextPiece[3][3].setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			nextPiece[3][2].setBackground(Color.MAGENTA);
			nextPiece[3][2].setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			nextPiece[3][4].setBackground(Color.MAGENTA);
			nextPiece[3][4].setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			nextPiece[2][3].setBackground(Color.MAGENTA);
			nextPiece[2][3].setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		}
		else if (next < .70) {
			nextPiece[2][1].setBackground(Color.BLUE);
			nextPiece[2][1].setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			nextPiece[2][2].setBackground(Color.BLUE);
			nextPiece[2][2].setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			nextPiece[3][2].setBackground(Color.BLUE);
			nextPiece[3][2].setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			nextPiece[3][3].setBackground(Color.BLUE);
			nextPiece[3][3].setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		}
		else if (next < .85) {
			nextPiece[2][4].setBackground(Color.ORANGE);
			nextPiece[2][4].setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			nextPiece[2][3].setBackground(Color.ORANGE);
			nextPiece[2][3].setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			nextPiece[3][3].setBackground(Color.ORANGE);
			nextPiece[3][3].setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			nextPiece[3][2].setBackground(Color.ORANGE);
			nextPiece[3][2].setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		}
		else if (next >= .85) {
			nextPiece[2][2].setBackground(Color.YELLOW);
			nextPiece[2][2].setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			nextPiece[2][3].setBackground(Color.YELLOW);
			nextPiece[2][3].setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			nextPiece[3][2].setBackground(Color.YELLOW);
			nextPiece[3][2].setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			nextPiece[3][3].setBackground(Color.YELLOW);
			nextPiece[3][3].setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		}
				
	}
	
	/** UnImplemented Sound Clip
	private void playSound() {
			
		try {
		    InputStream inputStream = getClass().getResourceAsStream("Cakestep.wav");
		    AudioStream audioStream = new AudioStream(inputStream);
		    
		    	if (music)
		    		AudioPlayer.player.start(audioStream);
		    	else
		    		AudioPlayer.player.stop();
		    
		  } catch (Exception e) {
			  System.out.println(":(");
		  }
		}
	}
	*/
	
	public void run() {
		
		if (thread == 1) {
			thread = 2;
			speed = 1;
	
			while (true) {
				
				if (rush) {
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {}
					
					if (!game.fallPiece())
						rush = false;
					repaintScreen();
				}
				else {
					
					try {
						Thread.sleep(timer/(1+(speed/5)));
					} catch (InterruptedException e) {}
					
					if (!rush) {	
						if (game.sendNewPiece()) {
							speed = speed%20 + 1;
							if (game.checkGameOver()) {
								pause = true;
								if (score > lowestScore) {
									String name = JOptionPane.showInputDialog("You set a new highscore!");
									gameHighscore.setHighscore(name, score);
								}
								int answer = JOptionPane.showConfirmDialog(window,
													"Game Over, Play Again?",
													"",
													JOptionPane.YES_NO_OPTION);
								if (answer == JOptionPane.YES_OPTION)
									newGame();
								else
									System.exit(0);
									
							}
						}
						if (!pause)
							game.fallPiece();
						score += game.getScore();
						textScore = Long.toString(score);
						repaintScreen();
					}
				}
			}
		}
		
		else if (thread == 2) {
			minutes = 0;
			seconds = 0;
			
			while (true) {
				
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {}
				
				if (!pause) {
					seconds++;
					if (seconds > 59) {
						seconds = 0;
						minutes++;
					}
					
					if (minutes > 9)
						clockDisplay = ("" + minutes);
					else
						clockDisplay = ("0" + minutes);
					if (seconds > 9)
						clockDisplay += (":" + seconds);
					else
						clockDisplay += (":0" + seconds);
					
					clock.setText(clockDisplay);
				}
			}
		}
		
	}
	
	/**
	public static synchronized void playSound(final String url) {
	    new Thread(new Runnable() { // the wrapper thread is unnecessary, unless it blocks on the Clip finishing, see comments
	      public void run() {
	        try {
	          Clip clip = AudioSystem.getClip();
	          AudioInputStream inputStream = AudioSystem.getAudioInputStream(Main.class.getResourceAsStream("/Block/Cakestep.wav"));
	          System.out.println(":(");
	          clip.open(inputStream);
	          clip.start(); 
	        } catch (Exception e) {
	          System.err.println(e.getMessage());
	        }
	      }
	    }).start();
	  }
	  */

}
