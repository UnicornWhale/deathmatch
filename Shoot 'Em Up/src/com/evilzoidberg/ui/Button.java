package com.evilzoidberg.ui;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;

public abstract class Button {
	Image image;
	Rectangle rect;

	public Button(Image image, int x, int y) {
		rect = new Rectangle(x, y, image.getWidth(), image.getHeight());
		this.image = image;
	}
	
	public boolean isClicked(int x, int y) {
		/**
		 * Returns whether the button is clicked or not at the given x and y of the mouse.
		 */
		if(rect.contains(x, y)) {
			return true;
		}
		return false;
	}
	
	public void paint(Graphics g) {
		image.draw(rect.getX(), rect.getY());
	}
}
