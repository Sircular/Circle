package com.sircular.circle.levels.extra;

import java.awt.Rectangle;
import java.awt.geom.Area;
import java.util.List;

import com.sircular.circle.engine.Sprite;

public abstract class Collidable extends Sprite {
	
	protected final float GRAVITY = 0.2f;
	
	public enum Side {
		TOP,
		LEFT,
		BOTTOM,
		RIGHT
	}
	
	public abstract void update(long delta, List<Rectangle> tiles, List<Collidable> entities);
	
	public Area getCollisionShape() {
		int width = this.image.getWidth();
		int height = this.image.getHeight();
		// JAVA AWT, Y U NEED INTS??
		return new Area(new Rectangle((int)this.x-(width/2), (int)this.y-(height/2), width, height));
	}
	
	public Side hasCollided(Area area) { // returns which side was hit, null if no collision occurred
		Area area1 = this.getCollisionShape();
		Area area2 = (Area) area.clone();
		area1.intersect(area2);
		if (!area1.isEmpty()) {
			Rectangle bounds = area2.getBounds();
			float distX = (float) Math.max(bounds.getMinX()-this.x, this.x-bounds.getMaxX());
			float distY = (float) Math.max(bounds.getMinY()-this.y, this.y-bounds.getMaxY());
			if (distY > distX) {
				if (this.y > bounds.getCenterY() && this.yvel < 0) {
					return Side.BOTTOM;
				} else if (this.y < bounds.getCenterY() && this.yvel > 0) {
					return Side.TOP;
				}
			} else {
				if (this.x > bounds.getCenterX() && this.xvel < 0) {
					return Side.RIGHT;
				} else if (this.x < bounds.getCenterX() && this.xvel > 0) {
					return Side.LEFT;
				}
			}
		}
		return null;
	}
	
	public boolean onCollide(Side side) { // true if it is solid from that side
		return side != null; // null means there was no collision; this is for lazy programmers (me) only.
	}

}
