package com.sircular.circle.levels.extra;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import com.sircular.circle.engine.Keyboard;
import com.sircular.circle.engine.Sprite;
import com.sircular.circle.engine.Utils;

public class Player extends Sprite {
	
	private final static String IMG_PATH = "/com/sircular/circle/data/assets/img/player.png";
	
	private final float DELTA_FACTOR = 0.05f; // arbitrary; used for variable frame rate; tweak to change speed
	
	private final float GRAVITY = 0.1f;
	private final float FRICTION = 0.99f;
	private final float BOUNCINESS = 0.6f;
	
	private float xvel = 2;
	private float yvel = 2;
		
	public Player() {
		try {
			this.image = ImageIO.read(this.getClass().getResource(IMG_PATH));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void update(long delta, List<Shape> collisionBoxes) {
		yvel += GRAVITY;
		
		if (Keyboard.isKeyDown(KeyEvent.VK_A))
			xvel -= 0.1;
		if (Keyboard.isKeyDown(KeyEvent.VK_D))
			xvel += 0.1;
		
		//xvel *= FRICTION;
		//yvel *= FRICTION;
		
		this.x += xvel*((float)delta*DELTA_FACTOR);
		this.y += yvel*((float)delta*DELTA_FACTOR);
		
		// check for collisions
		Area collCircle = new Area(new Ellipse2D.Float(this.x, this.y, this.image.getWidth()/2, this.image.getHeight()/2));
		for (Shape box : collisionBoxes) {
			Area boxArea = new Area(box);
			boxArea.intersect(collCircle);
			if (!boxArea.isEmpty()) {
				Rectangle bounds = box.getBounds();
				// only do one at a time
				if (Math.max(bounds.y-this.y, this.y-(bounds.y+bounds.height)) > Math.max(bounds.x-this.x, this.x-(bounds.y+bounds.height))) {
					if (this.y < bounds.y) {
						this.yvel = Utils.setSign(this.yvel*BOUNCINESS, -1);
						this.xvel *= FRICTION;
						
						this.y = bounds.y-(this.image.getHeight()/2);
					} else if (this.y > bounds.y + bounds.height) {
						this.yvel = Utils.setSign(this.yvel*BOUNCINESS, 1);
						this.xvel *= FRICTION;
						
						this.y = (bounds.y+bounds.height)+(this.image.getHeight()/2);
					}
				} else {
					if (this.x < bounds.x) {
						this.xvel = Utils.setSign(this.xvel*BOUNCINESS, -1);
						this.yvel *= FRICTION;
						
						this.x = bounds.x-(this.image.getWidth()/2);
					} else if (this.x > bounds.x + bounds.width) {
						this.xvel = Utils.setSign(this.xvel*BOUNCINESS, 1);
						this.xvel *= FRICTION; 
						
						this.x = bounds.x+bounds.width+(this.image.getWidth()/2);
					}
				}
				//break;
			}
				
		}
	}
	
	@Override
	public void draw(Graphics2D g2) {
		g2.translate(this.x, this.y);
		g2.rotate(x/25.0);
		
		g2.drawImage(image, -this.image.getWidth()/2, -this.image.getHeight()/2, null);
		
		g2.rotate(-x/25.0);
		g2.translate(-this.x, -this.y);
	}

}
