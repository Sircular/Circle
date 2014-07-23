package com.sircular.circle;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import com.sircular.circle.engine.Keyboard;
import com.sircular.circle.engine.MainCanvas;
import com.sircular.circle.engine.Mouse;
import com.sircular.circle.engine.StateEngine;
import com.sircular.circle.engine.TextRenderer;
import com.sircular.circle.menus.MainMenu;
import com.sircular.circle.menus.Menu1;

public class MainWindow extends JFrame implements KeyListener, FocusListener {

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
		
		TextRenderer.loadFont(6, 7, "/com/sircular/circle/data/assets/img/text.png");
		
		engine = new StateEngine();
		MainMenu level = new MainMenu(engine, width, height);
		level.setMenu(new Menu1(engine, level, width, height));
		engine.setState(level);
		
		Mouse.x = 0;
		Mouse.y = 0;
		
		this.setTitle(title);
		this.setName(title);

		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		this.addKeyListener(this);
		this.addFocusListener(this);
		
		canvas = new MainCanvas(width, height);
		this.add(canvas);
		this.pack();
		
		this.setVisible(true);
	}
	
	@Override
	public void dispose() {
		running = false;
	}
	
	public void loop() {
		// so we don't run this again
		if (running)
			return;
		running = true;
		
		long startTime = System.currentTimeMillis();
		long updateTime = startTime;
		
		while (running) {
			engine.update(startTime-updateTime);
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
		
		engine.setState(null);
		engine = null;

		System.exit(0);
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
	public void focusGained(FocusEvent e) {
		engine.setFocused(true);		
	}

	@Override
	public void focusLost(FocusEvent arg0) {
		engine.setFocused(false);		
	}

}
