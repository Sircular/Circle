package com.sircular.circle.levels.extra;

import java.awt.Rectangle;
import java.util.List;

public abstract class ActiveCollidable extends Collidable {
	
	public abstract void update(long delta, List<Rectangle> tiles, List<Collidable> entities);

}
