package com.sircular.circle.engine;

import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

public class Mouse {
	
	public static int x;
	public static int y;
	
	private static Map<Integer, Boolean> buttonMap = new HashMap<Integer, Boolean>();
	
	public static final int LEFT_BUTTON = MouseEvent.BUTTON1;
	public static final int MIDDLE_BUTTON = MouseEvent.BUTTON2;
	public static final int RIGHT_BUTTON = MouseEvent.BUTTON3;
	
	public static void setButtonDown(int code, boolean value) {
		 buttonMap.put(code, value);
	}
	
	public static boolean isButtonDown(int code) {
		return buttonMap.containsKey(code) && buttonMap.get(code);
	}

}
