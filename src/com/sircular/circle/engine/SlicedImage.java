package com.sircular.circle.engine;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class SlicedImage {
	
	private BufferedImage[] slices;
	
	private final int TOP_LEFT = 0;
	private final int TOP_CENTER = 1;
	private final int TOP_RIGHT = 2;
	private final int CENTER_LEFT = 3;
	private final int CENTER = 4;
	private final int CENTER_RIGHT = 5;
	private final int BOTTOM_LEFT = 6;
	private final int BOTTOM_CENTER = 7;
	private final int BOTTOM_RIGHT = 8;
	
	public SlicedImage(BufferedImage img, int tlx, int tly, int brx, int bry) {
		slices = new BufferedImage[9];
		
		// top row
		slices[TOP_LEFT] = img.getSubimage(0, 0, tlx, tly);
		slices[TOP_CENTER] = img.getSubimage(tlx, 0, brx-tlx, tly);
		slices[TOP_RIGHT] = img.getSubimage(brx, 0, img.getWidth()-brx, tly);
		// middle row
		slices[CENTER_LEFT] = img.getSubimage(0, tly, tlx, bry-tly);
		slices[CENTER] = img.getSubimage(tlx, tly, brx-tlx, bry-tly);
		slices[CENTER_RIGHT] = img.getSubimage(brx, tly, img.getWidth()-brx, bry-tly);
		// bottom row
		slices[BOTTOM_LEFT] = img.getSubimage(0, bry, tlx, img.getHeight()-bry);
		slices[BOTTOM_CENTER] = img.getSubimage(tlx, bry, brx-tlx, img.getHeight()-bry);
		slices[BOTTOM_RIGHT] = img.getSubimage(brx, bry, img.getWidth()-brx, img.getHeight()-bry);
	}
	
	public BufferedImage render(int width, int height) {
		
		BufferedImage renderedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		
		Rectangle bounds = new Rectangle(slices[0].getWidth(), slices[0].getHeight(), 
				width-(slices[0].getWidth()+slices[8].getWidth()), height-(slices[0].getHeight()+slices[8].getHeight()));
		
		Graphics g = renderedImage.getGraphics();
		// draw the corners
		g.drawImage(slices[TOP_LEFT], 0, 0, null);
		g.drawImage(slices[TOP_RIGHT], (int)bounds.getMaxX(), 0, null);
		g.drawImage(slices[BOTTOM_LEFT], 0, (int) bounds.getMaxY(), null);
		g.drawImage(slices[BOTTOM_RIGHT], (int)bounds.getMaxX(), (int)bounds.getMaxY(), null);
		
		// draw the edges
		g.drawImage(ImageTransform.tileImage(slices[TOP_CENTER], (int)bounds.width, (int)bounds.getMinY()), (int)bounds.getMinX(), 0, null);
		g.drawImage(ImageTransform.tileImage(slices[BOTTOM_CENTER], (int)bounds.width, height-(int)bounds.getMaxY()), (int)bounds.getMinX(), (int)bounds.getMaxY(), null);
		g.drawImage(ImageTransform.tileImage(slices[CENTER_LEFT], (int)bounds.getMinX(), bounds.height), 0, (int)bounds.getMinY(), null);
		g.drawImage(ImageTransform.tileImage(slices[CENTER_RIGHT], width-(int)bounds.getMaxX(), bounds.height), (int)bounds.getMaxX(), (int)bounds.getMinX(), null);
		
		// draw the center
		g.drawImage(ImageTransform.tileImage(slices[CENTER], bounds.width, bounds.height), (int)bounds.getMinX(), (int)bounds.getMinY(), null);
		
		return renderedImage;
	}

}
