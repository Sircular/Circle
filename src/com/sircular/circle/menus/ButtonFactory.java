package com.sircular.circle.menus;

import java.awt.Color;
import java.awt.image.BufferedImage;

import com.sircular.circle.engine.ImageColorizer;
import com.sircular.circle.engine.ImageLoader;
import com.sircular.circle.engine.SlicedImage;
import com.sircular.circle.engine.TextRenderer;

public class ButtonFactory {
	
	private final static String BUTTON_IMG_PATH = "/com/sircular/circle/data/assets/img/button.png";
	
	public static Button createTextButton(ButtonHandler parent, int id, int x, int y, int width, String text) {		
		BufferedImage textImg = TextRenderer.renderText(text, 2, Color.white);
		
		return createImageButton(parent, id, x, y, width, 48, textImg, true);
	}
	
	public static Button createImageButton(ButtonHandler parent, int id, int x, int y, int width, int height, BufferedImage image, boolean shadow) {
		BufferedImage[] sheet = ImageLoader.loadSpriteSheet(BUTTON_IMG_PATH, 48, 48);
		BufferedImage[] imgs = new BufferedImage[sheet.length];
		
		for (int i = 0; i < sheet.length; i++) { 
			BufferedImage img = sheet[i];
			SlicedImage slice = new SlicedImage(img, 8, 24, 40, 25);
			
			BufferedImage bgImg = slice.render(width, height); // because we don't include the outer edges
			
			if (shadow) {
				BufferedImage shadowImg = ImageColorizer.colorizeImage(image, new Color(0, 0, 0, 128));
				bgImg.getGraphics().drawImage(shadowImg, 3+(bgImg.getWidth()-image.getWidth())/2,
						3+(bgImg.getHeight()-image.getHeight())/2, null);
			}
			bgImg.getGraphics().drawImage(image, (bgImg.getWidth()-image.getWidth())/2,
					(bgImg.getHeight()-image.getHeight())/2, null);
			imgs[i] = bgImg;
		}
		
		return new Button(parent, id, x, y, width, height, imgs);
	}

}
