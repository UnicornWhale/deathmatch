package com.evilzoidberg.entities;

import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Input;

import com.evilzoidberg.Settings;
import com.evilzoidberg.utility.Cooldown;
import com.evilzoidberg.utility.MediaLoader;

@SuppressWarnings("serial")
public class Sugoi extends HeroEntity {
	boolean hasDoubleJump = true;
	int timeSinceLastOnGround = 0;
	int minAirTimeBeforeDoubleJump = 100;
	Cooldown shurikenCooldown = new Cooldown(500);
	static Animation idleAnimation = MediaLoader.getAnimation(Settings.SugoiIdleAnimationPath, 64, 64);
	static Animation shootAnimation = MediaLoader.getAnimation(Settings.SugoiShootAnimationPath, 64, 64);

	public Sugoi(int playerNumber, float x, float y) {
		super(idleAnimation, playerNumber, x, y, 24, 54, -20.0f, -5.0f);
		maxHealth = 3;
		currentHealth = 3;
		idleAnimation.setLooping(true);
		shootAnimation.setLooping(false);
	}
	
	@Override
	public void update(Input in, int delta, ArrayList<Entity> mapEntities, ArrayList<ProjectileEntity> projectiles) {
		/**
		 * Updates as a HeroEntity then responds to any hero specific inputs.
		 */
		super.update(in, delta, mapEntities, projectiles);
		
		if(currentAnimation.isStopped()) {
			currentAnimation = idleAnimation;
		}
		
		if(onGround) {
			timeSinceLastOnGround = 0;
			hasDoubleJump = true;
		}
		else {
			timeSinceLastOnGround += delta;
		}
		
		//Update Cooldowns
		shurikenCooldown.update(delta);
		
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
		}
	}
}
