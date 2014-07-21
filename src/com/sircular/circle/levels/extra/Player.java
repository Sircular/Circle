package com.sircular.circle.levels.extra;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import com.sircular.circle.engine.Keyboard;
import com.sircular.circle.engine.MathUtils;
import com.sircular.circle.engine.Sprite;

public class Player extends Sprite {
	
	private final static String IMG_PATH = "/com/sircular/circle/data/assets/img/player.png";
	
	private final float DELTA_FACTOR = 0.05f; // arbitrary; used for variable frame rate; tweak to change speed
	
	private final float GRAVITY = 0.2f;
	private final float FRICTION = 0.99f;
	private final float BOUNCINESS = 0.6f;
	
	private float xvel = 2;
	private float yvel = 2;
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
	}
	
	public void moveTo(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	private float deltaAdjust(long delta, float value) {
		return value*delta*DELTA_FACTOR;
	}
	
	private void accelerate(long delta, float xaccel, float yaccel) {
		this.xvel += /*deltaAdjust(delta, */xaccel;//);
		this.yvel += /*deltaAdjust(delta, */yaccel;//);
	}
	
	public void update(long delta, List<Rectangle> tileBoxes) {
		accelerate(delta, 0, GRAVITY);
		
		if (Keyboard.isKeyDown(KeyEvent.VK_A))
			accelerate(delta, -0.1f, 0);
		if (Keyboard.isKeyDown(KeyEvent.VK_D))
			accelerate(delta, 0.1f, 0);
		if (Keyboard.isKeyDown(KeyEvent.VK_W) && canJump)
			yvel = -5; // we don't want them to be bounce higher each time
		
		canJump = false;
		
		//xvel *= FRICTION;
		//yvel *= FRICTION;
		
		this.x += deltaAdjust(delta, xvel);
		this.y += deltaAdjust(delta, yvel);
		this.rotation += deltaAdjust(delta, rotvel);
		
		// check for collisions
		Area collCircle = new Area(new Ellipse2D.Float(this.x-this.image.getWidth()/2, this.y-this.image.getHeight()/2, this.image.getWidth(), this.image.getHeight()));
		for (Shape box : tileBoxes) {
			Area boxArea = new Area(box);
			Area circleArea = (Area) collCircle.clone();
			circleArea.intersect(boxArea);
			if (!circleArea.isEmpty()) {
				Rectangle bounds = box.getBounds();
				// only do one direction at a time
				if (Math.max(bounds.getMinY()-this.y, this.y-bounds.getMaxY()) > Math.max(bounds.getMinX()-this.x, this.x-bounds.getMaxX())) {
					if (this.y < bounds.getCenterY()) {
						this.yvel = MathUtils.setSign(this.yvel*BOUNCINESS, -1);
						this.xvel *= FRICTION;
						this.rotvel = deltaAdjust(delta, this.xvel)/20f;
						
						this.y = (float) (bounds.getMinY()-(this.image.getHeight()/2));
						// we can jump, because we're on the floor
						canJump = true;
					} else if (this.y > bounds.getCenterY()) {
						this.yvel = MathUtils.setSign(this.yvel*BOUNCINESS, 1);
						this.xvel *= FRICTION;
						this.rotvel = -deltaAdjust(delta, this.xvel)/20f;
						
						this.y =  (float) (bounds.getMaxY()+(this.image.getHeight()/2));
					}
				} else {
					if (this.x < bounds.getCenterX()) {
						this.xvel = MathUtils.setSign(this.xvel*BOUNCINESS, -1);
						this.yvel *= FRICTION;
						this.rotvel = -deltaAdjust(delta, this.yvel)/20f;
						
						this.x = (float) (bounds.getMinX()-(this.image.getWidth()/2));
					} else if (this.x > bounds.getCenterX()) {
						this.xvel = MathUtils.setSign(this.xvel*BOUNCINESS, 1);
						this.yvel *= FRICTION; 
						this.rotvel = deltaAdjust(delta, this.yvel)/20f;
						
						this.x = (float) (bounds.getMaxX()+(this.image.getWidth()/2));
					}
				}
			}
				
		}
	}
	
	@Override
	public void draw(Graphics2D g2) {
		g2.translate(this.x, this.y);
		g2.rotate(rotation);
		
		g2.drawImage(image, -this.image.getWidth()/2, -this.image.getHeight()/2, null);
		
		g2.rotate(rotation);
		g2.translate(-this.x, -this.y);
	}

}
