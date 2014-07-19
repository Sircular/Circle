package com.sircular.circle.engine;

/*
 * Used so that if it is wished, you can use event-driven rather than loop-driven input.
 * This might be somewhat useful.
 * 
 * Also, the calling code guards against repeat events (such as a held-down key
 * triggering multiple keyPressed events.)
 */

public interface InputListener {
	
	public void keyPressed(int keyCode);
	public void keyReleased(int keyCode);
	
	public void mouseMoved(int x, int y);
	public void mousePressed(int button);
	public void mouseReleased(int button);

}
