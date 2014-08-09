package com.sircular.circle.engine;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class ImageTransform {
	
	public static BufferedImage colorizeImage(BufferedImage image, Color color) {
		BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = (Graphics2D) newImage.getGraphics();
		
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, color.getAlpha()/255f)); // transparency stuff
		g2.drawImage(image, 0, 0, null);
		
		Color opColor = new Color(color.getRGB() | 0xFF000000); // sets the alpha using bit transforms
		g2.setColor(opColor);
		g2.setComposite(AlphaComposite.SrcAtop);
		g2.fillRect(0, 0, image.getWidth(), image.getHeight());
		
		g2.dispose();
		
		return newImage;
	}
	
	public static BufferedImage tileImage(BufferedImage image, int width, int height) {
		return tileImage(image, width, height, 0, 0);
	}
	
	public static BufferedImage tileImage(BufferedImage image, int width, int height, int xOffset, int yOffset) {
		BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = (Graphics2D) newImage.getGraphics(); // plain old graphics would work here, but it's another import >_<
		
		for (int y = (int)Math.floor(yOffset/(float)image.getHeight()); y <= Math.ceil(height/(float)image.getHeight()); y++) {
			for (int x = (int)(xOffset/(float)image.getWidth()); x <= Math.ceil(width/(float)image.getWidth()); x++) {
				g2.drawImage(image, xOffset+(x*image.getWidth()), yOffset+(y*image.getHeight()),null);
			}
		}
		
		g2.dispose();
		
		return newImage;
	}

}
