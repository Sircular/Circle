package com.sircular.circle.levels.extra;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.imageio.ImageIO;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.sircular.circle.levels.extra.entities.Sign;
import com.sircular.circle.levels.extra.entities.TestPlatform;

public class MapLoader {
	
	private static final int TILE_SIZE = 32; // change this to make tiles bigger or smaller
	
	private static final String LEVEL_PATH = "/com/sircular/circle/data/assets/levels/";
	private static final String LEVEL_IMG_EXT = ".png";
	private static final String LEVEL_META_EXT = ".json";
	
	private static final int[] COLORS = {
		0xffffff, // empty space
		0x000000, // solid tile
	};
	
	private static enum EntityType {
		platform,
		sign
	}
	
	public static TileMap loadMap(String name) {
		String mapPath = LEVEL_PATH+name+LEVEL_IMG_EXT;
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
			
			return new TileMap(mapData, TILE_SIZE);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static List<Collidable> loadMapEntities(String name) {
		String jsonPath = LEVEL_PATH+name+LEVEL_META_EXT;
		JSONParser parser = new JSONParser();
		
		Scanner scanner = new Scanner(MapLoader.class.getResourceAsStream(jsonPath));
		StringBuilder sb = new StringBuilder();
		while (scanner.hasNextLine())
			sb.append(scanner.nextLine());
		scanner.close();
		String rawJson = sb.toString();
		
		try {
			JSONArray data = (JSONArray) ((JSONObject) parser.parse(rawJson)).get("entities");
			
			List<Collidable> entities = new ArrayList<Collidable>();
			
			for (Object rawObj : data) {
				
				JSONObject obj = (JSONObject)rawObj;
				if (!obj.containsKey("type"))
					continue;
				int objX = 0;
				int objY = 0;
				int objW = TILE_SIZE;
				int objH = TILE_SIZE;
				
				// get all the commoon settings
				if (obj.containsKey("tile_x"))
					objX = (int) (TILE_SIZE*((Long) obj.get("tile_x")+0.5));
				if (obj.containsKey("tile_y"))
					objY = (int) (TILE_SIZE*((Long) obj.get("tile_y")+0.5));
				
				if (obj.containsKey("pixel_x"))
					objX += (Long) obj.get("pixel_x");
				if (obj.containsKey("pixel_y"))
					objY += (Long) obj.get("pixel_y");
				
				if (obj.containsKey("tile_width"))
					objW = (int)((long) ((Long) obj.get("tile_width"))); // WUT
				if (obj.containsKey("tile_height"))
					objH = TILE_SIZE*(int)((long) ((Long) obj.get("tile_height"))); // WUT;
				
				if (obj.containsKey("pixel_width"))
					objW += (Long) obj.get("pixel_width");
				if (obj.containsKey("pixel_height"))
					objH += (Long) obj.get("pixel_height");;
				
				// we've already checked that there is a type
				EntityType type = EntityType.valueOf((String) obj.get("type"));
				
				switch (type) {
				case sign:
					String text = (String)obj.get("text");
					Sign sign = new Sign(objX, objY, text);
					entities.add(sign);
					break;
				case platform:
					TestPlatform platform = new TestPlatform(objX, objY);
					entities.add(platform);
					break;
				}
				
				
			}
			return entities;
		} catch (ParseException e) {
			System.out.println(e.getPosition());
			System.out.println(e);
		}
		return null;
	}

}
