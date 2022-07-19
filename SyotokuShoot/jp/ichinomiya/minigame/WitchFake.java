package jp.ichinomiya.minigame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

/**
 * 偽物の魔女クラス
 * @author a50609
 */
public class WitchFake extends GameChara {
	/**
	 * 魔女が揺れ動く速度
	 */
	int speed=1;
	/**
	 * 揺れ動きの時間間隔
	 */
	int waittime=5;
	/**
	 *　体力 
	 */
	int HP;
	/**
	 * 揺れ動きのための値 1と-1をとる
	 */
	int inverse=1;
	/**
	 * 偽物魔女のコンストラクタ
	 * @param x 初期位置
	 * @param y　初期位置
	 * @param img　画像
	 * @param hp  表示するためのHP
	 */
	public WitchFake(int x, int y, BufferedImage img, int hp) {
		super(x, y, 90, 68, img, 0, 0, 90, 68);
		// TODO Auto-generated constructor stub
		this.HP=hp;
	}

	/**
	 *移動処理
	 */
	@Override
	public void move() {
		// TODO Auto-generated method stub

		//揺れる動き
		waittime = waittime-1;
		if(waittime<0) {
			if((int)(Math.random()*2)==1) inverse = 1;
			else inverse = -1;
			waittime=5;
			chara_x = chara_x + (int)(Math.random()*3)*inverse;
		}
	}

	/**
	 *体力ゲージの表示
	 */
	@Override
	public void showHP(Graphics g, ImageObserver io) {
		// TODO Auto-generated method stub
		g.setColor(Color.GRAY);
		g.fillRect(chara_x+5,chara_y-5,60,8);

		g.setColor(Color.RED);
		g.fillRect(chara_x+5,chara_y-5,2*HP,8);
	}

}
