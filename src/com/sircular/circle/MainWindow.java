package com.sircular.circle;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;

import com.sircular.circle.engine.Keyboard;
import com.sircular.circle.engine.MainCanvas;
import com.sircular.circle.engine.Mouse;
import com.sircular.circle.engine.StateEngine;
import com.sircular.circle.levels.MainLevel;

public class MainWindow extends JFrame implements MouseListener, MouseMotionListener, KeyListener {

	private static final long serialVersionUID = 1L;
	private static final int FPS = 60;
	
	private MainCanvas canvas;
	private StateEngine engine;
	private boolean running;
	private int milliDelay; // calculated during init so we don't recalculate every frame;
	
	public MainWindow() {
		running = false;
	}
	
	public static void main(String[] args) {
		MainWindow window = new MainWindow();
		window.init(640, 480, "Circle");
		window.loop();
	}
	
	public void init(int width, int height, String title) {
		milliDelay = 1000/FPS;
		
		engine = new StateEngine();
		MainLevel level = new MainLevel(engine, width, height);
		engine.setState(level);
		
		Mouse.x = 0;
		Mouse.y = 0;
		
		this.setTitle(title);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		this.addKeyListener(this);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		
		canvas = new MainCanvas(width, height);
		this.add(canvas);
		this.pack();
		
		this.setVisible(true);
	}
	
	public void loop() {
		// so we don't run this again
		if (running)
			return;
		running = true;
		
		long startTime = System.currentTimeMillis();
		long updateTime = startTime;
		
		while (running) {
			engine.update(updateTime-startTime);
			updateTime = System.currentTimeMillis();
			engine.draw(canvas.getCanvasGraphics());
			
			canvas.repaint();
			this.repaint();
			
			try {
				Thread.sleep(Math.max(0, milliDelay-(System.currentTimeMillis()-startTime)));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			startTime = System.currentTimeMillis();
		}
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
	public void mouseDragged(MouseEvent arg0) {}

	@Override
	public void mouseMoved(MouseEvent e) {
		Mouse.x = e.getX();
		Mouse.y = e.getY();
	}

}
