package spaceinvaders.math;

public class AABB2d {

	public Point2d min;
	public Point2d max;

	public AABB2d(double x1, double y1, double x2, double y2) {
		min = new Point2d(x1, y1);
		max = new Point2d(x2, y2);
	}

	public AABB2d(Point2d min, Point2d max) {
		this.min = new Point2d(min);
		this.max = new Point2d(max);
	}

	public AABB2d(AABB2d other) {
		min = new Point2d(other.min);
		max = new Point2d(other.max);
	}

	public Point2d center() {
		return new Point2d(max).sub(min).mul(0.5);
	}
	
	public double area() {
		return (max.x - min.x) * (max.y - min.y);
	}
	
	public double height() {
		return max.y - min.y;
	}

	public double width() {
		return max.x - min.x;
	}

	public AABB2d move(double dx, double dy) {
		min.add(dx, dy);
		max.add(dx, dy);
		return this;
	}

	public boolean contains(AABB2d other) {
		return min.x <= other.min.x && min.y <= other.min.y 
				&& max.x >= other.max.x && max.y >= other.max.y;
	}

	public boolean contains(Point2d other) {
		return min.x <= other.x && min.y <= other.y 
				&& max.x >= other.x && max.y >= other.y;
	}

	public boolean intersects(AABB2d rect) {
		return contains(rect.min) || contains(rect.max);
	}
}
