package ticTacToe;

/**
 * Player represents one of two players in a TicTacToe game. Player holds a 
 * reference to the GUI and the 3x3 game board to make moves. Player also can
 * calculate whether a player has won, lost, or tied given a 3x3 game board.
 * 
 * @author Aileen Mi
 */
public class Player {
	private String symbol;
	private String oppSymbol;
	private Player opp;
	private GUI GUI;
	private String[][] board;
	private boolean myTurn = true;

	/**
	 * Creates a Player.
	 * 
	 * @param GUI   - the GUI
	 * @param board - the 3x3 board
	 */
	public Player(GUI GUI, String[][] board) {
		this.GUI = GUI;
		this.board = board;
	}

	/**
	 * Sets the opposing player.
	 * 
	 * @param player - the opposing player
	 */
	public void setOpp(Player player) {
		opp = player;
	}

	/**
	 * Returns the opposing player.
	 * 
	 * @return opp
	 */
	public Player getOpp() {
		return opp;
	}

	/**
	 * Sets the symbol of the player and the symbol of the opposite player.
	 * 
	 * @param symbol - "x" or "o"
	 */
	public void setSymbol(String symbol) {
		this.symbol = symbol;
		if (symbol.equals("x")) {
			oppSymbol = "o";
		} else if (symbol.equals("o")) {
			oppSymbol = "x";
		}
	}

	/**
	 * Returns the GUI.
	 * 
	 * @return GUI
	 */
	public GUI getGUI() {
		return GUI;
	}

	/**
	 * Returns the current player's symbol.
	 * 
	 * @return symbol
	 */
	public String getSymbol() {
		return symbol;
	}

	/**
	 * Returns the opponent's symbol.
	 * 
	 * @return oppSymbol
	 */
	public String getOppSymbol() {
		return oppSymbol;
	}

	/**
	 * Returns the board.
	 * 
	 * @return board
	 */
	public String[][] getBoard() {
		return board;
	}

	/**
	 * Calculates the score of the board.
	 * 
	 * @param board the board to be checked
	 * @return the score of the board - 10 if the current player has won - -10 if
	 *         the opponent has won - 0 if the game is tied or the game is not over
	 */
	public int calculate(String[][] board) {
		int i = 0;
		for (int j = 0; j < 3; j++) {
			if (board[i][j].equals(getSymbol()) || board[i][j].equals(getOppSymbol())) {
				if (board[i][j].equals(board[i + 1][j]) && board[i + 1][j].equals(board[i + 2][j])) {
					if (board[i][j].equals(getOppSymbol())) {
						return -10;
					} else if (board[i][j].equals(getSymbol())) {
						return 10;
					}
				}
			}
		}

		int j = 0;
		for (i = 0; i < 3; i++) {
			if (board[i][j].equals(getSymbol()) || board[i][j].equals(getOppSymbol())) {
				if (board[i][j].equals(board[i][j + 1]) && board[i][j + 1].equals(board[i][j + 2])) {
					if (board[i][j].equals(getOppSymbol())) {
						return -10;
					} else if (board[i][j].equals(getSymbol())) {
						return 10;
					}
				}
			}
		}

		if ((board[0][0].equals(board[1][1]) && board[1][1].equals(board[2][2]))
				|| (board[0][2].equals(board[1][1]) && board[1][1].equals(board[2][0]))) {
			if (board[1][1].equals(getOppSymbol())) {
				return -10;
			} else if (board[1][1].equals(getSymbol())) {
				return 10;
			}
		}
		return 0;
	}

	/**
	 * Checks if the game is tied.
	 * 
	 * @param board - the board to be checked
	 * @return - true if the game is tied - false if the game is still going
	 */
	public boolean isTied(String[][] board) {
		if (calculate(board) != 0)
			return false;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (board[i][j].equals(" ")) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Checks if it is the current player's turn.
	 * 
	 * @return myTurn
	 */
	public boolean isMyTurn() {
		return myTurn;
	}

	/**
	 * Switches the turn of the current player.
	 * 
	 * @param turn - true if it is the player's turn - false if it is not the
	 *             player's turn
	 */
	public void setTurn(boolean turn) {
		myTurn = turn;
	}
}
