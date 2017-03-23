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

@SuppressWarnings("serial")
public class HeroEntity extends MoveableEntity {
	public MovementState state = MovementState.IDLE;
	boolean facingRight = true;
	boolean canMove = true;
	float walkSpeed = 500.0f;
	float aerialDriftAcceleration = 5000.0f;
	float jumpVelocity = -1600.0f; //Jumps go up, so is negative
	float currentHealth = 10, maxHealth = 10;
	int up, down, left, right, shoot, ability1;
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
			ability1 = Settings.Player1Ability1;
		}
		else {
			up = Settings.Player2Up;
			down = Settings.Player2Down;
			left = Settings.Player2Left;
			right = Settings.Player2Right;
			shoot = Settings.Player2Shoot;
			ability1 = Settings.Player2Ability1;
			facingRight = false;
		}
	}

	public HeroEntity(Animation animation, int playerNumber, float x, float y, int width, int height, float offsetX, float offsetY) {
		super(animation, x, y, width, height, offsetX, offsetY);
		if(playerNumber == 1) {
			up = Settings.Player1Up;
			down = Settings.Player1Down;
			left = Settings.Player1Left;
			right = Settings.Player1Right;
			shoot = Settings.Player1Shoot;
			ability1 = Settings.Player1Ability1;
		}
		else {
			up = Settings.Player2Up;
			down = Settings.Player2Down;
			left = Settings.Player2Left;
			right = Settings.Player2Right;
			shoot = Settings.Player2Shoot;
			ability1 = Settings.Player2Ability1;
		}
	}

	public void update(Input in, int delta, ArrayList<Entity> mapEntities, ArrayList<ProjectileEntity> projectiles) {
		/**
		 * Reads keys set in settings to check for inputs and adjusts velocity and acceleration based on what keys
		 * are being pressed. Then updates physics as any other MoveableEntity would.
		 */
		if(canMove) {
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
					state = MovementState.WALKING;
				}
				else if(in.isKeyDown(left) && !in.isKeyDown(right)) {
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
