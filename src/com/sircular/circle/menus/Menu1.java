package com.sircular.circle.menus;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import com.sircular.circle.engine.StateEngine;
import com.sircular.circle.engine.TextRenderer;
import com.sircular.circle.levels.MainLevel;

public class Menu1 extends MenuClass {
	
	private Button[] buttons;

	public Menu1(StateEngine engine, MainMenu menu, int width, int height) {
		super(engine, menu, width, height);
		
		buttons = new Button[2];
		buttons[0] = new Button(this, 0, width/2, 256, 320, "Play");
		buttons[1] = new Button(this, 1, width/2, 320, 320, "Next Menu");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(long delta) {}
	
	@Override
	public void update(long delta, Point mousePos) {
		for (Button b : buttons)
			b.update(delta, mousePos);
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(TextRenderer.renderText("Menu 1", 4, Color.white), 32, 32, null);
		for (Button b : buttons)
			b.draw(g);
	}

	@Override
	public void cleanup() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void buttonPressed(int id) {
		if (id == 0)
			this.engine.setState(new MainLevel(this.engine, this.width, this.height));
		if (id == 1)
			this.menu.setMenu(new Menu2(this.engine, this.menu, this.width, this.height));
		
	}

}
