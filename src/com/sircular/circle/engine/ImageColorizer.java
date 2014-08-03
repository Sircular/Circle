package com.sircular.circle.engine;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class ImageColorizer {
	
	public static BufferedImage colorizeImage(BufferedImage image, Color color) {
		BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = (Graphics2D) newImage.getGraphics();
		
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, color.getAlpha()/255f)); // transparency stuff
		g2.drawImage(image, 0, 0, null);
		
		Color opColor = new Color(color.getRGB() | 0xFF000000); // sets the alpha using bit transforms
		g2.setColor(opColor);
		g2.setComposite(AlphaComposite.SrcAtop);
		g2.fillRect(0, 0, image.getWidth(), image.getHeight());
		
		return newImage;
	}

}
