package com.evilzoidberg.entities;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;

@SuppressWarnings("serial")
public class Rigidbody extends Entity {
	public static float baseGravity = 0.05f;
	float xVel = 0.0f; //Velocities never change for base rigidbody unless
	float yVel = 0.0f; //some outside object changes them, children could differ
	boolean grounded = false;
	float x2, y2; //Center of entity
	float x3, y3; //Bottom right corner of entity

	public Rigidbody(Image image, float x, float y) {
		super(image, x, y);
	}

	public Rigidbody(Color color, float x, float y) {
		super(color, x, y);
	}
	
	public Rigidbody(Color color, float x, float y, float width, float height) {
		super(color, x, y, width, height);
	}
	
	/**
	 * updates the rigidbody's position due to gravity, and collides
	 * properly with all the passed objects.
	 * @param delta - num milliseconds since last update call
	 * @param objects - objects that the rigidbody should collide with
	 */
	public void update(int delta, Entity[] objects) {
		yVel += baseGravity;
		
		//Check if entity should be falling
		if(xVel == 0) {
			for(int i = 0; i < yVel; i++) {
				moveY(y > 0, objects); //move adjusts velocity, so it will end if hits ground
			}
		}
		else {
			float toMoveX = xVel;
			float toMoveY = yVel;
			
			//Add movement
		}
	}
	
	/**
	 * Moves object left or right one unit and checks collisions
	 * @param down - pass xVel > 0 and you get the correct value
	 * @param objects - objects to collide with
	 * @return
	 */
	public void moveX(boolean right, Entity[] objects) {
		
	}
	
	/**
	 * Moves object up or down one unit and checks collisions
	 * @param down - pass yVel > 0 and you get the correct value
	 * @param objects - objects to collide with
	 * @return
	 */
	public void moveY(boolean down, Entity[] objects) {
		grounded = false;
		y += 1;
		for(Entity e : objects) {
			if(e.intersects(this)) {
				grounded = true;
				yVel = 0.0f;
				y -= 1; //Undo test movement
				
				return; //No need to check more
			}
		}
	}
}
