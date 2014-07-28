package com.sircular.circle.engine;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

import com.sircular.circle.levels.extra.Camera;

public class Sprite {
	
	public float x, y;
	protected float xvel, yvel;
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
	
	public void moveTo(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	protected void accelerate(long delta, float xaccel, float yaccel) {
		this.xvel += xaccel*delta;
		this.yvel += yaccel*delta;
	}
	
	public void update(long delta) {
		
	}
	
	public void draw(Graphics2D g2, Camera cam) {
		Point frame = cam.getFramePosition();
		g2.drawImage(image, (int)this.x-(this.image.getWidth()/2)-frame.x, (int)this.y-(this.image.getHeight()/2)-frame.y, null);
	}
	
	
}
