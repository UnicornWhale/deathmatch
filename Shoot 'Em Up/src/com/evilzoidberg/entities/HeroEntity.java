package com.evilzoidberg.entities;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;

import com.evilzoidberg.Settings;

@SuppressWarnings("serial")
public class HeroEntity extends MoveableEntity {
	boolean facingRight = true;
	float walkSpeed = 800.0f;
	float aerialDriftAcceleration = 5000.0f;
	float jumpVelocity = -2500.0f; //Jumps go up, so is negative
	float currentHealth = 100, maxHealth = 100;
	int up, down, left, right, shoot;

	public HeroEntity(Image image, int playerNumber, float x, float y, int width, int height, float offsetX, float offsetY) {
		super(image, x, y, width, height, offsetX, offsetY);
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

	public HeroEntity(int playerNumber, float x, float y, int width, int height, float offsetX, float offsetY) {
		super(x, y, width, height, offsetX, offsetY);
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

	public void update(Input in, int delta, ArrayList<Entity> mapEntities) {
		/**
		 * Reads keys set in settings to check for inputs and adjusts velocity and acceleration based on what keys
		 * are being pressed. Then updates physics as any other MoveableEntity would.
		 */
		//Jump
		if(onGround && in.isKeyPressed(up)) {
			dy = jumpVelocity;
		}
		
		if(onGround) {
			//Grounded controls
			ddx = 0.0f;
			if(in.isKeyDown(right) && !in.isKeyDown(left)) {
				dx = walkSpeed;
				facingRight = true;
			}
			else if(in.isKeyDown(left) && !in.isKeyDown(right)) {
				dx = walkSpeed * -1.0f;
				facingRight = false;
			}
			else {
				dx = 0.0f;
			}
		}
		else{
			//Aerial controls
			if(in.isKeyDown(right) && !in.isKeyDown(left)) {
				ddx = aerialDriftAcceleration;
			}
			else if(in.isKeyDown(left) && !in.isKeyDown(right)) {
				ddx = aerialDriftAcceleration * -1.0f;
			}
			else {
				ddx = 0.0f;
			}
		}
		
		//Use MoveableEntity logic for movement
		updatePhysics(delta, mapEntities);
	}
	
	@Override
	public void paint(Graphics g) {
		paint(g, facingRight);
	}
}
