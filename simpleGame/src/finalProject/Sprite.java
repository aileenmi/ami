package finalProject;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Sprite {

	private Image[] images = { 
			new ImageIcon("catleft1.png").getImage(),
			new ImageIcon("catleft2.png").getImage(),
			new ImageIcon("catleft3.png").getImage(),
			new ImageIcon("catRight1.png").getImage(),
			new ImageIcon("catRight2.png").getImage(),
			new ImageIcon("catRight3.png").getImage() 
	};

	private int state = 4;
	private int dx;
	private int x;
	private Background bgFront;
	private Background bgBack;

	private boolean left, right;

	/**
	 * creates a sprite 
	 * @param x - starting x-position of the sprite
	 * @param dx - increment to move the sprite
	 */
	public Sprite (int x, int dx) {
		this.x = x;
		this.dx = dx;
	}

	/**
	 * get the image corresponding to the specified state
	 * 
	 * @return image
	 */
	public Image getState () {
		return images[state];
	}

	/**
	 * moves the sprite to the left
	 */
	public void goLeft() {
		x -= dx;
		bgBack.setDx(true);
		bgBack.move();
		if(x < 100) {
			if(!bgBack.getReachBegin())
			{
				bgFront.setDx(true);
				bgFront.move();
			}
			x = 100;
		}

	}

	/**
	 * sets whether the sprite is moving left
	 * @param left
	 */
	public void setLeft(boolean left) {
		this.left = left;
	}

	/**
	 * moves the sprite to the right
	 */
	public void goRight() {
		x += dx;
		bgBack.setDx(false);
		bgBack.move();
		if(x > Game.WIDTH - 200)
		{
			if(!bgBack.getReachEnd())
			{
				x = Game.WIDTH - 200;
				bgFront.setDx(false);
				bgFront.move();
			}
			else if( x > Game.WIDTH - 100)
			{
				x = Game.WIDTH - 100;
			}
		}
	}

	/**
	 * sets whether the sprite is moving right
	 * @param right
	 */
	public void setRight(boolean right) {
		this.right = right;
	}

	/**
	 * checks which direction to move the sprite and moves
	 */
	public void move() {
		if(right) {
			goRight();
			state++;
			if(state > 5) {
				state = 3;
			}
		}
		else if(left) {
			goLeft();
			state++;
			if(state > 2) {
				state = 0;
			}
		}
	}

	/**
	 * gets the x-position of the sprite
	 * 
	 * @return x
	 */
	public int getX() {
		return x;
	}

	/**
	 * gets the change in x of the background
	 * 
	 * @return x 
	 */
	public int getDx() {
		return dx;
	}

	/**
	 * sets the reference background 
	 * 
	 * @param bg - the background to be referenced
	 */
	public void setBackground(Background bgFront, Background bgBack) {
		this.bgFront = bgFront;
		this.bgBack = bgBack;
	}

}
