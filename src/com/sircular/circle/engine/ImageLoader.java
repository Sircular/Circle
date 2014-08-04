package com.sircular.circle.engine;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageLoader {
	
	public static BufferedImage loadImage(String path) { // try/catches are annoying sometimes
		try {
			return ImageIO.read(ImageLoader.class.getResource(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static BufferedImage[] loadSpriteSheet(String path, int tileWidth, int tileHeight) {
		try {
			BufferedImage raw = ImageIO.read(ImageLoader.class.getResource(path));
			int vertTileCount = raw.getHeight()/tileHeight;
			int horizTileCount = raw.getWidth()/tileWidth;
			BufferedImage[] tiles = new BufferedImage[vertTileCount*horizTileCount];
			for (int y = 0; y < vertTileCount; y++) {
				for (int x = 0; x < horizTileCount; x++) {
					BufferedImage tile = raw.getSubimage(x*tileWidth, y*tileHeight, tileWidth, tileHeight);
					tiles[x+(y*horizTileCount)] = tile;
				}
			}
			return tiles;
		} catch (IOException e) {
			return null;
		}
	}
}
