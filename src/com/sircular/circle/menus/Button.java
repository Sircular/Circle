package com.sircular.circle.menus;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.sircular.circle.engine.Mouse;
import com.sircular.circle.engine.animation.ReversibleAnimation;

public class Button {
	
	private ButtonHandler parent;
	private int id;
	
	private int x, y;
	private ReversibleAnimation anim;
	
	private boolean hovering;
	private boolean mouseDown;
	
	public Button(ButtonHandler parent, int id, int x, int y, int width, int height, BufferedImage[] imgs) {
		this.parent = parent;
		this.id = id;
		
		this.x = x;
		this.y = y;
		
		this.anim = new ReversibleAnimation(imgs, 60, false);
	}
	
	public int getID() {
		return id;
	}
	
	public void update(long delta, Point mousePos) {
		BufferedImage img = anim.getImage();
		Rectangle box = new Rectangle(x-(img.getWidth()/2), y-(img.getHeight()/2), img.getWidth(), img.getHeight());
		hovering = box.contains(mousePos);
		if (!mouseDown && Mouse.isButtonDown(Mouse.LEFT_BUTTON))
			if (hovering)
				this.parent.buttonPressed(id);
		mouseDown = Mouse.isButtonDown(Mouse.LEFT_BUTTON);
		
		// update the animation
		anim.setMovingForward(hovering);
		anim.update(delta);
	}
	
	public void draw(Graphics g) {
		BufferedImage img = anim.getImage();
		g.drawImage(img, x-(img.getWidth()/2), y-(img.getHeight()/2), null);
	}
}
