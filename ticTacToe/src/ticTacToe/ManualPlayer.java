package ticTacToe;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;

/**
 * ManualPlayer extends Player and represents a player that is controlled manually 
 * and moves using MouseListener. When the player uses their mouse to click on the GUI,
 * the click will register and send the MouseEvent to the GUI to be processed. 
 * ManualPlayer is also able to establish a socket connection to another ManualPlayer
 * through a server to enable a multi-player feature.
 * 
 * @author Aileen Mi
 * @author Ingrid Lee
 */
public class ManualPlayer extends Player implements MouseListener {

	private Sender sender;
	private static String IP = "localhost";
	private static int portNum = 5555;
	private static InputStream is;
	private static OutputStream os;
	private ServerSocket ss;

	/**
	 * Creates a ManualPlayer.
	 * 
	 * @param GUI - the GUI
	 * @param board - the board
	 */
	public ManualPlayer(GUI GUI, String[][] board) {
		super(GUI, board);
	}

	/**
	 * Sets the sender.
	 * 
	 * @param sender - the sender to send messages
	 */
	public void setSender(Sender sender) {
		this.sender = sender;
	}

	/**
	 * Sends messages from the Sender object
	 */
	public void send() {
		String msg = "";
		for (int i = 0; i < getBoard().length; i++) {
			for (int j = 0; j < getBoard().length; j++) {
				msg += getBoard()[i][j] + ",";
			}
		}
		sender.write(msg);
	}

	/**
	 * Receives messages.
	 * 
	 * @param message - message to be received
	 */
	public void receiveMessage(String message) {
		StringTokenizer tokenizer = new StringTokenizer(message, ",");

		for (int i = 0; i < getBoard().length; i++) {
			for (int j = 0; j < getBoard().length; j++) {
				getBoard()[i][j] = tokenizer.nextToken();
			}
		}

		setTurn(true);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(isMyTurn()) {
			getGUI().processEvent(e);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	/**
	 * Creates the player and starts the GUI thread.
	 * 
	 * @param args - the main Thread
	 */
	public static void main(String[] args) {
		GUI GUI = new GUI();
		String[][] startingBoard = { { " ", " ", " " }, { " ", " ", " " }, { " ", " ", " " } };
		GUI.setBoard(startingBoard);

		ManualPlayer me = new ManualPlayer(GUI, startingBoard);
		me.setSymbol("x");

		GUI.setPlayer(me);
		GUI.showMode();

		Thread t = new Thread(GUI);
		t.start();
	}

	/**
	 * Closes the socket connection
	 */
	public void closeConnection() {
		if(getOpp() instanceof AIPlayer) {
			return;
		}
		try {
			if(ss != null)
			{
				ss.close();
				ss = null;
			}
			is.close();
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Establishes a connection with a socket.
	 * 
	 * @param me - the current player
	 */
	public void connect(ManualPlayer me) {
		try {
			ss = new ServerSocket(portNum);
			Socket s = ss.accept();
			me.setSymbol("x");
			me.setTurn(true);

			is = s.getInputStream();
			os = s.getOutputStream();

			while (!s.isConnected()) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			try {
				Socket s = new Socket(IP, portNum);
				me.setSymbol("o");
				me.setTurn(false);

				is = s.getInputStream();
				os = s.getOutputStream();

			} catch (IOException i) {
				e.printStackTrace();
			}
		}

		Sender sender = new Sender(os);
		me.setSender(sender);
		Receiver receiver = new Receiver(is);
		receiver.setPlayer((ManualPlayer) me);

		new Thread(receiver).start();

	}

}
