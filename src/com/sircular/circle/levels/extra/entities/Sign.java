package com.sircular.circle.levels.extra.entities;

import java.io.IOException;

import javax.imageio.ImageIO;

import com.sircular.circle.levels.extra.Collidable;

public class Sign extends Collidable {
	
	private String text;
	
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

}
