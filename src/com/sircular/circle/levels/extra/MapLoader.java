package com.sircular.circle.levels.extra;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;

import javax.imageio.ImageIO;

public class MapLoader {
	
	private static final String LEVEL_PATH = "/com/sircular/circle/data/assets/levels/";
	private static final String LEVEL_EXT = ".png";
	
	private static final int[] COLORS = {
		0xffffff, // empty space
		0x000000, // solid tile
	};
	
	public static TileMap loadMap(String name) {
		String mapPath = LEVEL_PATH+name+LEVEL_EXT;
		try {
			BufferedImage rawImage = ImageIO.read(MapLoader.class.getResource(mapPath));
			BufferedImage convImage = new BufferedImage(rawImage.getWidth(), rawImage.getHeight(), BufferedImage.TYPE_INT_RGB);
			convImage.getGraphics().drawImage(rawImage, 0, 0, null);
			
			int[] imgData = ((DataBufferInt)convImage.getData().getDataBuffer()).getData(); 
			int[][] mapData = new int[rawImage.getHeight()][rawImage.getWidth()];
			
			for (int i = 0; i < imgData.length; i++) {
				int x = i%rawImage.getWidth();
				int y = i/rawImage.getWidth();
				
				for (int j = 0; j < COLORS.length; j++) {
					if (imgData[i] == COLORS[j]) {
						mapData[y][x] = j	;
						break;
					}
				}
			}
			
			return new TileMap(mapData);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

}
