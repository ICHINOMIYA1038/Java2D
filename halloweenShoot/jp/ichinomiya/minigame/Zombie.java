package jp.ichinomiya.minigame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

/**
 * ゾンビクラス
 * @author a50609
 */
public class Zombie extends GameChara {
	/**
	 * 歩く速度
	 */
	int speed=1;
	/**
	 * 歩く速度にインターバルを持たせてさらに足を遅くするための変数
	 */
	int waittime=5;
	/**
	 * 体力
	 */
	int HP=8;
	/**
	 * インターバル
	 * 魔法陣攻撃を複数回受けないようにしている
	 */
	int interval=5;
	/**
	 * 右向きの画像
	 */
	BufferedImage imageright;
	/**
	 * 左むきの画像
	 */
	BufferedImage imageleft;

	/**
	 * ゾンビクラスのコンストラクタ
	 * @param x 初期位置x
	 * @param y　初期位置y
	 * @param img　左向き画像
	 * @param img2 右向き画像
	 */
	public Zombie(int x, int y, BufferedImage img,BufferedImage img2) {
		super(x, y, 90, 250, img, 0, 0, 200, 266);
		// TODO Auto-generated constructor stub
		imageright=img2;
		imageleft = img;
	}

	/**
	 *　移動処理
	 */
	@Override
	public void move() {
		interval = interval -1;
		if(interval<=0) interval=0;
		// TODO Auto-generated method stub
		waittime = waittime-1;
		if(waittime<0) {
			chara_x = chara_x-speed;
			waittime=3;
		}

		if(speed>0&&chara_x<3) {
			speed=speed*-1;
			image1 = imageright;
		}
		if(speed<0&&chara_x>100) {
			speed=speed*-1;
			image1 = imageleft;
		}
	}

	/**
	 *体力ゲージの表示
	 */
	@Override
	public void showHP(Graphics g, ImageObserver io){
		//体力ゲージ
		g.setColor(Color.GRAY);
		g.fillRect(chara_x+82,chara_y-5,48,8);

		g.setColor(Color.RED);
		g.fillRect(chara_x+82,chara_y-5,6*HP,8);
	}

}
