package com.sircular.circle.engine;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class SlicedImage {
	
	private BufferedImage[] slices;
	
	public SlicedImage(BufferedImage img, int tlx, int tly, int brx, int bry) {
		slices = new BufferedImage[9];
		
		// top row
		slices[0] = img.getSubimage(0, 0, tlx, tly);
		slices[1] = img.getSubimage(tlx, 0, brx-tlx, tly);
		slices[2] = img.getSubimage(brx, 0, img.getWidth()-brx, tly);
		// middle row
		slices[3] = img.getSubimage(0, tly, tlx, bry-tly);
		slices[4] = img.getSubimage(tlx, tly, brx-tlx, bry-tly);
		slices[5] = img.getSubimage(brx, tly, img.getWidth()-brx, bry-tly);
		// bottom row
		slices[6] = img.getSubimage(0, bry, tlx, img.getHeight()-bry);
		slices[7] = img.getSubimage(tlx, bry, brx-tlx, img.getHeight()-bry);
		slices[8] = img.getSubimage(brx, bry, img.getWidth()-brx, img.getHeight()-bry);
	}
	
	public BufferedImage render(int width, int height) {
		
		BufferedImage renderedImage = new BufferedImage(slices[0].getWidth()+width+slices[8].getWidth(),
				slices[0].getHeight()+height+slices[8].getHeight(), BufferedImage.TYPE_INT_ARGB);
		
		Graphics g = renderedImage.getGraphics();
		// draw the corners
		g.drawImage(slices[0], 0, 0, null);
		g.drawImage(slices[2], slices[0].getWidth()+width, 0, null);
		g.drawImage(slices[6], 0, slices[0].getHeight()+height, null);
		g.drawImage(slices[8], slices[0].getWidth()+width, slices[0].getHeight()+height, null);
		
		// draw the edges
		g.drawImage(renderTiledSection(slices[1], width, slices[0].getHeight()), slices[0].getWidth(), 0, null);
		g.drawImage(renderTiledSection(slices[7], width, slices[8].getHeight()), slices[0].getWidth(), slices[0].getHeight()+height, null);
		g.drawImage(renderTiledSection(slices[3], slices[0].getWidth(), height), 0, slices[0].getHeight(), null);
		g.drawImage(renderTiledSection(slices[5], slices[0].getWidth(), height), slices[0].getWidth()+width, slices[0].getHeight(), null);
		
		// draw the center
		g.drawImage(renderTiledSection(slices[4], width, height), slices[0].getWidth(), slices[0].getHeight(), null);
		
		return renderedImage;
	}
	
	private BufferedImage renderTiledSection(BufferedImage img, int width, int height) {
		BufferedImage renderedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics g = renderedImage.getGraphics();
		
		for (int x = 0; x < Math.ceil(width/(float)img.getWidth()); x++) {
			for (int y = 0; y < Math.ceil(height/(float)img.getHeight()); y++) {
				g.drawImage(img, x*img.getWidth(), y*img.getHeight(), null);
			}
		}
		return renderedImage;
	}

}
