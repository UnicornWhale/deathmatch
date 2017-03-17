package com.evilzoidberg.states;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.evilzoidberg.Settings;
import com.evilzoidberg.entities.Giblet;
import com.evilzoidberg.entities.HeroEntity;
import com.evilzoidberg.entities.MoveableEntity;
import com.evilzoidberg.entities.ProjectileEntity;
import com.evilzoidberg.maploader.Map;

public class PlayState extends BasicGameState {
	int id;
	ArrayList<HeroEntity> heroes;
	Map map;
	ArrayList<ProjectileEntity> projectiles = new ArrayList<ProjectileEntity>();
	ArrayList<MoveableEntity> otherEntities = new ArrayList<MoveableEntity>();
	boolean mapAndPlayersInitialized = false; //Init method is called at start of program, so work around it
	
	public PlayState(int id) {
		this.id = id;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		heroes = new ArrayList<HeroEntity>();
		heroes.add(HeroEntity.getHeroByNumber(1, Settings.Player1Hero));
		heroes.add(HeroEntity.getHeroByNumber(2, Settings.Player2Hero));
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
		
		for(int i = 0; i < heroes.size(); i++) {
			if(heroes.get(i).alive) {
				heroes.get(i).paint(g);
			}
		}
		
		for(int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).paint(g);
		}
		
		for(int i = 0; i < otherEntities.size(); i++) {
			otherEntities.get(i).paint(g);
		}
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

		//Update and cull projectiles
		for(int i = projectiles.size() - 1; i >= 0; i--) {
			projectiles.get(i).updatePhysics(delta, map.collideableTiles);
			if(!projectiles.get(i).active) {
				projectiles.remove(i);
			}
		}

		//Update heroes
		for(int i = 0; i < heroes.size(); i++) {
			if(heroes.get(i).alive) {
				heroes.get(i).update(gc.getInput(), delta, map.collideableTiles, projectiles);
			}
			else {
				for(int n = 0; n < 10; n++) {
					otherEntities.add(new Giblet(heroes.get(i).getX() + (heroes.get(i).getWidth() / 2), heroes.get(i).getY() + (heroes.get(i).getHeight() / 2)));
				}
			}
		}

		//Cull heroes
		for(int i = heroes.size() - 1; i >= 0; i--) {
			if(!heroes.get(i).alive) {
				heroes.remove(i);
			}
		}
		
		//Update giblets
		for(int i = 0; i < otherEntities.size(); i++) {
			otherEntities.get(i).updatePhysics(delta, map.collideableTiles);
		}
	}

	@Override
	public int getID() {
		return id;
	}
}
