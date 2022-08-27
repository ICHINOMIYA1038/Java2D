package jp.ichinomiya.minigame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

/**
 * ヤマタノオロチクラス
 * @author a50609
 */
public class Boss extends GameChara {
	/**
	 * 揺れ動きの速度
	 */
	int speed=1;
	/**
	 * 揺れ動きのクールタイム
	 */
	int waittime=5;
	/**
	 * 体力
	 */
	int HP=30;
	/**
	 * インターバル
	 * 仏攻撃を複数回受けないようにしている
	 */
	int interval=5;
	/**
	 * 揺れ動きのための変数　-1,1を変動する
	 */
	int inverse=1;
	/**
	 * 偽物を出すまでの間隔
	 */
	int fakespan = 150;
	/**
	 * 突進攻撃の間隔
	 */
	int rushspan = 200;
	/**
	 * 突進攻撃のスピード
	 */
	int rushspeed = 30;
	/**
	 * 突進攻撃が済んだかどうか
	 */
	boolean rushdone = true;


	/**
	 * ヤマタノオロチクラスのコンストラクタ
	 * @param x スポーン位置
	 * @param y　スポーン位置
	 * @param img 画像
	 */
	public Boss(int x, int y, BufferedImage img) {
		super(x, y, 90, 68, img, 0, 0, 90, 68);
		// TODO Auto-generated constructor stub

	}

	@Override
	public void move() {
		//仏攻撃を一回だけ受けるためのインターバル
		interval = interval -1;
		if(interval<=0) interval=0;
		fakespan -= 1;
		rushspan -= 1;
		//揺れる動き
		waittime = waittime-1;
		if(waittime<0) {
			if((int)(Math.random()*2)==1) inverse = 1;
			else inverse = -1;
			waittime=5;
			chara_x = chara_x + (int)(Math.random()*3)*inverse;
			if(fakespan<0)
				fakespan=0;
		}
		if(rushspan<0) {
			fakespan=0;
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

	@Override
	public void draw(Graphics g, ImageObserver io) {
		g.drawImage(image1, chara_x, chara_y, chara_x + image_w, chara_y + image_h, image_x, image_y, image_x  + image_w, image_y  + image_h, io);

		showHP(g,io);
	}

	

}
