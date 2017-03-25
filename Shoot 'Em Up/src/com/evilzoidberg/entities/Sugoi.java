package com.evilzoidberg.entities;

import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Input;

import com.evilzoidberg.Settings;
import com.evilzoidberg.entities.projectiles.ProjectileEntity;
import com.evilzoidberg.entities.projectiles.Shuriken;
import com.evilzoidberg.entities.states.SugoiState;
import com.evilzoidberg.utility.Cooldown;
import com.evilzoidberg.utility.MediaLoader;

@SuppressWarnings("serial")
public class Sugoi extends HeroEntity {
	SugoiState actionState = SugoiState.IDLE;
	boolean hasDoubleJump = true;
	int dashSpeed = 1000, dashDx, dashDy;
	int timeSinceShootingStarted = 0, minTimeForShootAnimation = 250;
	int timeSinceLastOnGround = 0, minAirTimeBeforeDoubleJump = 100;
	int timeSinceDashingStarted = 0, minTimeForDash = 200;
	Cooldown shurikenCooldown = new Cooldown(350);
	static Animation idleAnimation = MediaLoader.getAnimation(Settings.SugoiIdleAnimationPath, 64, 64);
	static Animation shootAnimation = MediaLoader.getAnimation(Settings.SugoiShootAnimationPath, 64, 64);
	static Animation dashAnimation = MediaLoader.getAnimation(Settings.SugoiDashAnimationPath, 64, 64);

	public Sugoi(int playerNumber, float x, float y) {
		super(idleAnimation, playerNumber, x, y, 24, 54, -20.0f, -5.0f);
		maxHealth = 3;
		currentHealth = 3;
	}
	
	@Override
	public void update(Input in, int delta, ArrayList<Entity> mapEntities, ArrayList<ProjectileEntity> projectiles) {
		/**
		 * Updates as a HeroEntity then responds to any hero specific inputs.
		 */
		
		//Update whether still dashing
		if(actionState == SugoiState.DASHING) {
			if(timeSinceDashingStarted >= minTimeForDash) {
				canMove = true;
				timeSinceDashingStarted = 0;
				actionState = SugoiState.IDLE;
				currentAnimation = idleAnimation;
			}
			else {
				timeSinceDashingStarted += delta;
				dx = dashDx;
				dy = dashDy;
			}
		}
		
		super.update(in, delta, mapEntities, projectiles);
		
		//Update whether still shooting
		if(actionState == SugoiState.SHOOTING) {
			if(onGround) {
				//Stops weird sliding when landing while shooting
				dx = 0.0f;
			}
			if(timeSinceShootingStarted >= minTimeForShootAnimation) {
				canMove = true;
				timeSinceShootingStarted = 0;
				actionState = SugoiState.IDLE;
				currentAnimation = idleAnimation;
			}
			else {
				timeSinceShootingStarted += delta;
			}
		}
		
		//Update has double jump
		if(onGround) {
			timeSinceLastOnGround = 0;
			hasDoubleJump = true;
		}
		else {
			timeSinceLastOnGround += delta;
		}
		
		//Update Cooldowns
		shurikenCooldown.update(delta);
		
		if(canMove) {
			//Wall cling controls
			if(!onGround && in.isKeyDown(right) && onRightWall && dy > 0.0f) {
				actionState = SugoiState.WALL_CLINGING;
				dy = 0.0f;
				affectedByGravity = false;
				hasDoubleJump = true;
			}
			else if(!onGround && in.isKeyDown(left) && onLeftWall && dy > 0.0f) {
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
			if(in.isKeyPressed(up) && !onGround && hasDoubleJump && timeSinceLastOnGround > minAirTimeBeforeDoubleJump) {
				dy = jumpVelocity;
				hasDoubleJump = false;
				if(in.isKeyPressed(right) && !in.isKeyPressed(left)) {
					dx = walkSpeed;
				}
				if(in.isKeyPressed(left) && !in.isKeyPressed(right)) {
					dx = walkSpeed * -1.0f;
				}
			}
			
			//Dash controls
			if(in.isKeyPressed(ability1)) {
				actionState = SugoiState.DASHING;
				currentAnimation = dashAnimation;
				dashAnimation.restart();
				canMove = false;
				
				if(in.isKeyDown(right) && !in.isKeyDown(left)) {
					dashDx = dashSpeed;
				}
				else if(in.isKeyDown(left) && !in.isKeyDown(right)) {
					dashDx = dashSpeed * -1;
				}
				else {
					dashDx = 0;
				}
				
				if(in.isKeyDown(up) && !in.isKeyDown(down)) {
					dashDy = dashSpeed * -1;
				}
				else if(in.isKeyDown(down) && !in.isKeyDown(up)) {
					dashDy = dashSpeed;
				}
				else {
					dashDy = 0;
				}
				
				dx = dashDx;
				dy = dashDy;
			}
		
			//Shooting controls
			if(in.isKeyPressed(shoot) && shurikenCooldown.attemptToUse()) {
				int projectileX = (int)(x - 5.0f);
				if(facingRight) {
					projectileX = (int)(x + width);
				}
				int projectileY = (int)(y + (height / 2.0f)) - 3;
				projectiles.add(new Shuriken(projectileX, projectileY, facingRight, this));
				actionState = SugoiState.SHOOTING;
				currentAnimation = shootAnimation;
				shootAnimation.restart();
				canMove = false;
				if(onGround) {
					dx = 0.0f;
				}
			}
		}
	}
}
