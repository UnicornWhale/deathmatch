package com.evilzoidberg;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.evilzoidberg.states.HeroSelectState;
import com.evilzoidberg.states.ExitState;
import com.evilzoidberg.states.MenuState;

public class Engine extends StateBasedGame {
	public static int MenuStateID = 0;
	public static int HeroSelectStateID = 1;
	public static int PlayStateID = 2;
	public static int ExitStateID = 3;

	public Engine() {
		super("Deathmatch");
	}

	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		this.addState(new MenuState(MenuStateID));
		this.addState(new HeroSelectState(HeroSelectStateID));
		this.addState(new ExitState(ExitStateID));
	}

	public static void main(String[] args) {
		try {
			AppGameContainer agc = new AppGameContainer(new Engine());
			agc.setDisplayMode(Settings.WindowWidth, Settings.WindowHeight, false);
			agc.setMaximumLogicUpdateInterval(60);
			agc.start();
		} catch(SlickException e) {
			e.printStackTrace();
		}
	}
}
