package com.sircular.circle.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Keyboard {
	
	private static Map<Integer, Boolean> keyMap = new HashMap<Integer, Boolean>();
	private static List<InputListener> listeners = new ArrayList<InputListener>();
	
	public static void setKeyDown(int keyCode, boolean value) {
		if (isKeyDown(keyCode) == value)
			return;
		
		for (InputListener listener : listeners) {
			if (value)
				listener.keyPressed(keyCode);
			else
				listener.keyReleased(keyCode);
		}
		
		keyMap.put(keyCode, value);
	}
	
	public static boolean isKeyDown(int keyCode) {
		return keyMap.containsKey(keyCode) && keyMap.get(keyCode);
	}
	
	public static void addListener(InputListener listener) {
		listeners.add(listener);
	}
	
	public static void removeListener(InputListener listener) {
		listeners.remove(listener);
	}
	
	public static Map<Integer, Boolean> getKeyMap() {
		return keyMap;
	}

}
