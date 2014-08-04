package com.sircular.circle.levels.extra.entities;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

import com.sircular.circle.engine.ImageLoader;
import com.sircular.circle.engine.animation.Animation;
import com.sircular.circle.engine.animation.CrossfadeAnimation;
import com.sircular.circle.engine.animation.FrameLoopAnimation;
import com.sircular.circle.levels.extra.Camera;
import com.sircular.circle.levels.extra.Collidable;

public class Goal extends Collidable {
	
	/*private BufferedImage[] imgs;
	private int frame;
	*/
	private final int FPS = 2; // because we have a crossfader taking care of it
	/*private int milliDelay;
	
	private int timeUntilNextFrame;*/
	
	private Animation anim;
	
	public Goal (int x, int y) {
		this.x = x;
		this.y = y;
		
		anim = new CrossfadeAnimation(ImageLoader.loadSpriteSheet("/com/sircular/circle/data/assets/img/goal.png", 16, 16), 1000/FPS);
	}
	
	public void update(long delta) {
		anim.update(delta);
	}
	
	@Override
	public void draw(Graphics2D g2, Camera cam) {
		Point fr = cam.getFramePosition();
		BufferedImage img = anim.getImage();
		g2.drawImage(img, (int)x-(img.getWidth()/2)-fr.x, (int)y-(img.getHeight()/2)-fr.y, null);
	}
	
	@Override
	public Area getCollisionArea() {
		return new Area(new Ellipse2D.Float(x-5, y-5, 10, 10));
	}
	
	public boolean isSolid() {
		return false;
	}

}
