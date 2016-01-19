package com.evilzoidberg.states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.evilzoidberg.Engine;
import com.evilzoidberg.ui.Button;

public class MenuState extends BasicGameState {
	Button client;
	Button server;
	private int id;
	
	public MenuState(int id) {
		this.id = id;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		client = new Button("Client", 100, 100, 100, 50);
		server = new Button("Server", 100, 300, 100, 50);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		client.paint(g);
		server.paint(g);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input in = gc.getInput();
		
		if(in.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			int x = in.getMouseX();
			int y = in.getMouseY();
			
			if(client.contains(x, y)) {
				sbg.enterState(Engine.ClientMenuStateID);
			}
			else if(server.contains(x, y)) {
				sbg.enterState(Engine.ServerMenuStateID);
			}
		}
	}

	@Override
	public int getID() {
		return id;
	}

}
