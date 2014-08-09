package com.sircular.circle.engine;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Keyboard {
	
	private static Map<Integer, Boolean> keyMap = new HashMap<Integer, Boolean>();
	private static List<WeakReference<InputListener>> listeners = new ArrayList<WeakReference<InputListener>>();
	
	public static void setKeyDown(int keyCode, boolean value) {
		if (isKeyDown(keyCode) == value)
			return;
		
		for (WeakReference<InputListener> ref : listeners) {
			InputListener listener = ref.get();
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
		listeners.add(new WeakReference<InputListener>(listener));
	}
	
	public static void removeListener(InputListener listener) {
		Iterator<WeakReference<InputListener>> it = listeners.iterator();
		
		while (it.hasNext()) {
			if (it.next().get() == listener) {
				it.remove();
				return;
			}
		}
	}
	
	public static Map<Integer, Boolean> getKeyMap() {
		return keyMap;
	}

}
