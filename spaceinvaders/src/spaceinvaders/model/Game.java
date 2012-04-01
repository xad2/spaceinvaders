package spaceinvaders.model;

public class Game {
	
	public static final int RIGHT = 0;
	public static final int LEFT = 1;

	private static final double SHIP_MOVE_STEP = 5.;
	private static final int FLEET_SIZE = 10; 
	private World world;
	private Ship ship;
	private AlienFleet alienFleet;
	
	public Game()
	{
		world = new World();
		ship = new Ship(world);
		alienFleet = new AlienFleet(world, 1);
	}

	public void updateState()
	{
		alienFleet.move();
	}
	
	public World getWorld()
	{
		return world;
	}

	public Ship getShip() {
		return ship;
	}
	
	public AlienFleet getAlienFleet() {
		return alienFleet;
	}
	
	public void moveShip(boolean left)
	{
		if (left)
			ship.moveLeft(SHIP_MOVE_STEP);
		else
			ship.moveRight(SHIP_MOVE_STEP);
	}

	public void receiveMoveAction(int code) {
		if (code == RIGHT)
			ship.moveRight(SHIP_MOVE_STEP);
		else if (code == LEFT)
			ship.moveLeft(SHIP_MOVE_STEP);
	}
}
