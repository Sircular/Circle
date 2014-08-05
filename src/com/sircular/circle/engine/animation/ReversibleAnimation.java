package com.sircular.circle.engine.animation;

import java.awt.image.BufferedImage;

public class ReversibleAnimation implements Animation {
	
	protected BufferedImage[] imgs;
	protected int currentFrame = 0;
	
	protected int milliDelay;
	protected int elapsedTime = 0;
	
	protected boolean forward; // true if we're moving forward, false if backward
	
	
	public ReversibleAnimation(BufferedImage[] imgs, int fps, boolean forward) {
		this.imgs = imgs;
		this.milliDelay = 1000/fps;
		this.forward = forward;
	}
	
	public void setMovingForward(boolean value) {
		forward = value;
	}
	
	public boolean getMovingForward() {
		return forward;
	}

	@Override
	public void update(long delta) {
		if ((forward && currentFrame >= imgs.length-1) || (!forward && currentFrame <= 0)) {
			elapsedTime = 0;
			return;
		}
		
		elapsedTime += delta;
		while (elapsedTime >= milliDelay) {
			elapsedTime -= milliDelay;
			currentFrame = forward ? Math.min(imgs.length-1, currentFrame+1) : Math.max(0, currentFrame-1);
		}
		
	}

	@Override
	public BufferedImage getImage() {
		return imgs[currentFrame];
	}

}
