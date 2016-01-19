package com.evilzoidberg.entities;

import org.newdawn.slick.Image;

@SuppressWarnings("serial")
public class Rigidbody extends Entity {
	public static float baseGravity = 5;
	float xVel = 0; //Velocities never change for base rigidbody unless
	float yVel = 0; //some outside object changes them, children could differ

	public Rigidbody(Image image, float x, float y) {
		super(image, x, y);
	}
	
	/**
	 * updates the rigidbody's position due to gravity, and collides
	 * properly with all the passed objects.
	 * @param delta - num milliseconds since last update call
	 * @param objects - objects that the rigidbody should collide with
	 */
	public void update(int delta, Entity[] objects) {
		
	}
}
