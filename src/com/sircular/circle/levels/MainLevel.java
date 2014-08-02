package com.sircular.circle.levels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.util.Iterator;
import java.util.List;

import com.sircular.circle.engine.GameState;
import com.sircular.circle.engine.InputListener;
import com.sircular.circle.engine.Keyboard;
import com.sircular.circle.engine.StateEngine;
import com.sircular.circle.engine.TextRenderer;
import com.sircular.circle.levels.extra.ActiveCollidable;
import com.sircular.circle.levels.extra.Camera;
import com.sircular.circle.levels.extra.Collidable;
import com.sircular.circle.levels.extra.MapLoader;
import com.sircular.circle.levels.extra.TileMap;
import com.sircular.circle.levels.extra.entities.Enemy;
import com.sircular.circle.levels.extra.entities.Player;
import com.sircular.circle.menus.MainMenu;
import com.sircular.circle.menus.Menu1;

public class MainLevel extends GameState {
	
	TileMap map;
	Player player;
	Camera camera;
	
	List<Collidable> entities;
	
	private InputListener pauseListener;
	private boolean paused = false;

	public MainLevel(StateEngine engine, int width, int height) {
		super(engine, width, height);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init() {		
		map = MapLoader.loadMap("level_2");
		player = new Player(this);
		player.moveTo(48, 400);
		entities = MapLoader.loadMapEntities("level_2");
		
		entities.add(new Enemy(500, 150));
		
		camera = new Camera(this.width, this.height, new Rectangle(0, 0, map.getWidth()*map.getTileSize(), map.getHeight()*map.getTileSize()));
	
		// listener for pause menu
		pauseListener = new InputListener() {

			@Override
			public void keyPressed(int keyCode) {
				paused = keyCode == 80 ? !paused : paused;
			}

			@Override
			public void keyReleased(int keyCode) {}

			@Override
			public void mouseMoved(int x, int y) {}

			@Override
			public void mousePressed(int button) {}

			@Override
			public void mouseReleased(int button) {}
			
		};
		
		Keyboard.addListener(pauseListener);
	}

	@Override
	public void update(long delta) {
		if (!paused) {
			Iterator<Collidable> it = entities.iterator();
			while (it.hasNext()) {
				Collidable entity = it.next();
				
				if (entity.shouldRemove()) {
					it.remove();
					continue;
				}
				
				if (entity instanceof ActiveCollidable)
					((ActiveCollidable)entity).update(delta, map.getCollisionBoxes(), entities);
				else
					entity.update(delta);
			}
			
			player.update(delta, map.getCollisionBoxes(), entities, camera);
		}
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
		
		g2.setColor(Color.gray);
		g2.fillRect(0, 0, this.width, this.height);
		
		for (int x = 0; x < map.getWidth(); x++) {
			for (int y = 0; y < map.getHeight(); y++) {
				int type = map.getTileAt(x, y);
				if (type > 0) {
					g2.drawImage(map.getTileImage(type-1), (x*tileSize)-frame.x, (y*tileSize)-frame.y, null);
				}
			}
		}
		
		g2.setColor(Color.RED);
		
		for (Rectangle box : map.getCollisionBoxes()) {
			g2.drawRect(box.x-frame.x, box.y-frame.y, box.width, box.height);
		}
		
		for (Collidable sprite : entities) {
			sprite.draw(g2, camera);
			g2.draw(sprite.getCollisionShape().createTransformedArea(AffineTransform.getTranslateInstance(-frame.x, -frame.y)));
		}
		
		player.draw(g2, camera);
		
		g2.draw(player.getCollisionShape().createTransformedArea(AffineTransform.getTranslateInstance(-frame.x, -frame.y)));
		
		if (paused) {
			g2.setColor(new Color(0, 0, 0, 128));
			g2.fillRect(0, 0, width, height);
			g2.drawImage(TextRenderer.renderText("(Menu Not Implemented)", 2, Color.white), 40, 40, null);
		}
	}
	
	public void end() {
		MainMenu mm = new MainMenu(this.engine, this.width, this.height);
		Menu1 menu = new Menu1(this.engine, mm, this.width, this.height);
		mm.setMenu(menu);
		this.engine.setState(mm);
	}

	@Override
	public void cleanup() {
		Keyboard.removeListener(pauseListener);	
	}

	@Override
	public boolean mouseVisible() {
		return false;
	}

}
