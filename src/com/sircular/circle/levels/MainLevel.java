package com.sircular.circle.levels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;

import com.sircular.circle.engine.GameState;
import com.sircular.circle.engine.StateEngine;
import com.sircular.circle.levels.extra.Camera;
import com.sircular.circle.levels.extra.MapLoader;
import com.sircular.circle.levels.extra.Player;
import com.sircular.circle.levels.extra.TileMap;

public class MainLevel extends GameState {
	
	TileMap map;
	Player player;
	Camera camera;

	public MainLevel(StateEngine engine, int width, int height) {
		super(engine, width, height);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init() {
		map = MapLoader.loadMap("level_2");
		player = new Player();
		player.moveTo(300, 100);
		camera = new Camera(this.width, this.height, new Rectangle(0, 0, map.getWidth()*map.getTileSize(), map.getHeight()*map.getTileSize()));
	}

	@Override
	public void update(long delta) {
		player.update(delta, map.getCollisionBoxes(), camera);
	}

	@Override
	public void draw(Graphics g) {
		final int tileSize = map.getTileSize();
		
		if (!(g instanceof Graphics2D)) {
			return;
		}
		Graphics2D g2 = (Graphics2D)g;
		
		// "global" camera position
		Point frame = camera.getFramePosition();
		
		g2.setColor(Color.WHITE);
		g2.fillRect(0, 0, this.width, this.height);
		
		g2.setColor(Color.BLACK);
		
		for (int x = 0; x < map.getWidth(); x++) {
			for (int y = 0; y < map.getHeight(); y++) {
				if (map.getTileAt(x, y) > 0) {
					g2.fillRect((x*tileSize)-frame.x, (y*tileSize)-frame.y, tileSize, tileSize);
				}
			}
		}
		
		g2.setColor(Color.RED);
		
		for (Shape box : map.getCollisionBoxes()) {
			Rectangle b = box.getBounds();
			b.setLocation(b.x-frame.x, b.y-frame.y);
			g2.draw(b);
		}
		
		player.draw(g2, camera);
	}

	@Override
	public void cleanup() {
		// TODO Auto-generated method stub
		
	}

}
