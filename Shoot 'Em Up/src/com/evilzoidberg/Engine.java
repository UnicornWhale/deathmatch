package com.evilzoidberg;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.evilzoidberg.states.ClientMenuState;
import com.evilzoidberg.states.DemoState;
import com.evilzoidberg.states.MenuState;
import com.evilzoidberg.states.ServerMenuState;

public class Engine extends StateBasedGame {
	public static int WindowWidth = 600;
	public static int WindowHeight = 480;
	
	//States
	public static int MenuStateID = 0;
	public static int ClientMenuStateID = 1;
	public static int ServerMenuStateID = 2;
	public static int DemoStateID = 3;

	public Engine() {
		super("Deathmatch");
	}

	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		this.addState(new MenuState(MenuStateID));
		this.addState(new ClientMenuState(ClientMenuStateID));
		this.addState(new ServerMenuState(ServerMenuStateID));
		this.addState(new DemoState(DemoStateID));
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
