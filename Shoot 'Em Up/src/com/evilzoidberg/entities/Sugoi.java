package com.evilzoidberg.entities;

import java.util.ArrayList;

import org.newdawn.slick.Input;

import com.evilzoidberg.Settings;
import com.evilzoidberg.utility.ImageLoader;

@SuppressWarnings("serial")
public class Sugoi extends HeroEntity {
	boolean hasDoubleJump = true;
	int timeSinceLastOnGround = 0;
	int minAirTimeBeforeDoubleJump = 100;
	int shurikenDamage = 10;
	

	public Sugoi(int playerNumber, float x, float y) {
		super(ImageLoader.getImage(Settings.SugoiImagePath), playerNumber, x, y, 24, 55, -20.0f, -5.0f);
	}
	
	@Override
	public void update(Input in, int delta, ArrayList<Entity> mapEntities, ArrayList<ProjectileEntity> projectiles) {
		/**
		 * Updates as a HeroEntity then responds to any hero specific inputs.
		 */
		super.update(in, delta, mapEntities, projectiles);
		
		if(onGround) {
			timeSinceLastOnGround = 0;
			hasDoubleJump = true;
		}
		else {
			timeSinceLastOnGround += delta;
		}
		
		if(in.isKeyPressed(up) && !onGround && hasDoubleJump && timeSinceLastOnGround > minAirTimeBeforeDoubleJump) {
			dy = jumpVelocity;
			hasDoubleJump = false;
		}
		
		if(in.isKeyDown(shoot)) {
			
		}
	}
}
