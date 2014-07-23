package com.sircular.circle.engine;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class TextRenderer {
	
	private static final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890!@#$%^&*()-_+=.,";
	
	private static int glyphWidth, glyphHeight;
	private static Map<Character, BufferedImage> glyphs;
	
	public static void loadFont(int glyphWidth, int glyphHeight, String imgPath) {
		TextRenderer.glyphWidth = glyphWidth;
		TextRenderer.glyphHeight = glyphHeight;
		
		glyphs = new HashMap<Character, BufferedImage>();
		
		BufferedImage[] images = ImageLoader.loadSpriteSheet(imgPath, glyphWidth, glyphHeight);
		for (int i = 0; i < chars.length(); i++) {
			glyphs.put(chars.charAt(i), images[i]);
		}
	}
	
	public static BufferedImage renderText(String text, int scale) {
		return renderText(text, scale, Color.black);
	}
	
	public static BufferedImage renderText(String text, int scale, Color col) {
		if (glyphs == null)
			return null;
		BufferedImage renderedImg = new BufferedImage(glyphWidth*text.length()*scale, glyphHeight*scale, BufferedImage.TYPE_INT_ARGB);
		
		Graphics2D g2 = (Graphics2D) renderedImg.getGraphics();
		
		g2.setComposite(AlphaComposite.Clear);
		g2.setColor(new Color(0, 0, 0, 0));
		g2.fillRect(0, 0, renderedImg.getWidth(), renderedImg.getHeight());
		g2.setComposite(AlphaComposite.SrcOver);
		
		for (int i = 0; i < text.length(); i++) {
			BufferedImage glyph = glyphs.get(text.toUpperCase().charAt(i));
			g2.drawImage(glyph, i*glyphWidth*scale, 0, glyphWidth*scale, glyphHeight*scale, null);
		}
		
		g2.setColor(col);
		g2.setComposite(AlphaComposite.SrcAtop);
		g2.fillRect(0, 0, renderedImg.getWidth(), renderedImg.getHeight());
		
		return renderedImg;
	}
	
	

}
