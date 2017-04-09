package com.evilzoidberg.entities.projectiles;

import com.evilzoidberg.Settings;
import com.evilzoidberg.entities.HeroEntity;
import com.evilzoidberg.utility.MediaLoader;

@SuppressWarnings("serial")
public class Bullet extends ProjectileEntity {
	public Bullet(float x, float y, boolean goesRight, HeroEntity parent) {
		super(MediaLoader.getAnimation(Settings.BulletAnimationPath, 16, 5), x, y, 16, 5, 0, 0, goesRight, parent);
		speed = 700;
	}
}
