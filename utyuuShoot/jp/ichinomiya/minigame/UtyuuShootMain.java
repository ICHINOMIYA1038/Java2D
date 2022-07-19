package jp.ichinomiya.minigame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.sound.sampled.Clip;

/**
 *GameTemplateクラスを継承したメインクラス
 * @author a50609
 */
public class UtyuuShootMain extends GameTemplate {
	/**
	 * 画面の横幅
	 */
	public static final int SCREEN_W = 640;
	/**
	 * 画面の縦幅
	 */
	public static final int SCREEN_H = 480;
	/**
	 * 敵の出現する確率
	 */
	public static final int ENEMY_RATE = 10;
	/**
	 * 時間制限(ms)
	 */
	public static final int TIME_LIMIT=33000;
	/**
	 * 敵の行動パターン
	 */
	public static final String[] ENEMY_PATTERN = {"-4,0,60,0,0,30","-2,4,10,-2,-3,5,-2,-2,5,-2,0,5,-2,2,5,-2,3,5,-2,4,10,-2,3,5,-2,2,5,0,5,-2,-2,5,-2,-3,5","-2,0,10,-8,0,60,-2,0,10,4,3,60"};
	/**
	 * イテレーター
	 */
	Iterator it;
	/**
	 * フレームのインセット
	 */
	Insets insets = frame1.getInsets();
	Clip seClip1, seClip2, seClip3;
	BufferedImage charaimage,backimage,startimage;
	Player player;
	/**
	 * キー情報
	 */
	boolean upkey=false, downkey=false, rightkey=false, leftkey=false, shiftkey=false;
	ArrayList player_bullets = new ArrayList();
	ArrayList enemys = new ArrayList();
	ArrayList enemy_bullets = new ArrayList();
	ArrayList explosions = new ArrayList();
	/**
	 * 銃弾を放つ間隔
	 */
	int shoot_span = 0;
	/**
	 * 敵の数
	 */
	int enemy_num = 1;
	/**
	 * 敵の弾丸の確率
	 */
	int e_bullet_rate = 150;
	/**
	 * スタートした時の時間情報
	 */
	long starttime;
	/**
	 * スコアとステージ情報
	 */
	int score,highscore,stagenum=1;
	
	
	/**
	 * コンストラクタ　基底クラスのコンストラクタで、画面を作成し、画像と音声を変数に格納している
	 * goStartmain()を呼び出し、GAMESTATEを更新している。
	 */
	public UtyuuShootMain() {
		super(SCREEN_W, SCREEN_H, "宇宙シューティング");
		// TODO Auto-generated constructor stub
	
	try {
		charaimage  = ImageIO.read(getClass().getResource("shooting.png"));
		backimage   = ImageIO.read(getClass().getResource("back.jpg"));
		startimage   = ImageIO.read(getClass().getResource("start.jpg"));
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	seClip1 = readSound("burst01.wav");
	seClip2 = readSound("fm005.wav");
	seClip3 = readSound("smoke03.wav");
	readMidi("sw3.MID");
	
	goStartGamen();
	
	}

	/**
	 * ゲームオーバー処理の最初で呼び出される。
	 */
	@Override
	public void initGameOver() {
		// TODO Auto-generated method stub
		midiseq.stop();
		if(score>highscore) highscore = score;
		score = 0;
	}

	/**
	 * ステージクリア処理の最初で呼び出される
	 */
	@Override
	void initStageClear() {
		// TODO Auto-generated method stub
		midiseq.stop();
		stagenum+=1;
		enemy_num+=1;
		if(enemy_num>15) {
			enemy_num=1;
			e_bullet_rate=e_bullet_rate-50;
		}
		if(e_bullet_rate<20)e_bullet_rate=20;
		score +=stagenum*100;
	}

	/**
	 * ステージがスタートするタイミングで呼び出される
	 * BGMをスタートし、プレイヤーのオブジェクトを作成,エネミーなどの配列を作成
	 * 入力キーを全てfalseに戻す
	 * スタートタイムをこの時点で取得し、格納しておく
	 */
	@Override
	void initStageStart() {
		// TODO Auto-generated method stub
		midiseq.setTickPosition(0);
		midiseq.start();
		player = new Player(SCREEN_W/2,SCREEN_H/2,charaimage);
		player_bullets = new ArrayList();
		enemys = new ArrayList();
		explosions = new ArrayList();
		upkey=false;downkey=false;rightkey=false;leftkey=false;
		shiftkey=false;
		starttime=System.currentTimeMillis();
	}

	/**
	 * @param arg0 入力されたキー情報
	 * ここでキー入力の情報を格納する
	 */
	@Override
	void keyPressedGameMain(int arg0) {
		// TODO Auto-generated method stub
		if(arg0==KeyEvent.VK_UP) upkey=true;
		if(arg0==KeyEvent.VK_DOWN) downkey=true;
		if(arg0==KeyEvent.VK_LEFT) leftkey=true;
		if(arg0==KeyEvent.VK_RIGHT) rightkey=true;
		if(arg0==KeyEvent.VK_SHIFT) shiftkey=true;
		
		if(arg0==KeyEvent.VK_W) upkey=true;
		if(arg0==KeyEvent.VK_S) downkey=true;
		if(arg0==KeyEvent.VK_A) leftkey=true;
		if(arg0==KeyEvent.VK_D) rightkey=true;

	}

	/**
	 * @param arg0 離されたキー情報
	 * ここでキー入力の情報を格納する
	 */
	@Override
	void keyReleasedGameMain(int arg0) {
		// TODO Auto-generated method stub
		if(arg0==KeyEvent.VK_UP) upkey=false;
		if(arg0==KeyEvent.VK_DOWN) downkey=false;
		if(arg0==KeyEvent.VK_LEFT) leftkey=false;
		if(arg0==KeyEvent.VK_RIGHT) rightkey=false;
		if(arg0==KeyEvent.VK_SHIFT) shiftkey=false;
		
		if(arg0==KeyEvent.VK_W) upkey=false;
		if(arg0==KeyEvent.VK_S) downkey=false;
		if(arg0==KeyEvent.VK_A) leftkey=false;
		if(arg0==KeyEvent.VK_D) rightkey=false;
	}

	/**
	 * ゲームのメイン画面を描画するためのメソッド
	 * 当たり判定や画面の描画もここで行う
	 * @param g
	 */
	@Override
	void runGameMain(Graphics g) {
		// TODO Auto-generated method stub
		g.clearRect(0,0,SCREEN_W+insets.left+insets.right,SCREEN_H+insets.top+insets.bottom);
		g.drawImage(backimage,0,0,frame1);
		//自機移動
		player.move(upkey, downkey, leftkey, rightkey);
		
		//自分の弾発射
		if(shiftkey==true && shoot_span==0) {
			player_bullets.add(new PlayerBullet(player.chara_x+36,player.chara_y+24,charaimage));
			shoot_span = 10;
		}
		if(shoot_span>0) shoot_span = shoot_span - 1;
		
		//敵
		if(enemys.size()<enemy_num && Math.random()*ENEMY_RATE<1) {
			int ptnum = (int)(Math.random()*3);
			int ty =(int)(Math.random()*23)*20;
			enemys.add(new Enemy(SCREEN_W,ty,charaimage,ptnum,ENEMY_PATTERN[ptnum]));
		}
		
		//敵の弾発射
		it = enemys.iterator();
		while(it.hasNext()==true) {
			Enemy tk = (Enemy)it.next();
			if(Math.random()*e_bullet_rate<1) {
				enemy_bullets.add(new EnemyBullet(tk.chara_x-8,tk.chara_y+24,player.chara_x,player.chara_y,charaimage));
				seClip2.stop();
				seClip2.setFramePosition(0);
				seClip2.start();
			}
		}
		
		//当たり判定 てきのたまと自分
		it=enemys.iterator();
		while(it.hasNext()==true){
			Enemy tk = (Enemy) it.next();
			Iterator it2 =player_bullets.iterator();
			while(it2.hasNext()==true) {
				PlayerBullet jt = (PlayerBullet)it2.next();
				if(tk.isColided(jt)==true) {
					explosions.add(new Explosion(tk.chara_x,tk.chara_y,charaimage));
					it.remove();
					it2.remove();
					score=score+10;
					seClip1.stop();
					seClip1.setFramePosition(0);
					seClip1.start();
					break;
				}
			}
		}
		
		//当たり判定てきと自分
		it=enemys.iterator();
		while(it.hasNext()==true){
			Enemy tk = (Enemy) it.next();
			if(tk.isColided(player)) {
				int time;
				seClip3.stop();
				seClip3.setFramePosition(0);
				seClip3.start();
				goGameOver();
				break;
			}
		}
		
		it=enemy_bullets.iterator();
		while(it.hasNext()==true){
			EnemyBullet tt = (EnemyBullet) it.next();
			if(tt.isColided(player)) {
				it.remove();
				goGameOver();
				break;
			}
		}
		
		//爆発
				it = explosions.iterator();
				while(it.hasNext()==true) {
					Explosion bh =(Explosion)it.next();
					bh.draw(g, frame1);
					if(bh.isOutofScreen()==true) it.remove();
					
				}
				
		long jikansa = System.currentTimeMillis() - starttime;
		if(jikansa>TIME_LIMIT) goStageClear();
		
		g.setColor(Color.YELLOW);
		g.fillRect(150,50,(int)((TIME_LIMIT-jikansa)/60), 4);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Sanserif",Font.PLAIN,10));
		g.drawString("SC:"+score+" HI:"+highscore, 2, 50);
		
		//表示と削除
		player.draw(g, frame1);
		it = player_bullets.iterator();
		while(it.hasNext()==true) {
			PlayerBullet jt = (PlayerBullet)it.next();
			jt.draw(g, frame1);
			if(jt.isOutofScreen()) it.remove();
		}
		
		it=enemys.iterator();
		while(it.hasNext()==true) {
			Enemy tk = (Enemy)it.next();
			tk.draw(g,frame1);
			if(tk.isOutofScreen()==true) it.remove();
		}
		it=enemy_bullets.iterator();
		while(it.hasNext()==true) {
			EnemyBullet tm =(EnemyBullet)it.next();
			tm.draw(g, frame1);
			if(tm.isOutofScreen()==true) it.remove();
		}
	}

	/**
	 * ゲームオーバーの処理をここで行う
	 * テキストの表示を行っている
	 * @param g グラフィックスオブジェクト
	 */
	@Override
	void runGameOver(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(Color.RED);
		g.setFont(new Font("SansSerif",Font.BOLD,80));
		drawStringCenter("GAMEOVER",220,g);
		g.setFont(new Font("SansSerif",Font.BOLD,30));
		drawStringCenter("Push R Key",280,g);
	}

	/**
	 * ステージクリアの処理
	 * テキストの表示を行う
	 * @param g　グラフィックスオブジェクト
	 */
	@Override
	void runStageClear(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(Color.GREEN);
		g.setFont(new Font("SansSerif",Font.BOLD,50));
		drawStringCenter("ステージクリア",220,g);
	}

	/**
	 * ステージ開始の処理
	 * テキストの表示を行う
	 * @param g　グラフィックスオブジェクト
	 */
	@Override
	void runStageStart(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(Color.BLACK);
		g.fillRect(0,0,640,480);
		g.setColor(Color.GREEN);
		g.setFont(new Font("SansSerif",Font.BOLD,50));
		drawStringCenter("ステージ開始",220,g);
	}

	/**
	 * ゲームがスタートするときのタイトル画面を表示する
	 * @param g グラフィックスオブジェクト
	 */
	@Override
	void runStartGamen(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(startimage,0,0,frame1);
	}

	/**
	 * メインルーチン
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		UtyuuShootMain usm = new UtyuuShootMain();
	}

}
