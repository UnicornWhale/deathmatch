package com.evilzoidberg.ui;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

public abstract class Button {
	String text;
	Rectangle rect;

	public Button(String text, int x, int y, int width, int height) {
		rect = new Rectangle(x, y, width, height);
		this.text = text;
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
		g.setColor(Color.black);
		g.fill(rect);
		g.setColor(Color.white);
		g.drawString(text, rect.getMinX() + 5, rect.getMinY() + 5);
	}
}
