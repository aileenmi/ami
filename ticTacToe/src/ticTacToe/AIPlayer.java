package ticTacToe;

/**
 * AIPlayer extends Player and represents a player that moves using a minimax
 * game algorithm. The minimax algorithm uses recursion to test all possible
 * outcomes of a TicTacToe game to ensure that the AI makes the most optimal
 * move. 
 * 
 * @author Aileen Mi
 */
public class AIPlayer extends Player {

	private int mode;

	/**
	 * Creates an AIPlayer
	 * 
	 * @param GUI   - the GUI
	 * @param board - the board
	 */
	public AIPlayer(GUI GUI, String[][] board) {
		super(GUI, board);
	}

	/**
	 * Sets the mode of the AIPlayer
	 * 
	 * @param mode - 1 for regular - 2 for hard
	 */
	public void setMode(int mode) {
		this.mode = mode;
	}

	/**
	 * Returns the mode of the AIPlayer
	 * 
	 * @return mode
	 */
	public int getMode() {
		return mode;
	}

	/**
	 * The AIPlayer will move using either the minimaxRegular algorithm or
	 * minimaxHard algorithm based on the mode
	 */
	public void move() {
		
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		int row = 0;
		int col = 0;
		int bestMove = Integer.MIN_VALUE;
		if (mode == 2) {
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {

					if (getBoard()[i][j].equals(" ")) {

						getBoard()[i][j] = getSymbol();

						int score = minimaxHard(0, false);
						if (bestMove < score) {
							bestMove = score;
							row = i;
							col = j;
						}

						getBoard()[i][j] = " ";
					}
				}
			}
		} else if (mode == 1) {
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {

					if (getBoard()[i][j].equals(" ")) {

						getBoard()[i][j] = getSymbol();

						int score = minimaxRegular(false, 0);
						if (bestMove < score) {
							bestMove = score;
							row = i;
							col = j;
						}

						getBoard()[i][j] = " ";
					}
				}
			}
		}

		getBoard()[row][col] = getSymbol();
	}

	/**
	 * The regular minimax algorithm
	 * 
	 * @param isMax - states whether the current player is the maximizer
	 * @param count - a count to limit the calculations of the algorithm,
	 *              effectively making the AI less perfect
	 * @return the best move score
	 */
	public int minimaxRegular(boolean isMax, int count) {

		if (count >= 2) {
			return 0;
		}

		int score = calculate(getBoard());

		if (score != 0) {
			return score;
		} else if (!movesLeft(getBoard())) {
			return 0;
		}

		if (isMax) {
			int bestMove = Integer.MIN_VALUE;
			int val;
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					if (getBoard()[i][j].equals(" ")) {
						getBoard()[i][j] = getSymbol();

						val = minimaxRegular(false, count + 1);
						bestMove = Math.max(bestMove, val);

						getBoard()[i][j] = " ";
					}
				}
			}
			return bestMove;
		} else {
			int bestMove = Integer.MAX_VALUE;
			int val;
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					if (getBoard()[i][j].equals(" ")) {
						getBoard()[i][j] = getOppSymbol();

						val = minimaxRegular(true, count + 1);
						bestMove = Math.min(bestMove, val);
						getBoard()[i][j] = " ";
					}
				}
			}
			return bestMove;
		}
	}

	/**
	 * The regular minimax algorithm
	 * 
	 * @param isMax - states whether the current player is the maximizer
	 * @param depth - the depth to make the AI choose
	 * @return the best move score
	 */
	public int minimaxHard(int depth, boolean isMax) {

		int score = calculate(getBoard());

		if (score != 0) {
			return score;
		} else if (!movesLeft(getBoard())) {
			return 0;
		}

		if (isMax) {
			int bestMove = Integer.MIN_VALUE;
			int val;
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					if (getBoard()[i][j].equals(" ")) {
						getBoard()[i][j] = getSymbol();

						val = minimaxHard(depth + 1, false);
						bestMove = Math.max(bestMove, val);

						getBoard()[i][j] = " ";
					}
				}
			}
			return bestMove - depth;
		} else {
			int bestMove = Integer.MAX_VALUE;
			int val;
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					if (getBoard()[i][j].equals(" ")) {
						getBoard()[i][j] = getOppSymbol();

						val = minimaxHard(depth + 1, true);
						bestMove = Math.min(bestMove, val);
						getBoard()[i][j] = " ";
					}
				}
			}
			return bestMove + depth;
		}
	}

	/**
	 * Checks if there are moves left in the game
	 * 
	 * @param board - the board to be checked
	 * @return true if there are moves left
	 * 		false if there are no moves left
	 */
	public boolean movesLeft(String[][] board) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (board[i][j].equals(" ")) {
					return true;
				}
			}
		}
		return false;
	}
}
