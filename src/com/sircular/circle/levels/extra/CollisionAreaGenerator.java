package com.sircular.circle.levels.extra;

import java.awt.Rectangle;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;

public class CollisionAreaGenerator {
	
	
	// used to generate collision areas for complex shapes
	// WARNING: VERY SLOW
	public static Area generateCollisionArea(BufferedImage img) {
		
		Area builder = new Area();

		// we now have a white on black image
		int[] imgData = new int[img.getWidth()*img.getHeight()*4];
		img.getData().getPixels(0, 0, img.getWidth(), img.getHeight(), imgData);
		
		int width = img.getWidth();
		int height = img.getHeight();
		
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int ind = ((((y*width)+x))*4)+3; // every fourth item
				if (imgData[ind] > 128) { // more than halfway opaque (wut?)
					builder.add(new Area(new Rectangle(x, y, 1, 1)));
				}
			}
		}
		//System.out.println(builder.getBounds());
		return builder;
	}

}
