package spaceinvaders.math;

public class AABB2d {

	public Point2d m_bl;
	public Point2d m_tr;

	public AABB2d(double x1, double y1, double x2, double y2) {
		m_bl = new Point2d(x1, y1);
		m_tr = new Point2d(x2, y2);
	}

	public AABB2d(Point2d bl, Point2d tr) {
		m_bl = new Point2d(bl);
		m_tr = new Point2d(tr);
	}

	public AABB2d(AABB2d other) {
		m_bl = new Point2d(other.m_bl);
		m_tr = new Point2d(other.m_tr);
	}

	public double height() {
		return m_tr.m_y - m_bl.m_y;
	}

	public double width() {
		return m_tr.m_x - m_bl.m_x;
	}

	public void move(double dx, double dy) {
		m_bl.add(dx, dy);
		m_tr.add(dx, dy);
	}

	public boolean contains(AABB2d rect) {
		return contains(rect.m_bl) && contains(rect.m_tr);
	}

	public boolean contains(Point2d p) {
		return p.m_x > m_bl.m_x && p.m_x < m_tr.m_x && p.m_y > m_bl.m_y
				&& p.m_y < m_tr.m_y;
	}

	public boolean intersects(AABB2d rect) {
		return contains(rect.m_bl) || contains(rect.m_tr);
	}
}
