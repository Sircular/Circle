package com.sircular.circle.menus;

import java.awt.Color;
import java.awt.image.BufferedImage;

import com.sircular.circle.engine.ImageLoader;
import com.sircular.circle.engine.SlicedImage;
import com.sircular.circle.engine.TextRenderer;

public class ButtonFactory {
	
	private final static String BUTTON_IMG_PATH = "/com/sircular/circle/data/assets/img/button.png";
	
	public static Button createTextButton(ButtonHandler parent, int id, int x, int y, int width, String text) {
		BufferedImage[] sheet = ImageLoader.loadSpriteSheet(BUTTON_IMG_PATH, 48, 48);
		BufferedImage[] imgs = new BufferedImage[sheet.length];
		
		for (int i = 0; i < sheet.length; i++) { 
			BufferedImage img = sheet[i];
			SlicedImage slice = new SlicedImage(img, 8, 24, 40, 25);
			BufferedImage textImg = TextRenderer.renderText(text, 2, Color.white);
			BufferedImage textImg2 = TextRenderer.renderText(text, 2, new Color(40, 40, 40, 255));
			BufferedImage bgImg = slice.render(width, 48); // because we don't include the outer edges
			bgImg.getGraphics().drawImage(textImg2, 3+(bgImg.getWidth()-textImg.getWidth())/2,
					3+(bgImg.getHeight()-textImg.getHeight())/2, null);
			bgImg.getGraphics().drawImage(textImg, (bgImg.getWidth()-textImg.getWidth())/2,
					(bgImg.getHeight()-textImg.getHeight())/2, null);
			imgs[i] = bgImg;
		}
		
		return new Button(parent, id, x, y, width, 48, imgs);
	}

}
