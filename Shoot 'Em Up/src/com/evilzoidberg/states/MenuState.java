package com.evilzoidberg.states;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.evilzoidberg.Engine;
import com.evilzoidberg.Settings;
import com.evilzoidberg.ui.Button;
import com.evilzoidberg.ui.StateChangeButton;
import com.evilzoidberg.utility.MediaLoader;

public class MenuState extends BasicGameState {
	Button[] buttons;
	private int id;
	
	public MenuState(int id) {
		this.id = id;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {}
	
	@Override
	public void enter(GameContainer gc, StateBasedGame sbg) {
		buttons = new Button[] {
				new StateChangeButton(MediaLoader.getImage(Settings.PlayButtonImagePath), Engine.HeroSelectStateID, 100, 100, sbg),
				new StateChangeButton(MediaLoader.getImage(Settings.ExitButtonImagePath), Engine.ExitStateID, 100, 200, sbg),
		};
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		/**
		 * Draw all buttons to the screen
		 */
		g.setBackground(Color.darkGray);
		
		for(int i = 0; i < buttons.length; i++) {
			buttons[i].paint(g);
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		/**
		 * Check all buttons to see if they are being clicked, and if they are, change to the state
		 * that button represents.
		 */
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
