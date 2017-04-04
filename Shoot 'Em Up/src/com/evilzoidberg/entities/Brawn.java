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
	Cooldown bogLifeTimer;
	Ability shootAbility, flexAbility, bogAbility, bogShootAbility;
	static Animation idleAnimation, airIdleAnimation, shootAnimation, flexAnimation, airFlexAnimation, BoGAirShootAnimation;
	static Animation airShootAnimation, BoGAnimation, BoGIdleAnimation, BoGAirIdleAnimation, BoGShootAnimation;
	
	public Brawn(int playerNumber, float x, float y) {
		super(MediaLoader.getAnimation(Settings.BrawnIdleAnimationPath, 80, 80), playerNumber, x, y, 38, 76, -18.0f, -2.0f);
		idleAnimation = MediaLoader.getAnimation(Settings.BrawnIdleAnimationPath, 80, 80);
		airIdleAnimation = MediaLoader.getAnimation(Settings.BrawnAirIdleAnimationPath, 80, 80);
		shootAnimation = MediaLoader.getAnimation(Settings.BrawnShootAnimationPath, 80, 80);
		flexAnimation = MediaLoader.getAnimation(Settings.BrawnFlexAnimationPath, 80, 80);
		airFlexAnimation = MediaLoader.getAnimation(Settings.BrawnAirFlexAnimationPath, 80, 80);
		airShootAnimation = MediaLoader.getAnimation(Settings.BrawnAirShootAnimationPath, 80, 80);
		BoGAnimation = MediaLoader.getAnimation(Settings.BrawnBoGAnimationPath, 80, 80);
		BoGIdleAnimation = MediaLoader.getAnimation(Settings.BrawnBoGIdleAnimationPath, 80, 80);
		BoGAirIdleAnimation = MediaLoader.getAnimation(Settings.BrawnBoGAirIdleAnimationPath, 80, 80);
		BoGShootAnimation = MediaLoader.getAnimation(Settings.BrawnBoGShootAnimationPath, 80, 80);
		BoGAirShootAnimation = MediaLoader.getAnimation(Settings.BrawnBoGAirShootAnimationPath, 80, 80);
		maxHealth = 3;
		currentHealth = 3;
		shootAbility = new Ability(shootAnimation, airShootAnimation, 300);
		flexAbility = new Ability(flexAnimation, airFlexAnimation, 300);
		bogAbility = new Ability(BoGAnimation, 250);
		bogShootAbility = new Ability(BoGShootAnimation, BoGAirShootAnimation, 300);
		bogLifeTimer = new Cooldown(4000);
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
			if(controller.isShoot(in) && shootAbility.attemptToUse(this)) {
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
			if(controller.isAbility1(in) && currentHealth > 0 && flexAbility.attemptToUse(this)) {
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
		if(currentHealth == 0 && !bogLifeTimer.running()) {
			actionState = BrawnState.BLAZE_OF_GLORY;
			canMove = false;
			bogAbility.attemptToUse(this);
			bogLifeTimer.attemptToUse();
			//Override to get bog animations
			shootAbility = bogShootAbility;
			idleAnimation = BoGIdleAnimation;
			airIdleAnimation = BoGAirIdleAnimation;
		}
	}
}
