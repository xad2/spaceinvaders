package spaceinvaders.math;

public class AABB2d {

	public Point2d bl;
	public Point2d tr;

	public AABB2d(double x1, double y1, double x2, double y2) {
		bl = new Point2d(x1, y1);
		tr = new Point2d(x2, y2);
	}

	public AABB2d(Point2d bl, Point2d tr) {
		this.bl = new Point2d(bl);
		this.tr = new Point2d(tr);
	}

	public AABB2d(AABB2d other) {
		bl = new Point2d(other.bl);
		tr = new Point2d(other.tr);
	}

	public double height() {
		return tr.y - bl.y;
	}

	public double width() {
		return tr.x - bl.x;
	}

	public void move(double dx, double dy) {
		bl.add(dx, dy);
		tr.add(dx, dy);
	}

	public boolean contains(AABB2d rect) {
		return contains(rect.bl) && contains(rect.tr);
	}

	public boolean contains(Point2d p) {
		return p.x > bl.x && p.x < tr.x && p.y > bl.y
				&& p.y < tr.y;
	}

	public boolean intersects(AABB2d rect) {
		return contains(rect.bl) || contains(rect.tr);
	}
}
