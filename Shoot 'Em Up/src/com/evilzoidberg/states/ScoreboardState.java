package com.evilzoidberg.states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.evilzoidberg.Engine;
import com.evilzoidberg.Settings;
import com.evilzoidberg.utility.Controller;
import com.evilzoidberg.utility.MediaLoader;

public class ScoreboardState extends BasicGameState {
	Image emptySkull, fullSkull, player1Flag, player2Flag;
	Controller player1Controller, player2Controller;
	int offsetX = 100, spacingX = 75, offsetY = 300, spacingY = 75;
	private int id;
	
	public ScoreboardState(int id) {
		this.id = id;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		emptySkull = MediaLoader.getImage(Settings.EmptySkullImagePath);
		fullSkull = MediaLoader.getImage(Settings.FullSkullImagePath);
		player1Flag = MediaLoader.getImage(Settings.Player1FlagImagePath);
		player2Flag = MediaLoader.getImage(Settings.Player2FlagImagePath);
	}

	@Override
	public void enter(GameContainer gc, StateBasedGame sbg) {
		player1Controller = new Controller(1);
		player2Controller = new Controller(2);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		player1Flag.draw(offsetX, offsetY);
		player2Flag.draw(offsetX, offsetY + spacingY);
		for(int i = 0; i < Settings.FirstTo; i++) {
			if(Settings.Player1Score > i) {
				fullSkull.getScaledCopy(2.0f).draw(offsetX + (spacingX * (i + 1)), offsetY);
			}
			else {
				emptySkull.getScaledCopy(2.0f).draw(offsetX + (spacingX * (i + 1)), offsetY);
			}
			if(Settings.Player2Score > i) {
				fullSkull.getScaledCopy(2.0f).draw(offsetX + (spacingX * (i + 1)), offsetY + spacingY);
			}
			else {
				emptySkull.getScaledCopy(2.0f).draw(offsetX + (spacingX * (i + 1)), offsetY + spacingY);
			}
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input in = gc.getInput();
		
		if(player1Controller.isMenuConfirm(in) || player2Controller.isMenuConfirm(in)) {
			if(Settings.Player1Score < Settings.FirstTo && Settings.Player2Score < Settings.FirstTo) {
				sbg.enterState(Engine.PlayStateID);
			}
			else {
				sbg.enterState(Engine.HeroSelectStateID);
			}
		}
	}

	@Override
	public int getID() {
		return id;
	}
}
