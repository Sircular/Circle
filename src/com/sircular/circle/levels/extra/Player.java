package com.sircular.circle.levels.extra;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import com.sircular.circle.engine.Keyboard;
import com.sircular.circle.engine.Sprite;

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
		//yvel += GRAVITY;
		
		if (Keyboard.isKeyDown(KeyEvent.VK_A))
			xvel -= 0.1;
		
		xvel *= FRICTION;
		yvel *= FRICTION;
		
		this.x += xvel*((float)delta*DELTA_FACTOR);
		this.y += yvel*((float)delta*DELTA_FACTOR);
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
