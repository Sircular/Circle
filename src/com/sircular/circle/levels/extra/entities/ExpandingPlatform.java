package com.sircular.circle.levels.extra.entities;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.util.List;

import com.sircular.circle.engine.ImageLoader;
import com.sircular.circle.engine.animation.ReversibleCollidableAnimation;
import com.sircular.circle.levels.extra.ActiveCollidable;
import com.sircular.circle.levels.extra.Camera;
import com.sircular.circle.levels.extra.Collidable;

public class ExpandingPlatform extends ActiveCollidable {
	
	private int x, y;
	private ReversibleCollidableAnimation anim;
	
	private int timeLeft = 0;
	
	public ExpandingPlatform(int x, int y) {
		this.x = x;
		this.y = y;
		
		int[] heights = {44, 41, 34, 28, 22, 16, 10, 4, 0};
		
		Area[] collAreas = new Area[heights.length];
		
		for (int i = 0; i < heights.length; i++) {
			Area builder = new Area(new Rectangle(0, 54, 32, 10));
			builder.add(new Area(new Rectangle(0, heights[i], 32, 10)));
			builder.add(new Area(new Rectangle(8, heights[i], 16, 54-heights[i])));
			collAreas[i] = builder;
		}
		
		anim = new ReversibleCollidableAnimation(ImageLoader.loadSpriteSheet("/com/sircular//circle/data/assets/img/platform.png",
				32, 64), collAreas, 2, false);
	}
	// we just need this for the delta
	@Override
	public void update(long delta, List<Rectangle> tiles,
			List<Collidable> entities) {
		timeLeft += delta;
		if (timeLeft >= 8000) { // every four seconds
			anim.setMovingForward(!anim.getMovingForward());
			timeLeft -= 8000;
		}
		anim.update(delta);
		
	}

	@Override
	public Area getCollisionArea() {
		// TODO Auto-generated method stub
		Area area = anim.getCollisionArea().createTransformedArea(AffineTransform.getTranslateInstance(this.x-16, this.y-48));
		//System.out.println(area.getBounds());
		return area;
	}

	@Override
	public void draw(Graphics2D g2, Camera cam) {
		Point frame = cam.getFramePosition();
		BufferedImage img = anim.getImage();
		g2.drawImage(img, this.x-frame.x-img.getWidth()/2, this.y-frame.y-img.getHeight()/2-16 /* it's two tiles high */, null);
	}

}
