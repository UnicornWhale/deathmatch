package com.evilzoidberg.ui;

import org.newdawn.slick.Graphics;

import com.evilzoidberg.Settings;

public class HeroSelectButton extends Button {
	public int heroID;
	public boolean highlightedByPlayer1 = false;
	public boolean highlightedByPlayer2 = false;
	public boolean selectedByPlayer1 = false;
	public boolean selectedByPlayer2 = false;

	public HeroSelectButton(String text, int heroID, int x, int y, int width, int height) {
		super(text, x, y, width, height);
		this.heroID = heroID;
	}
	
	@Override
	public void paint(Graphics g) {
		/**
		 * Draws the button to the screen and adds highlighting to show if this button
		 * is selected by one or more players.
		 */
		super.paint(g);
		
		if(selectedByPlayer1) {
			g.setColor(Settings.Player1Color);
			g.fillRect(rect.getMinX(), rect.getMinY(), rect.getWidth() / 2, rect.getHeight() / 4);
		}
		else if(highlightedByPlayer1) {
			g.setColor(Settings.Player1Color);
			g.drawRect(rect.getMinX(), rect.getMinY(), rect.getWidth() / 2, rect.getHeight() / 4);
		}
		
		if(selectedByPlayer2) {
			g.setColor(Settings.Player2Color);
			g.fillRect(rect.getMaxX() - rect.getHeight() / 4, rect.getMinY(), rect.getWidth() / 2, rect.getHeight() / 4);
		}
		else if(highlightedByPlayer2) {
			g.setColor(Settings.Player2Color);
			g.drawRect(rect.getMaxX() - rect.getHeight() / 4, rect.getMinY(), rect.getWidth() / 2, rect.getHeight() / 4);
		}
	}
}
