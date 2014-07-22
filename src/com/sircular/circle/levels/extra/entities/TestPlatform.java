package com.sircular.circle.levels.extra.entities;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;

import com.sircular.circle.levels.extra.Collidable;

public class TestPlatform extends Collidable {
	
	private boolean rising = false;
	
	public TestPlatform(int x, int y) {
		this.x = x;
		this.y = y;
		
		this.image = new BufferedImage(320, 32, BufferedImage.TYPE_INT_RGB);
	}

	@Override
	public boolean onCollide(Side side) {
		if (side == Side.TOP) {
			rising = true;
			return true;
		}
		return false;
	}

	@Override
	public void update(long delta, List<Rectangle> tiles,
			List<Collidable> entities) {
		if (rising && y > 120)
			y--;
		
	}
	@Override
	public boolean allowsInsideCollision() {
		return false;
	}
	
	

}
