package com.evilzoidberg.entities.projectiles;

import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import com.evilzoidberg.entities.Entity;
import com.evilzoidberg.entities.HeroEntity;
import com.evilzoidberg.entities.MoveableEntity;

@SuppressWarnings("serial")
public class ProjectileEntity extends MoveableEntity {
	HeroEntity parent;
	public boolean active = true;
	boolean goesRight;
	int damage = 1, speed = 1000;

	public ProjectileEntity(Image image, float x, float y, int width, int height, float offsetX, float offsetY, boolean goesRight, HeroEntity parent) {
		super(image, x, y, width, height, offsetX, offsetY);
		this.parent = parent;
		this.goesRight = goesRight;
		affectedByGravity = false;
		if(goesRight) {
			dx = speed;
		}
		else {
			dx = speed * -1;
		}
		dxMax = 999999; //Projectiles can go faster than everything else
	}

	public ProjectileEntity(Animation animation, float x, float y, int width, int height, float offsetX, float offsetY, boolean goesRight, HeroEntity parent) {
		super(animation, x, y, width, height, offsetX, offsetY);
		this.parent = parent;
		this.goesRight = goesRight;
		affectedByGravity = false;
		if(goesRight) {
			dx = speed;
		}
		else {
			dx = speed * -1;
		}
		dxMax = 999999; //Projectiles can go faster than everything else
	}
	
	@Override
	public void paint(Graphics g) {
		paint(g, goesRight);
	}
	
	@Override
	public void updatePhysics(int delta, ArrayList<Entity> mapEntities) {
		super.updatePhysics(delta, mapEntities);
		
		if(collidedThisTurn) {
			active = false;
		}
	}
	
	public void onHit(HeroEntity hero) {
		if(hero != parent) {
			hero.damage(damage);
			active = false;
		}
	}
}
