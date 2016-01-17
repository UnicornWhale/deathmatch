package com.evilzoidberg;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.evilzoidberg.states.MenuState;

public class Engine extends StateBasedGame {
	public static int WindowWidth = 600;
	public static int WindowHeight = 480;
	
	//States
	public static int MenuStateID = 0;

	public Engine() {
		super("Deathmatch");
	}

	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		this.addState(new MenuState(MenuStateID));
	}

	public static void main(String[] args) {
		try {
			AppGameContainer agc = new AppGameContainer(new Engine());
			agc.setDisplayMode(WindowWidth, WindowHeight, false);
			agc.start();
		} catch(SlickException e) {
			e.printStackTrace();
		}
	}
}
