package spaceinvaders.math;

public class Point2d {

	public double m_x;
	public double m_y;
	
	public Point2d(double x, double y) {
		m_x = x;
		m_y = y;
	}
	
	public Point2d(Point2d o) {
		m_x = o.m_x;
		m_y = o.m_y;
	}

	public Point2d add(double dx, double dy) {
		m_x += dx;
		m_y += dy;
		return this;
	}
	
	public Point2d add(Point2d o) {
		return add(o.m_x, o.m_y);
	}
	
	public double dot(Point2d o) {
		return m_x * o.m_x + m_y * o.m_y;
	}
}
