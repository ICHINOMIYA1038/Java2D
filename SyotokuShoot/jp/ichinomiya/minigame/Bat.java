package jp.ichinomiya.minigame;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

/**
 * コウモリクラス
 * @author a50609
 */
public class Bat extends GameChara {
	/**
	 * 動きを反転させるための変数 1と-1をとる
	 */
	int inverse;
	/**
	 * x方向の速度、初期値は-3
	 */
	int v_x=-3;
	/**
	 * 体力
	 */
	int HP=1;
	/**
	 * @param x 初期位置
	 * @param y　初期位置
	 * @param img　画像
	 */
	public Bat(int x, int y, BufferedImage img) {
		super(x, y,  40, 30, img, 0, 0, 40, 30);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub
		if((int)(Math.random()*2)<1) inverse = 1;
		else inverse = -1;
		chara_x = chara_x + (int)(Math.random()*6)*inverse;
		chara_y = chara_y + (int)(Math.random()*5)*inverse;
		chara_x = chara_x + v_x;
	}
	/**
	 *　体力バーの表示,コウモリクラスは不要
	 */
	@Override
	public void showHP(Graphics g, ImageObserver io) {

	}

}
