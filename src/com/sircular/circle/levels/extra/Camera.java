package com.sircular.circle.levels.extra;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

public class Camera {
	
	private Point center;
	private Rectangle limits; // how far can we move in any direction
	
	private int width, height; // size of the frame
	
	public Camera(int width, int height, Rectangle limits) {
		this.width = width;
		this.height = height;
		
		center = new Point(width/2, height/2);
		this.limits = limits;
	}
	
	public void center(int x, int y) {
		center.x = (int) Math.max(limits.getMinX()+(width/2), Math.min(limits.getMaxX()-(width/2), x));
		center.y = (int) Math.max(limits.getMinY()+(height/2), Math.min(limits.getMaxY()-(height/2), y));
	}
	
	// NOTE: This function is not producing the expected output.
	// Granted, the movement looks nice, but I have no idea why.
	public void centerTowards(int x, int y, float speed) {
		float diffX = x-center.x;
		float diffY = y-center.y;
		diffX *= speed;
		diffY *= speed;
		center(center.x+(int)diffX, center.y+(int)diffY);
	}
	
	public Point getFramePosition() {
		return new Point(center.x-(width/2), center.y-(height/2));
	}
	
	public Dimension getSize() {
		return new Dimension(width, height);
	}

}
