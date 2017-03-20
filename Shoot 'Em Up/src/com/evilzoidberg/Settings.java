package com.evilzoidberg;

import org.newdawn.slick.Color;
import org.newdawn.slick.Input;

public class Settings {
	public static boolean debug = true;
	
	public static int TileSize = 64;
	public static int WindowWidth = 20 * TileSize;
	public static int WindowHeight = 12 * TileSize;
	
	//Universal Gravitation
	public static float Gravity = 8000.0f;
	
	//Wait times
	public static int waitOnCharacterSelect = 1000;
	public static int waitOnVictory = 1000;
	
	//Animation fps
	public static int AnimationsFPS = 10;

	//Player 1 Controls
	public static Color Player1Color = Color.red;
	public static int Player1Up = Input.KEY_W;
	public static int Player1Down = Input.KEY_S;
	public static int Player1Left = Input.KEY_A;
	public static int Player1Right = Input.KEY_D;
	public static int Player1Shoot = Input.KEY_V;

	//Player 2 Controls
	public static Color Player2Color = Color.blue;
	public static int Player2Up = Input.KEY_UP;
	public static int Player2Down = Input.KEY_DOWN;
	public static int Player2Left = Input.KEY_LEFT;
	public static int Player2Right = Input.KEY_RIGHT;
	public static int Player2Shoot = Input.KEY_ENTER;
	
	//Selected Heroes
	public static int Player1Hero = -1;
	public static int Player2Hero = -1;
	
	//Hero Start Locations
	public static int Player1StartX = 100;
	public static int Player1StartY = 100;
	
	public static int Player2StartX = WindowWidth - 100;
	public static int Player2StartY = 100;

	//Map Paths
	public static String TestMap = "maps/test_map.txt";
	
	//Tile Image Paths
	public static String BackgroundTilePath = "img/map/background_tile.png";
	public static String PlatformTilePath = "img/map/platform_tile.png";
	
	//Giblet Image Path
	public static String GibletImagePath = "img/giblet.png";
	
	//Sugoi Image Paths
	public static String SugoiImagePath = "img/sugoi/sugoi.png";
	public static String ShurikenImagePath = "img/sugoi/shuriken.png";
	
	//Brawn Image Paths
	public static String BrawnIdleAnimationPath = "img/brawn/brawn_idle.png";
	public static String BrawnJumpAnimationPath = "img/brawn/brawn_jump.png";
	public static String BrawnRisingAnimationPath = "img/brawn/brawn_rising.png";
	public static String BrawnFallingAnimationPath = "img/brawn/brawn_falling.png";
	public static String BrawnShootingAnimationPath = "img/brawn/brawn_shoot.png";
	public static String PlasmaBlastAnimationPath = "img/brawn/brawn_bullet.png";
}
