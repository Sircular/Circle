package com.sircular.circle.levels.extra;

import java.awt.Rectangle;
import java.awt.geom.Area;

import com.sircular.circle.engine.Sprite;

public abstract class Collidable extends Sprite {
	
	protected final float GRAVITY = 0.02f;
	
	public enum Side {
		TOP,
		LEFT,
		BOTTOM,
		RIGHT
	}
	
	public void update(long delta) {
		
	}

	
	public abstract Area getCollisionShape();
	
	public Side hasCollided(Area area, boolean inside) { // returns which side was hit, null if no collision occurred
		Area area1 = this.getCollisionShape();
		Area area2 = (Area) area.clone();
		
		float oldX = this.x-this.xvel;
		float oldY = this.y-this.yvel;
		
		float centerX = (this.x+oldX)/2f;
		float centerY = (this.y+oldY)/2f;
		
		area1.intersect(area2);
		if (!area1.isEmpty() || area.contains(centerX, centerY)) {
			Rectangle bounds = area2.getBounds();
			
			float distX = (float) Math.max(bounds.getMinX()-oldX, oldX-bounds.getMaxX());
			float distY = (float) Math.max(bounds.getMinY()-oldY, oldY-bounds.getMaxY());
			
			if (distY > distX) {
				boolean bottomCondition = inside ? oldY > bounds.getCenterY() : oldY > bounds.getMaxY() && this.yvel < 0;
				boolean topCondition = inside ? oldY < bounds.getCenterY() : oldY < bounds.getMinY() && this.yvel > 0;
				if (bottomCondition) {
					return Side.BOTTOM;
				} else if (topCondition) {
					return Side.TOP;
				}
			} else {
				boolean rightCondition = inside ? oldX > bounds.getCenterX() : oldX > bounds.getMaxX() && this.xvel < 0;
				boolean leftCondition = inside ? oldX < bounds.getCenterX() : oldX < bounds.getMinX() && this.xvel > 0;
				if (rightCondition) {
					return Side.RIGHT;
				} else if (leftCondition) {
					return Side.LEFT;
				}
			}
		}
		return null;
	}
	
	public boolean allowsInsideCollision() {
		return true;
	}
	
	public boolean isSolid() {
		return true;
	}
	
	public boolean onCollide(Collidable entity, Side side) { // true if it is solid from that side
		return side != null; // null means there was no collision; this is for lazy programmers (me) only.
	}

}
