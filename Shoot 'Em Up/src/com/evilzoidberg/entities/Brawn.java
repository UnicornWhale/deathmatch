package com.evilzoidberg.entities;

import java.util.ArrayList;

import org.newdawn.slick.Input;

import com.evilzoidberg.Settings;
import com.evilzoidberg.utility.Cooldown;
import com.evilzoidberg.utility.ImageLoader;

@SuppressWarnings("serial")
public class Brawn extends HeroEntity {
	Cooldown bulletCooldown = new Cooldown(200);

	public Brawn(int playerNumber, float x, float y) {
		super(ImageLoader.getImage(Settings.BrawnImagePath), playerNumber, x, y, 24, 47, -19.0f, -8.0f);
		maxHealth = 5;
		currentHealth = 5;
	}
	
	@Override
	public void update(Input in, int delta, ArrayList<Entity> mapEntities, ArrayList<ProjectileEntity> projectiles) {
		/**
		 * Updates as a HeroEntity then responds to any hero specific inputs.
		 */
		super.update(in, delta, mapEntities, projectiles);
		
		//Update cooldowns
		bulletCooldown.update(delta);
		
		//Shooting controls
		if(in.isKeyDown(shoot) && bulletCooldown.attemptToUse()) {
			int projectileX = (int)(x + width);
			if(facingRight) {
				projectileX = (int)(x - 5.0f);
			}
			int projectileY = (int)(y + (height / 2.0f)) - 3;
			projectiles.add(new Bullet(projectileX, projectileY, facingRight, this));
		}
	}
}
