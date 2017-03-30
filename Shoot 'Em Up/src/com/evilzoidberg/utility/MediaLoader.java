package com.evilzoidberg.utility;

import java.util.HashMap;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SpriteSheet;

public class MediaLoader {
	private static HashMap<String, Image> images = new HashMap<String, Image>();
	private static HashMap<String, Animation> animations = new HashMap<String, Animation>();
	private static HashMap<String, Sound> sounds = new HashMap<String, Sound>();
	private static HashMap<String, Music> music = new HashMap<String, Music>();
	
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
	
	public static boolean unloadAnimation(String path) {
		/**
		 * Removes the animation associated with the passed path from memory. Returns true if
		 * successfully removed and false if that animation wasn't in memory to begin with.
		 */
		if(animations.remove(path) == null) {
			return false;
		}
		return true;
	}
	
	public static boolean unloadSound(String path) {
		/**
		 * Removes the sound associated with the passed path from memory. Returns true if
		 * successfully removed and false if that sound wasn't in memory to begin with.
		 */
		if(sounds.remove(path) == null) {
			return false;
		}
		return true;
	}
	
	public static boolean unloadMusic(String path) {
		/**
		 * Removes the music associated with the passed path from memory. Returns true if
		 * successfully removed and false if that music wasn't in memory to begin with.
		 */
		if(music.remove(path) == null) {
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
			anim = new Animation(sheet, 100);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		animations.put(path, anim);
		return anim;
	}
	
	public static Sound getSound(String path) {
		/**
		 * If that sound has been loaded before, will use the copy already in memory.
		 * If it hasn't been loaded before, will load it and store it in memory.
		 */
		if(sounds.containsKey(path)) {
			return sounds.get(path);
		}
		Sound sound = null;
		try {
			sound = new Sound(path);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		sounds.put(path, sound);
		return sound;
	}
	
	public static Music getMusic(String path) {
		/**
		 * If that music has been loaded before, will use the copy already in memory.
		 * If it hasn't been loaded before, will load it and store it in memory.
		 */
		if(music.containsKey(path)) {
			return music.get(path);
		}
		Music result = null;
		try {
			result = new Music(path);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		music.put(path, result);
		return result;
	}
}
