package jp.ichinomiya.minigame;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

/**
 *ゲームキャラクタの情報のクラス
 * @author a50609
 */
public abstract class GameChara {
	/**
	 * 画面左端はみ出し時のキャラクターのサイズによる補正
	 * 汎用的な値にするために30を採用
	 */
	public static final int AREA_X1 = -30;
	/**
	 * 画面上端はみ出し時のキャラクターのサイズによる補正
	 * 汎用的な値にするために30を採用
	 */
	public static final int AREA_Y1 = -30;
	/**
	 * 画面右端はみ出し時のキャラクターのサイズによる補正
	 * 汎用的な値にするために30を採用
	 */
	public static final int AREA_X2 = halloweenShootMain.SCREEN_W+30;
	/**
	 * 画面下端はみ出し時のキャラクターのサイズによる補正
	 * 汎用的な値にするために30を採用
	 */
	public static final int AREA_Y2 = halloweenShootMain.SCREEN_W+30;
	/**
	 * キャラクターの位置情報を格納
	 */
	public int chara_x, chara_y;
	/**
	 * 画像における切り出しの開始地点と、切り出す画像のサイズ
	 */
	int image_x, image_y, image_w, image_h;
	/**
	 * 当たり判定の大きさ
	 */
	int collision_h, collision_w;
	/**
	 * 各キャラクタの画像
	 */
	BufferedImage image1;

	/**
	 * キャラクターのコンストラクタ
	 * @param x  キャラクターを配置するx座標 
	 * @param y  キャラクターを配置するy座標 
	 * @param aw x軸方向の衝突範囲
	 * @param ah　y軸方向の衝突範囲
	 * @param img キャラクター画像のファイル名
	 * @param ix 画像のx座標 (画像からの相対位置)
	 * @param iy 画像のy座標
	 * @param iw 画像の幅
	 * @param ih 画像の高さ
	 */
	GameChara(int x, int y, int aw, int ah, BufferedImage img, int ix, int iy, int iw, int ih){
		chara_x = x; 
		chara_y = y;
		collision_h = aw; 
		collision_w = ah;
		image1 = img;
		image_x = ix; //画像のx座標
		image_y = iy; //画像のy座標
		image_w = iw; //矩形の幅
		image_h = ih; //矩形の高さ
	}

	/**
	 * 判定領域の左上のx座標を取得
	 * @return 判定領域の左上のx座標
	 */
	public int getx1() {
		return chara_x + (image_w-collision_h)/2;
	}

	/**
	 * 判定領域の左上のy座標を取得
	 * @return　判定領域の左上のy座標
	 */
	public int gety1() {
		return chara_y + (image_h-collision_w)/2;
	}

	/**
	 * 判定領域の右下のx座標を取得
	 * @return 判定領域の右下のx座標
	 */
	public int getx2() {
		return chara_x + (image_w+collision_h)/2;
	}


	/**
	 * 判定領域の右下のy座標を取得
	 * @return 判定領域の右下のy座標
	 */
	public int gety2() {
		return chara_y + (image_h+collision_w)/2;
	}

	/**
	 * 衝突したかどうかを判定する
	 * @param aite 衝突した相手
	 * @return 衝突したかどうか true/false
	 */
	public boolean isColided(GameChara aite) {
		if(aite.getx2()>getx1()&& getx2()>aite.getx1()&&aite.gety2()>gety1()&&gety2()>aite.gety1()){
			return true;

		}else {
			return false;

		}
	}

	/**
	 * 画面の外に出たかどうかを判定する
	 * @return 外に出たかどうか true/false
	 */
	public boolean isOutofScreen() {
		if( chara_x<AREA_X1||chara_y<AREA_Y1||chara_x+image_w>AREA_X2||chara_y+image_w>AREA_Y2){
			return true;
		}else {
			return false;
		}
	}

	/**
	 * 画面にキャラクタを描画する
	 * @param g 描画するグラフィックオブジェクト
	 * @param io　イメージオブザーバ
	 */
	public void draw(Graphics g, ImageObserver io) {
		g.drawImage(image1, chara_x, chara_y, chara_x + image_w, chara_y + image_h, image_x, image_y, image_x  + image_w, image_y  + image_h, io);
		move();
		showHP(g,io);
	}
	/**
	 * 移動に関する抽象メソッド
	 */
	public abstract void move();
	/**
	 * 体力ゲージ表示のためのメソッド
	 * @param g グラフィックス
	 * @param io　イメージオブザーバー
	 */
	public abstract void showHP(Graphics g, ImageObserver io);
}