package com.evilzoidberg.ui;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

public class Button {
	String text;
	Rectangle rect;

	public Button(String text, int x, int y, int width, int height) {
		rect = new Rectangle(x, y, width, height);
		this.text = text;
	}
	
	public boolean contains(int x, int y) {
		return rect.contains(x, y);
	}
	
	public void paint(Graphics g) {
		g.setColor(Color.black);
		g.fill(rect);
		g.setColor(Color.white);
		g.drawString(text, rect.getMinX() + 5, rect.getMinY() + 5);
	}
}
