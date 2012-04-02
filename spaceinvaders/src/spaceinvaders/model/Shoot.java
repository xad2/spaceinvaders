package spaceinvaders.model;

import java.util.Observable;

import spaceinvaders.math.AABB2d;
import spaceinvaders.math.Point2d;

public class Shoot {

	private AABB2d position;
	private int shootType;
	private final World world;
	public static final int FROM_SHIP = 0;
	public static final int FROM_ALIEN = 1;
	
	public Shoot(World world, AABB2d initialPosition, int type) {
		this.world = world;
		position = initialPosition;
		shootType = type;
	}

	public AABB2d getPosition() {
		return position;
	}

	public void setPosition(AABB2d newPosition) {
		if (position.equals(newPosition))
			return;
		
		this.position = newPosition;
	}

	public boolean move(double shootMoveStep) {
		Point2d dir = shootType == FROM_SHIP ? new Point2d(0, 1) : new Point2d(0, -1);
		dir.mul(shootMoveStep);
		
		AABB2d candidatePos = new AABB2d(getPosition());
		candidatePos.move(dir.x, dir.y);
		
		if (!world.contains(candidatePos))
			return false;
		
		setPosition(candidatePos);
		return true;
	}
	
	public boolean
}
