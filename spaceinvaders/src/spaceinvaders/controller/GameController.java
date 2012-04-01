package spaceinvaders.controller;

import java.util.Observable;
import java.util.Observer;

import spaceinvaders.math.AABB2d;
import spaceinvaders.model.AlienFleet;
import spaceinvaders.model.Game;
import spaceinvaders.model.Ship;
import spaceinvaders.model.World;

public class GameController extends Observable implements Observer {

	private Game game;
	private ViewPort viewPort;
	
	private static final long TICKS_PER_SECOND = 5;
	private static final long SKIP_TICKS = 1000 / TICKS_PER_SECOND;
	private static final long MAX_FRAMESKIP = 5;
    private long nextGameTick = System.currentTimeMillis();
    
	public GameController() {
		super();
		game = new Game();
		game.getWorld().addObserver(this);
		game.getShip().addObserver(this);
		game.getAlienFleet().addObserver(this);
		
		viewPort = new ViewPort();
		viewPort.setModel(game.getWorld().getLimits());
		viewPort.addObserver(this);
	}
	
	public World getWorld()	{
		return game.getWorld();
	}
	
	public Ship getShip() {
		return game.getShip();
	}
	
	public AlienFleet getAlienFleet() {
		return game.getAlienFleet();
	}
	
	public ViewPort getViewPort() {
		return viewPort;
	}
	
	public void setViewSize(AABB2d viewArea) {
		viewPort.setView(viewArea);
	}
	
	public static final int ARROW_RIGHT = 0;
	public static final int ARROW_LEFT = 1;
	public static final int ARROW_UP = 2;
	public static final int ARROW_DOWN = 3;
	
	public void keyEvent(int keyCode) {
		switch(keyCode)
		{
		case ARROW_RIGHT: game.receiveMoveAction(Game.RIGHT); break;
		case ARROW_LEFT: game.receiveMoveAction(Game.LEFT); break;
		case ARROW_DOWN: System.out.println("received down"); break;
		case ARROW_UP: System.out.println("received up"); break;
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		setChanged();
		notifyObservers();
	}
    
	public void sendTick() {
		int loops = 0;
		while (System.currentTimeMillis() > nextGameTick && loops < MAX_FRAMESKIP) 
		{
			game.updateState();
			nextGameTick += SKIP_TICKS;
			loops++;
		}
	}
}

