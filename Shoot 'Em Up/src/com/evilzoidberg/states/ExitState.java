package com.evilzoidberg.states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class ExitState extends BasicGameState {
	private int id;
	
	public ExitState(int id) {
		this.id = id;
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {}
	
	@Override
	public void enter(GameContainer gc, StateBasedGame sbg) {
		/**
		 * Having this state simply allows state buttons to exit the game without modifying that class
		 */
		System.exit(0);
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2) throws SlickException {}
	
	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException {}

	@Override
	public int getID() {
		return id;
	}

}
