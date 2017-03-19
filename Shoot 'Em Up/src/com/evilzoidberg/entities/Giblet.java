package com.evilzoidberg.entities;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

@SuppressWarnings("serial")
public class Giblet extends MoveableEntity {
	public Giblet(float x, float y) {
		super(x, y, 5, 5, 0, 0);
		dy = (float)(Math.random() * -1000.0);
		if(Math.random() > 0.5) {
			dx = (float)(Math.random() * 200.0);
		}
		else {
			dx = (float)(Math.random() * -200.0);
		}
	}
	
	@Override
	public void updatePhysics(int delta, ArrayList<Entity> mapEntities) {
		super.updatePhysics(delta, mapEntities);
		
		if(onGround) {
			dx = 0.0f;
		}
	}
	
	@Override
	public void paint(Graphics g) {
		g.setColor(Color.red);
		g.fillRect(x, y, width, height);
	}
}
