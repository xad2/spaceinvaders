package spaceinvaders.view;

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
		Rectangle rect = toRectangle(toScreen.leftMul(alien.getPosition()));
		Color color = new Color(disp, 0, 0, 255);
		gc.setBackground(color);
		gc.fillRectangle(rect);
		color.dispose();
	}

	private void draw(Ship ship) {
		Rectangle rect = toRectangle(toScreen.leftMul(ship.getPosition()));
		Color color = new Color(disp, 0, 255, 0);
		gc.setBackground(color);
		gc.fillRectangle(rect);
		color.dispose();
	}

	private void draw(World world) {
		Rectangle rect = toRectangle(toScreen.leftMul(world.getTerrain()));
		Color color = new Color(disp, 255, 255, 255);
		gc.setBackground(color);
		gc.fillRectangle(rect);
		color.dispose();
	}

	private Rectangle toRectangle(AABB2d aabb) {
		return new Rectangle((int) Math.round(aabb.bl.x),
				(int) Math.round(aabb.bl.y),
				(int) Math.round(aabb.width()), (int) Math.round(aabb.height()));
	}

}
