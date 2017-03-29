package com.evilzoidberg.entities;

import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;

import com.evilzoidberg.Settings;
import com.evilzoidberg.entities.projectiles.ProjectileEntity;
import com.evilzoidberg.entities.states.MovementState;
import com.evilzoidberg.utility.Controller;

@SuppressWarnings("serial")
public class HeroEntity extends MoveableEntity {
	public MovementState state = MovementState.IDLE;
	Controller controller;
	boolean facingRight = true;
	boolean canMove = true;
	float walkSpeed = 500.0f;
	float aerialDriftAcceleration = 5000.0f;
	float jumpVelocity = -1600.0f; //Jumps go up, so is negative
	int currentHealth = 10, maxHealth = 10;
	int healthBarLength = Settings.TileSize;
	int healthBarHeight = 8;

	public HeroEntity(Image image, int playerNumber, float x, float y, int width, int height, float offsetX, float offsetY) {
		super(image, x, y, width, height, offsetX, offsetY);
		controller = new Controller(playerNumber);
		if(playerNumber == 2) {
			facingRight = false;
		}
	}

	public HeroEntity(Animation animation, int playerNumber, float x, float y, int width, int height, float offsetX, float offsetY) {
		super(animation, x, y, width, height, offsetX, offsetY);
		controller = new Controller(playerNumber);
		if(playerNumber == 2) {
			facingRight = false;
		}
	}

	public void update(Input in, int delta, ArrayList<Entity> mapEntities, ArrayList<ProjectileEntity> projectiles) {
		/**
		 * Reads keys set in settings to check for inputs and adjusts velocity and acceleration based on what keys
		 * are being pressed. Then updates physics as any other MoveableEntity would.
		 */
		if(canMove) {
			//Jump
			if(onGround && controller.isUp(in)) {
				dy = jumpVelocity;
			}
			
			if(onGround) {
				//Grounded controls
				ddx = 0.0f;
				if(controller.isRight(in) && !controller.isLeft(in)) {
					dx = walkSpeed;
					facingRight = true;
					state = MovementState.WALKING;
				}
				else if(controller.isLeft(in) && !controller.isRight(in)) {
					dx = walkSpeed * -1.0f;
					facingRight = false;
					state = MovementState.WALKING;
				}
				else {
					dx = 0.0f;
					state = MovementState.IDLE;
				}
			}
			else{
				//Aerial controls
				if(controller.isRight(in) && !controller.isLeft(in)) {
					ddx = aerialDriftAcceleration;
					facingRight = true;
				}
				else if(controller.isLeft(in) && !controller.isRight(in)) {
					ddx = aerialDriftAcceleration * -1.0f;
					facingRight = false;
				}
				else {
					ddx = 0.0f;
				}
				
				if(dy >= 0) {
					state = MovementState.FALLING;
				}
				else {
					state = MovementState.RISING;
				}
			}
		}
		
		//Use MoveableEntity logic for movement
		super.update(delta, mapEntities);
		
		//Check projectile collisions
		for(int i = 0; i < projectiles.size(); i++) {
			if(projectiles.get(i).intersects(this)) {
				projectiles.get(i).onHit(this);
			}
		}
	}
	
	public void damage(int damage) {
		currentHealth -= damage;
		
		if(currentHealth <= 0) {
			state = MovementState.DEAD;
		}
	}
	
	@Override
	public void paint(Graphics g) {
		paint(g, facingRight);
		
		//Draw Health bar
		int currentHealthBarLength = (int)(((double)currentHealth / maxHealth) * (double)healthBarLength);
		g.setColor(Color.red);
		g.fillRect((x + (width / 2)) - (healthBarLength / 2), y - (healthBarHeight * 2), healthBarLength, healthBarHeight);
		g.setColor(Color.green);
		g.fillRect((x + (width / 2)) - (healthBarLength / 2), y - (healthBarHeight * 2), currentHealthBarLength, healthBarHeight);
	}
	
	public static HeroEntity getHeroByNumber(int playerNumber, int heroNumber) {
		/**
		 * Returns a hero object based on the hero number passed to this. If the hero number isn't
		 * valid, it returns null.
		 */
		int startX = Settings.Player1StartX;
		int startY = Settings.Player1StartY;
		if(playerNumber == 2) {
			startX = Settings.Player2StartX;
			startY = Settings.Player2StartY;
		}
		
		if(heroNumber == 1) {
			return new Sugoi(playerNumber, startX, startY);
		}
		else if(heroNumber == 2) {
			return new Brawn(playerNumber, startX, startY);
		}
		return null;
	}
}
