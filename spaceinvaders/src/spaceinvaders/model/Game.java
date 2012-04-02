package spaceinvaders.model;

import java.util.Observable;

public class Game extends Observable {

	private final World world = new World();

	public Game() {
		world.initialize(1);
	}

	public World getWorld() {
		return world;
	}

	public void moveShipToTheLeft() {
		world.moveShipToTheLeft();
	}
	
	public void moveShipToTheRight() {
		world.moveShipToTheRight();
	}

	public void shoot() {
		world.shoot();
	}
	
	private static final int ALIEN_UPDATE_RATE = 5;
	private static final int SHOOTING_UPDATE_RATE = 3;
	private int lastAlienUpdate = 0;
	private int lastShootUpdate = 0;
	
	public void receiveTick() {
		if (lastAlienUpdate++ < ALIEN_UPDATE_RATE)		
			world.moveAlienFleet();
		else
			lastAlienUpdate = 0;
		
		if (lastShootUpdate++ < SHOOTING_UPDATE_RATE)
			world.moveShoots();
		else
			lastShootUpdate = 0;
	}
}
