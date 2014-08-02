package com.sircular.circle.levels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.sircular.circle.engine.GameState;
import com.sircular.circle.engine.Keyboard;
import com.sircular.circle.engine.StateEngine;
import com.sircular.circle.engine.TextRenderer;
import com.sircular.circle.menus.MainMenu;
import com.sircular.circle.menus.Menu1;

public class KeyTestLevel extends GameState {
	
	private BufferedImage helpText = TextRenderer.renderText("Press ESC to Exit", 1, Color.GRAY);

	public KeyTestLevel(StateEngine engine, int width, int height) {
		super(engine, width, height);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(long delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(Graphics g) {
		
		g.setColor(Color.black);
		g.fillRect(0, 0, this.width, this.height);
		
		g.drawImage(helpText, width-(helpText.getWidth()+5), height-(helpText.getHeight()+5), null);
		// bad to do this kind of thing in draw(), but waaay simpler this way.
		int i = 0;
		
		Integer[] keySet = new Integer[Keyboard.getKeyMap().keySet().size()]; // THIS KIND OF THING IS RIDICULOUS, JAVA!
		Keyboard.getKeyMap().keySet().toArray(keySet);
		
		for (Integer key : keySet) {
			if (Keyboard.isKeyDown(key)) {
				// BAD, BAD WALT
				if (key == 27) { // we've hit escape
					MainMenu mainMenu = new MainMenu(engine, width, height);
					Menu1 menu = new Menu1(engine, mainMenu, width, height);
					mainMenu.setMenu(menu);
					engine.setState(mainMenu);
				}
				
				g.drawImage(TextRenderer.renderText(String.valueOf(key), 1, Color.WHITE), 2, (i*16)+2, null);
				i++;
			}
		}
	}

	@Override
	public void cleanup() {
		// TODO Auto-generated method stub
		
	}

}
