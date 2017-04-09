package com.evilzoidberg.entities;

import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Input;

import com.evilzoidberg.Settings;
import com.evilzoidberg.entities.projectiles.Bullet;
import com.evilzoidberg.entities.projectiles.ChargeBullet;
import com.evilzoidberg.entities.projectiles.ProjectileEntity;
import com.evilzoidberg.entities.states.MovementState;
import com.evilzoidberg.utility.Ability;
import com.evilzoidberg.utility.MediaLoader;

@SuppressWarnings("serial")
public class TimePiece extends HeroEntity {
	Animation chargeShotAnimation;

	public TimePiece(int playerNumber, float x, float y) {
		super(MediaLoader.getAnimation(Settings.TimePieceIdleAnimationPath, 80, 80), playerNumber, x, y, 30, 65, -20, -10);
		
		//Movement Animations
		walkAnimation = MediaLoader.getAnimation(Settings.TimePieceWalkAnimationPath, 80, 80);
		idleAnimation = MediaLoader.getAnimation(Settings.TimePieceIdleAnimationPath, 80, 80);
		airIdleAnimation = MediaLoader.getAnimation(Settings.TimePieceAirIdleAnimationPath, 80, 80);
		Animation shootAnimation = MediaLoader.getAnimation(Settings.TimePieceShootAnimationPath, 80, 80);
		Animation airShootAnimation = MediaLoader.getAnimation(Settings.TimePieceAirShootAnimationPath, 80, 80);
		
		//Ability 1
		chargeShotAnimation = MediaLoader.getAnimation(Settings.TimePieceChargeShotAnimationPath, 80, 80);
		
		//Health
		maxHealth = 3;
		currentHealth = 3;
		
		//Abilities
		shootAbility = new Ability(shootAnimation, airShootAnimation, 500);
		ability1 = new Ability(chargeShotAnimation, chargeShotAnimation, 1200, 1025);
		tauntAbility = new Ability(MediaLoader.getAnimation(Settings.TimePieceTauntAnimationPath, 80, 80), 1050);
		
		currentAnimation = idleAnimation;
	}
	
	@Override
	public void update(Input in, int delta, ArrayList<Entity> mapEntities, ArrayList<ProjectileEntity> projectiles) {
		super.update(in, delta, mapEntities, projectiles);
		
		//Update whether still charge shotting
		if(state == MovementState.ABILITY_1) {
			if(ability1.attemptThreshold()) {
				addChargeProjectile(projectiles);
			}
			if(!ability1.running()) {
				state = MovementState.IDLE;
			}
		}
		
		if(canMove) {
			//Charge Shot controls
			if(controller.isAbility1(in) && ability1.attemptToUse(this)) {
				if(onGround) {
					dx = 0.0f;
				}
				state = MovementState.ABILITY_1;
			}
		}
	}

	@Override
	public void addProjectile(ArrayList<ProjectileEntity> projectiles) {
		int projectileX = (int)(x - 15.0f);
		if(facingRight) {
			projectileX = (int)(x + width + 5);
		}
		int projectileY = (int)(y + (height / 2.0f)) - 10;
		projectiles.add(new Bullet(projectileX, projectileY, facingRight, this));
	}

	public void addChargeProjectile(ArrayList<ProjectileEntity> projectiles) {
		int projectileX = (int)(x - 15.0f);
		if(facingRight) {
			projectileX = (int)(x + width + 5);
		}
		int projectileY = (int)(y + (height / 2.0f)) - 10;
		projectiles.add(new ChargeBullet(projectileX, projectileY, facingRight, this));
	}

}
