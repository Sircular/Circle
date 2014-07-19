package com.sircular.circle.levels;

import java.awt.Color;
import java.awt.Graphics;

import com.sircular.circle.engine.GameState;
import com.sircular.circle.engine.Mouse;
import com.sircular.circle.engine.StateEngine;

public class MainLevel extends GameState {
	
	private int x = 0;
	private boolean right = true;

	public MainLevel(StateEngine engine, int width, int height) {
		super(engine, width, height);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(long delta) {
		if (x >= this.width-6)
			right = false;
		if (x <= 6)
			right = true;
		
		x += right ? 1 : -1;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, this.width, this.height);
		
		if (!engine.hasFocus())
			return;
		
		g.setColor(Color.WHITE);
		g.fillOval(Mouse.x, Mouse.y, 12, 12);
		
		if (Mouse.isButtonDown(Mouse.LEFT_BUTTON)) {
			g.fillRect(0, 0, 30, 30);
		}
	}

	@Override
	public void cleanup() {
		// TODO Auto-generated method stub
		
	}

}
