package spaceinvaders.model;

import java.util.Observable;

import spaceinvaders.math.AABB2d;

public class Ship extends Observable {

	private AABB2d position;
	private World world;
	
	public Ship(World world) {
		super();
		this.world = world;
		position = new AABB2d(-2, -0.5, 2, 0.5);
		setInitialPosition();
	}

	public AABB2d getPosition() {
		return position;
	}

	public void moveRight(double step) {
		move(step, 0);
	}

	public void move(double dx, double dy) {
		AABB2d candidate = new AABB2d(position);
		candidate.move(dx, dy);
		
		if (!world.contains(candidate))
			return;
		
		position = candidate;

		setChanged();
		notifyObservers();
	}

	public void moveLeft(double step) {
		move(-step, 0.f);
	}

	public void setInitialPosition() {
		AABB2d terrain = world.getTerrain();
		move(terrain.width() / 2., terrain.height() + 5.);
	}

	public void shoot() {
		new Shoot(world, getPosition(), Shoot.FROM_SHIP);
	}
}
