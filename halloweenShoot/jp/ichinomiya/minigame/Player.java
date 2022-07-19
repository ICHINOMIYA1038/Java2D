package jp.ichinomiya.minigame;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

/**
 * プレイヤークラス
 * @author a50609
 */
public class Player extends GameChara {

	/**
	 * 移動速度
	 */
	public int speed=8;
	/**
	 * プレイヤー残機
	 */
	public int player_life=3;
	/**
	 * 連続でダメージを受けない間隔
	 */
	public int damaged_span=0;
	/**
	 * プレイヤークラスのコンストラクタ
	 * @param x　初期位置
	 * @param y　初期位置
	 * @param img　画像
	 */
	public Player(int x, int y, BufferedImage img) {
		super(x, y, 12,12,img, 0, 0, 50, 50);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub


	}
	/**
	 * プレイヤーの移動処理
	 * @param up boolean　キー入力されているかどうか
	 * @param down boolean　キー入力されているかどうか
	 * @param left boolean　キー入力されているかどうか
	 * @param right boolean　キー入力されているかどうか
	 */
	public void move(boolean up, boolean down, boolean left, boolean right) {
		// TODO Auto-generated method stub
		if(up==true) chara_y=chara_y-speed;
		if(down==true) chara_y=chara_y+speed;
		if(left==true) chara_x=chara_x-speed;
		if(right==true) chara_x=chara_x+speed;

		if(chara_y<0) chara_y=0;
		if(chara_x<0) chara_x=0;
		if(chara_y>halloweenShootMain.SCREEN_H-48) 
			chara_y=halloweenShootMain.SCREEN_H-48;
		if(chara_x>halloweenShootMain.SCREEN_W-48) 
			chara_x=halloweenShootMain.SCREEN_W-48;

		damaged_span -=1;
		if(damaged_span<=0) {
			damaged_span=0;
		}
	}

	@Override
	public void showHP(Graphics g, ImageObserver io){

	}

	/**
	 * @param num　与えられるダメージ量
	 * @return ダメージを受けたかどうかを指す真偽値
	 */
	public boolean damaged(int num) {
		if(damaged_span==0) {
			player_life -= num;
			damaged_span = 10;
			return true;
		}
		return false;
	}
}
