package com.evilzoidberg.entities;

import java.util.ArrayList;

import org.newdawn.slick.Image;

import com.evilzoidberg.Settings;

@SuppressWarnings("serial")
public class MoveableEntity extends Entity {
	float dx = 0, dy = 0; //Velocity
	float ddx = 0, ddy = 0; //Acceleration
	boolean onGround = false;
	boolean onCeiling = false;
	boolean onLeftWall = false;
	boolean onRightWall = false;
	boolean affectedByGravity = true;

	public MoveableEntity(Image image, float x, float y, float offsetX, float offsetY) {
		super(image, x, y, offsetX, offsetY);
	}

	public MoveableEntity(float x, float y, float offsetX, float offsetY) {
		super(x, y, offsetX, offsetY);
	}

	public void updatePhysics(int deltaInt, ArrayList<Entity> mapEntities) {
		//Turn delta to a fraction of a second
		float delta = ((float)deltaInt) / 1000.0f;
		
		if(!onGround) {
			if(affectedByGravity) {
				ddy = Settings.Gravity;
			}
		}
		else {
			ddy = 0.0f;
		}

		//Calculate distance to move
		float totalDy = dy * delta;
		float totalDx = dx * delta;
		
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
					float oldX = x;
					x = (float)Math.floor(x);
					if(x == oldX) {
						x--; //Cover whole numbers
					}
					totalDx = 0.0f;
					dx = 0.0f;
					onRightWall = true;
				}
				else {
					totalDx--;
					onLeftWall = false;
				}
			}
			else if(totalDx < 0.0f) {
				x--;
				if(collidesWithSomething(mapEntities)) {
					float oldX = x;
					x = (float)Math.ceil(x);
					if(x == oldX) {
						x++; //Cover whole numbers
					}
					totalDx = 0.0f;
					dx = 0.0f;
					onLeftWall = true;
				}
				else {
					totalDx++;
					onRightWall = false;
				}
			}
			
			//Vertical movement
			if(totalDy < 0.9f && totalDy > -0.9f){
				totalDy = 0.0f;
			}
			else if(totalDy > 0.0f) {
				y++;
				if(collidesWithSomething(mapEntities)) {
					float oldY = y;
					y = (float)Math.floor(y);
					if(y == oldY) {
						y--; //Cover whole numbers
					}
					totalDy = 0.0f;
					onGround = true;
				}
				else {
					totalDy--;
					onCeiling = false;
				}
			}
			else if(totalDy < 0.0f) {
				y--;
				if(collidesWithSomething(mapEntities)) {
					float oldY = y;
					y = (float)Math.ceil(y);
					if(y == oldY) {
						y++; //Cover whole numbers
					}
					totalDy = 0.0f;
					dy = 0.0f;
					onCeiling = true;
				}
				else {
					totalDy++;
					onGround = false;
				}
			}
		}
	}
	
	private boolean collidesWithSomething(ArrayList<Entity> mapEntities) {
		for(int object = 0; object < mapEntities.size(); object++) {
			if(mapEntities.get(object).intersects(this)) {
				return true;
			}
		}
		return false;
	}
}
