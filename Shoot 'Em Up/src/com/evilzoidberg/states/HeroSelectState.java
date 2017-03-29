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
import com.evilzoidberg.utility.Controller;

public class HeroSelectState extends BasicGameState {
	HeroSelectButton[] heroButtons;
	StateChangeButton[] stateButtons;
	Controller controller1 = new Controller(1), controller2 = new Controller(2);
	int player1SelectedHero, player2SelectedHero;
	int player1HighlightedHero, player2HighlightedHero;
	int waitCounter = 0;
	int player1ScrollWait = 100, player2ScrollWait = 100, scrollWait = 100;
	
	private int id;
	
	public HeroSelectState(int id) {
		this.id = id;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {}
	
	@Override
	public void enter(GameContainer gc, StateBasedGame sbg) {
		heroButtons = new HeroSelectButton[] {
				new HeroSelectButton("Sugoi", 1, 100, 100, 50, 25),
				new HeroSelectButton("Brawn", 2, 175, 100, 50, 25)
		};
		stateButtons = new StateChangeButton[] {
				new StateChangeButton("Back", Engine.MenuStateID, 50, 600, 100, 50, sbg)
		};

		Settings.Player1Hero = -1;
		Settings.Player2Hero = -1;
		player1SelectedHero = -1;
		player2SelectedHero = -1;
		player1HighlightedHero = 0;
		player2HighlightedHero = 1;
		waitCounter = 0;
		
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
		
		//Update wait timer
		if(player1SelectedHero != -1 && player2SelectedHero != -1) {
			waitCounter += delta;
		}
		else {
			waitCounter = 0;
		}
		
		//Update scroll waits
		if(player1ScrollWait < scrollWait) {
			player1ScrollWait += delta;
		}
		if(player2ScrollWait < scrollWait) {
			player2ScrollWait += delta;
		}
		
		//Start game
		if(waitCounter >= Settings.waitOnCharacterSelect) {
			Settings.Player1Hero = heroButtons[player1SelectedHero].heroID;
			Settings.Player2Hero = heroButtons[player2SelectedHero].heroID;
			sbg.enterState(Engine.MapSelectStateID);
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
			if(controller1.isLeft(in) && player1ScrollWait >= scrollWait) {
				player1ScrollWait = 0;
				heroButtons[player1HighlightedHero].highlightedByPlayer1 = false;
				player1HighlightedHero--;
				if(player1HighlightedHero < 0) {
					player1HighlightedHero = heroButtons.length -1;
				}
				heroButtons[player1HighlightedHero].highlightedByPlayer1 = true;
			}
			if(controller1.isRight(in) && player1ScrollWait >= scrollWait) {
				player1ScrollWait = 0;
				heroButtons[player1HighlightedHero].highlightedByPlayer1 = false;
				player1HighlightedHero++;
				if(player1HighlightedHero >= heroButtons.length) {
					player1HighlightedHero = 0;
				}
				heroButtons[player1HighlightedHero].highlightedByPlayer1 = true;
			}
			if(controller1.isMenuConfirm(in)) {
				player1SelectedHero = player1HighlightedHero;
				heroButtons[player1HighlightedHero].selectedByPlayer1 = true;
			}
		}
		else {
			if(controller1.isMenuDeny(in)) {
				player1ScrollWait = scrollWait;
				player1SelectedHero = -1;
				heroButtons[player1HighlightedHero].selectedByPlayer1 = false;
				Settings.Player1Hero = -1;
			}
		}

		//Player 2 menu controls
		if(player2SelectedHero == -1) {
			if(controller2.isLeft(in) && player2ScrollWait >= scrollWait) {
				player2ScrollWait = 0;
				heroButtons[player2HighlightedHero].highlightedByPlayer2 = false;
				player2HighlightedHero--;
				if(player2HighlightedHero < 0) {
					player2HighlightedHero = heroButtons.length -1;
				}
				heroButtons[player2HighlightedHero].highlightedByPlayer2 = true;
			}
			if(controller2.isRight(in) && player2ScrollWait >= scrollWait) {
				player2ScrollWait = 0;
				heroButtons[player2HighlightedHero].highlightedByPlayer2 = false;
				player2HighlightedHero++;
				if(player2HighlightedHero >= heroButtons.length) {
					player2HighlightedHero = 0;
				}
				heroButtons[player2HighlightedHero].highlightedByPlayer2 = true;
			}
			if(controller2.isMenuConfirm(in)) {
				player2SelectedHero = player2HighlightedHero;
				heroButtons[player2HighlightedHero].selectedByPlayer2 = true;
			}
		}
		else {
			if(controller2.isMenuDeny(in)) {
				player2ScrollWait = scrollWait;
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
