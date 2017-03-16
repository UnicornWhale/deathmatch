package com.evilzoidberg.maploader;

import org.newdawn.slick.Image;

import com.evilzoidberg.Settings;
import com.evilzoidberg.entities.Entity;

@SuppressWarnings("serial")
public class Tile extends Entity {
	public boolean collideable;

	public Tile(Image image, float x, float y, boolean collideable) {
		super(image, x, y, Settings.TileSize, Settings.TileSize, 0, 0);
		this.collideable = collideable;
	}
}
