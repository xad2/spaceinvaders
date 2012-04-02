package spaceinvaders.math;

public class Matrix33 {
	
	private double[] data;

	public Matrix33(double v00, double v01, double v02, double v10, double v11, double v12, double v20, double v21, double v22) {
		data = new double[9];
		data[0] = v00;
		data[1] = v01;
		data[2] = v02;
		data[3] = v10;
		data[4] = v11;
		data[5] = v12;
		data[6] = v20;
		data[7] = v21;
		data[8] = v22;
	}
	
	public Matrix33() {
		data = new double[9];
		setIdentity();
	}
	
	public Matrix33 setIdentity() 
	{
		data[0] = 1;
		data[1] = 0;
		data[2] = 0;
		data[3] = 0;
		data[4] = 1;
		data[5] = 0;
		data[6] = 0;
		data[7] = 0;
		data[8] = 1;
		return this;
	}
	
	public void set(int i, int j, double val) {
		set(i * 3 + j, val);
	}
	
	public void set(int ind, double val) {
		data[ind] = val;
	}
	
	/**
	 * M = O * M
	 * 
	 * @param o
	 * @return
	 */
	public Matrix33 leftMul(Matrix33 o)
	{
		double[] me = new double[9];
		System.arraycopy(data, 0, me, 0, 9);
		
		double[] other = o.data;
		
		data[0] = other[0] * me[0] + other[1] * me[3] + other[2] * me[6];
		data[1] = other[0] * me[1] + other[1] * me[4] + other[2] * me[7];
		data[2] = other[0] * me[2] + other[1] * me[5] + other[2] * me[8];

		data[3] = other[3] * me[0] + other[4] * me[3] + other[5] * me[6];
		data[4] = other[3] * me[1] + other[4] * me[4] + other[5] * me[7];
		data[5] = other[3] * me[2] + other[4] * me[5] + other[5] * me[8];

		data[6] = other[6] * me[0] + other[7] * me[3] + other[8] * me[6];
		data[7] = other[6] * me[1] + other[7] * me[4] + other[8] * me[7];
		data[8] = other[6] * me[2] + other[7] * me[5] + other[8] * me[8];
		
		return this;
	}
	
	/**
	 * M = M * O
	 * @param o
	 * @return
	 */
	public Matrix33 rightMul(Matrix33 o)
	{
		double[] me = new double[9];
		System.arraycopy(data, 0, me, 0, 9);
		
		double[] other = o.data;
		
		data[0] = me[0] * other[0] + me[1] * other[3] + me[2] * other[6];
		data[1] = me[0] * other[1] + me[1] * other[4] + me[2] * other[7];
		data[2] = me[0] * other[2] + me[1] * other[5] + me[2] * other[8];

		data[3] = me[3] * other[0] + me[4] * other[3] + me[5] * other[6];
		data[4] = me[3] * other[1] + me[4] * other[4] + me[5] * other[7];
		data[5] = me[3] * other[2] + me[4] * other[5] + me[5] * other[8];

		data[6] = me[6] * other[0] + me[7] * other[3] + me[8] * other[6];
		data[7] = me[6] * other[1] + me[7] * other[4] + me[8] * other[7];
		data[8] = me[6] * other[2] + me[7] * other[5] + me[8] * other[8];
		
		return this;
	}
	
	/**
	 * P1 = M * P
	 * 
	 * @param p
	 * @return
	 */
	public Point2d leftMul(Point2d p)
	{
		Point2d ret = new Point2d();
		double tmp =  data[6] * p.x + data[7] * p.y + data[8] * 1;
		ret.x = (data[0] * p.x + data[1] * p.y + data[2] * 1) / tmp;
		ret.y = (data[3] * p.x + data[4] * p.y + data[5] * 1) / tmp;
		return ret;
	}
	
	/**
	 * R1 = M * R
	 * 
	 * @param r
	 * @return
	 */
	public AABB2d leftMul(AABB2d r) {
		return new AABB2d(leftMul(r.min), leftMul(r.max));
	}
	
	public Matrix33 inverse() throws Exception
	{
		double[] me = new double[9];
		System.arraycopy(data, 0, me, 0, 9);

		double det = me[0] * (me[8] * me[4] - me[7] * me[5]) 
				   - me[3] * (me[8] * me[1] - me[7] * me[2]) 
				   + me[6] * (me[5] * me[1] - me[4] * me[2]);

		// we need to check against an epsilon here!
		if (det == 0.)
			throw new Exception("Matrix isn't inversible");

		data[0] = (me[8] * me[4] - me[7] * me[5]) / det;
		data[1] = -(me[8] * me[1] - me[7] * me[2]) / det;
		data[2] = (me[5] * me[1] - me[4] * me[2]) / det;

		data[3] = -(me[8] * me[3] - me[6] * me[5]) / det;
		data[4] = (me[8] * me[0] - me[6] * me[2]) / det;
		data[5] = -(me[5] * me[0] - me[3] * me[2]) / det;

		data[6] = (me[7] * me[3] - me[6] * me[4]) / det;
		data[7] = -(me[7] * me[0] - me[6] * me[1]) / det;
		data[8] = (me[4] * me[0] - me[3] * me[1]) / det;

		return this;
	}
	
	Matrix33 transpose()
	{
		double tmp;

		tmp = data[0];
		data[0] = data[8];
		data[8] = tmp;

		tmp = data[1];
		data[1] = data[5];
		data[5] = tmp;

		tmp = data[3];
		data[3] = data[7];
		data[7] = tmp;

		return this;
	}
	
	public static Matrix33 createScaleTransform(double xscale, double yscale)
	{
		return createScaleAndTranslateTransform(xscale, yscale, 0, 0);
	}
	
	public static Matrix33 createTranslationTransform(double dx, double dy)
	{
		return createScaleAndTranslateTransform(1., 1., dx, dy);
	}
	
	public static Matrix33 createScaleAndTranslateTransform(double xscale, double yscale, double dx, double dy)
	{
		return new Matrix33(xscale, 0., dx, 0., yscale, dy, 0., 0., 1.);
	}

}
