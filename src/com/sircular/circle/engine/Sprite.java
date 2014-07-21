package com.sircular.circle.engine;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;

public class Sprite {
	
	public float x, y;
	protected BufferedImage image;
	
	public Sprite() {
		this.image = null;
		this.x = 0;
		this.y = 0;
	}
	
	public Sprite(BufferedImage image) {
		this.image = image;
		this.x = 0;
		this.y = 0;
	}
	
	public Sprite(int x, int y, BufferedImage image) {
		this.x = x;
		this.y = y;
		this.image = image;
	}
	
	public Area getCollisionShape() {
		int width = this.image.getWidth();
		int height = this.image.getHeight();
		// JAVA AWT, Y U NEED INTS??
		return new Area(new Rectangle((int)this.x-(width/2), (int)this.y-(height/2), width, height));
	}
	
	public void draw(Graphics2D g2) {
		g2.drawImage(image, (int)this.x-(this.image.getWidth()/2), (int)this.y-(this.image.getHeight()/2), null);
	}
	
	
}
