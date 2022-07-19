import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Test{
	public static void main(String[] args) {
		// フレームの大きさを設定
		int w  = 400;
		int h = 320;

		// フレームを作成
		JFrame frame = new JFrame();

		// // タイトル名を設定
		frame.setTitle( "サインカーブ" );


		// 内側フレームの大きさを設定
		frame.getContentPane().setPreferredSize( new Dimension( w, h ) );
		frame.pack();

		// ”×”ボタンを押した時の処理を設定
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

		// フレームにパネルを追加
		MyPanel panel = new MyPanel();
		frame.getContentPane().add( panel );

		// フレームを表示
		frame.setVisible( true );
	}
}


// JPanelを継承したMyPanelを作成
class MyPanel extends JPanel {
	public MyPanel() {
		// 背景色を黒(black)に設定
		setBackground( Color.white );
	}

	// 関数
	private double f( double x )
	{
		// 角度degをラジアンradに変換
		double rad = Math.toRadians( (double)x );
		// サインを計算
		return Math.sin( rad );
	}


	// ラインの開始座標
	private int line_x1 = 0;
	private int line_y1 = 0;

	// ラインの開始座標の設定
	private void moveTo(Graphics g, int x, int y )
	{
		line_x1 = x;
		line_y1 = y;
	}

	// ラインの表示
	private void lineTo(Graphics g, int x, int y )
	{
		g.drawLine( line_x1, line_y1, x, y );
		line_x1 = x;
		line_y1 = y;
	}

	// 描画
	public void paintComponent( Graphics g ) {
		super.paintComponent( g );

		// フレームの大きさを取得
		int frame_w = getWidth();
		int frame_h = getHeight();

		// 座標変換クラスの作成
		Transformation2d trans = new Transformation2d();

		// 座標を設定
		// (-360.0,1.2)-(360.0,-1.2)の範囲がフレーム全体に表示する
		trans.set( -360.0, 1.2, 360, -1.2, 0.0, 0.0,
			(double)( frame_w - 1 ), (double)( frame_h - 1 ) );

		// 原点の画像座標を取得
		double origin_x = trans.getX( 0.0 );
		double origin_y = trans.getY( 0.0 );

		// 座標軸の色を黒
		g.setColor( Color.black );

		// x座標の描画
		g.drawLine( 0, (int)origin_y, frame_w - 1, (int)origin_y );

		// y座標の描画
		g.drawLine( (int)origin_x, 0, (int)origin_x, frame_h - 1 );

		// サイン(sin)カーブの描画
		// サインカーブの色を赤
		g.setColor( Color.red );

		// 角度-360から360度までのサインカーブを表示
		boolean isStart = true;
		for ( int x = -360; x <= 360; x++ ) {
			// 計算結果をyに代入
			double y = f( x );

			// 数学座標( deg, value )を
			// 画像座標( line_x2, line_x2 )に変換
			int px = (int)trans.getX( (double)x );
			int py = (int)trans.getY( y );
			
			//
			if ( true == isStart ) {
				moveTo( g, px, py );
				isStart = false;
			}
			else
				lineTo( g, px, py );
		}
	}
}
