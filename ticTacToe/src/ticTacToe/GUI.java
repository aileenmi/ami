package ticTacToe;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * The GUI extends JPanel and shows the TicTacToe game on the screen for the 
 * user to play. The GUI handles the turn-based feature of a Player vs. Computer
 * game, as well as processes MouseEvents from the ManualPlayer.
 * 
 * @author Aileen Mi
 * @author Kelly Chen
 */
public class GUI extends JPanel implements Runnable {
	
	final static int WIDTH = 800;
	final static int HEIGHT = 800;

	private Image bgImage;
	private JButton[][] buttons = new JButton[3][3];
	private int numOfPlayers;
	private String[][] board;
	private Graphics2D g;
	private ManualPlayer player;
	private AIPlayer opp;
	private int state;
	private JFrame frame;
	private JButton b1;
	private JButton b2;
	private boolean gameOver = false;

	/**
	 * Constructs a GUI
	 */
	public GUI() {
	}

	/**
	 * Returns the number of players
	 * 
	 * @return numOfPlayers
	 */
	public int getNumPlayers() {
		return numOfPlayers;
	}

	/**
	 * Sets the current player
	 * 
	 * @param p - the player
	 */
	public void setPlayer(ManualPlayer p) {
		player = p;
	}

	/**
	 * Sets the board to be repainted during the game.
	 * 
	 * @param board - the board
	 */
	public void setBoard(String[][] board) {
		this.board = board;
	}

