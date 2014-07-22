package com.sircular.circle.levels.extra.entities;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import com.sircular.circle.engine.Keyboard;
import com.sircular.circle.engine.MathUtils;
import com.sircular.circle.levels.extra.Camera;
import com.sircular.circle.levels.extra.Collidable;

public class Player extends Collidable {
	
	private final static String IMG_PATH = "/com/sircular/circle/data/assets/img/player.png";
	
	// arbitrary; used for variable frame rate; tweak to change speed

	private final float FRICTION = 0.99f;
	private final float BOUNCINESS = 0.2f;

	private float rotation = 0;
	private float rotvel = 0;
	
	private boolean canJump;
		
	public Player() {
		canJump = false;
		
		try {
			this.image = ImageIO.read(this.getClass().getResource(IMG_PATH));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.x = 0;
		this.y = 0;
	}
	
	public void update(long delta, List<Rectangle> tiles, List<Collidable> entities, Camera cam) {
		// run Collidable update
		this.update(delta,tiles, entities);
		// move the camera
		cam.centerTowards((int)this.x, (int)this.y, 0.1f);
	}
	
	@Override
	public void update(long delta, List<Rectangle> tiles, List<Collidable> entities) {
		accelerate(delta, 0, GRAVITY);
		
		if (Keyboard.isKeyDown(KeyEvent.VK_A))
			accelerate(delta, -0.1f, 0);
		if (Keyboard.isKeyDown(KeyEvent.VK_D))
			accelerate(delta, 0.1f, 0);
		if (Keyboard.isKeyDown(KeyEvent.VK_W) && canJump)
			yvel = -7;
		
		canJump = false;
		
		//xvel *= FRICTION;
		//yvel *= FRICTION;
		
		this.x += deltaAdjust(delta, xvel);
		this.y += deltaAdjust(delta, yvel);
		this.rotation += deltaAdjust(delta, rotvel);
		
		// check for collisions
		
		// tile collisions
		for (Rectangle box : tiles) {
			Area boxArea = new Area(box);
			Side side = this.hasCollided(boxArea, true);
			handleCollisions(delta, new Area(box), side);
		}
		
		for (Collidable entity : entities) {
			Area colArea = entity.getCollisionShape();
			Side side = this.hasCollided(colArea, entity.allowsInsideCollision());
			if (entity.onCollide(side))
				handleCollisions(delta, colArea, side);
		}
	}
	
	private void handleCollisions(long delta, Area area, Side side) {
		
		Rectangle box = area.getBounds();
		
		if (side == Side.TOP) {
			this.yvel = MathUtils.setSign(this.yvel*BOUNCINESS, -1);
			this.xvel *= FRICTION;
			this.rotvel = deltaAdjust(delta, this.xvel)/20f;
			
			this.y = (float) (box.getMinY()-(this.image.getHeight()/2));
			// we can jump, because we're on the floor
			canJump = true;
		} else if (side == Side.BOTTOM) {
			this.yvel = MathUtils.setSign(this.yvel*BOUNCINESS, 1);
			this.xvel *= FRICTION;
			this.rotvel = -deltaAdjust(delta, this.xvel)/20f;
			
			this.y =  (float) (box.getMaxY()+(this.image.getHeight()/2));
		} else if (side == Side.LEFT) {
			this.xvel = MathUtils.setSign(this.xvel*BOUNCINESS, -1);
			this.yvel *= FRICTION;
			this.rotvel = -deltaAdjust(delta, this.yvel)/20f;
			
			this.x = (float) (box.getMinX()-(this.image.getWidth()/2));
		} else if (side == Side.RIGHT) {
			this.xvel = MathUtils.setSign(this.xvel*BOUNCINESS, 1);
			this.yvel *= FRICTION; 
			this.rotvel = deltaAdjust(delta, this.yvel)/20f;
			
			this.x = (float) (box.getMaxX()+(this.image.getWidth()/2));
		}
	}
	
	public Area getCollisionShape() {
		return new Area(new Ellipse2D.Float(this.x-this.image.getWidth()/2, this.y-this.image.getHeight()/2, this.image.getWidth(), this.image.getHeight()));
	}
	
	public void draw(Graphics2D g2, Camera cam) {
		Point frame = cam.getFramePosition();
		
		g2.translate(this.x-frame.x, this.y-frame.y);
		g2.rotate(rotation);
		
		g2.drawImage(image, -this.image.getWidth()/2, -this.image.getHeight()/2, null);
		
		g2.rotate(-rotation);
		g2.translate(-this.x+frame.x, -this.y+frame.y);
	}

}
