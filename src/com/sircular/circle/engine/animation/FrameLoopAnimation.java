package com.sircular.circle.engine.animation;

import java.awt.image.BufferedImage;

public class FrameLoopAnimation implements Animation {
	
	private int milliDelay;
	private int elapsedTime = 0; // I can't think of a better name right now
	
	private BufferedImage[] frames;
	private int currentFrame = 0;
	
	public FrameLoopAnimation(BufferedImage[] frames, int fps) {
		this.frames = frames;
		this.milliDelay = 1000/fps;
	}

	@Override
	public void update(long delta) {
		elapsedTime += delta;
		while (elapsedTime >= milliDelay) {
			elapsedTime -= milliDelay;
			currentFrame = (currentFrame+1) % frames.length;
		}
	}

	@Override
	public BufferedImage getImage() {
		return frames[currentFrame];
	}

}