	/**
	 * Processes the MouseEvent from ManualPlayer based on the state of the game. -
	 * state 1: chooses Player vs Player or Player vs Computer mode - state 2:
	 * chooses the mode of the AI - state 3: changes the state of the 3x3 board
	 * 
	 * @param e - MouseEvent
	 */
	public void processEvent(MouseEvent e) {
		if (state == 1) { // choose game mode
			JButton b = (JButton) e.getSource();
			if (b.equals(b1)) { // pvp
				numOfPlayers = 2;

				opp = null;
				
				showWait();
			} else if (b.equals(b2)) { // pvc
				numOfPlayers = 1;

				opp = new AIPlayer(this, board);
				opp.setOpp(player);
				opp.setSymbol("o");
				player.setOpp(opp);
				opp.setOpp(player);
				showDifficulty();
			}
		} else if (state == 2) { // choose difficulty 
			JButton b = (JButton) e.getSource();
			player.setSymbol("x");
			if (b.equals(b1)) // regular
			{
				opp.setMode(1);
				showGame();
			} else if (b.equals(b2)) // hard
			{
				opp.setMode(2);
				showGame();
			}
		} else if (state == 3) { // game started
			JButton b = (JButton) e.getSource();
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					if (buttons[i][j] == b) {
						if (board[i][j].equals(" ")) {
							board[i][j] = player.getSymbol();

							player.setTurn(false);
							
							if(opp == null) {
								player.send();
							}
							else {
								opp.setTurn(true);
							}
						}
						break;
					}
				}
			}
		}
	}

	/**
	 * Translates the board to the button panel to be printed.
	 * 
	 * @param board - the 3x3 board to be drawn
	 */
	public void drawBoard(String[][] board) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (!board[i][j].equals(" ")) {
					if(board[i][j].equals("x")) {
						buttons[i][j].setIcon(new ImageIcon("x.png"));
					}
					else if (board[i][j].equals("o")) {
						buttons[i][j].setIcon(new ImageIcon("o.png"));
					}
				}
			}
		}
	}

	/**
	 * Asks for the mode of the game (player vs player or player vs computer).
	 */
	public void showMode() {
		gameOver = false;
		state = 1;

		frame = new JFrame("TicTacToe");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setSize(WIDTH, HEIGHT);
		frame.setLocationRelativeTo(null);
		frame.add(this);

		removeAll();
		setBorder(new EmptyBorder(0, 0, 0, 0));
		setLayout(new GridLayout(1, 2));

		frame.add(this);

		bgImage = new ImageIcon("mode.png").getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_DEFAULT);

		b1 = new JButton();
		b1.addMouseListener((MouseListener) player);
		b1.setOpaque(false);
		b1.setContentAreaFilled(false);
		b1.setBorderPainted(false);

		b2 = new JButton();
		b2.addMouseListener((MouseListener) player);
		b2.setOpaque(false);
		b2.setContentAreaFilled(false);
		b2.setBorderPainted(false);

		add(b1);
		add(b2);

		frame.setVisible(true);
	}

	/**
	 * Displays the waiting screen as the player waits for a connection.
	 */
	public void showWait() {
		state = 4;

		removeAll();

		player.connect(player);

		showGame();

	}

	/**
	 * Asks for the level of difficulty of the TicTacToe AI.
	 */
	public void showDifficulty() {
		state = 2;

		bgImage = new ImageIcon("difficulty.png").getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_DEFAULT);
	}

	/**
	 * Starts the game.
	 */
	public void showGame() {
		state = 3;

		bgImage = new ImageIcon("tictactoe.jpg").getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_DEFAULT);
		
		removeAll();
		setBorder(new EmptyBorder(240, 140, 20, 140));
		setLayout(new GridLayout(3, 3));

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				JButton b = new JButton();
				buttons[i][j] = b;
				add(b);
				b.setOpaque(false);
				b.setContentAreaFilled(false);
				b.setBorderPainted(false);
				b.addMouseListener((MouseListener) player);
			}
		}
	}

	/**
	 * Displays the game over screen and asks if the player wants to play again.
	 */
	public void showGameOver() {
		state = 5;
		player.setTurn(true);
		int input = 0;
		
		player.closeConnection();

		Object[] options = { "Play Again" };

		if (player.calculate(board) == 10) {
			input = JOptionPane.showOptionDialog(null, "YOU WIN!", "Game Over", JOptionPane.OK_OPTION,
					JOptionPane.PLAIN_MESSAGE, null, options, null);
		} else if (player.calculate(board) == -10) {
			input = JOptionPane.showOptionDialog(null, "YOU LOSE!", "Game Over", JOptionPane.OK_OPTION,
					JOptionPane.PLAIN_MESSAGE, null, options, null);
		} else if (player.isTied(board)) {
			input = JOptionPane.showOptionDialog(null, "YOU TIED!", "Game Over", JOptionPane.OK_OPTION,
					JOptionPane.PLAIN_MESSAGE, null, options, null);
		}

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				board[i][j] = " ";
			}
		}

		if (input == JOptionPane.OK_OPTION) {
			frame.dispose();
			showMode();
			opp = null;
		}
	}

	/**
	 * Overrides JPanel's paintComponent method.
	 * 
	 * @param g - Graphics to repaint GUI
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.clearRect(0, 0, WIDTH, HEIGHT);
		g.drawImage(bgImage, 0, 0, null);
	}

	/**
	 * Sets the background image.
	 * 
	 * @param image - the background image
	 */
	public void setImage(Image image) {
		this.bgImage = image;
	}

	/**
	 * Overrides Runnable's run method. - Repaints the GUI - Alternates turns
	 * between the current player and the opposing player
	 */
	@Override
	public void run() {

		g = (Graphics2D) getGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		while (true) {

			while (state == 3 && !gameOver) {
				
				drawBoard(board);
				repaint();
				
				if (player.calculate(board) != 0 || player.isTied(board)) {
					gameOver = true;
					drawBoard(board);
					repaint();
					showGameOver();
				}

				if (player.isMyTurn()) {
					
				}
				else {
					
					drawBoard(board);
					repaint();
					
					if (opp != null) {
						
						opp.move();
						
						opp.setTurn(false);
						player.setTurn(true);

					}
				}
			}
			repaint();
		}
	}
}
