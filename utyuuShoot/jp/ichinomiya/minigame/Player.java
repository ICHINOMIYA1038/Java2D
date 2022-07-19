package jp.ichinomiya.minigame;

import java.awt.image.BufferedImage;

/**
 * プレイヤークラス
 * @author a50609
 */
public class Player extends GameChara {

	/**
	 * 移動速度s
	 */
	public int speed=8;
	public Player(int x, int y, BufferedImage img) {
		super(x, y, 12,12,img, 0, 0, 48, 48);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub
		
		
	}
	/**
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
		if(chara_y>UtyuuShootMain.SCREEN_H-48) 
			chara_y=UtyuuShootMain.SCREEN_H-48;
		if(chara_x>UtyuuShootMain.SCREEN_W-48) 
			chara_x=UtyuuShootMain.SCREEN_W-48;
	}
}
