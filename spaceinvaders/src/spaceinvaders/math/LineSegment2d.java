package spaceinvaders.math;

public class LineSegment2d {

	public Point2d m_p1;
	public Point2d m_p2;

	public LineSegment2d(Point2d p1, Point2d p2) {
		m_p1 = p1;
		m_p2 = p2;
	}

	public LineSegment2d(double x1, double y1, double x2, double y2) {
		m_p1 = new Point2d(x1, y1);
		m_p2 = new Point2d(x2, y2);
	}
}
