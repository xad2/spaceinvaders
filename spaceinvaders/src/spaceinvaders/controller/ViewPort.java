package spaceinvaders.controller;

import java.util.Observable;

import spaceinvaders.math.AABB2d;
import spaceinvaders.math.Matrix33;
import spaceinvaders.math.Point2d;

public class ViewPort extends Observable {

	private AABB2d model;
	private AABB2d view;
	private Matrix33 toScreen;

	public ViewPort() {
		model = new AABB2d(0, 0, 1, 1);
		view = new AABB2d(0, 0, 1, 1);
	}

	public void setModel(AABB2d model) {
		this.model = model;
		updateTransform();

		setChanged();
		notifyObservers();
	}

	public void setView(AABB2d view) {
		this.view = view;
		updateTransform();

		setChanged();
		notifyObservers();
	}
	
	public AABB2d getView() {
		return view;
	}

	public Point2d toScreen(Point2d p) {
		return toScreen().mul(p);
	}

	public Matrix33 toScreen() {
		return toScreen;
	}

	private void updateTransform() {
		double xscale = view.width() / model.width();
		double yscale = view.height() / model.height();
		toScreen = Matrix33.createScaleTranslationTransform(xscale, -yscale,
				0.f, view.height());
	}

}
