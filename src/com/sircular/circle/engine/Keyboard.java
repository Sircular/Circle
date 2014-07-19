package com.sircular.circle.engine;

import java.util.HashMap;
import java.util.Map;

public class Keyboard {
	
	private static Map<Integer, Boolean> keyMap = new HashMap<Integer, Boolean>();
	
	public static void setKeyDown(int keyCode, boolean value) {
		keyMap.put(keyCode, value);
	}
	
	public static boolean isKeyDown(int keyCode) {
		return keyMap.containsKey(keyCode) && keyMap.get(keyCode);
	}

}
