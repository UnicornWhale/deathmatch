package com.evilzoidberg;

import org.newdawn.slick.Color;
import org.newdawn.slick.Input;

public class Settings {
	public static boolean debug = true;
	
	public static int TileSize = 64;
	public static int WindowWidth = 20 * TileSize;
	public static int WindowHeight = 12 * TileSize;
	
	//Universal Gravitation
	public static float Gravity = 5000.0f;
	
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
	public static int Player1Ability1 = Input.KEY_C;
	public static int Player1Ability2 = Input.KEY_B;

	//Player 2 Controls
	public static Color Player2Color = Color.blue;
	public static int Player2Up = Input.KEY_UP;
	public static int Player2Down = Input.KEY_DOWN;
	public static int Player2Left = Input.KEY_LEFT;
	public static int Player2Right = Input.KEY_RIGHT;
	public static int Player2Shoot = Input.KEY_ENTER;
	public static int Player2Ability1 = Input.KEY_RSHIFT;
	public static int Player2Ability2 = Input.KEY_BACKSLASH;
	
	//Hero Start Locations
	public static int Player1StartX = 50;
	public static int Player1StartY = 200;
	
	public static int Player2StartX = WindowWidth - 50;
	public static int Player2StartY = 200;
	
	//Selected Heroes
	public static int Player1Hero = -1;
	public static int Player2Hero = -1;
	
	//Selected Map
	public static int SelectedMap = 0;

	//Map Paths
	public static String[] MapPaths = new String[] {
			"res/maps/showdown.txt",
			"res/maps/battlefield.txt",
			"res/maps/nooksandcrannys.txt"
	};

	//Map Names
	public static String[] MapNames = new String[] {
			"Showdown",
			"Battlefield",
			"Nooks and Crannys"
	};
	
	//Tile Image Paths
	public static String BackgroundTilePath = "res/images/map/background_tile.png";
	public static String PlatformTilePath = "res/images/map/platform_tile.png";
	
	//Giblet Image Path
	public static String GibletImagePath = "res/images/giblet.png";
	
	//Sugoi Image Paths
	public static String SugoiIdleAnimationPath = "res/animations/sugoi/sugoi_idle.png";
	public static String SugoiAirIdleAnimationPath = "res/animations/sugoi/sugoi_air_idle.png";
	public static String SugoiShootAnimationPath = "res/animations/sugoi/sugoi_shoot.png";
	public static String ShurikenAnimationPath = "res/animations/sugoi/shuriken.png";
	
	//Brawn Image Paths
	public static String BrawnIdleAnimationPath = "res/animations/brawn/brawn_idle.png";
	public static String BrawnShootAnimationPath = "res/animations/brawn/brawn_shoot.png";
	public static String BrawnBoGIdleAnimationPath = "res/animations/brawn/brawn_bog_idle.png";
	public static String BrawnBoGShootAnimationPath = "res/animations/brawn/brawn_bog_shoot.png";
	public static String BrawnFlexAnimationPath = "res/animations/brawn/brawn_flex.png";
	public static String BrawnBoGAnimationPath = "res/animations/brawn/brawn_bog.png";
	public static String PlasmaBlastAnimationPath = "res/animations/brawn/brawn_bullet.png";
}
