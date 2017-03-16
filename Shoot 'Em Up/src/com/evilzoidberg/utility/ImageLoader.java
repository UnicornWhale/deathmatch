package com.evilzoidberg.utility;

import java.util.HashMap;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class ImageLoader {
	private static HashMap<String, Image> images = new HashMap<String, Image>();
	
	public static boolean unloadImage(String path) {
		/**
		 * Removes the image associated with the passed path from memory. Returns true if
		 * successfully removed and false if that image wasn't in memory to begin with.
		 */
		if(images.remove(path) == null) {
			return false;
		}
		return true;
	}
	
	public static Image getImage(String path) {
		/**
		 * If that image has been loaded before, will use the copy already in memory.
		 * If it hasn't been loaded before, will load it and store it in memory.
		 */
		if(images.containsKey(path)) {
			return images.get(path);
		}
		Image img = null;
		try {
			img = new Image(path);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		images.put(path, img);
		return img;
	}
}
