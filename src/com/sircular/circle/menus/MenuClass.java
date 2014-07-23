package com.sircular.circle.menus;

import java.awt.Point;

import com.sircular.circle.engine.GameState;
import com.sircular.circle.engine.StateEngine;

public abstract class MenuClass extends GameState {
	
	protected MainMenu menu;

	public MenuClass(StateEngine engine, MainMenu menu, int width, int height) {
		super(engine, width, height);
		this.menu = menu;
		// TODO Auto-generated constructor stub
	}
	
	public abstract void update(long delta, Point mousePos);
	
	public abstract void buttonPressed(int id);

}
