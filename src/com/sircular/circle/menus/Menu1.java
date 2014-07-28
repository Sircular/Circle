package com.sircular.circle.menus;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import com.sircular.circle.engine.StateEngine;
import com.sircular.circle.engine.TextRenderer;
import com.sircular.circle.levels.KeyTestLevel;
import com.sircular.circle.levels.MainLevel;

public class Menu1 extends MenuClass {
	
	private Button[] buttons;

	public Menu1(StateEngine engine, MainMenu menu, int width, int height) {
		super(engine, menu, width, height);
		
		buttons = new Button[4];
		buttons[0] = new Button(this, 0, width/2, 200, 400, "Play");
		buttons[1] = new Button(this, 1, width/2, 264, 400, "Next Menu");
		buttons[2] = new Button(this, 3, width/2, 328, 400, "Keyboard Test");
		buttons[3] = new Button(this, 2, width/2, 392, 400, "Quit");
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
		g.drawImage(TextRenderer.renderText("Menu 1", 2, Color.white), 32, 32, null);
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
		if (id == 2)
			System.exit(0);
		if (id == 3)
			this.engine.setState(new KeyTestLevel(this.engine, this.width, this.height));
	}

}
