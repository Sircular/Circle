package com.sircular.circle.levels.extra;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class TileMap {
	
	private int TILE_SIZE;
	private int[][] TILE_MAP;
	
	private BufferedImage[] tileImgs;
	private List<Rectangle> collisionTiles = new ArrayList<Rectangle>();
	
	public TileMap(int[][] mapData, int tileSize, BufferedImage[] tileImgs) {
		this.TILE_MAP = mapData;
		this.TILE_SIZE = tileSize;
		
		this.tileImgs = tileImgs;
		
		// generate collision tiles
		for (int y = 0; y < TILE_MAP.length; y++) {
			for (int x = 0; x < TILE_MAP[0].length; x++) {
				// if we're solid and not surrounded by solid tiles
				if (getTileAt(x, y) > 0 && !((getTileAt(x-1, y) > 0 || x == 0) && (getTileAt(x+1, y) > 0 || x == TILE_MAP[0].length-1) &&
						(getTileAt(x, y-1) > 0 || y == 0) && (getTileAt(x, y+1) > 0 || y == TILE_MAP.length-1))) {
					collisionTiles.add(new Rectangle(x*TILE_SIZE, y*TILE_SIZE, TILE_SIZE, TILE_SIZE));
				}
			}
		}
		
		// reduce the number of tiles
		// done twice to possibly merge large blocks
		for (int i = 0; i < 2; i++) {

			ListIterator<Rectangle> it = collisionTiles.listIterator();
			
			// merge vertically and horizontally
			// we can only use one iterator, requires weird hackery
			while (it.hasNext()) {
				int index = it.nextIndex();
				Rectangle t1 = it.next();
				
				while (it.hasNext()) {
					Rectangle t2 = it.next();
					if ((t1.getMaxY() >= t2.getMinY() && t1.x == t2.x && t1.width == t2.width) ||
							(t1.getMaxX() >= t2.getMinX() && t1.y == t2.y && t1.height == t1.height)) {
						it.remove();
						t1.add(t2);
					}
				}
				// reset the iterator
				while(it.previousIndex() > index)
					it.previous();
			}
		}
		
	}
	
	public int getTileAt(int x, int y) {
		if (y < 0 || y >= TILE_MAP.length || x < 0 || x >= TILE_MAP[y].length) return 0;
		
		return TILE_MAP[y][x];
	}
	
	public BufferedImage getTileImage(int index) {
		return tileImgs[index];
	}
	
	public List<Rectangle> getCollisionBoxes() {
		return collisionTiles;
	}
	
	public int getWidth() {
		return TILE_MAP[0].length; 	// not a guarantee, but it should be correct,
									// since final data will be computer-generated.
	}
	
	public int getHeight() {
		return TILE_MAP.length;
	}
	
	public int getTileSize() {
		return TILE_SIZE;
	}

}
