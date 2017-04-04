package com.evilzoidberg.states;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
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
import com.evilzoidberg.utility.MediaLoader;

public class HeroSelectState extends BasicGameState {
	HeroSelectButton[] heroButtons;
	StateChangeButton[] stateButtons;
	Controller controller1 = new Controller(1), controller2 = new Controller(2);
	int player1SelectedHero, player2SelectedHero;
	int player1HighlightedHero, player2HighlightedHero;
	int waitCounter = 0;
	int player1ScrollWait = 100, player2ScrollWait = 100, scrollWait = 100;
	Animation player1Display, player2Display;
	
	private int id;
	
	public HeroSelectState(int id) {
		this.id = id;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {}
	
	@Override
	public void enter(GameContainer gc, StateBasedGame sbg) {
		heroButtons = new HeroSelectButton[] {
				new HeroSelectButton(MediaLoader.getImage(Settings.SugoiButtonImagePath), 1, 600, 100),
				new HeroSelectButton(MediaLoader.getImage(Settings.BrawnButtonImagePath), 2, 600, 300),
		};
		stateButtons = new StateChangeButton[] {
				new StateChangeButton(MediaLoader.getImage(Settings.BackButtonImagePath), Engine.MenuStateID, 50, 600, sbg)
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
		
		player1Display = getAnimationByHeroNumber(player1HighlightedHero, false);
		player2Display = getAnimationByHeroNumber(player2HighlightedHero, false);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		/**
		 * Draw all buttons to the screen
		 */
		g.setBackground(Color.darkGray);
		
		for(int i = 0; i < heroButtons.length; i++) {
			heroButtons[i].paint(g);
		}
		for(int i = 0; i < stateButtons.length; i++) {
			stateButtons[i].paint(g);
		}

		player1Display.getCurrentFrame().getScaledCopy(4).draw(100, 100);
		player2Display.getCurrentFrame().getScaledCopy(4).draw(800, 100);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		/**
		 * Check all buttons to see if they are being clicked. If they are, update the highlighting
		 * and selected heroes to visually show it.
		 */
		Input in = gc.getInput();

		if(player1Display.isStopped()) {
			player1Display = getAnimationByHeroNumber(player1HighlightedHero, false);
		}
		if(player2Display.isStopped()) {
			player2Display = getAnimationByHeroNumber(player2HighlightedHero, false);
		}
		player1Display.update(delta);
		player2Display.update(delta);
		
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
				player1Display = getAnimationByHeroNumber(player1HighlightedHero, false);
			}
			if(controller1.isRight(in) && player1ScrollWait >= scrollWait) {
				player1ScrollWait = 0;
				heroButtons[player1HighlightedHero].highlightedByPlayer1 = false;
				player1HighlightedHero++;
				if(player1HighlightedHero >= heroButtons.length) {
					player1HighlightedHero = 0;
				}
				heroButtons[player1HighlightedHero].highlightedByPlayer1 = true;
				player1Display = getAnimationByHeroNumber(player1HighlightedHero, false);
			}
			if(controller1.isMenuConfirm(in)) {
				player1SelectedHero = player1HighlightedHero;
				player1Display = getAnimationByHeroNumber(player1HighlightedHero, true);
			}
		}
		else {
			if(controller1.isMenuDeny(in)) {
				player1ScrollWait = scrollWait;
				player1SelectedHero = -1;
				Settings.Player1Hero = -1;
				player1Display = getAnimationByHeroNumber(player1HighlightedHero, false);
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
				player2Display = getAnimationByHeroNumber(player2HighlightedHero, false);
			}
			if(controller2.isRight(in) && player2ScrollWait >= scrollWait) {
				player2ScrollWait = 0;
				heroButtons[player2HighlightedHero].highlightedByPlayer2 = false;
				player2HighlightedHero++;
				if(player2HighlightedHero >= heroButtons.length) {
					player2HighlightedHero = 0;
				}
				heroButtons[player2HighlightedHero].highlightedByPlayer2 = true;
				player2Display = getAnimationByHeroNumber(player2HighlightedHero, false);
			}
			if(controller2.isMenuConfirm(in)) {
				player2SelectedHero = player2HighlightedHero;
				player2Display = getAnimationByHeroNumber(player2HighlightedHero, true);
			}
		}
		else {
			if(controller2.isMenuDeny(in)) {
				player2ScrollWait = scrollWait;
				player1SelectedHero = -1;
				Settings.Player2Hero = -1;
				player2Display = getAnimationByHeroNumber(player2HighlightedHero, false);
			}
		}
	}
	
	private Animation getAnimationByHeroNumber(int heroNumber, boolean selectedAnimation) {
		Animation anim;
		if(heroNumber == 0) {
			if(selectedAnimation) {
				anim = MediaLoader.getAnimation(Settings.SugoiTauntAnimationPath, 80, 80);
			}
			else {
				anim = MediaLoader.getAnimation(Settings.SugoiIdleAnimationPath, 80, 80);
			}
		}
		else {
			if(selectedAnimation) {
				anim = MediaLoader.getAnimation(Settings.BrawnTauntAnimationPath, 80, 80);
			}
			else {
				anim = MediaLoader.getAnimation(Settings.BrawnIdleAnimationPath, 80, 80);
			}
		}
		
		if(selectedAnimation) {
			anim.setLooping(false);
		}
		else {
			anim.setLooping(true);
		}
		
		return anim;
	}

	@Override
	public int getID() {
		return id;
	}

}
