package spaceinvaders.model;

import java.util.ArrayList;
import java.util.Observable;

import spaceinvaders.math.AABB2d;

public class AlienFleet extends Observable {

	private ArrayList<Alien> aliens;
	private World world;
	
	public AlienFleet() {
		super();
	}
	
	public AlienFleet(World world, int fleetSize) {
		this.world = world;
		this.aliens = new ArrayList<Alien>();
		
		AABB2d limits = world.getLimits();
		
		for (int i = 0; i < fleetSize; ++i) {
			Alien alien = new Alien(world);
			alien.move(10., limits.height() - 10);
			addAlien(alien);
		}
	}
	
	public void addAlien(Alien a)
	{
		aliens.add(a);
		setChanged();
		notifyObservers();
	}

	public int getNrOfAliens() {
		return aliens.size();
	}

	public Alien getAlien(int i) {
		return aliens.get(i);
	}

	public void move() {
		for (Alien alien : aliens) {
			alien.moveDown(2.);
		}
		setChanged();
		notifyObservers();
	}
}
