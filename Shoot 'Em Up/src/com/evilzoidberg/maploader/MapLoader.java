package com.evilzoidberg.maploader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.evilzoidberg.Settings;
import com.evilzoidberg.utility.MediaLoader;

public class MapLoader {
	public static ArrayList<Tile> loadMap(String path) {
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
			    		map.add(new Tile(MediaLoader.getImage(Settings.BackgroundTilePath), x * Settings.TileSize, y * Settings.TileSize, false));
			    	}
			    	else {
			    		map.add(new Tile(MediaLoader.getImage(Settings.PlatformTilePath), x * Settings.TileSize, y * Settings.TileSize, true));
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
