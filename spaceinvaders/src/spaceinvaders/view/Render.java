package spaceinvaders.view;

import java.util.Collection;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;

import spaceinvaders.controller.GameController;
import spaceinvaders.math.AABB2d;
import spaceinvaders.math.Matrix33;
import spaceinvaders.model.Alien;
import spaceinvaders.model.AlienFleet;
import spaceinvaders.model.Ship;
import spaceinvaders.model.Shoot;
import spaceinvaders.model.World;

public class Render {

	private GC gc;
	private Display disp;
	private Matrix33 toScreen;
	//private Rectangle viewPort;

	public Render(GC gc, Display disp) {
		this.gc = gc;
		this.disp = disp;
		//this.viewPort = new Rectangle(0, 0, 0, 0);
	}

	public void draw(GameController gameController) {
		toScreen = gameController.getViewPort().toScreen();
		//AABB2d rect = gameController.getViewPort().getView();
		//viewPort = toRectangle(rect);

		draw(gameController.getWorld());
		draw(gameController.getShip());
		draw(gameController.getAlienFleet());
	}

	private void draw(AlienFleet alienFleet) {
		for (int i = 0; i < alienFleet.getNrOfAliens(); ++i) {
			draw(alienFleet.getAlien(i));
		}
	}

	private void draw(Alien alien) {
		drawAABB2d(alien.getPosition(), new Color(disp, 0, 0, 255));
	}

	private void draw(Ship ship) {
		drawAABB2d(ship.getPosition(), new Color(disp, 0, 255, 0));
	}

	private void draw(World world) {
		drawAABB2d(world.getLimits(), new Color(disp, 0, 0, 0));
		drawAABB2d(world.getTerrain(), new Color(disp, 255, 0, 0));
		
		Collection<Shoot> shoots = world.getShoots();
		
		for (Shoot shoot : shoots) {
			drawShoot(shoot);
		}
	}

	private void drawShoot(Shoot shoot) {
		drawAABB2d(shoot.getPosition(), new Color(disp, 0, 255, 255));
	}

	private void drawAABB2d(AABB2d rect, Color color) {
		Rectangle r = toRectangle(toScreen.leftMul(rect));
		gc.setBackground(color);
		gc.fillRectangle(r);
		color.dispose();
	}
	
	private Rectangle toRectangle(AABB2d aabb) {
		return new Rectangle((int) Math.round(aabb.min.x),
				(int) Math.round(aabb.min.y),
				(int) Math.round(aabb.width()), (int) Math.round(aabb.height()));
	}

}
