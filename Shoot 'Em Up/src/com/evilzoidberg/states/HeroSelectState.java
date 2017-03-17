package com.evilzoidberg.states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.evilzoidberg.Engine;
import com.evilzoidberg.Settings;
import com.evilzoidberg.ui.HeroSelectButton;
import com.evilzoidberg.ui.StateChangeButton;

public class HeroSelectState extends BasicGameState {
	HeroSelectButton[] heroButtons;
	StateChangeButton[] stateButtons;
	int player1SelectedHero = -1;
	int player2SelectedHero = -1;
	int player1HighlightedHero = 0;
	int player2HighlightedHero = 1;
	
	private int id;
	
	public HeroSelectState(int id) {
		this.id = id;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		heroButtons = new HeroSelectButton[] {
				new HeroSelectButton("Sugoi", 1, 100, 100, 50, 25),
				new HeroSelectButton("Brawn", 2, 175, 100, 50, 25)
		};
		stateButtons = new StateChangeButton[] {
				new StateChangeButton("Back", Engine.MenuStateID, 50, 600, 100, 50, sbg)
		};

		heroButtons[player1HighlightedHero].highlightedByPlayer1 = true;
		heroButtons[player2HighlightedHero].highlightedByPlayer2 = true;
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		/**
		 * Draw all buttons to the screen
		 */
		for(int i = 0; i < heroButtons.length; i++) {
			heroButtons[i].paint(g);
		}
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
		
		//Start game
		if(player1SelectedHero != -1 && player2SelectedHero != -1) {
			Settings.Player1Hero = heroButtons[player1SelectedHero].heroID;
			Settings.Player2Hero = heroButtons[player2SelectedHero].heroID;
			sbg.enterState(Engine.PlayStateID);
		}
		
		//State buttons
		if(in.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			int x = in.getMouseX();
			int y = in.getMouseY();
			
			for(int i = 0; i < stateButtons.length; i++) {
				stateButtons[i].isClicked(x, y);
			}
		}
		
		//Player 1 menu controls
		if(player1SelectedHero == -1) {
			if(in.isKeyPressed(Settings.Player1Left)) {
				heroButtons[player1HighlightedHero].highlightedByPlayer1 = false;
				player1HighlightedHero--;
				if(player1HighlightedHero < 0) {
					player1HighlightedHero = heroButtons.length -1;
				}
				heroButtons[player1HighlightedHero].highlightedByPlayer1 = true;
			}
			if(in.isKeyPressed(Settings.Player1Right)) {
				heroButtons[player1HighlightedHero].highlightedByPlayer1 = false;
				player1HighlightedHero++;
				if(player1HighlightedHero >= heroButtons.length) {
					player1HighlightedHero = 0;
				}
				heroButtons[player1HighlightedHero].highlightedByPlayer1 = true;
			}
			if(in.isKeyPressed(Settings.Player1Shoot)) {
				player1SelectedHero = player1HighlightedHero;
				heroButtons[player1HighlightedHero].selectedByPlayer1 = true;
			}
		}
		else {
			if(in.isKeyPressed(Settings.Player1Shoot)) {
				player1SelectedHero = -1;
				heroButtons[player1HighlightedHero].selectedByPlayer1 = false;
				Settings.Player1Hero = -1;
			}
		}

		//Player 2 menu controls
		if(player2SelectedHero == -1) {
			if(in.isKeyPressed(Settings.Player2Left)) {
				heroButtons[player2HighlightedHero].highlightedByPlayer2 = false;
				player2HighlightedHero--;
				if(player2HighlightedHero < 0) {
					player2HighlightedHero = heroButtons.length -1;
				}
				heroButtons[player2HighlightedHero].highlightedByPlayer2 = true;
			}
			if(in.isKeyPressed(Settings.Player2Right)) {
				heroButtons[player2HighlightedHero].highlightedByPlayer2 = false;
				player2HighlightedHero++;
				if(player2HighlightedHero >= heroButtons.length) {
					player2HighlightedHero = 0;
				}
				heroButtons[player2HighlightedHero].highlightedByPlayer2 = true;
			}
			if(in.isKeyPressed(Settings.Player2Shoot)) {
				player2SelectedHero = player2HighlightedHero;
				heroButtons[player2HighlightedHero].selectedByPlayer2 = true;
			}
		}
		else {
			if(in.isKeyPressed(Settings.Player2Shoot)) {
				player1SelectedHero = -1;
				heroButtons[player2HighlightedHero].selectedByPlayer2 = false;
				Settings.Player2Hero = -1;
			}
		}
	}

	@Override
	public int getID() {
		return id;
	}

}
