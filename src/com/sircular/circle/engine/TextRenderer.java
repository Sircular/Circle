package com.sircular.circle.engine;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class TextRenderer {
	
	private static final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890!@#$%^&*()-_+=.,";
	private static final String IMG_DIR = "/com/sircular/circle/data/assets/img/";
	
	private static final String DEFAULT_FONT = "text";
	
	private static final int GLYPH_WIDTH = 12;
	private static final int GLYPH_HEIGHT = 14;
	
	private static int glyphWidth, glyphHeight;
	private static Map<Character, BufferedImage> glyphs;
	private static String currentFont = "";
	
	private static void loadFont(int glyphWidth, int glyphHeight, String imgPath) {
		TextRenderer.glyphWidth = glyphWidth;
		TextRenderer.glyphHeight = glyphHeight;
		
		glyphs = new HashMap<Character, BufferedImage>();
		
		BufferedImage[] images = ImageLoader.loadSpriteSheet(imgPath, glyphWidth, glyphHeight);
		for (int i = 0; i < chars.length(); i++) {
			glyphs.put(chars.charAt(i), images[i]);
		}
	}
	
	public static BufferedImage renderText(String text, int scale) {
		return renderText(text, scale, Color.black, DEFAULT_FONT);
	}
	
	public static BufferedImage renderText(String text, int scale, Color col) {
		return renderText(text, scale, col, DEFAULT_FONT);
	}
	
	public static BufferedImage renderText(String text, int scale, Color col, String fontName) {
		if (fontName == null)
			fontName = DEFAULT_FONT;
		
		if (!currentFont.equals(fontName)) {
			loadFont(GLYPH_WIDTH, GLYPH_HEIGHT, IMG_DIR+fontName+".png");
			currentFont = fontName;
		}
		
		BufferedImage renderedImg = new BufferedImage(glyphWidth*text.length()*scale, glyphHeight*scale, BufferedImage.TYPE_INT_ARGB);
		
		Graphics2D g2 = (Graphics2D) renderedImg.getGraphics();
		
		g2.setComposite(AlphaComposite.SrcOver);
		
		for (int i = 0; i < text.length(); i++) {
			BufferedImage glyph = glyphs.get(text.toUpperCase().charAt(i));
			g2.drawImage(glyph, i*glyphWidth*scale, 0, glyphWidth*scale, glyphHeight*scale, null);
		}
		
		return ImageTransform.colorizeImage(renderedImg, col);
	}
	
	

}
