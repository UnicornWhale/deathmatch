package com.evilzoidberg.entities.projectiles;

import java.util.ArrayList;

import com.evilzoidberg.Settings;
import com.evilzoidberg.entities.Entity;
import com.evilzoidberg.entities.MoveableEntity;
import com.evilzoidberg.utility.MediaLoader;

@SuppressWarnings("serial")
public class Giblet extends MoveableEntity {
	public Giblet(float x, float y) {
		super(MediaLoader.getImage(Settings.GibletImagePath), x, y, 5, 5, 0, 0);
		dy = (float)(Math.random() * -1500.0);
		if(Math.random() > 0.5) {
			dx = (float)(Math.random() * 400.0);
		}
		else {
			dx = (float)(Math.random() * -400.0);
		}
	}
	
	@Override
	public void updatePhysics(int delta, ArrayList<Entity> mapEntities) {
		super.updatePhysics(delta, mapEntities);
		
		if(onGround) {
			dx = 0.0f;
		}
	}
}
