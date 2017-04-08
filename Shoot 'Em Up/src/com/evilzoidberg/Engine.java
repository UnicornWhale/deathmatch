package com.evilzoidberg;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.evilzoidberg.states.ExitState;
import com.evilzoidberg.states.HeroSelectState;
import com.evilzoidberg.states.MapSelectState;
import com.evilzoidberg.states.MenuState;
import com.evilzoidberg.states.PlayState;
import com.evilzoidberg.states.ScoreboardState;

public class Engine extends StateBasedGame {
	public static int MenuStateID = 0;
	public static int HeroSelectStateID = 1;
	public static int MapSelectStateID = 2;
	public static int PlayStateID = 3;
	public static int ExitStateID = 4;
	public static int ScoreboardStateID = 5;

	public Engine() {
		super("Deathmatch");
	}

	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		this.addState(new MenuState(MenuStateID));
		this.addState(new HeroSelectState(HeroSelectStateID));
		this.addState(new MapSelectState(MapSelectStateID));
		this.addState(new PlayState(PlayStateID));
		this.addState(new ExitState(ExitStateID));
		this.addState(new ScoreboardState(ScoreboardStateID));
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
