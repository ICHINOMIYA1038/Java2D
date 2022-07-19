package jp.ichinomiya.minigame;

import java.awt.image.BufferedImage;

/**
 *　敵の銃弾のクラス
 * @author a50609
 */
public class EnemyBullet extends GameChara {

	int dx,dy;
	public EnemyBullet(int x, int y, int jx, int jy, BufferedImage img) {
		super(x, y, 12, 12, img, 240, 12, 12, 12);
		double d = Math.sqrt((jx-x)*(jx-x) + (jy-y)*(jy-y));
		if(d!=0) {
			dx = (int)Math.round((jx-x)/d*6.0);
			dy = (int)Math.round((jy-y)/d*6.0);
		}
		// TODO Auto-generated constructor stub
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub
		chara_x = chara_x + dx;
		chara_y = chara_y + dy;
	}

}
