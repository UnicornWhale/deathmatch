package com.evilzoidberg.states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.evilzoidberg.Engine;
import com.evilzoidberg.ui.Button;

public class ClientMenuState extends BasicGameState {
	Button menu;
	Button demo;
	private int id;

	public ClientMenuState(int id) {
		this.id = id;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		demo = new Button("Demo", 100, 100, 100, 50);
		menu = new Button("Back", 100, 200, 100, 50);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		demo.paint(g);
		menu.paint(g);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input in = gc.getInput();
		
		if(in.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			int x = in.getMouseX();
			int y = in.getMouseY();
			
			if(demo.contains(x, y)) {
				sbg.enterState(Engine.DemoStateID);
			}
			
			if(menu.contains(x, y)) {
				sbg.enterState(Engine.MenuStateID);
			}
		}
	}

	@Override
	public int getID() {
		return id;
	}
}