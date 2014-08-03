package com.sircular.circle.engine.animation;

import java.awt.image.BufferedImage;

public interface Animation {
	
	public void update(long delta);
	public BufferedImage getImage();

}
