package com.sircular.circle.levels.extra.entities;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.util.List;

import com.sircular.circle.engine.ImageLoader;
import com.sircular.circle.levels.extra.ActiveCollidable;
import com.sircular.circle.levels.extra.Camera;
import com.sircular.circle.levels.extra.Collidable;

public class Enemy extends ActiveCollidable{
	
	private boolean alive = true;
	private boolean movingRight; // false is left, true is right (obviously)
	
	private BufferedImage[] frames;
	
	public Enemy(int x, int y) {
		this.x = x;
		this.y = y;
		
		this.frames = ImageLoader.loadSpriteSheet("/com/sircular/circle/data/assets/img/enemy.png", 20, 20);
	}
	
	private BufferedImage currentFrame() {
		return frames[movingRight ? 1 : 0];
	}

	@Override
	public void update(long delta, List<Rectangle> tiles,
			List<Collidable> entities) {
		// we don't do anything with other entities
		this.yvel += this.GRAVITY*delta;
		
		this.x += xvel;
		this.y += yvel;
		
		for (Rectangle tile : tiles) {
			Side collided = hasCollided(new Area(tile), false);
			if (collided != null) {
				if (collided == Side.BOTTOM) {
					this.yvel = 0; //stop upward movement
					this.y = (float) (tile.getMaxY() + (currentFrame().getHeight()/2));
				}
				if (collided == Side.TOP) {
					this.yvel = 0; // stop downward movement
					this.y = (float) (tile.getMinY() - (currentFrame().getHeight()/2));
				}
				if (collided == Side.RIGHT) {
					movingRight = true;
					this.x = (float) (tile.getMaxX() + (currentFrame().getWidth()/2));
				}
				if (collided == Side.LEFT) {
					movingRight = false;
					this.x = (float) (tile.getMinX() - (currentFrame().getWidth()/2));
				}
			}
			
		}
		this.xvel = movingRight ? 0.1f*delta : -0.1f*delta;
		
	}
	
	@Override
	public void draw(Graphics2D g2, Camera cam) {
		Point frame = cam.getFramePosition();
		BufferedImage img = currentFrame();
		
		g2.drawImage(img, (int)this.x-frame.x-(img.getWidth()/2), (int)this.y-frame.y-(img.getHeight()/2), null);
	}
	
	@Override
	public boolean shouldRemove() {
		return !alive;
	}
	
	@Override
	public boolean onCollide(Collidable other, Side side) {
		boolean wasAlive = alive;
		if (side == Side.TOP && other instanceof Player) {
			System.out.println("DEAD!");
			alive = false;
		}
		return wasAlive;
	}
	
	public boolean isSolid() {
		return true;
	}
	
	public Area getCollisionArea() {
		BufferedImage img = currentFrame();
		int width = img.getWidth();
		int height = img.getHeight();
		
		return new Area(new Rectangle((int)this.x-(width/2), (int)this.y-(width/2), width, height));
	}

}
