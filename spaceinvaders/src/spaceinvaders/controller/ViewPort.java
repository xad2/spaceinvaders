package spaceinvaders.controller;

import java.util.Observable;

import Jama.Matrix;
import Jama.SingularValueDecomposition;

import spaceinvaders.math.AABB2d;
import spaceinvaders.math.Matrix33;
import spaceinvaders.math.Point2d;

public class ViewPort extends Observable {

	private AABB2d model;
	private AABB2d view;
	private Matrix33 toScreen;
	private Matrix33 fromScreen;

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
		return toScreen().leftMul(p);
	}

	public Matrix33 toScreen() {
		return toScreen;
	}
	
	public Matrix33 fromScreen() {
		return fromScreen;
	}

	private void updateTransform() {
		
		//Point2d mcenter = model.center();
		//Point2d vcenter = view.center();
		
//	    // tl, tr, br, bl
	    Point2d mtl = new Point2d(model.min.x, model.max.y);
	    Point2d mtr = model.max;
	    Point2d mbr = new Point2d(model.max.x, model.min.y);
	    Point2d mbl = model.min;
	    Point2d src[] = { mtl, mtr, mbr, mbl };
	    
	    Point2d vtl = new Point2d(view.min);
	    Point2d vtr = new Point2d(view.max.x, view.min.y);
	    Point2d vbr = new Point2d(view.max);
	    Point2d vbl = new Point2d(view.min.x, view.max.y);
	    Point2d dst[] = { vtl, vtr, vbr, vbl };

	    toScreen = computeHomography(src, dst);
	    fromScreen = computeHomography(dst, src); //could call toScreen.inverse()
	}

	// http://www.cse.iitd.ernet.in/~suban/vision/geometry/node24.html
	private Matrix33 computeHomography(Point2d[] src, Point2d[] dst) {
		
		Matrix A = new Matrix(12, 9);
		
		for (int i = 0; i < 4; ++i) {
			int shift = i * 3;
			
			A.set(0 + shift, 0, 0);
			A.set(0 + shift, 1, 0);
			A.set(0 + shift, 2, 0);
			
			A.set(0 + shift, 3, -1 * src[i].x);
			A.set(0 + shift, 4, -1 * src[i].y);
			A.set(0 + shift, 5, -1);
			
			A.set(0 + shift, 6, dst[i].y * src[i].x);
			A.set(0 + shift, 7, dst[i].y * src[i].y);
			A.set(0 + shift, 8, dst[i].y);
			
			A.set(1 + shift, 0, src[i].x);
			A.set(1 + shift, 1, src[i].y);
			A.set(1 + shift, 2, 1);
			
			A.set(1 + shift, 3, 0);
			A.set(1 + shift, 4, 0);
			A.set(1 + shift, 5, 0);
			
			A.set(1 + shift, 6, -dst[i].x * src[i].x);
			A.set(1 + shift, 7, -dst[i].x * src[i].y);
			A.set(1 + shift, 8, -dst[i].x);
			
			A.set(2 + shift, 0, -dst[i].y * src[i].x);
			A.set(2 + shift, 1, -dst[i].y * src[i].y);
			A.set(2 + shift, 2, -dst[i].y);
			
			A.set(2 + shift, 3, dst[i].x * src[i].x);
			A.set(2 + shift, 4, dst[i].x * src[i].y);
			A.set(2 + shift, 5, dst[i].x);
			
			A.set(2 + shift, 6, 0);
			A.set(2 + shift, 7, 0);
			A.set(2 + shift, 8, 0);
		}
		
		SingularValueDecomposition svd = new SingularValueDecomposition(A);
		Matrix V = svd.getV();
		
		Matrix33 result = new Matrix33();
		for (int i = 0; i < 9; ++i)
			result.set(i, V.get(i, 8));
		
		
		return result;
	}

}
