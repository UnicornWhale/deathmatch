package com.evilzoidberg.entities;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;

@SuppressWarnings("serial")
public class Entity extends Rectangle {
	Image image = null;
	float offsetX, offsetY;
	
	public Entity(Image image, float x, float y, float offsetX, float offsetY) {
		super(x, y, image.getWidth(), image.getHeight());
		this.image = image;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
	}
	
	public Entity(float x, float y, float offsetX, float offsetY) {
		//Super hacky, but sue offsetX and offsetY as width and height for non-image things
		super(x, y, offsetX, offsetY);
		this.image = null;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
	}
	
	public void paint(Graphics g) {
		if(image != null) {
			g.drawImage(image, x + offsetX, y + offsetY);
		}
		else {
			g.setColor(Color.red);
			g.fillRect(x, y, offsetX, offsetY);
		}
	}
}
