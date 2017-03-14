package com.evilzoidberg.states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.evilzoidberg.Engine;
import com.evilzoidberg.ui.Button;
import com.evilzoidberg.ui.StateChangeButton;

public class MenuState extends BasicGameState {
	Button[] buttons;
	private int id;
	
	public MenuState(int id) {
		this.id = id;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		buttons = new Button[] {
				new StateChangeButton("Play", Engine.HeroSelectStateID, 100, 100, 100, 50, sbg),
				new StateChangeButton("Exit", Engine.ExitStateID, 100, 200, 100, 50, sbg),
		};
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		for(int i = 0; i < buttons.length; i++) {
			buttons[i].paint(g);
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input in = gc.getInput();
		
		if(in.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			int x = in.getMouseX();
			int y = in.getMouseY();
			
			for(int i = 0; i < buttons.length; i++) {
				buttons[i].isClicked(x, y);
			}
		}
	}

	@Override
	public int getID() {
		return id;
	}

}
