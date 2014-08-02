package com.sircular.circle.engine;

import java.awt.Dimension;
import java.awt.Graphics2D;

import com.sircular.circle.levels.extra.Camera;

public abstract class Sprite {
	
	public float x, y;
	protected float xvel, yvel;
	
	public Sprite() {
		this.x = 0;
		this.y = 0;
	}
	
	public Sprite(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public abstract Dimension getSize();
	
	public void moveTo(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	protected void accelerate(long delta, float xaccel, float yaccel) {
		this.xvel += xaccel*delta;
		this.yvel += yaccel*delta;
	}
	
	public abstract void update(long delta);
	
	public abstract void draw(Graphics2D g2, Camera cam);
	
	public boolean shouldRemove() {
		return false;
	}
	
	
}
