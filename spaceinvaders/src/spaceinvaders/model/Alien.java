package spaceinvaders.model;

import java.util.Observable;

import spaceinvaders.math.AABB2d;

public class Alien extends Observable {

	private AABB2d position;
	private World world;
	
	public Alien(World world) {
		super();
		this.world = world;
		position = new AABB2d(-1, -1, 1, 1);
	}
	
	public AABB2d getPosition() {
		return position;
	}
	
	public void move(double dx, double dy) {
		AABB2d nextMove = new AABB2d(position);
		nextMove.move(dx, dy);
		
		//if (world.getTerrain().intersects(nextMove))
		
		position.move(dx, dy);

		setChanged();
		notifyObservers();
	}
	
	public void moveRight(double step) {
		move(step, 0.f);
	}

	public void moveLeft(double step) {
		move(-step, 0.f);
	}
	
	public void moveUp(double step) {
		move(0.f, step);
	}
	
	public void moveDown(double step) {
		move(0.f, -step);
	}

}
