package com.evilzoidberg.entities;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;

import com.evilzoidberg.Settings;

@SuppressWarnings("serial")
public class Rigidbody extends Entity {
	public float xVel = 0.0f;
	public float yVel = 0.0f;
	Rectangle bottomDetector;
	Rectangle topDetector;
	Rectangle leftDetector;
	Rectangle rightDetector;

	public Rigidbody(Image image, float x, float y) {
		super(image, x, y);
		init();
	}

	public Rigidbody(Color color, float x, float y) {
		super(color, x, y);
		init();
	}
	
	public Rigidbody(Color color, float x, float y, float width, float height) {
		super(color, x, y, width, height);
		init();
	}
	
	private void init() {
		//Collision detectors
		bottomDetector = new Rectangle(getMinX(), getMaxY(), getWidth(), 1);
		topDetector = new Rectangle(getMinX(), getMinY() - 1, getWidth(), 1);
		leftDetector = new Rectangle(getMinX() - 1, getMinY(), 1, getHeight());
		rightDetector = new Rectangle(getMaxX(), getMinY(), 1, getHeight());
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		if(Settings.debug) {
			g.setColor(Color.white);
			g.draw(bottomDetector);
			g.draw(topDetector);
			g.draw(leftDetector);
			g.draw(rightDetector);
		}
	}
	
	public void update(int delta, Entity[] objects) {
		//Gravity
		yVel += 100.0f * (delta / 100.0f);
		
		int steps = (int)Math.max(Math.abs(xVel), Math.abs(yVel));
		float toMoveX = xVel;
		float toMoveY = yVel;
		
		for(int i = 0; i < steps; i++) {
			float dx = 0.0f;
			float dy = 0.0f;
			
			//Check collisions before each step of movement
			if(xVel > 0.0f && collides(rightDetector, objects)) {
				xVel = 0.0f;
			}
			if(xVel < 0.0f && collides(leftDetector, objects)) {
				xVel = 0.0f;
			}
			if(yVel > 0.0f && collides(bottomDetector, objects)) {
				yVel = 0.0f;
			}
			if(yVel < 0.0f && collides(topDetector, objects)) {
				yVel = 0.0f;
			}
			
			//If no collisions, and movement still needed
			//on that axis, move one unit
			if(Math.abs(toMoveX) >= 1.0f) {
				if(xVel > 0.0f) {
					dx = 1.0f * (delta / 100.0f);
					toMoveX -= 1.0f * (delta / 100.0f);
				}
				else {
					dx = -1.0f * (delta / 100.0f);
					toMoveX += 1.0f * (delta / 100.0f);
				}
			}
			if(Math.abs(toMoveY) >= 1.0f) {
				if(yVel > 0.0f) {
					dy = 1.0f * (delta / 100.0f);
					toMoveY -= 1.0f * (delta / 100.0f);
				}
				else {
					dy = -1.0f * (delta / 100.0f);
					toMoveY += 1.0f * (delta / 100.0f);
				}
			}
			
			//DEAR GOD FIX IT
			
			//Eliminate jitteryness
			if(Math.abs(dx) < 0.2f) {
				dx = 0.0f;
			}
			if(Math.abs(dy) < 0.2f) {
				dy = 0.0f;
			}
			
			System.out.println("dx: " + dx + ", dy: " + dy);
			move(dx, dy);
		}
	}
	
	protected void move(float dx, float dy) {
		x += dx;
		y += dy;

		bottomDetector.setX(bottomDetector.getX() + dx);
		bottomDetector.setY(bottomDetector.getY() + dy);

		topDetector.setX(topDetector.getX() + dx);
		topDetector.setY(topDetector.getY() + dy);

		leftDetector.setX(leftDetector.getX() + dx);
		leftDetector.setY(leftDetector.getY() + dy);

		rightDetector.setX(rightDetector.getX() + dx);
		rightDetector.setY(rightDetector.getY() + dy);
	}
	
	protected boolean collides(Rectangle rect, Entity[] objects) {
		for(Entity e : objects) {
			if(e.intersects(rect)) {
				return true;
			}
		}
		return false;
	}
}
