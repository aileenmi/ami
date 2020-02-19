package ticTacToe;

import java.io.InputStream;
import java.util.Scanner;

/**
 * Receiver receives messages from the InputStream. When a message is
 * scanned from the other user, it is sent to the ManualPlayer to 
 * change the game board
 * 
 * @author Ingrid Lee
 */
public class Receiver implements Runnable {

	private Scanner scanner;
	private ManualPlayer player;

	/**
	 * Creates a Receiver with an InputStream
	 * 
	 * @param is - the InputStream
	 */
	public Receiver(InputStream is) {
		scanner = new Scanner(is);
	}

	/**
	 * Sets the player 
	 * 
	 * @param player - the ManualPlayer
	 */
	public void setPlayer(ManualPlayer player) {
		this.player = player;
	}

	/**
	 * Waits for a message to be printed, then scans the message
	 * and sends it to ManualPlayer to be processed
	 */
	public void run() {
		String next;

		while (true) {
			next = scanner.nextLine();
			player.receiveMessage(next);
		}
	}

}
