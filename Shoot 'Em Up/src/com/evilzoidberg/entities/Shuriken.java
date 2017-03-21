package com.evilzoidberg.entities;

import com.evilzoidberg.Settings;
import com.evilzoidberg.utility.MediaLoader;

@SuppressWarnings("serial")
public class Shuriken extends ProjectileEntity {
	public Shuriken(float x, float y, boolean goesRight, HeroEntity parent) {
		super(MediaLoader.getAnimation(Settings.ShurikenAnimationPath, 10, 10), x, y, 10, 10, 0, 0, goesRight, parent);
	}
}
