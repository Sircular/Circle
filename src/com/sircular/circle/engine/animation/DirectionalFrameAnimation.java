package com.sircular.circle.engine.animation;

import java.awt.image.BufferedImage;

public class DirectionalFrameAnimation implements Animation {
	
	private BufferedImage[] imgs;
	private int currentFrame = 0;
	
	private int milliDelay;
	private int deltaBuffer;
	
	private boolean forward; // true if we're moving forward, false if backward
	
	
	public DirectionalFrameAnimation(BufferedImage[] imgs, int fps, boolean forward) {
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
		deltaBuffer += delta;
		while (deltaBuffer >= milliDelay) {
			deltaBuffer -= milliDelay;
			currentFrame = forward ? Math.min(imgs.length-1, currentFrame+1) : Math.max(0, currentFrame-1);
		}
		
	}

	@Override
	public BufferedImage getImage() {
		return imgs[currentFrame];
	}

}
