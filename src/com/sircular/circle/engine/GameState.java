package com.sircular.circle.engine;

import java.awt.Graphics;

public abstract class GameState {
	
	protected StateEngine engine;
	protected int width;
	protected int height;
	
	public GameState(StateEngine engine, int width, int height) {
		this.engine = engine;
		
		this.width = width;
		this.height = height;
	}
	
	public abstract void init();
	public abstract void update(long delta);
	public abstract void draw(Graphics g);
	public abstract void cleanup();
	public boolean mouseVisible() { // only is run when the level is loaded, to prevent extra calls
		return true;
	}

}
