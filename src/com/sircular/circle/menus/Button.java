package com.sircular.circle.menus;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.sircular.circle.engine.Mouse;

public class Button {
	
	private MenuClass menu;
	private int id;
	
	private int x, y;
	private BufferedImage[] imgs;
	
	private boolean hovering;
	private boolean mouseDown;
	
	public Button(MenuClass menu, int id, int x, int y, int width, int height, BufferedImage[] imgs) {
		this.menu = menu;
		this.id = id;
		
		this.x = x;
		this.y = y;
		
		this.imgs = imgs;
	}
	
	public int getID() {
		return id;
	}
	
	public void update(long delta, Point mousePos) {
		
		Rectangle box = new Rectangle(x-(imgs[0].getWidth()/2), y-(imgs[0].getHeight()/2), imgs[0].getWidth(), imgs[0].getHeight());
		hovering = box.contains(mousePos);
		if (!mouseDown && Mouse.isButtonDown(Mouse.LEFT_BUTTON))
			if (hovering)
				this.menu.buttonPressed(id);
		mouseDown = Mouse.isButtonDown(Mouse.LEFT_BUTTON);
	}
	
	public void draw(Graphics g) {
		g.drawImage(hovering ? imgs[1] : imgs[0], x-(imgs[0].getWidth()/2), y-(imgs[0].getHeight()/2), null);
	}
}
