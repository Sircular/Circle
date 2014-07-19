package com.sircular.circle.engine;

import java.util.HashMap;
import java.util.Map;

import kuusisto.tinysound.Sound;
import kuusisto.tinysound.TinySound;

public class SoundPlayer {
	
	static {
		TinySound.init();
	}
	
	static Map<String, Sound> sounds = new HashMap<String, Sound>();
	
	public static void loadSound(String path, String identifier) {
		Sound newSound = TinySound.loadSound(SoundPlayer.class.getResource(path));
		sounds.put(identifier, newSound);
	}
	
	public static void playSound(String identifier) {
		sounds.get(identifier).play();
	}
	
	public static Sound getSound(String identifier) { // for the people that want to interact with the object themselves
		return sounds.get(identifier);
	}
	
	public static boolean isSoundLoaded(String identifier) {
		return sounds.containsKey(identifier);
	}

}
