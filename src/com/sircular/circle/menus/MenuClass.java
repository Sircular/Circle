package com.sircular.circle.menus;

import java.awt.Graphics;
import java.awt.Point;

import com.sircular.circle.engine.StateEngine;

public abstract class MenuClass implements ButtonHandler {
	
	protected StateEngine engine;
	protected MainMenu menu;
	protected int width, height;

	public MenuClass(StateEngine engine, MainMenu menu, int width, int height) {
		this.engine = engine;
		this.width = width;
		this.height = height;
		this.menu = menu;
		// TODO Auto-generated constructor stub
	}
	public abstract void init();
	public abstract void update(long delta, Point mousePos);
	public abstract void draw(Graphics g);
	public abstract void cleanup();
	
	@Override
	public abstract void buttonPressed(int id);

}
