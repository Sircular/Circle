package com.sircular.circle.levels.extra.entities;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;

import com.sircular.circle.levels.extra.Collidable;

public class TestPlatform extends Collidable {
	
	public TestPlatform(int x, int y) {
		this.x = x;
		this.y = y;
		
		this.image = new BufferedImage(320, 16, BufferedImage.TYPE_INT_RGB);
	}

	@Override
	public boolean onCollide(Side side) {
		// TODO Auto-generated method stub
		return side == Side.TOP;
	}

	@Override
	public void update(long delta, List<Rectangle> tiles,
			List<Collidable> entities) {
		// TODO Auto-generated method stub
		
	}
	
	

}
