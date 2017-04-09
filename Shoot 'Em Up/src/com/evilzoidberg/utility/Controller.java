package com.evilzoidberg.utility;

import org.newdawn.slick.Input;

import com.evilzoidberg.Settings;

public class Controller {
	int up, down, left, right, shoot, ability1, ability2;
	int controllerNumber;
	
	public Controller(int playerNumber) {
		controllerNumber = playerNumber - 1;
		if(playerNumber == 1) {
			up = Settings.Player1Up;
			down = Settings.Player1Down;
			left = Settings.Player1Left;
			right = Settings.Player1Right;
			shoot = Settings.Player1Shoot;
			ability1 = Settings.Player1Ability1;
			ability2 = Settings.Player1Ability2;
		}
		else {
			up = Settings.Player2Up;
			down = Settings.Player2Down;
			left = Settings.Player2Left;
			right = Settings.Player2Right;
			shoot = Settings.Player2Shoot;
			ability1 = Settings.Player2Ability1;
			ability2 = Settings.Player2Ability2;
		}
	}
	
	public boolean isUp(Input in) {
		return in.isKeyPressed(up) || in.isControlPressed(15, controllerNumber);
	}
	
	public boolean isDown(Input in) {
		return in.isKeyDown(down) || in.isControllerDown(controllerNumber);
	}
	
	public boolean isLeft(Input in) {
		return in.isKeyDown(left) || in.isControllerLeft(controllerNumber);
	}
	
	public boolean isRight(Input in) {
		return in.isKeyDown(right) || in.isControllerRight(controllerNumber);
	}
	
	public boolean isShoot(Input in) {
		return in.isKeyPressed(shoot) || in.isControlPressed(13, controllerNumber);
	}
	
	public boolean isAbility1(Input in) {
		return in.isKeyPressed(ability1) || in.isControlPressed(17, controllerNumber);
	}
	
	public boolean isAbility2(Input in) {
		return in.isKeyPressed(ability2) || in.isControlPressed(18, controllerNumber);
	}
	
	public boolean isTaunt(Input in) {
		return in.isControlPressed(16, controllerNumber);
	}
	
	public boolean isMenuConfirm(Input in) {
		return in.isKeyPressed(shoot) || in.isControlPressed(15, controllerNumber);
	}
	
	public boolean isMenuDeny(Input in) {
		return in.isKeyPressed(shoot) || in.isControlPressed(16, controllerNumber);
	}
}
