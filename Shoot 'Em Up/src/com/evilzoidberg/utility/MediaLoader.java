package com.evilzoidberg.utility;

import java.util.HashMap;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import com.evilzoidberg.Settings;

public class MediaLoader {
	private static HashMap<String, Image> images = new HashMap<String, Image>();
	private static HashMap<String, Animation> animations = new HashMap<String, Animation>();
	
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
	
	public static Animation getAnimation(String path, int frame_width, int frame_height) {
		/**
		 * If that animation has been loaded before, will use the copy already in memory.
		 * If it hasn't been loaded before, will load it and store it in memory.
		 */
		if(animations.containsKey(path)) {
			return animations.get(path);
		}
		Animation anim = null;
		try {
			SpriteSheet sheet = new SpriteSheet(path, frame_width, frame_height);
			anim = new Animation(sheet, 1000 / Settings.AnimationsFPS);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		animations.put(path, anim);
		return anim;
	}
}
