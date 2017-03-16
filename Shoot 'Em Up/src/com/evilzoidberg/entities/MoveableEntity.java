package com.evilzoidberg.entities;

import java.util.ArrayList;

import org.newdawn.slick.Image;

import com.evilzoidberg.Settings;

@SuppressWarnings("serial")
public class MoveableEntity extends Entity {
	float dx = 0, dy = 0; //Velocity
	float ddx = 0, ddy = 0; //Acceleration
	float dxMax = 500.0f, dyMax = 2000.0f;
	boolean onGround = false;
	boolean affectedByGravity = true;

	public MoveableEntity(Image image, float x, float y, int width, int height, float offsetX, float offsetY) {
		super(image, x, y, width, height, offsetX, offsetY);
	}

	public MoveableEntity(float x, float y, int width, int height, float offsetX, float offsetY) {
		super(x, y, width, height, offsetX, offsetY);
	}

	public void updatePhysics(int deltaInt, ArrayList<Entity> mapEntities) {
		/**
		 * Uses set velocities and accelerations to determine where this should move to, then moves it
		 * step by step and stops on collision.
		 * 
		 * @param deltaInt The number of milliseconds that have passed since the last update
		 * @param mapEntities All terrain entities that this should collide properly with
		 */
		//Turn delta to a fraction of a second
		float delta = ((float)deltaInt) / 1000.0f;
		
		if(affectedByGravity) {
			ddy = Settings.Gravity;
		}
		else {
			ddy = 0.0f;
		}

		//Calculate distance to move
		float totalDy = dy * delta;
		float totalDx = dx * delta;

		//Limit by maximums
		if(totalDx > (dxMax * delta)) {
			totalDx = dxMax * delta;
		}
		if(totalDy > (dyMax * delta)) {
			totalDy = dyMax * delta;
		}
		if(totalDx < (dxMax * delta * -1.0f)) {
			totalDx = dxMax * delta * -1.0f;
		}
		if(totalDy < (dyMax * delta * -1.0f)) {
			totalDy = dyMax * delta * -1.0f;
		}
		
		//Apply acceleration to velocity
		dy += ddy * delta;
		dx += ddx * delta;
		
		//Move it one at a time
		while(totalDx != 0.0f || totalDy != 0.0f) {
			//Horizontal movement
			if(totalDx < 0.9f && totalDx > -0.9f){
				totalDx = 0.0f;
			}
			else if(totalDx > 0.0f) {
				x++;
				if(collidesWithSomething(mapEntities)) {
					//Collision on right
					float oldX = x;
					x = (float)Math.floor(x);
					if(x == oldX) {
						x--; //Cover whole numbers
					}
					totalDx = 0.0f;
					dx = 0.0f;
				}
				else {
					totalDx--;
				}
			}
			else if(totalDx < 0.0f) {
				x--;
				if(collidesWithSomething(mapEntities)) {
					//Collision on left
					float oldX = x;
					x = (float)Math.ceil(x);
					if(x == oldX) {
						x++; //Cover whole numbers
					}
					totalDx = 0.0f;
					dx = 0.0f;
				}
				else {
					totalDx++;
				}
			}
			
			//Vertical movement
			if(totalDy < 0.9f && totalDy > -0.9f){
				totalDy = 0.0f;
			}
			else if(totalDy > 0.0f) {
				y++;
				if(collidesWithSomething(mapEntities)) {
					//Collision below
					float oldY = y;
					y = (float)Math.floor(y);
					if(y == oldY) {
						y--; //Cover whole numbers
					}
					totalDy = 0.0f;
					dy = 0.0f;
				}
				else {
					totalDy--;
				}
			}
			else if(totalDy < 0.0f) {
				y--;
				if(collidesWithSomething(mapEntities)) {
					//Collision above
					float oldY = y;
					y = (float)Math.ceil(y);
					if(y == oldY) {
						y++; //Cover whole numbers
					}
					totalDy = 0.0f;
					dy = 0.0f;
				}
				else {
					totalDy++;
				}
			}
		}
		
		//Keep entities within the map
		if(x < 0.0f) {
			x = 0.0f;
			dx = 0.0f;
		}
		if(x > Settings.WindowWidth - width) {
			x = Settings.WindowWidth - width;
			dx = 0.0f;
		}
		if(y < 0.0f) {
			y = 0.0f;
			dy = 0.0f;
		}
		if(y > Settings.WindowHeight - height) {
			y = Settings.WindowHeight - height;
			dy = 0.0f;
		}
		
		//Check if on ground
		y++;
		if(collidesWithSomething(mapEntities)) {
			onGround = true;
		}
		else {
			onGround = false;
		}
		y--;
	}
	
	private boolean collidesWithSomething(ArrayList<Entity> mapEntities) {
		/**
		 * Checks for collisions with all passed entities and returns true if it collides
		 * with one or more of those entities. False otherwise.
		 */
		for(int object = 0; object < mapEntities.size(); object++) {
			if(mapEntities.get(object).intersects(this)) {
				return true;
			}
		}
		return false;
	}
}
