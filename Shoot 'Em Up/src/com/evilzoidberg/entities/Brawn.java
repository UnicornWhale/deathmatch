package com.evilzoidberg.entities;

import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Input;

import com.evilzoidberg.Settings;
import com.evilzoidberg.entities.projectiles.PlasmaBlast;
import com.evilzoidberg.entities.projectiles.ProjectileEntity;
import com.evilzoidberg.entities.states.BrawnState;
import com.evilzoidberg.entities.states.MovementState;
import com.evilzoidberg.utility.Ability;
import com.evilzoidberg.utility.Cooldown;
import com.evilzoidberg.utility.MediaLoader;

@SuppressWarnings("serial")
public class Brawn extends HeroEntity {
	BrawnState actionState = BrawnState.IDLE;
	Cooldown bogLifeTimer = new Cooldown(5000);
	Ability shootAbility = new Ability(MediaLoader.getAnimation(Settings.BrawnShootAnimationPath, 64, 64), 200);
	Ability flexAbility = new Ability(MediaLoader.getAnimation(Settings.BrawnFlexAnimationPath, 64, 64), 200);
	Ability bogAbility = new Ability(MediaLoader.getAnimation(Settings.BrawnBoGAnimationPath, 64, 64), 300);
	static Animation idleAnimation = MediaLoader.getAnimation(Settings.BrawnIdleAnimationPath, 64, 64);
	static Animation BoGIdleAnimation = MediaLoader.getAnimation(Settings.BrawnBoGIdleAnimationPath, 64, 64);
	static Animation BoGShootAnimation = MediaLoader.getAnimation(Settings.BrawnBoGShootAnimationPath, 64, 64);
	
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
		
		//Update abilities
		flexAbility.update(delta);
		shootAbility.update(delta);
		bogAbility.update(delta);
		
		//Update whether still ripping shirt off
		if(actionState == BrawnState.BLAZE_OF_GLORY) {
			if(!bogAbility.running()) {
				actionState = BrawnState.IDLE;
			}
		}
		
		//Update whether still in BoG life
		if(currentHealth <= 0) {
			bogLifeTimer.update(delta);
		}
		
		//Update whether still alive
		if(!bogLifeTimer.running() && currentHealth <= 0) {
			state = MovementState.DEAD;
		}
		
		//Update whether still shooting
		if(actionState == BrawnState.SHOOTING) {
			if(onGround) {
				//Stops weird sliding when landing while shooting
				dx = 0.0f;
			}
			if(!shootAbility.running()) {
				actionState = BrawnState.IDLE;
			}
		}
		
		//Update whether still flexing
		if(actionState == BrawnState.FLEXING) {
			if(!flexAbility.running()) {
				actionState = BrawnState.IDLE;
			}
		}
		
		if(canMove) {
			//Shooting controls
			if(in.isKeyPressed(shoot) && shootAbility.attemptToUse(this)) {
				int projectileX = (int)(x - 10.0f);
				if(facingRight) {
					projectileX = (int)(x + width);
				}
				int projectileY = (int)(y + (height / 2.0f)) - 3;
				projectiles.add(new PlasmaBlast(projectileX, projectileY, facingRight, this));
				actionState = BrawnState.SHOOTING;
				BoGShootAnimation.restart();
				if(onGround) {
					dx = 0.0f;
				}
			}
			
			//Flexing controls
			if(in.isKeyPressed(ability1) && currentHealth > 0 && flexAbility.attemptToUse(this)) {
				if(onGround) {
					dx = 0.0f;
				}
				actionState = BrawnState.FLEXING;
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
			if(currentHealth == 0) {
				currentAnimation = BoGIdleAnimation;
			}
			else {
				currentAnimation = idleAnimation;
			}
			canMove = true;
			break;
		case SHOOTING:
			canMove = false;
			break;
		case FLEXING:
			canMove = false;
			break;
		case BLAZE_OF_GLORY:
			canMove = false;
			break;
		default:
			currentAnimation = idleAnimation;
			canMove = true;
			break;
		}
	}
	
	@Override
	public void damage(int damage) {
		//Ignore damage while flexing
		if(actionState != BrawnState.FLEXING && currentHealth > 0) {
			currentHealth -= damage;
		}
		System.out.println("Health at " + currentHealth);
		if(currentHealth == 0 && !bogLifeTimer.running()) {
			actionState = BrawnState.BLAZE_OF_GLORY;
			canMove = false;
			bogAbility.attemptToUse(this);
			bogLifeTimer.attemptToUse();
			shootAbility.animation = BoGShootAnimation;
		}
	}
}
