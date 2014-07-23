package com.sircular.circle.levels.extra.entities;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

import com.sircular.circle.engine.ImageLoader;
import com.sircular.circle.levels.extra.Camera;
import com.sircular.circle.levels.extra.Collidable;

public class Goal extends Collidable {
	
	private BufferedImage[] imgs;
	private int frame;
	
	private final int FPS = 10;
	private int milliDelay;
	
	private int timeUntilNextFrame;
	
	public Goal (int x, int y) {
		milliDelay = 1000/FPS;
		frame = 0;
		
		this.x = x;
		this.y = y;
		
		imgs = ImageLoader.loadSpriteSheet("/com/sircular/circle/data/assets/img/goal.png", 16, 16);
	}
	
	public void update(long delta) {
		timeUntilNextFrame += (int)delta;
		while (timeUntilNextFrame > milliDelay) {
			timeUntilNextFrame -= milliDelay;
			frame = (frame+1)%imgs.length;
		}
	}
	
	@Override
	public void draw(Graphics2D g2, Camera cam) {
		Point fr = cam.getFramePosition();
		g2.drawImage(imgs[frame], (int)x-(imgs[frame].getWidth()/2)-fr.x, (int)y-(imgs[frame].getHeight()/2)-fr.y, null);
	}
	
	@Override
	public Area getCollisionShape() {
		return new Area(new Ellipse2D.Float(x-5, y-5, 10, 10));
	}
	
	public boolean isSolid() {
		return false;
	}

}
