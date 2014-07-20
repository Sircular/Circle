package com.sircular.circle.levels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;

import com.sircular.circle.engine.GameState;
import com.sircular.circle.engine.StateEngine;
import com.sircular.circle.levels.extra.Player;
import com.sircular.circle.levels.extra.TileMap;

public class MainLevel extends GameState {
	
	TileMap map;
	Player player;

	public MainLevel(StateEngine engine, int width, int height) {
		super(engine, width, height);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init() {
		map = new TileMap();
		player = new Player();
	}

	@Override
	public void update(long delta) {
		player.update(delta, map.getCollisionBoxes());
	}

	@Override
	public void draw(Graphics g) {
		if (!(g instanceof Graphics2D)) {
			return;
		}
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(Color.WHITE);
		g2.fillRect(0, 0, this.width, this.height);
		
		g.setColor(Color.BLACK);
		for (Shape rekt : map.getCollisionBoxes()) {
			g2.fill(rekt.getBounds2D());
		}
		
		player.draw(g2);
	}

	@Override
	public void cleanup() {
		// TODO Auto-generated method stub
		
	}

}
