package com.evilzoidberg.states;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.evilzoidberg.Engine;
import com.evilzoidberg.Settings;
import com.evilzoidberg.entities.HeroEntity;
import com.evilzoidberg.entities.MoveableEntity;
import com.evilzoidberg.entities.projectiles.Giblet;
import com.evilzoidberg.entities.projectiles.ProjectileEntity;
import com.evilzoidberg.entities.states.MovementState;
import com.evilzoidberg.maploader.Map;

public class PlayState extends BasicGameState {
	int id, waitCounter = 0;
	ArrayList<HeroEntity> heroes;
	Map map;
	ArrayList<ProjectileEntity> projectiles = new ArrayList<ProjectileEntity>();
	ArrayList<MoveableEntity> otherEntities = new ArrayList<MoveableEntity>();
	
	public PlayState(int id) {
		this.id = id;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {}

	@Override
	public void enter(GameContainer gc, StateBasedGame sbg) {
		heroes = new ArrayList<HeroEntity>();
		heroes.add(HeroEntity.getHeroByNumber(1, Settings.Player1Hero));
		heroes.add(HeroEntity.getHeroByNumber(2, Settings.Player2Hero));
		map = new Map(Settings.MapPaths[Settings.SelectedMap]);
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
			if(heroes.get(i).state != MovementState.DEAD) {
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
		//Check Victory
		if(heroes.size() == 1) {
			waitCounter += delta;
			
			if(waitCounter >= Settings.waitOnVictory) {
				sbg.enterState(Engine.HeroSelectStateID);
			}
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
			if(heroes.get(i).state != MovementState.DEAD) {
				heroes.get(i).update(gc.getInput(), delta, map.collideableTiles, projectiles);
			}
			if(heroes.get(i).state == MovementState.DEAD) {
				for(int n = 0; n < 10; n++) {
					float spawnX = heroes.get(i).getX() + (heroes.get(i).getWidth() / 2.0f);
					float spawnY = heroes.get(i).getY() + (heroes.get(i).getHeight() / 2.0f);
					otherEntities.add(new Giblet(spawnX, spawnY));
				}
			}
		}

		//Cull heroes
		for(int i = heroes.size() - 1; i >= 0; i--) {
			if(heroes.get(i).state == MovementState.DEAD) {
				heroes.remove(i);
			}
		}
		
		//Update giblets
		for(int i = 0; i < otherEntities.size(); i++) {
			otherEntities.get(i).update(delta, map.collideableTiles);
		}
	}

	@Override
	public int getID() {
		return id;
	}
}
