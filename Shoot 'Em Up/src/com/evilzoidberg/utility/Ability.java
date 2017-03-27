package com.evilzoidberg.utility;

import org.newdawn.slick.Animation;

import com.evilzoidberg.entities.HeroEntity;

public class Ability extends Cooldown {
	public Animation animation;

	public Ability(Animation animation, int duration) {
		super(duration);
		this.animation = animation;
	}
	
	@Override
	public void update(int delta) {
		super.update(delta);
		animation.update(delta);
	}
	
	public boolean attemptToUse(HeroEntity hero) {
		if(super.attemptToUse()) {
			animation.restart();
			hero.currentAnimation = animation;
			return true;
		}
		return false;
	}
}
