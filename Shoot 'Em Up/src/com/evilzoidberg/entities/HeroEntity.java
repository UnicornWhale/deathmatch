package com.evilzoidberg.entities;

import java.util.ArrayList;

import org.newdawn.slick.Image;
import org.newdawn.slick.Input;

import com.evilzoidberg.Settings;

@SuppressWarnings("serial")
public class HeroEntity extends MoveableEntity {
	float walkSpeed = 800.0f;
	float aerialDriftAcceleration = 500.0f;
	float jumpVelocity = -2300.0f; //Jumps go up, so is negative
	float currentHealth = 100, maxHealth = 100;
	int up, down, left, right, shoot;

	public HeroEntity(Image image, int playerNumber, float x, float y, float offsetX, float offsetY) {
		super(image, x, y, offsetX, offsetY);
		if(playerNumber == 1) {
			up = Settings.Player1Up;
			down = Settings.Player1Down;
			left = Settings.Player1Left;
			right = Settings.Player1Right;
			shoot = Settings.Player1Shoot;
		}
		else {
			up = Settings.Player2Up;
			down = Settings.Player2Down;
			left = Settings.Player2Left;
			right = Settings.Player2Right;
			shoot = Settings.Player2Shoot;
		}
	}

	public HeroEntity(int playerNumber, float x, float y, float offsetX, float offsetY) {
		super(x, y, offsetX, offsetY);
		if(playerNumber == 1) {
			up = Settings.Player1Up;
			down = Settings.Player1Down;
			left = Settings.Player1Left;
			right = Settings.Player1Right;
			shoot = Settings.Player1Shoot;
		}
		else {
			up = Settings.Player2Up;
			down = Settings.Player2Down;
			left = Settings.Player2Left;
			right = Settings.Player2Right;
			shoot = Settings.Player2Shoot;
		}
	}

	public void update(Input in, int delta, ArrayList<Entity> mapEntities, ArrayList<ProjectileEntity> projectiles) {
		if(onGround && in.isKeyDown(up)) {
			dy = jumpVelocity;
		}
		
		if(onGround) {
			if(in.isKeyDown(right) && !in.isKeyDown(left) && !onRightWall) {
				dx = walkSpeed;
			}
			else if(in.isKeyDown(left) && !in.isKeyDown(right) && !onLeftWall) {
				dx = walkSpeed * -1.0f;
			}
			else {
				dx = 0.0f;
			}
		}
		else{
			if(in.isKeyDown(right) && !in.isKeyDown(left) && !onRightWall) {
				ddx = aerialDriftAcceleration;
			}
			else if(in.isKeyDown(left) && !in.isKeyDown(right) && !onLeftWall) {
				ddx = aerialDriftAcceleration * -1.0f;
			}
			else {
				ddx = 0.0f;
			}
		}
		
		updatePhysics(delta, mapEntities);
	}
}
