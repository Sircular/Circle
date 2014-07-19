package com.sircular.circle.engine;

import java.awt.Graphics;

public class StateEngine {
	
	private GameState currentState;
	
	public StateEngine() {}
	
	public void setState(GameState newState) {
		this.currentState = newState;
	}
	
	public void update(long delta) {
		if (currentState == null) return;
		
		currentState.update(delta);
	}
	
	public void draw(Graphics g) {
		if (currentState == null) return;
		
		currentState.draw(g);
	}

}
