package com.evilzoidberg.maploader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.newdawn.slick.SpriteSheet;

import com.evilzoidberg.Settings;
import com.evilzoidberg.utility.MediaLoader;

public class MapLoader {
	public static ArrayList<Tile> loadMap(String path) {
		SpriteSheet map_tiles = new SpriteSheet(MediaLoader.getImage(Settings.MapTileSheetPath), 64, 64);
		
		ArrayList<Tile> map = new ArrayList<Tile>();
		BufferedReader in;
	    String line;
	    int y = 0;
		try {
			in = new BufferedReader(new FileReader(path));
			while((line=in.readLine())!=null){
			    char[] chars = line.toCharArray();
			    for(int x = 0; x < chars.length; x++) {
			    	if(chars[x] == '.') {
			    		map.add(new Tile(map_tiles.getSubImage(4, 0), x * Settings.TileSize, y * Settings.TileSize, false));
			    	}
			    	else if(chars[x] == 'L') {
			    		map.add(new Tile(map_tiles.getSubImage(1, 0), x * Settings.TileSize, y * Settings.TileSize, true));
			    	}
			    	else if(chars[x] == 'R') {
			    		map.add(new Tile(map_tiles.getSubImage(3, 0), x * Settings.TileSize, y * Settings.TileSize, true));
			    	}
			    	else if(chars[x] == 'O') {
			    		map.add(new Tile(map_tiles.getSubImage(2, 0), x * Settings.TileSize, y * Settings.TileSize, true));
			    	}
			    	else {
			    		map.add(new Tile(map_tiles.getSubImage(0, 0), x * Settings.TileSize, y * Settings.TileSize, false));
			    	}
			    }
			    y++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}
}
