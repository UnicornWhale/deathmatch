package com.evilzoidberg;

import org.newdawn.slick.Color;
import org.newdawn.slick.Input;

public class Settings {
	public static boolean debug = true;
	
	public static int WindowWidth = 1200;
	public static int WindowHeight = 680;

	public static Color Player1Color = Color.red;
	public static int Player1Up = Input.KEY_W;
	public static int Player1Down = Input.KEY_S;
	public static int Player1Left = Input.KEY_A;
	public static int Player1Right = Input.KEY_D;
	public static int Player1Shoot = Input.KEY_V;

	public static Color Player2Color = Color.blue;
	public static int Player2Up = Input.KEY_UP;
	public static int Player2Down = Input.KEY_DOWN;
	public static int Player2Left = Input.KEY_LEFT;
	public static int Player2Right = Input.KEY_RIGHT;
	public static int Player2Shoot = Input.KEY_ENTER;
}
