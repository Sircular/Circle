package com.sircular.circle.menus;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.sircular.circle.engine.ImageLoader;
import com.sircular.circle.engine.Mouse;
import com.sircular.circle.engine.SlicedImage;
import com.sircular.circle.engine.TextRenderer;

public class Button {
	
	private final String BUTTON_IMG_PATH = "/com/sircular/circle/data/assets/img/button.png";
	
	private MenuClass menu;
	private int id;
	
	private int x, y;
	private BufferedImage[] imgs;
	
	private boolean hovering;
	private boolean mouseDown;
	
	public Button(MenuClass menu, int id, int x, int y, int width, String text) {
		this.menu = menu;
		this.id = id;
		
		this.x = x;
		this.y = y;
		
		BufferedImage[] sheet = ImageLoader.loadSpriteSheet(BUTTON_IMG_PATH, 48, 48);
		imgs = new BufferedImage[sheet.length];
		
		for (int i = 0; i < sheet.length; i++) { 
			BufferedImage img = sheet[i];
			SlicedImage slice = new SlicedImage(img, 8, 24, 40, 25);
			BufferedImage textImg = TextRenderer.renderText(text, 3, Color.white);
			BufferedImage textImg2 = TextRenderer.renderText(text, 3, new Color(40, 40, 40, 255));
			BufferedImage bgImg = slice.render(width, 1); // because we don't include the outer edges
			bgImg.getGraphics().drawImage(textImg2, 3+(bgImg.getWidth()-textImg.getWidth())/2,
					3+(bgImg.getHeight()-textImg.getHeight())/2, null);
			bgImg.getGraphics().drawImage(textImg, (bgImg.getWidth()-textImg.getWidth())/2,
					(bgImg.getHeight()-textImg.getHeight())/2, null);
			imgs[i] = bgImg;
		}
	}
	
	public int getID() {
		return id;
	}
	
	public void update(long delta, Point mousePos) {
		
		Rectangle box = new Rectangle(x-(imgs[0].getWidth()/2), y-(imgs[0].getHeight()/2), imgs[0].getWidth(), imgs[0].getHeight());
		hovering = box.contains(mousePos);
		if (!mouseDown && Mouse.isButtonDown(Mouse.LEFT_BUTTON))
			if (hovering)
				this.menu.buttonPressed(id);
		mouseDown = Mouse.isButtonDown(Mouse.LEFT_BUTTON);
	}
	
	public void draw(Graphics g) {
		g.drawImage(hovering ? imgs[1] : imgs[0], x-(imgs[0].getWidth()/2), y-(imgs[0].getHeight()/2), null);
	}
}
