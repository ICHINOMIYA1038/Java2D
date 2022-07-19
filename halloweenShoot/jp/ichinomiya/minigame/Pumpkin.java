package jp.ichinomiya.minigame;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

/**
 * カボチャクラス
 * @author a50609
 */
public class Pumpkin extends GameChara {
	/**
	 * x方向の速度
	 */
	int speed_x=10;
	/**
	 * y方向の速度
	 */
	int speed_y=-70;

	/**
	 * カボチャクラスのコンストラクタ
	 * @param x　初期位置
	 * @param y　初期位置
	 * @param img　画像
	 */
	public Pumpkin(int x, int y, BufferedImage img) {
		super(x, y, 35, 35, img, 0, 0, 30, 30);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub
		chara_x = chara_x + speed_x;
		chara_y = chara_y + speed_y;
		speed_y = speed_y + 12;
	}

	public boolean isOutofScreen() {
		if( chara_x<AREA_X1||chara_x+image_w>AREA_X2||chara_y+image_w>AREA_Y2){
			return true;
		}else {
			return false;
		}
	}
	@Override
	public void showHP(Graphics g, ImageObserver io){

	}

}
