package com.evilzoidberg.entities.projectiles;

import com.evilzoidberg.Settings;
import com.evilzoidberg.entities.HeroEntity;
import com.evilzoidberg.utility.MediaLoader;

@SuppressWarnings("serial")
public class Shuriken extends ProjectileEntity {
	public Shuriken(float x, float y, boolean goesRight, boolean onGround, HeroEntity parent) {
		super(MediaLoader.getAnimation(Settings.ShurikenAnimationPath, 10, 10), x, y, 10, 10, 0, 0, goesRight, parent);
		speed = 700;
		if(!onGround) {
			dy = (speed / 3) * 2;
		}
	}
}
