package com.evilzoidberg.entities;

import org.newdawn.slick.Image;

@SuppressWarnings("serial")
public class ProjectileEntity extends MoveableEntity {

	public ProjectileEntity(Image image, float x, float y, float offsetX, float offsetY) {
		super(image, x, y, offsetX, offsetY);
		affectedByGravity = false;
	}
	
	public void onHit(HeroEntity hero) {
		
	}
}
