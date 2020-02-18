package finalProject;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Background {

	Image image;
	private int x;
	private int dx;
	private int bgOption;
	private boolean reachEnd;
	private boolean reachBegin;
	
	private Image[] bgs = {
			new ImageIcon("dayfront.png").getImage(),
			new ImageIcon("nightfront.png").getImage(),
			new ImageIcon("dayback.png").getImage(),
			new ImageIcon("nightback.png").getImage()
	};
	
	/**
	 * creates a background
	 * @param x - starting x-position of the background
	 * @param dx - increment to move the background
	 */
	public Background(int x, int dx, int bgOption) {
		this.x = x;
		this.dx = dx;
		this.bgOption = bgOption;
		image = bgs[bgOption];
		reachEnd = false;
		reachBegin = true;
	}
	
	/**
	 * sets the image 
	 * 
	 * @param bgOption - the option to set
	 */
	public void setImage(int bgOption) {
		this.bgOption = bgOption;
		image = bgs[bgOption];
	}
	
	/**
	 * returns the image to draw
	 * 
	 * @return image
	 */
	public Image getImage() {
		return bgs[bgOption];
	}
	
	/**
	 * gets the x-position of the background
	 * 
	 * @return x
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * gets whether the background has reached the end
	 * 
	 * @return reachEnd
	 */
	public boolean getReachEnd() {
		return reachEnd;
	}
	
	/**
	 * gets whether the background has reached the beginning
	 * 
	 * @return reachBegin
	 */
	public boolean getReachBegin() {
		return reachBegin;
	}
	
	/**
	 * sets the direction of dx
	 * 
	 * @param direction
	 * 		- false for left
	 * 		- true for right
	 */
	public void setDx(boolean direction) {
		if(direction) {
			dx = Math.abs(dx);
		}
		else {
			dx = -1 * Math.abs(dx);
		}
	}
	
	/**
	 * moves the background
	 */
	public void move() {
		x += dx;
		if(x >= 0) {
			x = 0;
			reachBegin = true;
		}
		else if(x <= 0 - (image.getWidth(null) - Game.WIDTH) ) {
			x = -1 * (image.getWidth(null) - Game.WIDTH);
			reachEnd = true;
		}
		else
		{
			reachBegin = false;
			reachEnd = false;
		}
	}
	
}
