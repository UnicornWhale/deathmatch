package com.evilzoidberg.entities;

import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Input;

import com.evilzoidberg.Settings;
import com.evilzoidberg.entities.projectiles.PlasmaBlast;
import com.evilzoidberg.entities.projectiles.ProjectileEntity;
import com.evilzoidberg.entities.states.MovementState;
import com.evilzoidberg.utility.Ability;
import com.evilzoidberg.utility.Cooldown;
import com.evilzoidberg.utility.MediaLoader;

@SuppressWarnings("serial")
public class Brawn extends HeroEntity {
	Cooldown bogLifeTimer;
	static Animation flexAnimation, airFlexAnimation, BoGAirShootAnimation, BoGAnimation, BoGIdleAnimation, BoGAirIdleAnimation, BoGShootAnimation;
	
	public Brawn(int playerNumber, float x, float y) {
		super(MediaLoader.getAnimation(Settings.BrawnIdleAnimationPath, 80, 80), playerNumber, x, y, 38, 76, -18.0f, -2.0f);
		
		//Movement Animations
		walkAnimation = MediaLoader.getAnimation(Settings.BrawnWalkAnimationPath, 80, 80);
		idleAnimation = MediaLoader.getAnimation(Settings.BrawnIdleAnimationPath, 80, 80);
		airIdleAnimation = MediaLoader.getAnimation(Settings.BrawnAirIdleAnimationPath, 80, 80);
		shootAnimation = MediaLoader.getAnimation(Settings.BrawnShootAnimationPath, 80, 80);
		airShootAnimation = MediaLoader.getAnimation(Settings.BrawnAirShootAnimationPath, 80, 80);
		
		//Ability 1 Animations
		flexAnimation = MediaLoader.getAnimation(Settings.BrawnFlexAnimationPath, 80, 80);
		airFlexAnimation = MediaLoader.getAnimation(Settings.BrawnAirFlexAnimationPath, 80, 80);
		
		//Ability 2 Animations
		BoGAnimation = MediaLoader.getAnimation(Settings.BrawnBoGAnimationPath, 80, 80);
		BoGIdleAnimation = MediaLoader.getAnimation(Settings.BrawnBoGIdleAnimationPath, 80, 80);
		BoGAirIdleAnimation = MediaLoader.getAnimation(Settings.BrawnBoGAirIdleAnimationPath, 80, 80);
		BoGShootAnimation = MediaLoader.getAnimation(Settings.BrawnBoGShootAnimationPath, 80, 80);
		BoGAirShootAnimation = MediaLoader.getAnimation(Settings.BrawnBoGAirShootAnimationPath, 80, 80);
		
		//Health
		maxHealth = 3;
		currentHealth = 3;
		
		//Abilities
		shootAbility = new Ability(shootAnimation, airShootAnimation, 300);
		ability1 = new Ability(flexAnimation, airFlexAnimation, 300);
		ability2 = new Ability(BoGAnimation, 250);
		bogLifeTimer = new Cooldown(4000);
		
		currentAnimation = idleAnimation;
	}
	
	@Override
	public void update(Input in, int delta, ArrayList<Entity> mapEntities, ArrayList<ProjectileEntity> projectiles) {
		/**
		 * Updates as a HeroEntity then responds to any hero specific inputs.
		 */
		super.update(in, delta, mapEntities, projectiles);
		
		//Update whether still alive
		if(!bogLifeTimer.running() && currentHealth <= 0) {
			state = MovementState.DEAD;
		}
		
		//Update whether still ripping shirt off
		if(state == MovementState.ABILITY_2) {
			if(!ability2.running()) {
				state = MovementState.IDLE;
			}
		}
		
		//Update whether still in BoG life
		if(currentHealth <= 0) {
			bogLifeTimer.update(delta);
		}
		
		//Update whether still flexing
		if(state == MovementState.ABILITY_1) {
			if(!ability1.running()) {
				state = MovementState.IDLE;
			}
		}
		
		if(canMove) {
			//Flexing controls
			if(controller.isAbility1(in) && currentHealth > 0 && ability1.attemptToUse(this)) {
				if(onGround) {
					dx = 0.0f;
				}
				state = MovementState.ABILITY_1;
			}
		}
	}
	
	@Override
	public void damage(int damage) {
		//Ignore damage while flexing
		if(state != MovementState.ABILITY_1 && currentHealth > 0) {
			currentHealth -= damage;
		}
		if(currentHealth == 0 && !bogLifeTimer.running()) {
			state = MovementState.ABILITY_2;
			canMove = false;
			ability2.attemptToUse(this);
			bogLifeTimer.attemptToUse();
			//Override to get bog animations
			shootAbility.onGround = BoGShootAnimation;
			shootAbility.inAir = BoGAirShootAnimation;
			idleAnimation = BoGIdleAnimation;
			airIdleAnimation = BoGAirIdleAnimation;
		}
	}

	@Override
	public void addProjectile(ArrayList<ProjectileEntity> projectiles) {
		int projectileX = (int)(x - 10.0f);
		if(facingRight) {
			projectileX = (int)(x + width);
		}
		int projectileY = (int)(y + (height / 2.0f)) - 3;
		projectiles.add(new PlasmaBlast(projectileX, projectileY, facingRight, this));
	}
}
