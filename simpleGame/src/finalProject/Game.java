package finalProject;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class Game extends JPanel implements Runnable, MouseListener, KeyListener {

	final static int WIDTH = 400;
	final static int HEIGHT = 300;

	private JFrame frame;
	private JButton day, night;
	private Graphics2D g;
	private Sprite s;
	private Background bgFront;
	private Background bgBack;
	
	/**
	 * creates a game
	 */
	public Game() {
		super();
		frame = new JFrame("Cat");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(WIDTH, HEIGHT);
		frame.setResizable(false);

		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setLayout(null);
		setFocusable(true);
		requestFocusInWindow();
		setVisible(true);
		
		day = new JButton(new ImageIcon("sun.png"));
		day.setBounds(10, 10, 20, 20);
		day.setOpaque(false);
		day.setContentAreaFilled(false);
		day.setBorderPainted(false);
		day.addMouseListener(this);
		
		night = new JButton(new ImageIcon("moon.png"));
		night.setBounds(40, 10, 20, 20);
		night.setOpaque(false);
		night.setContentAreaFilled(false);
		night.setBorderPainted(false);
		night.addMouseListener(this);
		
		this.add(day);
		this.add(night);
		
		frame.add(this);
		
		s = new Sprite(200, WIDTH/40);
		bgFront = new Background(0, s.getDx(), 0);
		bgBack = new Background(-100, s.getDx() - 3, 2);
		s.setBackground(bgFront, bgBack);
		
		frame.setVisible(true);
	}
	
	/**
	 * override JPanel paintComponent method
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.clearRect(0, 0, WIDTH, HEIGHT);
		g.drawImage(bgBack.getImage(), bgBack.getX(), 0, null);
		g.drawImage(bgFront.getImage(), bgFront.getX(), 0, null);
		g.drawImage(s.getState(), s.getX(), HEIGHT - 82, null);
	}

	boolean running = true;

	/**
	 * runs the game
	 */
	public void run() {

		g = (Graphics2D) getGraphics();
		addKeyListener(this);
		
		while (running) {
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			s.move();
			repaint();
		}
	}

	/**
	 * main 
	 * 
	 * @param args
	 */
	public static void main(String [] args){
		Game cat = new Game();
		new Thread(cat).start();
	}
	
	@Override
	public void keyTyped(KeyEvent e) { }

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			s.setRight(true);
		}
		else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			s.setLeft(true);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			s.setRight(false);
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			s.setLeft(false);
		}

	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource() == day) {
			bgFront.setImage(0);
			bgBack.setImage(2);
			day.setFocusable(false);
		}
		else if(e.getSource() == night) {
			bgFront.setImage(1);
			bgBack.setImage(3);
			night.setFocusable(false);
		}
		
	}
	
	@Override
	public void mousePressed(MouseEvent e) { }

	@Override
	public void mouseReleased(MouseEvent e) { }

	@Override
	public void mouseEntered(MouseEvent e) { }

	@Override
	public void mouseExited(MouseEvent e) { }

}