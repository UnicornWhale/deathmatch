package com.evilzoidberg.entities;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;

@SuppressWarnings("serial")
public class Entity extends Rectangle {
	Image image = null;
	Color color = Color.red; //Default to a red square
	
	public Entity(Image image, float x, float y) {
		super(x, y, image.getWidth(), image.getHeight());
		this.image = image;
	}
	
	public void paint(Graphics g) {
		if(image != null) {
			//For now, don't use sprite sheets, change this later.
			g.drawImage(image, x, y);
		}
		else {
			g.setColor(color);
			g.fillRect((int)x, (int)y, width, height);
		}
	}
}
