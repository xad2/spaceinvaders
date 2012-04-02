package spaceinvaders.math;

public class Point2d {

	public double x;
	public double y;
	
	public Point2d(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public Point2d(Point2d o) {
		x = o.x;
		y = o.y;
	}

	public Point2d() {
		x = 0;
		y = 0;
	}

	public Point2d add(double dx, double dy) {
		x += dx;
		y += dy;
		return this;
	}
	
	public Point2d add(Point2d o) {
		return add(o.x, o.y);
	}
	
	public double dot(Point2d o) {
		return x * o.x + y * o.y;
	}
}
