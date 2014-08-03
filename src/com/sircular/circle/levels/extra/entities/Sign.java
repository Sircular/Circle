package com.sircular.circle.levels.extra.entities;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.sircular.circle.levels.extra.Camera;
import com.sircular.circle.levels.extra.Collidable;

public class Sign extends Collidable {
	
	private String text;
	private BufferedImage image;
	
	public Sign(int x, int y, String text) {
		this.x = x;
		this.y = y;
		this.text = text;
		
		try {
			this.image = ImageIO.read(this.getClass().getResource("/com/sircular/circle/data/assets/img/sign.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getText() {
		return this.text;
	}
	
	public boolean isSolid() {
		return false;
	}
	
	@Override
	public void draw(Graphics2D g2, Camera cam) {
		Point frame = cam.getFramePosition();
		g2.drawImage(this.image, (int)this.x-image.getWidth()/2-frame.x, (int)this.y-image.getHeight()/2-frame.y, null);
	}

	@Override
	public Area getCollisionArea() {
		return new Area(new Rectangle((int)x-image.getWidth()/2, (int)y-image.getHeight()/2, image.getWidth(), image.getHeight()));
	}

}
