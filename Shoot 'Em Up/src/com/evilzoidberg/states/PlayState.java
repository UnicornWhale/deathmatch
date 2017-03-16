package com.evilzoidberg.states;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.evilzoidberg.Settings;
import com.evilzoidberg.entities.Entity;
import com.evilzoidberg.entities.HeroEntity;

public class PlayState extends BasicGameState {
	int id;
	HeroEntity player1;
	HeroEntity player2;
	ArrayList<Entity> map;
	
	public PlayState(int id) {
		this.id = id;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		player1 = new HeroEntity(1, 100.0f, 100.0f, 50.0f, 50.0f);
		player2 = new HeroEntity(2, 300.0f, 100.0f, 50.0f, 50.0f);
		float oneThirdHeight = ((float)Settings.WindowHeight) / 3.0f;
		float twoThirdsHeight = ((float)Settings.WindowHeight) * 2.0f / 3.0f;
		float oneThirdWidth = ((float)Settings.WindowWidth) / 3.0f;
		float oneHalfWidth = ((float)Settings.WindowWidth) / 2.0f;
		
		map = new ArrayList<Entity>();
		map.add(new Entity(0.0f, Settings.WindowHeight - 25.0f, Settings.WindowWidth, 50.0f)); //Floor
		map.add(new Entity(0.0f, 0.0f, Settings.WindowWidth, 50.0f)); //Ceiling
		map.add(new Entity(0.0f, 0.0f, 10.f, Settings.WindowHeight)); //Left Wall
		map.add(new Entity(Settings.WindowWidth - 10.0f, 0.0f, 10.f, Settings.WindowHeight)); //Right Wall
		map.add(new Entity(0.0f, twoThirdsHeight, oneThirdWidth, 25.0f)); //Left Platform
		map.add(new Entity(Settings.WindowWidth - oneThirdWidth, twoThirdsHeight, oneThirdWidth, 25.0f)); //Right Platform
		map.add(new Entity(oneHalfWidth - (oneThirdWidth / 2.0f), oneThirdHeight, oneThirdWidth, 25.0f)); //Center Platform
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		for(int i = 0; i < map.size(); i++) {
			map.get(i).paint(g);
		}
		player1.paint(g);
		player2.paint(g);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		player1.update(gc.getInput(), delta, map, null);
		player2.update(gc.getInput(), delta, map, null);
	}

	@Override
	public int getID() {
		return id;
	}
}
