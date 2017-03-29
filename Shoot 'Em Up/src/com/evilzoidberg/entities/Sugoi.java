package com.evilzoidberg.entities;

import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Input;

import com.evilzoidberg.Settings;
import com.evilzoidberg.entities.projectiles.ProjectileEntity;
import com.evilzoidberg.entities.projectiles.Shuriken;
import com.evilzoidberg.entities.states.SugoiState;
import com.evilzoidberg.utility.Ability;
import com.evilzoidberg.utility.MediaLoader;

@SuppressWarnings("serial")
public class Sugoi extends HeroEntity {
	SugoiState actionState = SugoiState.IDLE;
	boolean hasDoubleJump = true;
	Ability shootAbility = new Ability(MediaLoader.getAnimation(Settings.SugoiShootAnimationPath, 80, 80), 300);
	static Animation idleAnimation = MediaLoader.getAnimation(Settings.SugoiIdleAnimationPath, 80, 80);
	static Animation airIdleAnimation = MediaLoader.getAnimation(Settings.SugoiAirIdleAnimationPath, 80, 80);

	public Sugoi(int playerNumber, float x, float y) {
		super(idleAnimation, playerNumber, x, y, 34, 75, -24.0f, -2.0f);
		maxHealth = 3;
		currentHealth = 3;
	}
	
	@Override
	public void update(Input in, int delta, ArrayList<Entity> mapEntities, ArrayList<ProjectileEntity> projectiles) {
		/**
		 * Updates as a HeroEntity then responds to any hero specific inputs.
		 */
		
		//Update whether still dashing
//		if(actionState == SugoiState.DASHING) {
//			if(timeSinceDashingStarted >= minTimeForDash) {
//				canMove = true;
//				timeSinceDashingStarted = 0;
//				actionState = SugoiState.IDLE;
//				currentAnimation = idleAnimation;
//			}
//			else {
//				timeSinceDashingStarted += delta;
//				dx = dashDx;
//				dy = dashDy;
//			}
//		}
		
		super.update(in, delta, mapEntities, projectiles);
		
		//Update Abilities
		shootAbility.update(delta);
		
		//Update whether still shooting
		if(actionState == SugoiState.SHOOTING) {
			if(onGround) {
				//Stops weird sliding when landing while shooting
				dx = 0.0f;
			}
			if(!shootAbility.running()) {
				actionState = SugoiState.IDLE;
			}
		}
		
		//Update has double jump
		if(onGround) {
			hasDoubleJump = true;
		}
		
		if(canMove) {
			//Wall cling controls
			if(!onGround && controller.isRight(in) && onRightWall && dy > 0.0f) {
				actionState = SugoiState.WALL_CLINGING;
				dy = 0.0f;
				affectedByGravity = false;
				hasDoubleJump = true;
			}
			else if(!onGround && controller.isLeft(in) && onLeftWall && dy > 0.0f) {
				actionState = SugoiState.WALL_CLINGING;
				dy = 0.0f;
				affectedByGravity = false;
				hasDoubleJump = true;
			}
			else {
				affectedByGravity = true;
				actionState = SugoiState.IDLE;
			}
			
			//Double jump controls
			if(controller.isUp(in) && !onGround && hasDoubleJump) {
				dy = jumpVelocity;
				hasDoubleJump = false;
				if(controller.isRight(in) && !controller.isLeft(in)) {
					dx = walkSpeed;
				}
				if(controller.isLeft(in) && !controller.isRight(in)) {
					dx = walkSpeed * -1.0f;
				}
			}
			
			//Dash controls
//			if(in.isKeyPressed(ability1)) {
//				actionState = SugoiState.DASHING;
//				currentAnimation = dashAnimation;
//				dashAnimation.restart();
//				canMove = false;
//				
//				if(in.isKeyDown(right) && !in.isKeyDown(left)) {
//					dashDx = dashSpeed;
//				}
//				else if(in.isKeyDown(left) && !in.isKeyDown(right)) {
//					dashDx = dashSpeed * -1;
//				}
//				else {
//					dashDx = 0;
//				}
//				
//				if(in.isKeyDown(up) && !in.isKeyDown(down)) {
//					dashDy = dashSpeed * -1;
//				}
//				else if(in.isKeyDown(down) && !in.isKeyDown(up)) {
//					dashDy = dashSpeed;
//				}
//				else {
//					dashDy = 0;
//				}
//				
//				dx = dashDx;
//				dy = dashDy;
//			}
		
			//Shooting controls
			if(controller.isShoot(in) && shootAbility.attemptToUse(this)) {
				int projectileX = (int)(x - 5.0f);
				if(facingRight) {
					projectileX = (int)(x + width);
				}
				int projectileY = (int)(y + (height / 2.0f)) - 3;
				projectiles.add(new Shuriken(projectileX, projectileY, facingRight, this));
				actionState = SugoiState.SHOOTING;
				if(onGround) {
					dx = 0.0f;
				}
			}
		}
		
		updateByState();
	}
	
	public void updateByState() {
		/**
		 * Sets the current animation based on the state of the character if needed
		 * and updates the canMove variable based on the state
		 */
		switch(actionState) {
		case IDLE:
			if(onGround) {
				currentAnimation = idleAnimation;
			}
			else {
				currentAnimation = airIdleAnimation;
			}
			canMove = true;
			break;
		case SHOOTING:
			canMove = false;
			break;
		case WALL_CLINGING:
			currentAnimation = idleAnimation;
			canMove = true;
			break;
		default:
			currentAnimation = idleAnimation;
			canMove = true;
			break;
		}
	}
}
