package spaceinvaders.controller;

import java.util.Observable;

import Jama.Matrix;

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
		
		Point2d mcenter = model.center();
		Point2d vcenter = view.center();
		
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

	/* Extracted from opencv getPerspectiveTransform
	 *
	 * Calculates coefficients of perspective transformation
	 * which maps (xi,yi) to (ui,vi), (i=1,2,3,4):
	 *
	 *      c00*xi + c01*yi + c02
	 * ui = ---------------------
	 *      c20*xi + c21*yi + c22
	 *
	 *      c10*xi + c11*yi + c12
	 * vi = ---------------------
	 *      c20*xi + c21*yi + c22
	 *
	 * Coefficients are calculated by solving linear system:
	 * / x0 y0  1  0  0  0 -x0*u0 -y0*u0 \ /c00\ /u0\
	 * | x1 y1  1  0  0  0 -x1*u1 -y1*u1 | |c01| |u1|
	 * | x2 y2  1  0  0  0 -x2*u2 -y2*u2 | |c02| |u2|
	 * | x3 y3  1  0  0  0 -x3*u3 -y3*u3 |.|c10|=|u3|,
	 * |  0  0  0 x0 y0  1 -x0*v0 -y0*v0 | |c11| |v0|
	 * |  0  0  0 x1 y1  1 -x1*v1 -y1*v1 | |c12| |v1|
	 * |  0  0  0 x2 y2  1 -x2*v2 -y2*v2 | |c20| |v2|
	 * \  0  0  0 x3 y3  1 -x3*v3 -y3*v3 / \c21/ \v3/
	 *
	 * where:
	 *   cij - matrix coefficients, c22 = 1
	 */
	private Matrix33 computeHomography(Point2d[] src, Point2d[] dst) {
		Matrix A = new Matrix(8, 8);
		Matrix b = new Matrix(8, 1);

	    for( int i = 0; i < 4; ++i )
	    {
	    	A.set(i,     0, src[i].x);
	    	A.set(i + 4, 3, src[i].x);
	        A.set(i,     1, src[i].y);
	        A.set(i + 4, 4, src[i].y);
	        A.set(i,     2, 1.);
	        A.set(i + 4, 5, 1.);
	        A.set(i,     3, 0.);
	        A.set(i,     4, 0.);
	        A.set(i,     5, 0.);
	        A.set(i + 4, 0, 0.);
	        A.set(i + 4, 1, 0.);
	        A.set(i + 4, 2, 0.);
	        A.set(i,     6, -src[i].x * dst[i].x);
	        A.set(i,     7, -src[i].y * dst[i].x);
	        A.set(i + 4, 6, -src[i].x * dst[i].y);
	        A.set(i + 4, 7, -src[i].y * dst[i].y);
	        b.set(i,     0, dst[i].x);
	        b.set(i + 4, 0, dst[i].y);
	    }
	    
	    Matrix x = A.solve(b);

	    Matrix33 result = new Matrix33();
	    for (int i = 0; i < 8; ++i)
	    	result.set(i, x.get(i, 0));
	    result.set(8, 1.);
	    
	    return result;
	}

}
