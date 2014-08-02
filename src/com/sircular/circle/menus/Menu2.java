package com.sircular.circle.menus;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import com.sircular.circle.engine.StateEngine;
import com.sircular.circle.engine.TextRenderer;

public class Menu2 extends MenuClass {
	
	private Button button;

	public Menu2(StateEngine engine, MainMenu menu, int width, int height) {
		super(engine, menu, width, height);
		button = ButtonFactory.createTextButton(this, 0, width/2, 256, 320, "Back");
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(TextRenderer.renderText("Menu 2", 4, Color.white), 32, 32, null);
		button.draw(g);
	}

	@Override
	public void cleanup() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(long delta, Point mousePos) {
		button.update(delta, mousePos);
		
	}

	@Override
	public void buttonPressed(int id) {
		this.menu.setMenu(new Menu1(this.engine, this.menu, this.width, this.height));
		
	}

}
