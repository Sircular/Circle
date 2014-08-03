package com.sircular.circle.levels.extra.entities;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.util.List;

import com.sircular.circle.levels.extra.ActiveCollidable;
import com.sircular.circle.levels.extra.Camera;
import com.sircular.circle.levels.extra.Collidable;

public class TestPlatform extends ActiveCollidable {
	
	private boolean rising = false;
	private BufferedImage image;
	
	public TestPlatform(int x, int y) {
		this.x = x;
		this.y = y;
		
		this.image = new BufferedImage(320, 32, BufferedImage.TYPE_INT_RGB);
	}

	@Override
	public boolean onCollide(Collidable other, Side side) {
		if (side == Side.TOP && other instanceof Player) {
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

	@Override
	public void draw(Graphics2D g2, Camera cam) {
		Point frame = cam.getFramePosition();
		g2.drawImage(image, (int)x-image.getWidth()/2-frame.x, (int)y-image.getHeight()/2-frame.y, null);		
	}

	@Override
	public Area getCollisionArea() {
		return new Area(new Rectangle((int)x-image.getWidth()/2, (int)y-image.getHeight()/2, image.getWidth(), image.getHeight()));

	}
	
	

}
