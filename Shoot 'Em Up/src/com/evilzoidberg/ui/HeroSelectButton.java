package com.evilzoidberg.ui;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import com.evilzoidberg.Settings;
import com.evilzoidberg.utility.MediaLoader;

public class HeroSelectButton extends Button {
	public int heroID;
	public boolean highlightedByPlayer1 = false;
	public boolean highlightedByPlayer2 = false;
	Image player1Flag = MediaLoader.getImage(Settings.Player1FlagImagePath);
	Image player2Flag = MediaLoader.getImage(Settings.Player2FlagImagePath);

	public HeroSelectButton(Image image, int heroID, int x, int y) {
		super(image, x, y);
		this.heroID = heroID;
	}
	
	@Override
	public void paint(Graphics g) {
		/**
		 * Draws the button to the screen and adds highlighting to show if this button
		 * is selected by one or more players.
		 */
		image.getScaledCopy(3).draw(rect.getX(), rect.getY());
		
		if(highlightedByPlayer1) {
			player1Flag.draw(rect.getX(), rect.getY());
		}
		if(highlightedByPlayer2) {
			player2Flag.draw(rect.getX() + (image.getWidth() * 3) - player2Flag.getWidth(), rect.getY());
		}
	}
}
