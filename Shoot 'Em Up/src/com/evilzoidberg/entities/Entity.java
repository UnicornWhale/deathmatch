package com.evilzoidberg.entities;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;

@SuppressWarnings("serial")
public class Entity extends Rectangle {
	Image image = null;
	float offsetX, offsetY;
	
	public Entity(Image image, float x, float y, int width, int height, float offsetX, float offsetY) {
		super(x, y, width, height);
		this.image = image;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
	}
	
	public Entity(float x, float y, int width, int height, float offsetX, float offsetY) {
		super(x, y, width, height);
		this.image = null;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
	}
	
	public void paint(Graphics g) {
		/**
		 * Draws the image of this to the screen offset from this hitbox by
		 * the amount this was initialized with. If debug is on, all hitboxes
		 * will be drawn in blue.
		 */
		if(image != null) {
			g.drawImage(image, x + offsetX, y + offsetY);
		}
		else {
			g.setColor(Color.red);
			g.fillRect(x + offsetX, y + offsetY, width, height);
		}
	}
	
	public void paint(Graphics g, boolean facingRight) {
		/**
		 * Draws the image of this to the screen offset from this hitbox by
		 * the amount this was initialized with and switches that amount to
		 * the correct value to flip the image horizontally if the passed
		 * boolean is false.
		 */
		if(facingRight) {
			if(image != null) {
				image.draw(x + offsetX, y + offsetY);
			}
			else {
				g.setColor(Color.red);
				g.fillRect(x + offsetX, y + offsetY, width, height);
			}
		}
		else {
			int newOffset = (int)(-1.0f * (image.getWidth() - (width - offsetX)));
			if(image != null) {
				image.getFlippedCopy(true, false).draw(x + newOffset, y + offsetY);
			}
			else {
				g.setColor(Color.red);
				g.fillRect(x + newOffset, y + offsetY, width, height);
			}
		}
	}
}
