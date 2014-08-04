package com.sircular.circle.engine.animation;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class CrossfadeAnimation implements Animation {
	
	private int fadeTime;
	private int timePassed = 0;
	
	private int currentFrame = 0;
	private int nextFrame = 1;
	
	private BufferedImage[] images;
	
	public CrossfadeAnimation(BufferedImage[] images, int fadeTime) {
		if (images.length < 2) 
			throw new IllegalArgumentException("Cannot crossfade with less than two image");
		this.fadeTime = fadeTime;
		this.images = images;
	}

	@Override
	public void update(long delta) {
		timePassed += delta;
		while (timePassed >= fadeTime) {
			timePassed -= fadeTime;
			
			currentFrame = nextFrame;
			nextFrame = (nextFrame+1) % images.length;
		}
	}

	@Override
	public BufferedImage getImage() {
		float fac = timePassed/(float)fadeTime;
		
		BufferedImage composite = new BufferedImage(images[0].getWidth(), images[0].getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = (Graphics2D) composite.getGraphics();
		
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1-fac));
		g2.drawImage(images[currentFrame], 0, 0, null);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, fac));
		g2.drawImage(images[nextFrame], 0, 0, null);
		
		g2.dispose(); // I really should do this in lots of places
		
		return composite;
	}
	

}
