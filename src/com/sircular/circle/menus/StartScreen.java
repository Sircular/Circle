package com.sircular.circle.menus;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.Scanner;

import com.sircular.circle.engine.StateEngine;
import com.sircular.circle.engine.TextRenderer;

public class StartScreen extends MenuClass {
	
	private int milliWait = 4000;
	
	private final BufferedImage textImg;
	private final BufferedImage logo;

	public StartScreen(StateEngine engine, MainMenu menu, int width, int height) {
		super(engine, menu, width, height);
		
		// random name generation
		// no bounds checking, because I check the files myself
		// (BAD IDEA, BUT I'M LAZY!)
		Random r = new Random();
		
		Scanner adjScan = new Scanner(this.getClass().getResourceAsStream("/com/sircular/circle/data/assets/adj.txt"));
		Scanner nounScan = new Scanner(this.getClass().getResourceAsStream("/com/sircular/circle/data/assets/noun.txt"));
		
		int adjCount = adjScan.nextInt();
		int nounCount = nounScan.nextInt();
		
		int adjInd = r.nextInt(adjCount); 
		int nounInd = r.nextInt(nounCount);
		
		for (int i = 0; i < adjInd; i++) {
			adjScan.next();
		}
		String adj = adjScan.next();
		
		for (int i = 0; i < nounInd; i++) {
			nounScan.next();
		}
		String noun = nounScan.next();
		
		textImg = TextRenderer.renderText(adj+" "+noun+" Games", 2, Color.white);
		logo = TextRenderer.renderText(adj.charAt(0)+""+noun.charAt(0), 7, Color.white);
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(long delta, Point mousePos) {
		milliWait -= (int)delta;
		
		if (milliWait <= 0)
			this.menu.setMenu(new Menu1(this.engine, this.menu, this.width, this.height));
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(textImg, (this.width-this.textImg.getWidth())/2, (int)(this.height*0.56), null);
		g.drawImage(logo, (this.width-this.logo.getWidth())/2, (int)(this.height*0.3), null);
	}

	@Override
	public void cleanup() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void buttonPressed(int id) {
		// TODO Auto-generated method stub
		
	}

}
