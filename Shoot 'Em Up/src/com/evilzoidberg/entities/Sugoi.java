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
	int timeSinceShootingStarted = 0;
	int minTimeForShootAnimation = 500;
	int timeSinceLastOnGround = 0;
	int minAirTimeBeforeDoubleJump = 100;
	Cooldown shurikenCooldown = new Cooldown(500);
	static Animation idleAnimation = MediaLoader.getAnimation(Settings.SugoiIdleAnimationPath, 64, 64);
	static Animation shootAnimation = MediaLoader.getAnimation(Settings.SugoiShootAnimationPath, 64, 64);

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
		super.update(in, delta, mapEntities, projectiles);
		
		//Update has double jump
		if(onGround) {
			timeSinceLastOnGround = 0;
			hasDoubleJump = true;
		}
		else {
			timeSinceLastOnGround += delta;
		}
		
		//Update whether still shooting
		if(actionState == SugoiState.SHOOTING) {
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
		
		//Update Cooldowns
		shurikenCooldown.update(delta);
		
		if(canMove) {
			//Wall cling controls
			if(!onGround && in.isKeyPressed(right) && onRightWall) {
				actionState = SugoiState.WALL_CLINGING;
				dy = 0.0f;
				affectedByGravity = false;
				hasDoubleJump = true;
			}
			else if(!onGround && in.isKeyPressed(left) && onLeftWall) {
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
		
			//Shooting controls
			if(in.isKeyDown(shoot) && shurikenCooldown.attemptToUse()) {
				int projectileX = (int)(x + width);
				if(facingRight) {
					projectileX = (int)(x - 5.0f);
				}
				int projectileY = (int)(y + (height / 2.0f)) - 3;
				projectiles.add(new Shuriken(projectileX, projectileY, facingRight, this));
				currentAnimation = shootAnimation;
				actionState = SugoiState.SHOOTING;
				canMove = false;
			}
		}
	}
}
