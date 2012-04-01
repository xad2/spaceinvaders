package spaceinvaders.model;

import java.util.Observable;

import spaceinvaders.math.AABB2d;

public class World extends Observable {

	private AABB2d limits;
	private AABB2d terrain;
	
	public World() {
		limits = new AABB2d(0, 0, 100, 100);
		terrain = new AABB2d(0, 0, 100, 10);
	}
	
	public AABB2d getLimits() {
		return limits;
	}
	
	public AABB2d getTerrain() {
		return terrain;
	}

	public boolean contains(AABB2d rect) {
		return limits.contains(rect);
	}
}
