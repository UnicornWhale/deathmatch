package com.evilzoidberg.entities;

import org.newdawn.slick.Image;

@SuppressWarnings("serial")
public class Rigidbody extends Entity {
	public static float baseGravity = 5;
	float xVel = 0; //Velocities never change for base rigidbody unless
	float yVel = 0; //some outside object changes them, children could differ
	boolean grounded = false;
	float x2, y2; //Center of entity
	float x3, y3; //Bottom right corner of entity
	

	public Rigidbody(Image image, float x, float y) {
		super(image, x, y);
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
			
			if(xVel > yVel) {
				float ratio = xVel / yVel; //Proportion of velocities, so moved in about a straight line
				
				//Add movement
			}
			else {
				float ratio = yVel / xVel; //Proportion of velocities, so moved in about a straight line
				
				//Add movement
			}
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
				yVel = 0;
				y -= 1; //Undo test movement
				
				return; //No need to check more
			}
		}
	}
}
