package com.sircular.circle.engine;

import java.awt.Graphics;

public class StateEngine {
	
	private GameState currentState;
	
	private boolean focused;
	
	public StateEngine() {
		focused = true;
	}
	
	public void setState(GameState newState) {
		if (this.currentState != null)
			this.currentState.cleanup();
		
		this.currentState = newState;
		this.currentState.init();
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
