package com.sircular.circle.levels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import com.sircular.circle.engine.GameState;
import com.sircular.circle.engine.StateEngine;
import com.sircular.circle.levels.extra.MapLoader;
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
		map = MapLoader.loadMap("level_1");
		player = new Player();
		player.moveTo(300, 100);
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
		
		for (int x = 0; x < map.getWidth(); x++) {
			for (int y = 0; y < map.getHeight(); y++) {
				if (map.getTileAt(x, y) > 0) {
					g.fillRect(x*32, y*32, 32, 32);
				}
			}
		}
		
		player.draw(g2);
	}

	@Override
	public void cleanup() {
		// TODO Auto-generated method stub
		
	}

}
