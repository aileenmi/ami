package ticTacToe;

import java.io.*;

/**
 * Sender allows ManualPlayer to send the 3x3 game board in String
 * format to the other user to print onto the GUI.
 * 
 * @author Ingrid Lee
 */
public class Sender {

	private PrintWriter out;

	/**
	 * Creates a Sender with an OutputStream to send messages
	 * 
	 * @param os - the OutputStream
	 */
	public Sender(OutputStream os) {
		out = new PrintWriter(os);
	}

	/**
	 * Writes a message to the Output Stream to send to the other user
	 * through the socket.
	 * 
	 * @param message - the message to be sent
	 */
	public void write(String message) {
		out.println(message);
		out.flush();
	}

}
