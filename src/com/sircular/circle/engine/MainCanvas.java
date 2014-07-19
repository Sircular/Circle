package com.sircular.circle.engine;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class MainCanvas extends JPanel implements MouseListener, MouseMotionListener, KeyListener {
	
	private static final long serialVersionUID = 1943500282452199718L;
	
	private BufferedImage image;

	public MainCanvas(int width, int height) {
		this.setPreferredSize(new Dimension(width, height));
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		
		this.addKeyListener(this);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}
	
	public Graphics getCanvasGraphics() {
		return image.getGraphics();
	}
	
	@Override
	public void paint(Graphics g) {
		g.drawImage(image, 0, 0, null);
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		Keyboard.setKeyDown(e.getKeyCode(), true);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		Keyboard.setKeyDown(e.getKeyCode(), false);
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {
		Mouse.setButtonDown(e.getButton(), true);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		Mouse.setButtonDown(e.getButton(), false);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mouseDragged(MouseEvent e) {
		Mouse.x = e.getX();
		Mouse.y = e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		Mouse.x = e.getX();
		Mouse.y = e.getY();
	}

}
