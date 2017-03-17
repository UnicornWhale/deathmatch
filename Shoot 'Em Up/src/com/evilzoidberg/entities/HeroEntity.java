package com.evilzoidberg.entities;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;

import com.evilzoidberg.Settings;
import com.evilzoidberg.utility.ImageLoader;

@SuppressWarnings("serial")
public class HeroEntity extends MoveableEntity {
	boolean facingRight = true;
	float walkSpeed = 800.0f;
	float aerialDriftAcceleration = 5000.0f;
	float jumpVelocity = -2500.0f; //Jumps go up, so is negative
	float currentHealth = 100, maxHealth = 100;
	int up, down, left, right, shoot;
	int healthBarLength = Settings.TileSize;
	int healthBarHeight = 8;

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

	public void update(Input in, int delta, ArrayList<Entity> mapEntities, ArrayList<ProjectileEntity> projectiles) {
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
				facingRight = true;
			}
			else if(in.isKeyDown(left) && !in.isKeyDown(right)) {
				ddx = aerialDriftAcceleration * -1.0f;
				facingRight = false;
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
		
		//Draw Health bar
		int currentHealthBarLength = (int)(((double)currentHealth / 100.0) * (double)healthBarLength);
		g.setColor(Color.red);
		g.fillRect((x + (width / 2)) - (healthBarLength / 2), y - (healthBarHeight * 2), healthBarLength, healthBarHeight);
		g.setColor(Color.green);
		g.fillRect((x + (width / 2)) - (healthBarLength / 2), y - (healthBarHeight * 2), currentHealthBarLength, healthBarHeight);
	}
	
	public static HeroEntity getHeroByNumber(int playerNumber, int heroNumber) {
		int startX = Settings.Player1StartX;
		int startY = Settings.Player1StartY;
		if(playerNumber == 2) {
			startX = Settings.Player2StartX;
			startY = Settings.Player2StartY;
		}
		
		if(heroNumber == 1) {
			return new Sugoi(playerNumber, startX, startY);
		}
		return new HeroEntity(ImageLoader.getImage(Settings.TestHeroImagePath), playerNumber, startX, startY, 24, 50, -13.0f, -7.0f);
	}
}
