package com.sircular.circle.levels.extra.entities;

import java.awt.Rectangle;
import java.util.List;

import com.sircular.circle.levels.extra.ActiveCollidable;
import com.sircular.circle.levels.extra.Collidable;

public class Enemy extends ActiveCollidable{
	
	private boolean alive;

	@Override
	public void update(long delta, List<Rectangle> tiles,
			List<Collidable> entities) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean shouldRemove() {
		return alive;
	}

}
