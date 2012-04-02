package spaceinvaders.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Observable;

import spaceinvaders.math.AABB2d;

public class World extends Observable {

	private static final double SHIP_MOVE_STEP = 5.;
	private static final double SHOOT_MOVE_STEP = 5.;

	private AABB2d limits;
	private AABB2d terrain;
	private ArrayList<Shoot> shoots;
	private Ship ship;
	private AlienFleet alienFleet;

	public World() {
	}

	public void initialize(int alienFleetSize) {
		limits = new AABB2d(0, 0, 100, 100);
		terrain = new AABB2d(0, 0, 100, 10);
		shoots = new ArrayList<>();
		ship = new Ship(this);
		alienFleet = new AlienFleet(this, alienFleetSize);
	}

	public AABB2d getLimits() {
		return limits;
	}

	public AABB2d getTerrain() {
		return terrain;
	}

	public Collection<Shoot> getShoots() {
		return shoots;
	}

	public Ship getShip() {
		return ship;
	}

	public AlienFleet getAlienFleet() {
		return alienFleet;
	}

	public boolean contains(AABB2d rect) {
		return limits.contains(rect);
	}

	public void registerShoot(Shoot shoot) {
		shoots.add(shoot);

		setChanged();
		notifyObservers();
	}

	public void updateShoots(double shootMoveStep) {
		for (Shoot shoot : shoots) {
			shoot.move(shootMoveStep);
		}
	}

	public void moveShipToTheLeft() {
		ship.moveLeft(SHIP_MOVE_STEP);
	}

	public void moveShipToTheRight() {
		ship.moveRight(SHIP_MOVE_STEP);
	}

	public void shoot() {
		Shoot shoot = new Shoot(this, ship.getPosition(), Shoot.FROM_SHIP);
		shoots.add(shoot);
	}

	public void moveAlienFleet() {
		alienFleet.move();
	}

	public void moveShoots() {
		for (Shoot shoot : shoots) {
			shoot.move(SHOOT_MOVE_STEP);
		}
	}

}
