package com.sircular.circle.levels.extra.entities;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import com.sircular.circle.engine.Keyboard;
import com.sircular.circle.engine.MathUtils;
import com.sircular.circle.engine.SlicedImage;
import com.sircular.circle.engine.TextRenderer;
import com.sircular.circle.levels.MainLevel;
import com.sircular.circle.levels.extra.Camera;
import com.sircular.circle.levels.extra.Collidable;
import com.sircular.circle.levels.extra.MapLoader;

public class Player extends Collidable {
	
	private final static String IMG_PATH = "/com/sircular/circle/data/assets/img/player.png";
	
	// arbitrary; used for variable frame rate; tweak to change speed
	
	private MainLevel level;

	private final float FRICTION = 0.99f;
	private final float BOUNCINESS = 0.02f;	// needs to be really, REALLY low
											// for some unknown reason

	private float rotation = 0;
	private float rotvel = 0;
	
	private boolean canJump;
	
	private BufferedImage signDisplay;
	
	private SlicedImage signBg;
		
	public Player(MainLevel level) {
		this.level = level;
		
		try {
			signBg = new SlicedImage(ImageIO.read(this.getClass().getResource("/com/sircular/circle/data/assets/img/sign_bg.png")),
					32, 32, 64, 64);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
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
		signDisplay = null;
		
		yvel += GRAVITY*delta;
		
		if (Keyboard.isKeyDown(KeyEvent.VK_A))
			xvel = Math.max(-0.5f*delta, xvel-(0.02f*delta));
		if (Keyboard.isKeyDown(KeyEvent.VK_D))
			xvel = Math.min(0.5f*delta, xvel+(0.02f*delta));
		if (Keyboard.isKeyDown(KeyEvent.VK_W) && canJump)
			yvel = -0.5f*delta;
		
		canJump = false;
		
		//xvel *= FRICTION;
		//yvel *= FRICTION;
		
		this.x += xvel;
		this.y += yvel;
		this.rotation += rotvel;
		
		// check for collisions
		
		// tile collisions
		for (Rectangle box : tiles) {
			Area boxArea = new Area(box);
			Side side = this.hasCollided(boxArea, true);
			handleCollisions(delta, boxArea, side);
		}
		
		boolean removeSign = true;
		
		for (Collidable entity : entities) {
			Area colArea = entity.getCollisionShape();
			Side side = this.hasCollided(colArea, entity.allowsInsideCollision());
			if (entity.onCollide(this, side)) {
				if (entity.isSolid())
					handleCollisions(delta, colArea, side);
				// special logic here
				
				// SIGNS
				if (entity instanceof Sign) {
					removeSign = false;
					if (signDisplay != null)
						continue;
					Sign sign = (Sign)entity;
					String text = sign.getText();
					BufferedImage textImg = TextRenderer.renderText(text, 1, Color.white);
					BufferedImage signImg = signBg.render(textImg.getWidth()+64, textImg.getHeight()+64);
					signImg.getGraphics().drawImage(textImg, 32, 32, null);
					signDisplay = signImg;
				}
				if (entity instanceof Goal) {
					this.level.end();
				}
			}
		}
		
		if (removeSign)
			signDisplay = null;
		
		// move the camera
		cam.centerTowards((int)this.x, (int)this.y, 0.1f);
	}
	
	private void handleCollisions(long delta, Area area, Side side) {
		
		if (side == null)
			return;
		
		Rectangle box = area.getBounds();
		
		if (side == Side.TOP) {
			this.yvel = MathUtils.setSign(this.yvel*BOUNCINESS, -1)*delta;
			this.xvel *= FRICTION;
			this.rotvel = (xvel)/20f;
			
			this.y = (float) (box.getMinY()-(this.image.getHeight()/2));
			// we can jump, because we're on the floor
			canJump = true;
		} else if (side == Side.BOTTOM) {
			this.yvel = MathUtils.setSign(this.yvel*BOUNCINESS, 1)*delta;
			this.xvel *= FRICTION;
			this.rotvel = (xvel)/-20f;
			
			this.y =  (float) (box.getMaxY()+(this.image.getHeight()/2));
		} else if (side == Side.LEFT) {
			this.xvel = MathUtils.setSign(this.xvel*BOUNCINESS, -1)*delta;
			this.yvel *= FRICTION;
			this.rotvel = (yvel)/-20f;
			
			this.x = (float) (box.getMinX()-(this.image.getWidth()/2));
		} else if (side == Side.RIGHT) {
			this.xvel = MathUtils.setSign(this.xvel*BOUNCINESS, 1)*delta;
			this.yvel *= FRICTION; 
			this.rotvel =(yvel)/20f;
			
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
		
		if (signDisplay != null) {
			Dimension camSize = cam.getSize();
			int yPos = (int) (this.y-frame.y > camSize.height/2 ? (camSize.height/4)-(signDisplay.getHeight()/2) : (camSize.height*0.75f)-(signDisplay.getHeight()/2));
			g2.drawImage(signDisplay, (camSize.width/2)-(signDisplay.getWidth()/2), yPos, null);
		}
		
		// useful debug
		g2.drawImage(TextRenderer.renderText((int)(this.x/MapLoader.TILE_SIZE)+", "+(int)(this.y/MapLoader.TILE_SIZE), 1), 48, 48, null);
	}

}
