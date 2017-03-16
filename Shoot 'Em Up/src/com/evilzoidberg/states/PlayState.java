package com.evilzoidberg.states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.evilzoidberg.Settings;
import com.evilzoidberg.entities.HeroEntity;
import com.evilzoidberg.maploader.Map;
import com.evilzoidberg.utility.ImageLoader;

public class PlayState extends BasicGameState {
	int id;
	HeroEntity player1;
	HeroEntity player2;
	Map map;
	
	public PlayState(int id) {
		this.id = id;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		player1 = new HeroEntity(ImageLoader.getImage("img/cowboy.png"), 1, 100.0f, 100.0f, 24, 50, -25.0f, -7.0f);
		player2 = new HeroEntity(ImageLoader.getImage("img/cowboy.png"), 2, 300.0f, 100.0f, 24, 50, -25.0f, -7.0f);
		map = new Map(Settings.TestMap);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		/**
		 * Draws all children to the screen
		 */
		for(int i = 0; i < map.tiles.size(); i++) {
			map.tiles.get(i).paint(g);
		}
		player1.paint(g);
		player2.paint(g);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		/**
		 * Calls update methods of all children so that they can use their own game logic to update.
		 */
		player1.update(gc.getInput(), delta, map.collideableTiles);
		player2.update(gc.getInput(), delta, map.collideableTiles);
	}

	@Override
	public int getID() {
		return id;
	}
}
