package jp.ichinomiya.minigame;

import java.awt.image.BufferedImage;

/**
 * プレイヤーの銃弾クラス
 * @author a50609
 */
public class PlayerBullet extends GameChara {

	/**
	 * コンストラクタ
	 * @param x 画像のx座標
	 * @param y 画像のy座標
	 * @param img 画像
	 */
	public PlayerBullet(int x, int y, BufferedImage img) {
		super(x, y, 12, 12, img, 240, 0, 12, 12);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void move() {
		chara_x= chara_x+12;
		// TODO Auto-generated method stub

	}

}
