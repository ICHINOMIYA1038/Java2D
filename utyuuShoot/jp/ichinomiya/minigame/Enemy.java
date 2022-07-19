package jp.ichinomiya.minigame;

import java.awt.Point;
import java.awt.image.BufferedImage;

/**
 * 敵クラス
 * @author a50609
 */
public class Enemy extends GameChara {
	PatternReader preader;
	int inversion;

	public Enemy(int x, int y, BufferedImage img, int ptnum, String patstr) {
		super(x, y, 40, 40, img, 48+ptnum*48, 0, 48, 48);
		// TODO Auto-generated constructor stub
		preader = new PatternReader(patstr);
		if(chara_y>UtyuuShootMain.SCREEN_H/2) {
			inversion = -1;
		}else {
			inversion = 1;
		}
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub
		Point movexy = preader.next();
		chara_x = chara_x + movexy.x;
		chara_y = chara_y + movexy.y*inversion;

	}

}
