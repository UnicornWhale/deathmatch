package com.evilzoidberg.states;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.evilzoidberg.Settings;
import com.evilzoidberg.entities.HeroEntity;
import com.evilzoidberg.entities.ProjectileEntity;
import com.evilzoidberg.maploader.Map;

public class PlayState extends BasicGameState {
	int id;
	HeroEntity player1;
	HeroEntity player2;
	Map map;
	ArrayList<ProjectileEntity> projectiles = new ArrayList<ProjectileEntity>();
	boolean mapAndPlayersInitialized = false; //Init method is called at start of program, so work around it
	
	public PlayState(int id) {
		this.id = id;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		player1 = HeroEntity.getHeroByNumber(1, Settings.Player1Hero);
		player2 = HeroEntity.getHeroByNumber(2, Settings.Player2Hero);
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
		if(!mapAndPlayersInitialized) {
			init(gc, sbg);
			mapAndPlayersInitialized = true;
		}
		
		player1.update(gc.getInput(), delta, map.collideableTiles, projectiles);
		player2.update(gc.getInput(), delta, map.collideableTiles, projectiles);
	}

	@Override
	public int getID() {
		return id;
	}
}
