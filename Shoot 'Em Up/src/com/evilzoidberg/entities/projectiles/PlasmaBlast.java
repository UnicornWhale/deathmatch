package com.evilzoidberg.entities.projectiles;

import com.evilzoidberg.Settings;
import com.evilzoidberg.entities.HeroEntity;
import com.evilzoidberg.utility.MediaLoader;

@SuppressWarnings("serial")
public class PlasmaBlast extends ProjectileEntity {
	public PlasmaBlast(float x, float y, boolean goesRight, HeroEntity parent) {
		super(MediaLoader.getAnimation(Settings.PlasmaBlastAnimationPath, 10, 10), x, y, 10, 10, 0, 0, goesRight, parent);
	}
}
