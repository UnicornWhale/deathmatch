package com.evilzoidberg.entities;

import com.evilzoidberg.Settings;
import com.evilzoidberg.utility.ImageLoader;

@SuppressWarnings("serial")
public class Bullet extends ProjectileEntity {
	public Bullet(float x, float y, boolean goesRight, HeroEntity parent) {
		super(ImageLoader.getImage(Settings.BulletImagePath), x, y, 6, 6, -2, -2, goesRight, parent);
	}

}
