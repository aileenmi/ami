package ticTacToe;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Tests ticTacToe.
 * 
 * @author Aileen Mi
 */
public class TicTacToeTest {

	GUI GUI = new GUI();
	private String[][] board = { { " ", " ", " " }, { " ", " ", " " }, { " ", " ", " " } };

	// Test Player

	Player p = new Player(GUI, board);

	@Test
	public void testPlayerConstructor() {
		Player p1 = new Player(GUI, board);
		assertNotNull(p1.getGUI());
		assertNotNull(p1.getBoard());
	}

	@Test
	public void testPlayerOpp() {
		Player p1 = new Player(GUI, board);
		p1.setOpp(p);
		assertEquals(p1.getOpp(), p);
	}

	@Test
	public void testPlayerSymbol() {
		Player p1 = new Player(GUI, board);
		p1.setSymbol("x");
		assertEquals(p1.getSymbol(), "x");
	}

	@Test
	public void testPlayerOppSymbol() {
		Player p1 = new Player(GUI, board);
		p1.setSymbol("x");
		assertEquals(p1.getOppSymbol(), "o");
	}

	@Test
	public void testPlayerCalculateTied() {
		String[][] b = { { "x", "o", "x" }, { "x", "x", "o" }, { "o", "o", "x" } };
		assertEquals(p.calculate(b), 0);
	}

	@Test
	public void testPlayerCalculateWin() {
		String[][] b = { { "x", "o", "x" }, { "x", "x", "o" }, { "o", "o", "x" } };
		p.setSymbol("x");
		assertEquals(p.calculate(b), 10);
	}

	@Test
	public void testPlayerCalculateLose() {
		String[][] b = { { "x", "o", "x" }, { "x", "x", "o" }, { "o", "o", "o" } };
		p.setSymbol("x");
		assertEquals(p.calculate(b), -10);
	}

	@Test
	public void testPlayerIsTiedTrue() {
		String[][] b = { { "o", "o", "x" }, { "x", "x", "o" }, { "o", "o", "x" } };
		p.setSymbol("x");
		assertTrue(p.isTied(b));
	}

	@Test
	public void testPlayerIsTiedFalse() {
		String[][] b = { { "x", "o", "x" }, { "x", "x", "o" }, { "o", "o", "x" } };
		p.setSymbol("x");
		assertFalse(p.isTied(b));
	}

	@Test
	public void testPlayerTurns() {
		p.setTurn(false);
		assertFalse(p.isMyTurn());
	}

	// Test ManualPlayer

	ManualPlayer mp = new ManualPlayer(GUI, board);

	@Test
	public void testManualPlayerConstructor() {
		assertNotNull(mp.getGUI());
		assertNotNull(mp.getBoard());
	}

	@Test
	public void testManualPlayerSend() {
		mp.setSender(new Sender(System.out));
		try {
			mp.send();
		} catch (NullPointerException e) {
			fail("ManuaPlayer send failed: Sender not set");
		}
	}

	// Test AIPlayer

	AIPlayer ap = new AIPlayer(GUI, board);

	@Test
	public void testAIPlayerConstructor() {
		AIPlayer ap1 = new AIPlayer(GUI, board);
		assertNotNull(ap1.getGUI());
		assertNotNull(ap1.getBoard());
	}

	@Test
	public void testAIPlayerMode() {
		ap.setMode(1);
		assertEquals(ap.getMode(), 1);
		ap.setMode(2);
		assertEquals(ap.getMode(), 2);
	}

	@Test
	public void testAIPlayerMoveRegular() {
		String[][] b = { { "x", " ", " " }, { " ", " ", " " }, { " ", " ", " " } };
		ap = new AIPlayer(GUI, b);
		ap.setSymbol("o");
		ap.setMode(1);
		ap.move();
		assertEquals(b[0][1], "o");
	}

	@Test
	public void testAIPlayerMoveHard() {
		String[][] b = { { "x", " ", " " }, { " ", " ", " " }, { " ", " ", " " } };
		ap = new AIPlayer(GUI, b);
		ap.setSymbol("o");
		ap.setMode(2);
		ap.move();
		assertEquals(b[1][1], "o");
	}

}
