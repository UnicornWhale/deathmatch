package com.evilzoidberg.maploader;

import java.util.ArrayList;

import com.evilzoidberg.entities.Entity;

public class Map {
	public ArrayList<Tile> tiles;
	public ArrayList<Entity> collideableTiles = new ArrayList<Entity>();
	
	public Map(String path) {
		tiles = MapLoader.loadMap(path);
		for(int i = 0; i < tiles.size(); i++) {
			if(tiles.get(i).collideable) {
				collideableTiles.add(tiles.get(i));
			}
		}
	}
}
