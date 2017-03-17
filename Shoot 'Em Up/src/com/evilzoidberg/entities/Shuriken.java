package com.evilzoidberg.entities;

import com.evilzoidberg.Settings;
import com.evilzoidberg.utility.ImageLoader;

@SuppressWarnings("serial")
public class Shuriken extends ProjectileEntity {
	public Shuriken(float x, float y, boolean goesRight, HeroEntity parent) {
		super(ImageLoader.getImage(Settings.ShurikenImagePath), x, y, 10, 10, 0, 0, goesRight, parent);
	}
}