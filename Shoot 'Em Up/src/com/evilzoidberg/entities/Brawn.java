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
	Animation flexAnimation, airFlexAnimation, BoGAirShootAnimation, BoGAnimation, BoGIdleAnimation;
	Animation BoGAirIdleAnimation, BoGShootAnimation, BoGTauntAnimation;
	
	public Brawn(int playerNumber, float x, float y) {
		super(MediaLoader.getAnimation(Settings.BrawnIdleAnimationPath, 80, 80), playerNumber, x, y, 38, 76, -18.0f, -2.0f);
		
		//Movement Animations
		walkAnimation = MediaLoader.getAnimation(Settings.BrawnWalkAnimationPath, 80, 80);
		idleAnimation = MediaLoader.getAnimation(Settings.BrawnIdleAnimationPath, 80, 80);
		airIdleAnimation = MediaLoader.getAnimation(Settings.BrawnAirIdleAnimationPath, 80, 80);
		Animation shootAnimation = MediaLoader.getAnimation(Settings.BrawnShootAnimationPath, 80, 80);
		Animation airShootAnimation = MediaLoader.getAnimation(Settings.BrawnAirShootAnimationPath, 80, 80);
		
		//Ability 1 Animations
		flexAnimation = MediaLoader.getAnimation(Settings.BrawnFlexAnimationPath, 80, 80);
		airFlexAnimation = MediaLoader.getAnimation(Settings.BrawnAirFlexAnimationPath, 80, 80);
		
		//Ability 2 Animations
		BoGAnimation = MediaLoader.getAnimation(Settings.BrawnBoGAnimationPath, 80, 80);
		BoGIdleAnimation = MediaLoader.getAnimation(Settings.BrawnBoGIdleAnimationPath, 80, 80);
		BoGAirIdleAnimation = MediaLoader.getAnimation(Settings.BrawnBoGAirIdleAnimationPath, 80, 80);
		BoGShootAnimation = MediaLoader.getAnimation(Settings.BrawnBoGShootAnimationPath, 80, 80);
		BoGAirShootAnimation = MediaLoader.getAnimation(Settings.BrawnBoGAirShootAnimationPath, 80, 80);
		BoGTauntAnimation = MediaLoader.getAnimation(Settings.BrawnBoGTauntAnimationPath, 80, 80);
		
		//Health
		maxHealth = 3;
		currentHealth = 3;
		
		//Abilities
		shootAbility = new Ability(shootAnimation, airShootAnimation, 300, 50);
		tauntAbility = new Ability(MediaLoader.getAnimation(Settings.BrawnTauntAnimationPath, 80, 80), 1200, 400);
		ability1 = new Ability(flexAnimation, airFlexAnimation, 500);
		ability2 = new Ability(BoGAnimation, 500);
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
			if(ability1.attemptThreshold()) {
				invulnerable = true;
			}
			if(!ability1.running()) {
				state = MovementState.IDLE;
				invulnerable = false;
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
		if(currentHealth > 0) {
			super.damage(damage);
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
			tauntAbility.onGround = BoGTauntAnimation;
			tauntAbility.inAir = BoGTauntAnimation;
		}
	}

	@Override
	public void addProjectile(ArrayList<ProjectileEntity> projectiles) {
		int projectileX = (int)(x - 15.0f);
		if(facingRight) {
			projectileX = (int)(x + width + 5);
		}
		int projectileY = (int)(y + (height / 2.0f)) - 10;
		projectiles.add(new PlasmaBlast(projectileX, projectileY, facingRight, this));
	}
}
