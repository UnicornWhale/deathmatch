package com.evilzoidberg.states;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.evilzoidberg.Settings;
import com.evilzoidberg.entities.Entity;
import com.evilzoidberg.entities.Rigidbody;

public class DemoState extends BasicGameState {
	private int id;
	Rigidbody body;
	Entity ground, leftWall, rightWall;
	Entity[] objects;
	
	public DemoState(int id) {
		this.id = id;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		body = new Rigidbody(Color.red, 300, 100);
		
		ground = new Entity(Color.blue, 0, Settings.WindowHeight - 50, Settings.WindowWidth, 50);
		leftWall = new Entity(Color.blue, -45, 0, 50, Settings.WindowHeight);
		rightWall = new Entity(Color.blue, Settings.WindowWidth - 5, 0, 50, Settings.WindowHeight);
		
		objects = new Entity[3];
		objects[0] = ground;
		objects[1] = leftWall;
		objects[2] = rightWall;
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		body.paint(g);
		
		for(Entity e : objects) {
			e.paint(g);
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		body.update(delta, objects);

		boolean moving = false;
		if(gc.getInput().isKeyDown(Input.KEY_W)) {
			body.yVel = -10;
		}
		if(gc.getInput().isKeyDown(Input.KEY_D)) {
			body.xVel = 5;
			moving = true;
		}
		if(gc.getInput().isKeyDown(Input.KEY_A)) {
			body.xVel = -5;
			moving = !moving;
		}
		
		if(!moving) {
			body.xVel = 0;
		}
	}

	@Override
	public int getID() {
		return id;
	}
}
