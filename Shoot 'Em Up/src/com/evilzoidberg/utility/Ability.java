package com.evilzoidberg.utility;

import org.newdawn.slick.Animation;

import com.evilzoidberg.entities.HeroEntity;

public class Ability extends Cooldown {
	public Animation onGround, inAir;
	int threshold = 0;
	boolean thresholdUsed = false;

	public Ability(Animation animation, int duration) {
		super(duration);
		this.onGround = animation;
		this.inAir = animation;
	}

	public Ability(Animation animation, int duration, int threshold) {
		super(duration);
		this.onGround = animation;
		this.inAir = animation;
		this.threshold = threshold;
	}

	public Ability(Animation onGroundAnimation, Animation inAirAnimation, int duration) {
		super(duration);
		this.onGround = onGroundAnimation;
		this.inAir = inAirAnimation;
	}

	public Ability(Animation onGroundAnimation, Animation inAirAnimation, int duration, int threshold) {
		super(duration);
		this.onGround = onGroundAnimation;
		this.inAir = inAirAnimation;
		this.threshold = threshold;
	}
	
	@Override
	public void update(int delta) {
		super.update(delta);
	}
	
	public boolean ready() {
		return current >= length;
	}
	
	public boolean attemptThreshold() {
		/**
		 * Will return whether or not the threshold has been reached, but
		 * once it returns true once, will return false until it starts over.
		 */
		if(thresholdUsed) {
			return false;
		}
		boolean ready = current >= threshold;
		if(ready) {
			thresholdUsed = true;
		}
		return ready;
	}
	
	public boolean attemptToUse(HeroEntity hero) {
		if(super.attemptToUse()) {
			thresholdUsed = false;
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
