package jp.ichinomiya.minigame;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

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
	 *  サイズ指定をしない爆発エフェクトのコンストラクタ
	 * @param x キャラクターのx座標
	 * @param y キャラクターのy座標
	 * @param img キャラクター画像
	 */
	public Explosion(int x, int y, BufferedImage img) {
		super(x, y, 0, 0, img, 0, 0, 40, 40);
		waittime = 5;
		// TODO Auto-generated constructor stub
	}

	/**
	 * サイズを引数にとる爆発エフェクトのコンストラクタ
	 * 画像のサイズにあったエフェクトのサイズを指定する
	 * @param x　初期位置
	 * @param y　爆発のエフェクト
	 * @param img　画像 
	 * @param sizex　エフェクトのサイズ
	 * @param sizey　エフェクトのサイズ
	 */
	public Explosion(int x, int y, BufferedImage img, int sizex,int sizey) {
		super(x, y, 0, 0, img, 0, 0, sizex, sizey);
		waittime = 5;
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
	public void showHP(Graphics g, ImageObserver io){

	}

	@Override
	public void move() {
		// TODO Auto-generated method stub
		waittime = waittime-1;
	}

}