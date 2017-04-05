package com.evilzoidberg.entities;

import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Input;

import com.evilzoidberg.Settings;
import com.evilzoidberg.entities.projectiles.ProjectileEntity;
import com.evilzoidberg.entities.projectiles.Shuriken;
import com.evilzoidberg.entities.states.MovementState;
import com.evilzoidberg.utility.Ability;
import com.evilzoidberg.utility.MediaLoader;

@SuppressWarnings("serial")
public class Sugoi extends HeroEntity {
	boolean hasDoubleJump = true;
	static Animation wallclingAnimation;

	public Sugoi(int playerNumber, float x, float y) {
		super(MediaLoader.getAnimation(Settings.SugoiIdleAnimationPath, 80, 80), playerNumber, x, y, 34, 75, -24.0f, -2.0f);
		
		//Movement Animations
		walkAnimation = MediaLoader.getAnimation(Settings.SugoiWalkAnimationPath, 80, 80);
		idleAnimation = MediaLoader.getAnimation(Settings.SugoiIdleAnimationPath, 80, 80);
		airIdleAnimation = MediaLoader.getAnimation(Settings.SugoiAirIdleAnimationPath, 80, 80);
		shootAnimation = MediaLoader.getAnimation(Settings.SugoiShootAnimationPath, 80, 80);
		airShootAnimation = MediaLoader.getAnimation(Settings.SugoiAirShootAnimationPath, 80, 80);
		
		//Ability 2
		wallclingAnimation = MediaLoader.getAnimation(Settings.SugoiWallclingAnimationPath, 80, 80);
		
		//Health
		maxHealth = 3;
		currentHealth = 3;
		
		//Abilities
		shootAbility = new Ability(shootAnimation, airShootAnimation, 300, 100);
		
		currentAnimation = idleAnimation;
	}
	
	@Override
	public void update(Input in, int delta, ArrayList<Entity> mapEntities, ArrayList<ProjectileEntity> projectiles) {
		/**
		 * Updates as a HeroEntity then responds to any hero specific inputs.
		 */
		super.update(in, delta, mapEntities, projectiles);
		
		//Update has double jump
		if(onGround) {
			hasDoubleJump = true;
		}
		
		if(canMove) {
			//Wall cling controls
			if(!onGround && controller.isRight(in) && onRightWall && dy > 0.0f) {
				state = MovementState.ABILITY_2;
				dy = 0.0f;
				affectedByGravity = false;
				hasDoubleJump = true;
			}
			else if(!onGround && controller.isLeft(in) && onLeftWall && dy > 0.0f) {
				state = MovementState.ABILITY_2;
				dy = 0.0f;
				affectedByGravity = false;
				hasDoubleJump = true;
			}
			else if(!onGround) {
				affectedByGravity = true;
				state = MovementState.IDLE;
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
		}
	}
	
	public void addProjectile(ArrayList<ProjectileEntity> projectiles) {
		int projectileX = (int)(x - 5.0f);
		if(facingRight) {
			projectileX = (int)(x + width);
		}
		int projectileY = (int)(y + (height / 2.0f)) - 3;
		projectiles.add(new Shuriken(projectileX, projectileY, facingRight, onGround, this));
	}
}
