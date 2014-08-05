package com.sircular.circle.engine.animation;

import java.awt.geom.Area;

public interface CollidableAnimation extends Animation {
	
	public Area getCollisionArea();

}
