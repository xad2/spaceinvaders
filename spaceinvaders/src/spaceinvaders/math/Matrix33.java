package spaceinvaders.math;

public class Matrix33 {
	
	private double[][] data;

	public Matrix33() {
		data = new double[3][3];
		setIdentity();
	}
	
	public Matrix33 setIdentity() {
		for (int i = 0; i < 3; ++i)
			for (int j = 0; j < 3; ++j) {
				if (i == j)
					data[i][j] = 1.f;
				else
					data[i][j] = 0.f;
			}
		return this;
	}
	
	public Matrix33 mul(Matrix33 o)
	{
		double[][] result = new double[3][3];
		
		for (int i = 0; i < 3; ++i)
			for (int j = 0; j < 3; j++)
				for (int k = 0; k < 3; ++k) {
					result[i][j] += data[i][k] * o.data[k][j];
				}
		data = result;
		return this;
	}
	
	public Point2d mul(Point2d p)
	{
		double x = p.m_x * data[0][0] + p.m_y * data[0][1] + data[0][2];
		double y = p.m_x * data[1][0] + p.m_y * data[1][1] + data[1][2];
		return new Point2d(x, y);
	}
	
	public static Matrix33 createScaleTransform(double xscale, double yscale)
	{
		Matrix33 m = new Matrix33();
		m.data[0][0] = xscale;
		m.data[1][1] = yscale;
		return m;
	}
	
	public static Matrix33 createTranslationTransform(double dx, double dy)
	{
		Matrix33 m = new Matrix33();
		m.data[0][2] = dx;
		m.data[1][2] = dy;
		return m;
	}
	
	public static Matrix33 createScaleTranslationTransform(double xscale, double yscale, double dx, double dy)
	{
		Matrix33 m = createTranslationTransform(dx, dy);
		m.mul(createScaleTransform(xscale, yscale));
		return m;
	}

	public AABB2d mul(AABB2d r) {
		return new AABB2d(mul(r.m_bl), mul(r.m_tr));
	}
}
