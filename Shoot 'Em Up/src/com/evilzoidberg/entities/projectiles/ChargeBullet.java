package com.evilzoidberg.entities.projectiles;

import com.evilzoidberg.Settings;
import com.evilzoidberg.entities.HeroEntity;
import com.evilzoidberg.utility.MediaLoader;

@SuppressWarnings("serial")
public class ChargeBullet extends ProjectileEntity {
	public ChargeBullet(float x, float y, boolean goesRight, HeroEntity parent) {
		super(MediaLoader.getAnimation(Settings.ChargeBulletAnimationPath, 32, 5), x, y, 32, 5, 0, 0, goesRight, parent);
		speed = 3000;
	}
}