package com.sircular.circle.levels.extra;

import java.awt.Rectangle;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.List;

public class TileMap {
	
	private int[][] TILE_MAP;
	
	private List<Shape> collisionTiles = new ArrayList<Shape>();
	
	public TileMap(int[][] mapData) {
		this.TILE_MAP = mapData;
		
		for (int y = 0; y < TILE_MAP.length; y++) {
			for (int x = 0; x < TILE_MAP[0].length; x++) {
				if (getTileAt(x, y) > 0 && !(getTileAt(x-1, y) > 0 && getTileAt(x+1, y) > 0 &&
						getTileAt(x, y-1) > 0 && getTileAt(x, y+1) > 0)) {
					collisionTiles.add(new Rectangle(x*32, y*32, 32, 32));
				}
			}
		}
	}
	
	public int getTileAt(int x, int y) {
		if (y < 0 || y >= TILE_MAP.length || x < 0 || x >= TILE_MAP[y].length) return 0;
		
		return TILE_MAP[y][x];
	}
	
	public List<Shape> getCollisionBoxes() {
		return collisionTiles;
	}
	
	public int getWidth() {
		return TILE_MAP[0].length; 	// not a guarantee, but it should be correct,
									// since final data will be computer-generated.
	}
	
	public int getHeight() {
		return TILE_MAP.length;
	}

}
