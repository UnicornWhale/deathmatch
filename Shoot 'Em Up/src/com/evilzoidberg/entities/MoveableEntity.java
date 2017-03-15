package com.evilzoidberg.entities;

import java.util.ArrayList;

import org.newdawn.slick.Image;

import com.evilzoidberg.Settings;

@SuppressWarnings("serial")
public class MoveableEntity extends Entity {
	float oldX, oldY; //Position
	float dx = 0, dy = 0; //Velocity
	float ddx = 0, ddy = 0; //Acceleration
	boolean onGround = false;
	boolean onCeiling = false;
	boolean onLeftWall = false;
	boolean onRightWall = false;
	boolean affectedByGravity = true;

	public MoveableEntity(Image image, float x, float y, float offsetX, float offsetY) {
		super(image, x, y, offsetX, offsetY);
		oldX = x;
		oldY = y;
	}

	public MoveableEntity(float x, float y, float offsetX, float offsetY) {
		super(x, y, offsetX, offsetY);
		oldX = x;
		oldY = y;
	}

	public void updatePhysics(int delta, ArrayList<Entity> mapEntities) {
		oldX = x;
		oldY = y;
		
		if(!onGround) {
			if(affectedByGravity) {
				ddy = Settings.Gravity;
			}
		}
		else {
			ddy = 0.0f;
		}
		
		//Apply acceleration to velocity
		dy += ddy * (float)delta;
		dx += ddx * (float)delta;

		//Apply velocity to position
		float totalDy = dy * (float)delta;
		float totalDx = dx * (float)delta;
		y += totalDy;
		x += totalDx;
		
		//Check collisions
		boolean carefulMoveNeeded = false;
		for(int object = 0; object < mapEntities.size(); object++) {
			if(mapEntities.get(object).intersects(this)) {
				carefulMoveNeeded = true;
			}
		}
		
		//Step by step move if there is a collision with terrain
		if(carefulMoveNeeded) {
			//Careful move in Y direction
			if(totalDy > 0) {
				for(; y < oldY + (int)Math.ceil(totalDy); y++) {
					//Check collisions
					for(int object = 0; object < mapEntities.size(); object++) {
						if(mapEntities.get(object).intersects(this)) {
							System.out.println("Hit something below me");
							y--; //End loop, moved as far as needed
							oldY = y - (int)Math.ceil(totalDy) - 1.0f;
							onGround = true;
							dy = 0.0f;
							ddy = 0.0f;
						}
					}
				}
			}
			else {
				for(; y > oldY + (int)Math.floor(totalDy); y--) {
					//Check collisions
					for(int object = 0; object < mapEntities.size(); object++) {
						if(mapEntities.get(object).intersects(this)) {
							System.out.println("Hit something above me");
							y++; //End loop, moved as far as needed
							oldY = y - (int)Math.ceil(totalDy) + 1.0f;
							onCeiling = true;
							dy = 0.0f;
						}
					}
				}
			}
			
			//Careful move in X direction
			if(totalDx > 0) {
				for(; x < oldX + (int)Math.ceil(totalDx); x++) {
					//Check collisions
					for(int object = 0; object < mapEntities.size(); object++) {
						if(mapEntities.get(object).intersects(this)) {
							System.out.println("Hit something to my right");
							x--; //End loop, moved as far as needed
							oldX = x - (int)Math.ceil(totalDx) - 1.0f;
							onRightWall = true;
							dx = 0.0f;
						}
					}
				}
			}
			else {
				for(; x > oldX + (int)Math.floor(totalDx); x--) {
					//Check collisions
					for(int object = 0; object < mapEntities.size(); object++) {
						if(mapEntities.get(object).intersects(this)) {
							System.out.println("Hit something to my left");
							x++; //End loop, moved as far as needed
							oldX = x - (int)Math.ceil(totalDx) + 1.0f;
							onLeftWall = true;
							dx = 0.0f;
						}
					}
				}
			}
		}
	}
}
