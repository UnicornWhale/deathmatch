package com.evilzoidberg.utility;

public class Cooldown {
	int length, current = 0;
	
	public Cooldown(int length) {
		this.length = length;
	}
	
	public void update(int delta) {
		/**
		 * Cools down the delta of time passed in.
		 */
		if(current < length ) {
			current += delta;
		}
	}
	
	public boolean attemptToUse() {
		/**
		 * If the cooldown is ready, returns true and starts new cooldown, else returns false
		 */
		if(current >= length) {
			current = 0;
			return true;
		}
		return false;
	}
}
