package com.sircular.circle.engine;

import java.awt.Graphics;

import com.sircular.circle.MainWindow;

public class StateEngine {
	
	private GameState currentState;
	
	private MainWindow window;
	
	private boolean focused;
	
	public StateEngine(MainWindow window) {
		this.window = window;
		
		focused = true;
	}
	
	public void setState(GameState newState) {
		if (this.currentState != null)
			this.currentState.cleanup();
		
		this.currentState = newState;
		
		if (this.currentState != null) {
			this.currentState.init();
			this.window.setMouseVisible(this.currentState.mouseVisible());
		}
	}
	
	public void update(long delta) {
		if (currentState == null) return;
		
		currentState.update(delta);
	}
	
	public void draw(Graphics g) {
		if (currentState == null) return;
		
		currentState.draw(g);
	}
	
	public void setFocused(boolean focused) {
		this.focused = focused;
	}
	
	public boolean hasFocus() {
		return focused;
	}

}
