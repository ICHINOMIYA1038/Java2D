package jp.ichinomiya.minigame;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

/**
 *　アイテム十字架のクラス
 *  @author a50609
 */
public class Cross extends GameChara {

	/**
	 * @param x　初期位置
	 * @param y　初期位置
	 * @param img　画像
	 */
	public Cross(int x, int y, BufferedImage img) {
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
