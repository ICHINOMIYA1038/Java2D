package jp.ichinomiya.minigame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

/**
 * ヴァンパイアのクラス
 * @author a5060
 */
public class Vampire extends GameChara {
	/**
	 * ヴァンパイアが移動するときの待機時間
	 */
	int waittime=50;
	/**
	 * 動きを反転させるための変数 1と-1をとる
	 */
	int inverse =1;
	/**
	 * 体力
	 */
	int HP=3;
	/**
	 * 魔法陣攻撃を2回受けないようにするための変数
	 */
	int interval=5;
	/**
	 * ヴァンパイアクラスのコンストラクタ
	 * @param x スポーン位置
	 * @param y　スポーン位置
	 * @param img キャラクター
	 */
	public Vampire(int x, int y, BufferedImage img) {
		super(x, y, 50, 50, img, 0, 0, 50, 50);
		// TODO Auto-generated constructor stub
	}

	/**
	 *移動処理
	 */
	@Override
	public void move() {
		interval = interval -1;
		if(interval<=0) interval=0;
		// TODO Auto-generated method stub
		waittime -=1;
		if(waittime<0)
		{	waittime=50;
		chara_x =(int)((Math.random()*8)*20)+300;
		chara_y =(int)((Math.random()*22)*20);

		}
		if((int)(Math.random()*2)<1) inverse = 1;
		else inverse = -1;
		chara_x = chara_x + (int)(Math.random()*5)*inverse;
		chara_y = chara_y + (int)(Math.random()*5)*inverse;
	}
	/**
	 * 体力ゲージの表示
	 */
	@Override
	public void showHP(Graphics g, ImageObserver io){
		g.setColor(Color.GRAY);
		g.fillRect(chara_x+5,chara_y-10,30,5);

		g.setColor(Color.RED);
		g.fillRect(chara_x+5,chara_y-10,10*HP,5);

	}

}
