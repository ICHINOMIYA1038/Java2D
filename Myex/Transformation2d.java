public class Transformation2d {
	// 変換パラメータ a,b,c,d
	private double a = 0.0;
	private double b = 0.0;
	private double c = 0.0;
	private double d = 0.0;

	// パラメータを計算
	boolean set( double x1, double y1, double x2, double y2,
				double px1, double py1, double px2, double py2 )
	{
		// ゼロとみなす値
		double e = 0.0000000001;

		// 初期値を代入
		a = b = c = d = 0.0;

		// x1とx2が同じ値であればエラー
		if ( e > Math.abs( x1 - x2 ) ) return false;

		// y1とy2が同じ値であればエラー
		if ( e > Math.abs( y1 - y2 ) ) return false;

		// パラメータの計算
		a = ( px1 - px2 ) / ( x1 - x2 );
		b = ( px1 * x2 - px2 * x1 ) / ( x2 - x1 );
		c = ( py1 - py2 ) / ( y1 - y2 );
		d = ( py1 * y2 - py2 * y1 ) / ( y2 - y1 );

		return true;
	}


	// 変換後のx座標を取得
	public double getX( double x )
	{
		return a * x + b;
	}

	// 変換後のy座標を取得
	public double getY( double y )
	{
		return c * y + d;
	}
}