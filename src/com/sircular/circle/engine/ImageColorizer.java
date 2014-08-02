package com.sircular.circle.engine;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class ImageColorizer {
	
	public static BufferedImage colorizeImage(BufferedImage image, Color color) {
		BufferedImage newImage = image.getSubimage(0, 0, image.getWidth(), image.getHeight());
		Graphics2D g2 = (Graphics2D) newImage.getGraphics();
		g2.setColor(color);
		g2.setComposite(AlphaComposite.SrcAtop);
		g2.fillRect(0, 0, image.getWidth(), image.getHeight());
		return newImage;
	}

}
