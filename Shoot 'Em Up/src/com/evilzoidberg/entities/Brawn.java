package com.evilzoidberg.entities;

import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Input;

import com.evilzoidberg.Settings;
import com.evilzoidberg.entities.projectiles.PlasmaBlast;
import com.evilzoidberg.entities.projectiles.ProjectileEntity;
import com.evilzoidberg.entities.states.BrawnState;
import com.evilzoidberg.utility.Cooldown;
import com.evilzoidberg.utility.MediaLoader;

@SuppressWarnings("serial")
public class Brawn extends HeroEntity {
	BrawnState actionState = BrawnState.IDLE;
	int timeSinceShootingStarted = 0, minTimeForShootAnimation = 250;
	int timeSinceFlexingStarted = 0, minTimeForFlexAnimation = 200;
	Cooldown bulletCooldown = new Cooldown(200);
	static Animation idleAnimation = MediaLoader.getAnimation(Settings.BrawnIdleAnimationPath, 64, 64);
	static Animation shootAnimation = MediaLoader.getAnimation(Settings.BrawnShootAnimationPath, 64, 64);
	static Animation flexAnimation = MediaLoader.getAnimation(Settings.BrawnFlexAnimationPath, 64, 64);
	
	public Brawn(int playerNumber, float x, float y) {
		super(idleAnimation, playerNumber, x, y, 35, 60, -10.0f, 0.0f);
		maxHealth = 3;
		currentHealth = 3;
	}
	
	@Override
	public void update(Input in, int delta, ArrayList<Entity> mapEntities, ArrayList<ProjectileEntity> projectiles) {
		/**
		 * Updates as a HeroEntity then responds to any hero specific inputs.
		 */
		super.update(in, delta, mapEntities, projectiles);
		
		//Update whether still shooting
		if(actionState == BrawnState.SHOOTING) {
			if(onGround) {
				//Stops weird sliding when landing while shooting
				dx = 0.0f;
			}
			if(timeSinceShootingStarted >= minTimeForShootAnimation) {
				canMove = true;
				timeSinceShootingStarted = 0;
				actionState = BrawnState.IDLE;
				currentAnimation = idleAnimation;
			}
			else {
				timeSinceShootingStarted += delta;
			}
		}
		
		//Update whether still flexing
		if(actionState == BrawnState.FLEXING) {
			if(timeSinceFlexingStarted >= minTimeForFlexAnimation) {
				canMove = true;
				timeSinceFlexingStarted = 0;
				actionState = BrawnState.IDLE;
				currentAnimation = idleAnimation;
			}
			else {
				timeSinceFlexingStarted += delta;
			}
		}
		
		//Update cooldowns
		bulletCooldown.update(delta);
		
		if(canMove) {
			//Shooting controls
			if(in.isKeyPressed(shoot) && bulletCooldown.attemptToUse()) {
				int projectileX = (int)(x - 10.0f);
				if(facingRight) {
					projectileX = (int)(x + width);
				}
				int projectileY = (int)(y + (height / 2.0f)) - 3;
				projectiles.add(new PlasmaBlast(projectileX, projectileY, facingRight, this));
				actionState = BrawnState.SHOOTING;
				currentAnimation = shootAnimation;
				shootAnimation.restart();
				canMove = false;
				if(onGround) {
					dx = 0.0f;
				}
			}
			
			//Flexing controls
			if(in.isKeyPressed(ability1)) {
				currentAnimation = flexAnimation;
				flexAnimation.restart();
				canMove = false;
				if(onGround) {
					dx = 0.0f;
				}
				actionState = BrawnState.FLEXING;
			}
		}
	}
	
	@Override
	public void damage(int damage) {
		//Ignore damage while flexing
		if(actionState != BrawnState.FLEXING) {
			super.damage(damage);
		}
	}
}
