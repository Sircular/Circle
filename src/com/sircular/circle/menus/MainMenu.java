package com.sircular.circle.menus;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.sircular.circle.engine.GameState;
import com.sircular.circle.engine.Mouse;
import com.sircular.circle.engine.StateEngine;

public class MainMenu extends GameState {
	
	private MenuClass menu1, menu2;
	private BufferedImage menuImg1, menuImg2;
	
	private float transitioning = 0f;

	public MainMenu(StateEngine engine, int width, int height) {
		super(engine, width, height);
		
		menuImg1 = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		menuImg2 = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		// TODO Auto-generated constructor stub
	}
	
	public void setMenu(MenuClass newMenu) {
		if (menu1 == null)
			menu1 = newMenu;
		else {
			menu2 = newMenu;
			transitioning = 0.01f;
		}
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(long delta) {
		if (transitioning > 0)
			transitioning += (1-transitioning)*0.3*(delta*0.05);
		if (transitioning >= 0.999) {
			menu1 = menu2;
			menu2 = null;
			transitioning = 0f;
		}
		if (transitioning == 0f) { // don't update if we're transitioning
			menu1.update(delta, Mouse.position());
		}
		
	}

	@Override
	public void draw(Graphics g) {
		g.clearRect(0, 0, width, height);
		
		Graphics m1g = menuImg1.getGraphics();
		m1g.clearRect(0, 0, width, height);
		menu1.draw(m1g);
		if (menu2 != null) {
			Graphics m2g = menuImg2.getGraphics();
			m2g.clearRect(0, 0, width, height);
			menu2.draw(m2g);
		}
		
		if (transitioning > 0) {
			g.drawImage(menuImg1, (int)(-transitioning*width), 0, null);
			g.drawImage(menuImg2, (int)((1-transitioning)*width), 0, null);
		} else {
			g.drawImage(menuImg1, 0, 0, null);
		}
	}

	@Override
	public void cleanup() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean mouseVisible() {
		return true;
	}

}
