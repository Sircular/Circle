package com.sircular.circle.engine;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Mouse {
	
	public static int x;
	public static int y;
	
	private static Map<Integer, Boolean> buttonMap = new HashMap<Integer, Boolean>();
	private static List<InputListener> listeners = new ArrayList<InputListener>();
	
	public static final int LEFT_BUTTON = MouseEvent.BUTTON1;
	public static final int MIDDLE_BUTTON = MouseEvent.BUTTON2;
	public static final int RIGHT_BUTTON = MouseEvent.BUTTON3;
	
	public static Point position() {
		return new Point(x, y);
	}
	
	public static void setMousePosition(int newX, int newY) {
		if (x == newX && y == newY)
			return;
		
		for (InputListener listener : listeners)
			listener.mouseMoved(newX, newY);
		
		x = newX;
		y = newY;
	}
	
	public static void setButtonDown(int code, boolean value) {
		if (isButtonDown(code) == value)
			return;
		
		for (InputListener listener : listeners) {
			if (value)
				listener.mousePressed(code);
			else
				listener.mouseReleased(code);
		}
		
		buttonMap.put(code, value);
	}
	
	public static boolean isButtonDown(int code) {
		return buttonMap.containsKey(code) && buttonMap.get(code);
	}
	
	public static void addListener(InputListener listener) {
		listeners.add(listener);
	}
	
	public static void removeListener(InputListener listener) {
		listeners.remove(listener);
	}

}
