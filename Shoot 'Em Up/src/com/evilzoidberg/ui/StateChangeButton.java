package com.evilzoidberg.ui;

import org.newdawn.slick.state.StateBasedGame;

public class StateChangeButton extends Button {
	int state;
	StateBasedGame sbg;

	public StateChangeButton(String text, int state, int x, int y, int width, int height, StateBasedGame sbg) {
		super(text, x, y, width, height);
		this.state = state;
		this.sbg = sbg;
	}
	
	@Override
	public boolean isClicked(int x, int y) {
		/**
		 * Returns whether the button is clicked or not, and also on click will change the game
		 * state to the state this was initialized with.
		 */
		if(rect.contains(x, y)) {
			sbg.enterState(state);
			return true;
		}
		return false;
	}
}

