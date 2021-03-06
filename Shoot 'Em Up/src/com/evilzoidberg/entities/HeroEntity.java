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
import com.evilzoidberg.utility.Ability;
import com.evilzoidberg.utility.Controller;

@SuppressWarnings("serial")
public abstract class HeroEntity extends MoveableEntity {
	public MovementState state = MovementState.IDLE;
	Controller controller;
	boolean facingRight = true, canMove = true, invulnerable = false, hasWon;
	float walkSpeed = 500.0f;
	float aerialDriftAcceleration = 4000.0f;
	float jumpVelocity = -1600.0f; //Jumps go up, so is negative
	int currentHealth = 10, maxHealth = 10;
	int healthBarLength = Settings.TileSize;
	int healthBarHeight = 8;
	Animation walkAnimation, idleAnimation, airIdleAnimation;
	Ability shootAbility, ability1, ability2, tauntAbility;

	public HeroEntity(Image image, int playerNumber, float x, float y, int width, int height, float offsetX, float offsetY) {
		super(image, x, y, width, height, offsetX, offsetY);
		controller = new Controller(playerNumber);
		if(playerNumber == 2) {
			facingRight = false;
		}
		hasWon = false;
	}

	public HeroEntity(Animation animation, int playerNumber, float x, float y, int width, int height, float offsetX, float offsetY) {
		super(animation, x, y, width, height, offsetX, offsetY);
		controller = new Controller(playerNumber);
		if(playerNumber == 2) {
			facingRight = false;
		}
		hasWon = false;
	}

	public void update(Input in, int delta, ArrayList<Entity> mapEntities, ArrayList<ProjectileEntity> projectiles) {
		/**
		 * Reads keys set in settings to check for inputs and adjusts velocity and acceleration based on what keys
		 * are being pressed. Then updates physics as any other MoveableEntity would.
		 */
		updateByState();
		
		//Update Abilities
		shootAbility.update(delta);
		tauntAbility.update(delta);
		if(ability1 != null) {
			ability1.update(delta);
		}
		if(ability2 != null) {
			ability2.update(delta);
		}
		
		//Update whether still shooting
		if(state == MovementState.SHOOTING) {
			if(shootAbility.attemptThreshold()) {
				addProjectile(projectiles);
			}
			if(onGround) {
				//Stops weird sliding when landing while shooting
				dx = 0.0f;
			}
			if(!shootAbility.running()) {
				state = MovementState.IDLE;
				canMove = true;
			}
		}
		
		//Update whether still taunting
		if(state == MovementState.TAUNTING) {
			if(onGround) {
				//Stops weird sliding when landing while taunting
				dx = 0.0f;
			}
			if(!tauntAbility.running()) {
				state = MovementState.IDLE;
				canMove = true;
			}
		}
		
		if(canMove) {
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
					state = MovementState.IDLE;
				}
				else if(controller.isLeft(in) && !controller.isRight(in)) {
					ddx = aerialDriftAcceleration * -1.0f;
					facingRight = false;
					state = MovementState.IDLE;
				}
				else {
					ddx = 0.0f;
					state = MovementState.IDLE;
				}
			}
			
			//Taunting controls
			if(controller.isTaunt(in) && tauntAbility.attemptToUse(this)) {
				state = MovementState.TAUNTING;
			}
			
			//Shooting controls
			if(controller.isShoot(in) && shootAbility.attemptToUse(this)) {
				state = MovementState.SHOOTING;
				if(onGround) {
					dx = 0.0f;
				}
			}
			
			//Jump
			if(onGround && controller.isUp(in)) {
				dy = jumpVelocity;
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
		
		updateByState();
	}
	
	public void updateByState() {
		/**
		 * Sets the current animation based on the state of the character if needed
		 * and updates the canMove variable based on the state
		 */
		switch(state) {
		case IDLE:
			if(onGround) {
				currentAnimation = idleAnimation;
			}
			else {
				currentAnimation = airIdleAnimation;
			}
			canMove = true;
			break;
			
		case WALKING:
			currentAnimation = walkAnimation;
			canMove = true;
			break;
			
		case SHOOTING:
			canMove = false;
			break;
			
		case TAUNTING:
			canMove = false;
			break;
			
		case ABILITY_1:
			canMove = false;
			break;
			
		case ABILITY_2:
			canMove = false;
			break;
			
		case DEAD:
			canMove = false;
			break;
			
		case JUMPING:
			canMove = true;
			break;
			
		case MISC_1:
			break;
			
		case MISC_2:
			break;
		}
	}
	
	public boolean doVictoryMotion() {
		/**
		 * Puts the character in to the taunt animation, makes them
		 * invulnerable so they don't die after they've won, and returns
		 * true when the animation has been completed. False otherwise
		 */
		if(!hasWon) {
			tauntAbility.attemptToUse(this);
		}
		hasWon = true;
		state = MovementState.TAUNTING;
		invulnerable = true;
		
		return !tauntAbility.running();
		
	}
	
	public void damage(int damage) {
		if(!invulnerable) {
			currentHealth -= damage;
		}
		
		if(currentHealth <= 0) {
			state = MovementState.DEAD;
		}
	}
	
	public abstract void addProjectile(ArrayList<ProjectileEntity> projectiles);
	
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
		else if(heroNumber == 3) {
			return new TimePiece(playerNumber, startX, startY);
		}
		return null;
	}
}
