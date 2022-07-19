package jp.ichinomiya.minigame;

import java.awt.image.BufferedImage;

/**
 * 爆発エフェクトのクラス
 * @author a50609
 */
public class Explosion extends GameChara {
	/**
	 * 残存時間
	 * 0になったらisOutofScreenのフラグがtrueになる
	 */
	int waittime;

	/**
	 * @param x キャラクターのx座標
	 * @param y キャラクターのy座標
	 * @param img キャラクター画像
	 */
	public Explosion(int x, int y, BufferedImage img) {
		super(x, y, 0, 0, img, 192, 0, 48, 48);
		waittime = 15;
		// TODO Auto-generated constructor stub
	}
	
	public boolean isOutofScreen() {
		if(waittime<0) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub
		waittime = waittime-1;
	}

}
