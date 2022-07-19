package jp.ichinomiya.minigame;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

/**
 * 魔法陣のクラス
 * @author a50609
 *
 */
public class MagicCircle extends GameChara {
	/**
	 * 消滅するまでの時間
	 */
	int waittime=3;

	/**
	 * 魔法陣クラスのコンストラクタ
	 * @param x　初期位置
	 * @param y　初期位置
	 * @param img　画像
	 */
	public MagicCircle(int x, int y, BufferedImage img) {
		super(x, y, 200, 200, img, 0, 0, 200, 200);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 呼び出されるごとにwaittimeを1ずつ減らす
	 */
	@Override
	public void move() {
		// TODO Auto-generated method stub
		waittime-=1;
	}
	/**
	 * waittimeが0を下回ったかどうかを判断する
	 * @return waittimeが0を下回った時にtrueを返す真偽値
	 */
	public boolean isOutofScreen() {
		if(waittime<0) {
			return true;
		}else {
			return false;
		}
	}
	@Override
	public void showHP(Graphics g, ImageObserver io){

	}
}
