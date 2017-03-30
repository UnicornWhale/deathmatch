package com.evilzoidberg.utility;

import org.newdawn.slick.Animation;

import com.evilzoidberg.entities.HeroEntity;

public class Ability extends Cooldown {
	public Animation onGround, inAir;

	public Ability(Animation animation, int duration) {
		super(duration);
		this.onGround = animation;
		this.inAir = animation;
	}

	public Ability(Animation onGroundAnimation, Animation inAirAnimation, int duration) {
		super(duration);
		this.onGround = onGroundAnimation;
		this.inAir = inAirAnimation;
	}
	
	@Override
	public void update(int delta) {
		super.update(delta);
		onGround.update(delta);
		inAir.update(delta);
	}
	
	public boolean ready() {
		return current >= length;
	}
	
	public boolean attemptToUse(HeroEntity hero) {
		if(super.attemptToUse()) {
			if(!hero.onGround) {
				inAir.restart();
				hero.currentAnimation = inAir;
			}
			else {
				onGround.restart();
				hero.currentAnimation = onGround;
			}
			return true;
		}
		return false;
	}
}
