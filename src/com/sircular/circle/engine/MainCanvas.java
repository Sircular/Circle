package com.sircular.circle.engine;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class MainCanvas extends JPanel{
	
	private static final long serialVersionUID = 1943500282452199718L;
	
	private BufferedImage image;

	public MainCanvas(int width, int height) {
		this.setPreferredSize(new Dimension(width, height));
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	}
	
	public Graphics getCanvasGraphics() {
		return image.getGraphics();
	}
	
	@Override
	public void paint(Graphics g) {
		g.drawImage(image, 0, 0, null);
	}

}
