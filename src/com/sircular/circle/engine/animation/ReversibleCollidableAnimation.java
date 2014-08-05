package com.sircular.circle.engine.animation;

import java.awt.geom.Area;
import java.awt.image.BufferedImage;

import com.sircular.circle.levels.extra.CollisionAreaGenerator;

public class ReversibleCollidableAnimation extends ReversibleAnimation implements CollidableAnimation {
	
	protected Area[] colAreas;
	
	public ReversibleCollidableAnimation(BufferedImage[] imgs, Area[] colAreas, int fps, boolean forward) {
		super(imgs, fps, forward);
		if (colAreas.length != imgs.length)
			throw new IllegalArgumentException("The number of images and collision areas must be equal.");
		this.colAreas = colAreas;
	}
	
	// convenience constructor
	public ReversibleCollidableAnimation(BufferedImage[] imgs, int fps, boolean forward) {
		super(imgs, fps, forward);
		this.colAreas = new Area[imgs.length];
		
		for (int i = 0; i < imgs.length; i++) {
			colAreas[i] = CollisionAreaGenerator.generateCollisionArea(imgs[i]);
		}
	}

	@Override
	public Area getCollisionArea() {
		return colAreas[currentFrame];
	}

}
