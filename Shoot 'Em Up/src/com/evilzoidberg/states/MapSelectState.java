package com.evilzoidberg.states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.evilzoidberg.Engine;
import com.evilzoidberg.Settings;
import com.evilzoidberg.ui.StateChangeButton;

public class MapSelectState extends BasicGameState {
	StateChangeButton[] stateButtons;
	private int id;
	
	public MapSelectState(int id) {
		this.id = id;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {}
	
	@Override
	public void enter(GameContainer gc, StateBasedGame sbg) {
		stateButtons = new StateChangeButton[Settings.MapPaths.length + 1];
		for(int i = 0; i < Settings.MapPaths.length; i++) {
			stateButtons[i] = new StateChangeButton(Settings.MapNames[i], Engine.PlayStateID, 50 + (200 * i), 100, 150, 75, sbg);
		}
		stateButtons[Settings.MapPaths.length] = new StateChangeButton("Back", Engine.MenuStateID, 50, 600, 75, 75, sbg);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		/**
		 * Draw all buttons to the screen
		 */
		for(int i = 0; i < stateButtons.length; i++) {
			stateButtons[i].paint(g);
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		/**
		 * Check all buttons to see if they are being clicked. If they are, update the highlighting
		 * and selected heroes to visually show it.
		 */
		Input in = gc.getInput();
		
		//State buttons
		if(in.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			int x = in.getMouseX();
			int y = in.getMouseY();
			
			for(int i = 0; i < stateButtons.length; i++) {
				if(stateButtons[i].isClicked(x, y)) {
					Settings.SelectedMap = i;
				}
			}
		}
	}

	@Override
	public int getID() {
		return id;
	}

}
