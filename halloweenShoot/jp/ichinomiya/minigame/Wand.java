package jp.ichinomiya.minigame;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

/**
 * 杖クラス
 * @author a50609
 */
public class Wand extends GameChara {

	/**
	 * 杖クラスのコンストラクタ
	 * @param x スポーン位置
	 * @param y スポーン位置
	 * @param img 画像
	 */
	public Wand(int x, int y, BufferedImage img) {
		super(x, y, 24, 24, img, 0, 0, 30, 30);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub

	}
	@Override
	public void showHP(Graphics g, ImageObserver io){

	}

}
