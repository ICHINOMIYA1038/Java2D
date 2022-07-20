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
 * ゲームテンプレートクラスを継承したメインクラス
 * @author a50609
 */
public class ShyotokuShoot extends GameTemplate {
	/**
	 * 画面の横幅
	 */
	public static final int SCREEN_W = 640;
	/**
	 * 画面の縦幅
	 */
	public static final int SCREEN_H = 480;

	/**
	 * 魔法ゲージの最大
	 */
	public static final int MAGIC_LIMIT=10;


	/**
	 * イテレーター
	 */
	Iterator<GameChara> it;
	/**
	 * フレームのインセット
	 */
	Insets insets = frame1.getInsets();
	Clip seButton, seTitle, seGameClear,seGameOver,bgmMain,seItemGet,seMagic,sePumpkin,seVampiredown,seBatdown,seZombiedown,seDamaged,selaugh0,selaugh1;
	Clip selaugh2,selaugh3,selaugh4,seRush,seBeforeRush,seFake,seClip21;
	BufferedImage charaimage,backimage,startimage,item1image,item2image,scepterimg,spiritimg,lanternimg,buddhaimg;
	BufferedImage snakeimage,zombieimage,zombieimage2,smokeimage, explosionimg,explosion2img,explosion3img,explosion4img;
	BufferedImage gameoverimg,gameclearimg;
	Player player;
	Witch witch;
	MagicCircle magicAtk;
	/**
	 * 背景の色
	 */
	Color backcolor=new Color(227,222,241,200);
	/**
	 * キー情報
	 */

	ArrayList<GameChara> pumpkins = new ArrayList<GameChara>();
	ArrayList<GameChara> bats = new ArrayList<GameChara>();
	ArrayList<GameChara> vampires = new ArrayList<GameChara>();
	ArrayList<GameChara> items_cross = new ArrayList<GameChara>();
	ArrayList<GameChara> items_wand = new ArrayList<GameChara>();
	ArrayList<GameChara> magics = new ArrayList<GameChara>();
	ArrayList<GameChara> zombies = new ArrayList<GameChara>();
	ArrayList<GameChara> witchFakes = new ArrayList<GameChara>();
	ArrayList<GameChara> explosions = new ArrayList<GameChara>();
	/**
	 * 魔法ゲージで表示される色
	 */
	Color magic_color=new Color(0,0.8f,0.96f,1);
	/**
	 * 銃弾を放つ間隔
	 */
	int shoot_span = 0;
	/**
	 * コウモリの最大数
	 */
	int bat_num;
	/**
	 * ヴァンパイアの最大数
	 */
	int vampire_num;
	/**
	 * 巨大ゾンビの最大数
	 */
	int zombie_num;
	/**
	 * スタートした時の時間情報
	 */
	long starttime;
	/**
	 * スコア
	 */
	float distance=0;
	

	/**
	 * プレイヤー魔法ゲージ
	 */
	int magic = 0;
	/**
	 * 魔女が出現したかどうか
	 */
	boolean isWitch = false;
	/**
	 * コウモリの出現する確率
	 */
	int bat_rate;
	/**
	 * ヴァンパイアの出現する確率
	 */
	int vampire_rate;
	/**
	 * ゾンビの出現する確率
	 */
	int zombie_rate;
	
	/**
	 * ボスが現れるスコア
	 */
	int goal_distance;
	
	/**
	 * メインクラスのコンストラクタ
	 */
	public ShyotokuShoot() {
		super(SCREEN_W, SCREEN_H, "聖徳シュート");
		// TODO Auto-generated constructor stub
		try {
			charaimage = ImageIO.read(getClass().getResource("player.png"));
			backimage  = ImageIO.read(getClass().getResource("main.jpg"));
			startimage = ImageIO.read(getClass().getResource("title.jpg"));
			item1image = ImageIO.read(getClass().getResource("cross.png"));
			item2image = ImageIO.read(getClass().getResource("wand.png"));
			scepterimg = ImageIO.read(getClass().getResource("scepter.png"));
			spiritimg = ImageIO.read(getClass().getResource("spirit.png"));
			lanternimg = ImageIO.read(getClass().getResource("lantern.png"));
			buddhaimg =  ImageIO.read(getClass().getResource("Buddha.png"));
			zombieimage =  ImageIO.read(getClass().getResource("zombie.png"));
			zombieimage2= ImageIO.read(getClass().getResource("zombie2.png"));
			snakeimage =  ImageIO.read(getClass().getResource("snake.png"));
			smokeimage =  ImageIO.read(getClass().getResource("smoke.png"));
			explosionimg = ImageIO.read(getClass().getResource("explosion.png"));
			explosion2img = ImageIO.read(getClass().getResource("explosion2.png"));
			explosion3img = ImageIO.read(getClass().getResource("explosion3.png"));
			explosion4img = ImageIO.read(getClass().getResource("blood.png"));
			gameoverimg = ImageIO.read(getClass().getResource("gameover.jpg"));
			gameclearimg = ImageIO.read(getClass().getResource("gameclear.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		seButton = readSound("button.wav");
		seTitle = readSound("titlebgm.wav");
		seGameClear = readSound("gameclear.wav");
		seGameOver = readSound("gameover.wav");
		bgmMain = readSound("main.wav");
		seItemGet = readSound("item_get.wav");
		seMagic = readSound("magic.wav");
		sePumpkin = readSound("pumpkin.wav");
		seVampiredown = readSound("vampire_down.wav");
		seBatdown = readSound("bat_down.wav");
		seZombiedown = readSound("zombie_down.wav");
		seDamaged = readSound("damaged.wav");
		selaugh0 = readSound("shortlaugh.wav");
		selaugh1 = readSound("laugh1.wav");
		selaugh2 = readSound("laugh2.wav");
		selaugh3 = readSound("laugh3.wav");
		selaugh4 = readSound("laugh4.wav");
		seRush = readSound("rush.wav");
		seBeforeRush = readSound("beforerush.wav");
		seFake = readSound("fake.wav");
		seClip21 = readSound("fake.wav");
		gameInit();


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
		if(arg0==KeyEvent.VK_SPACE) spacekey=true;
		if(arg0==KeyEvent.VK_ESCAPE) esckey=true;

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
		if(arg0==KeyEvent.VK_SPACE) spacekey=false;
		if(arg0==KeyEvent.VK_ESCAPE) esckey=false;

		if(arg0==KeyEvent.VK_W) upkey=false;
		if(arg0==KeyEvent.VK_S) downkey=false;
		if(arg0==KeyEvent.VK_A) leftkey=false;
		if(arg0==KeyEvent.VK_D) rightkey=false;
	}

	/**
	 * タイトル画面を表示する
	 * @param g 
	 */
	@Override
	void runTitle(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(startimage,0,0,frame1);
		g.setColor(Color.BLACK);
		g.fillRect(205,325,230,40);
		g.setColor(Color.RED);
		g.setFont(new Font("SansSerif",Font.BOLD,40));
		drawStringCenter("聖徳シュート",200,g);
		g.setColor(Color.BLUE);
		g.setFont(new Font("SansSerif",Font.BOLD,20));
		drawStringCenter("Push SPACE to Start",350,g);
		

	}

	@Override
	void runStageStart(Graphics g) {
		// TODO Auto-generated method stub

	}

	@Override
	void runStageClear(Graphics g) {
		// TODO Auto-generated method stub
		g.clearRect(0,0,SCREEN_W,SCREEN_H);
		g.drawImage(gameclearimg,0,0,frame1);
		g.setColor(backcolor);
		g.fillRect(0, 0, SCREEN_W, SCREEN_H);
		g.setColor(Color.BLACK);
		g.fillRect(205,325,230,40);
		g.setColor(Color.RED);
		g.setFont(new Font("SansSerif",Font.BOLD,40));
		drawStringCenter("ゲームクリア",200,g);
		g.setColor(Color.BLUE);
		g.setFont(new Font("SansSerif",Font.BOLD,20));
		drawStringCenter("Push \"P\" to Title",350,g);
	}

	@Override
	void runGameMain(Graphics g,int mode, int difficulty) {
		changeDifficulty(difficulty);
		distance +=1;
		player_x = player.chara_x;
		player_y = player.chara_y;
		// TODO Auto-generated method stub
		g.clearRect(0,0,SCREEN_W,SCREEN_H);
		g.setColor(backcolor);
		g.fillRect(0, 0, SCREEN_W, SCREEN_H);
		g.setColor(backcolor);
		g.fillRect(0, 0, SCREEN_W, SCREEN_H);
		g.drawImage(backimage,0,0,frame1);
		//自機移動
		player.move(upkey, downkey, leftkey, rightkey);
		//表示と削除
		player.draw(g, frame1);

		//残機の表示
		g.setColor(Color.BLACK);
		g.setFont(new Font("SansSerif",Font.BOLD,20));
		g.drawString("残機:"+player.player_life,30,30);

		//魔法ゲージ
		g.setColor(Color.GRAY);
		g.fillRect(30,50,80,10);
		g.setColor(magic_color);
		g.fillRect(30,50,80/MAGIC_LIMIT*magic,10);

		if(magic==MAGIC_LIMIT) {
			g.setColor(magic_color.brighter());
			g.fillRect(30,50,80/MAGIC_LIMIT*magic,10);
		}

		//スコア表示
		g.setColor(Color.BLACK);
		g.setFont(new Font("SansSerif",Font.BOLD,20));
		drawStringCenter("DISTANCE:"+distance + "m",30,g);
		g.setColor(Color.BLACK);
		g.setFont(new Font("SansSerif",Font.BOLD,10));
		if(distance<goal_distance)
			drawStringCenter("あと"+(goal_distance-distance)+"mでゴールだ",50,g);
		if(distance>=goal_distance)
			drawStringCenter("ヤマタノオロチが現れた",50,g);


		//カボチャ発射
		if(shiftkey==true && shoot_span==0 ) {
			pumpkins.add(new Scepter(player.chara_x+36,player.chara_y,scepterimg));
			shoot_span = 15;
			//sePumpkin.stop();
			sePumpkin.setFramePosition(0);
			sePumpkin.start();
		}
		if(shoot_span>0) shoot_span = shoot_span - 1;

		//魔法陣
		if(spacekey==true && shoot_span==0 && magic==MAGIC_LIMIT) {
			magics.add(new MagicCircle(player.chara_x-70,player.chara_y-70,buddhaimg));
			magic=0;
			//seMagic.stop();
			seMagic.setFramePosition(0);
			seMagic.start();
		}

		//batの生成
		if(bats.size()<bat_num && (int)(Math.random()*bat_rate)<1) {
			int rand_x =(int)((Math.random()*8)*20)+300;
			int rand_y =(int)((Math.random()*22)*20);
			bats.add(new Bat(rand_x,rand_y,spiritimg));
		}

		//ヴァンパイアの生成
		if(vampires.size()<vampire_num && (int)(Math.random()*vampire_rate)<1) {
			int rand_x =(int)((Math.random()*8)*20)+300;
			int rand_y =(int)((Math.random()*22)*20);
			vampires.add(new Vampire(rand_x,rand_y,lanternimg));
		}

		//ゾンビの生成
		if(zombies.size()<zombie_num && (int)(Math.random()*zombie_rate)<1) {
			int rand_x =(int)((Math.random()*8)*20)+300;
			int rand_y =200;
			zombies.add(new Zombie(rand_x,rand_y,zombieimage,zombieimage2));
		}

		//魔女の生成
		if(distance>goal_distance && isWitch == false) {
			isWitch=true;
			//selaugh1.stop();
			selaugh1.setFramePosition(0);
			selaugh1.start();
			g.setColor(Color.BLACK);
			g.setFont(new Font("SansSerif",Font.BOLD,40));				
		}

		//ヴァンパイアの能力　コウモリ出現
		it=vampires.iterator();
		while(it.hasNext()==true){
			Vampire va = (Vampire) it.next();
			if((int)(Math.random()*10)<1&&bats.size()<bat_num) {
				bats.add(new Bat(va.chara_x,va.chara_y,spiritimg));
			}
		}
		if(esckey==false) {
			esckey=false;
		}
		if(esckey==true) {
			esckey=false;
			goMenu();
		}

		//魔女の能力1:偽物
		if(isWitch==true&& witch.fakespan==0) {
			witchFakes.clear();
			selaugh4.stop();
			selaugh4.setFramePosition(0);
			selaugh4.start();
			int pattern=(int)(Math.random()*3);
			witch.fakespan=300;
			explosions.add(new Explosion(witch.chara_x,110,smokeimage,100,148));
			explosions.add(new Explosion(witch.chara_x,210,smokeimage,100,148));
			explosions.add(new Explosion(witch.chara_x,310,smokeimage,100,148));
			switch(pattern) {
			case 0://本物が一番上
				witch.chara_y=110;
				witchFakes.add(new WitchFake(witch.chara_x,witch.chara_y+100,snakeimage, witch.HP));
				witchFakes.add(new WitchFake(witch.chara_x,witch.chara_y+200,snakeimage, witch.HP));
				break;
			case 1://本物が真ん中
				witch.chara_y=210;
				witchFakes.add(new WitchFake(witch.chara_x,witch.chara_y+100,snakeimage, witch.HP));
				witchFakes.add(new WitchFake(witch.chara_x,witch.chara_y-100,snakeimage, witch.HP));
				break;
			case 2://本物が一番下
				witch.chara_y=310;
				witchFakes.add(new WitchFake(witch.chara_x,witch.chara_y-200,snakeimage, witch.HP));
				witchFakes.add(new WitchFake(witch.chara_x,witch.chara_y-100,snakeimage, witch.HP));
				break;
			}
		}

		
		//当たり判定 カボチャとコウモリ
		it=bats.iterator();
		while(it.hasNext()==true){
			Bat ba = (Bat) it.next();
			Iterator<GameChara> it2 =pumpkins.iterator();
			while(it2.hasNext()==true) {
				Scepter pu = (Scepter)it2.next();
				if(pu.isColided(ba)==true) {
					it.remove();
					it2.remove();
					explosions.add(new Explosion(ba.chara_x,ba.chara_y,explosion2img));
					magic=magic+1;
					if(magic>MAGIC_LIMIT)magic = MAGIC_LIMIT;
					//seBatdown.stop();
					seBatdown.setFramePosition(0);
					seBatdown.start();
					break;
				}
			}
		}

		//当たり判定 魔法陣とコウモリ
		it=bats.iterator();
		while(it.hasNext()==true){
			Bat ba = (Bat) it.next();
			Iterator<GameChara> it2 =magics.iterator();
			while(it2.hasNext()==true) {
				MagicCircle ma= (MagicCircle)it2.next();
				if(ma.isColided(ba)==true) {
					it.remove();
					explosions.add(new Explosion(ba.chara_x,ba.chara_y,explosion2img));

					//seBatdown.stop();
					seBatdown.setFramePosition(0);
					seBatdown.start();
					break;
				}
			}
		}

		//カボチャと偽物魔女
		it=witchFakes.iterator();
		while(it.hasNext()==true){
			WitchFake wf = (WitchFake) it.next();
			Iterator<GameChara> it2 =pumpkins.iterator();
			while(it2.hasNext()==true) {
				Scepter pu = (Scepter)it2.next();
				if(pu.isColided(wf)==true) {
					explosions.add(new Explosion(wf.chara_x-10,wf.chara_y-50,smokeimage,100,148));
					for (int i=0; i<5; i++)
					{
						bats.add(new Bat(wf.chara_x,wf.chara_y,spiritimg));
					}
					it.remove();
					it2.remove();

					if(magic>MAGIC_LIMIT)magic = MAGIC_LIMIT;
					//selaugh0.stop();
					selaugh3.setFramePosition(0);
					selaugh3.start();
					break;
				}
			}
		}

		//当たり判定 魔法陣と偽物魔女
		it=witchFakes.iterator();
		while(it.hasNext()==true){
			WitchFake wf = (WitchFake) it.next();
			Iterator<GameChara> it2 =magics.iterator();
			while(it2.hasNext()==true) {
				MagicCircle ma= (MagicCircle)it2.next();
				if(ma.isColided(wf)==true) {
					//explosions.add(new Explosion(tk.chara_x,tk.chara_y,charaimage));
					it.remove();
					explosions.add(new Explosion(wf.chara_x-10,wf.chara_y-50,smokeimage,100,148));
					//seClip16.stop();
					selaugh3.setFramePosition(0);
					selaugh3.start();
					break;
				}
			}
		}

		//当たり判定 カボチャとヴァンパイア
		it=vampires.iterator();
		while(it.hasNext()==true){
			Vampire va = (Vampire) it.next();
			Iterator<GameChara> it2 =pumpkins.iterator();
			while(it2.hasNext()==true) {
				Scepter pu = (Scepter)it2.next();
				if(pu.isColided(va)==true) {
					//explosions.add(new Explosion(tk.chara_x,tk.chara_y,charaimage));
					va.HP=va.HP - 1;
					if(va.HP<=0) {
						it.remove();
						explosions.add(new Explosion(va.chara_x,va.chara_y,explosionimg));
						//seVampiredown.stop();
						seVampiredown.setFramePosition(0);
						seVampiredown.start();
						magic=magic+1;
						if(magic>MAGIC_LIMIT)magic = MAGIC_LIMIT;
					}
					it2.remove();


					break;
				}
			}
		}

		//当たり判定 魔法陣とヴァンパイア
		it=vampires.iterator();
		while(it.hasNext()==true){
			Vampire va = (Vampire) it.next();
			Iterator<GameChara> it2 =magics.iterator();
			while(it2.hasNext()==true) {
				MagicCircle ma= (MagicCircle)it2.next();
				if(ma.isColided(va)==true) {
					va.HP=va.HP - 3;
					if(va.HP<=0) {
						it.remove();
						explosions.add(new Explosion(va.chara_x,va.chara_y,explosionimg));
						//seVampiredown.stop();
						seVampiredown.setFramePosition(0);
						seVampiredown.start();
					}

					break;
				}
			}
		}
		//当たり判定　コウモリとプレイヤー
		it=bats.iterator();
		while(it.hasNext()==true){
			Bat ba = (Bat)it.next();
			if(ba.isColided(player)) {
				if(player.damaged(1)) {
					//seDamaged.stop();
					seDamaged.setFramePosition(0);
					seDamaged.start();
					it.remove();
					explosions.add(new Explosion(player.chara_x,player.chara_y,explosion4img));
				}
				if(player.player_life<0)
				{
					goGameOver();
				}
				break;
			}
		}

		//当たり判定　ヴァンパイアとプレイヤー
		it=vampires.iterator();
		while(it.hasNext()==true){
			Vampire va = (Vampire)it.next();
			if(va.isColided(player)) {
				if(player.damaged(1)) {
					//seDamaged.stop();
					seDamaged.setFramePosition(0);
					seDamaged.start();
					it.remove();
					explosions.add(new Explosion(player.chara_x,player.chara_y,explosion4img));
				}
				if(player.player_life<0)
				{
					goGameOver();
				}
				break;
			}
		}
		//当たり判定　ゾンビとプレイヤー
		it=zombies.iterator();
		while(it.hasNext()==true){
			Zombie zo = (Zombie)it.next();
			if(zo.isColided(player)) {
				if(player.damaged(1)) {
					//seDamaged.stop();
					seDamaged.setFramePosition(0);
					seDamaged.start();
					it.remove();
					explosions.add(new Explosion(player.chara_x,player.chara_y,explosion4img));
				}
				if(player.player_life<0)
				{
					goGameOver();
				}
				break;
			}
		}
		//当たり判定　偽物魔女とプレイヤー
				it=witchFakes.iterator();
				while(it.hasNext()==true){
					WitchFake wf = (WitchFake)it.next();
					if(wf.isColided(player)) {
						//seDamaged.stop();
						seDamaged.setFramePosition(0);
						seDamaged.start();
						if(player.damaged(1)) {
							explosions.add(new Explosion(player.chara_x,player.chara_y,explosion4img));
						}
						if(player.player_life<0)
						{
							goGameOver();
						}
						break;
					}
				}
		
		//当たり判定 ゾンビとカボチャ
		it=zombies.iterator();
		while(it.hasNext()==true){
			Zombie zo = (Zombie) it.next();
			Iterator<GameChara> it2 =pumpkins.iterator();
			while(it2.hasNext()==true) {
				Scepter pu = (Scepter)it2.next();
				if(pu.isColided(zo)==true) {
					zo.HP=zo.HP - 1;
					if(zo.HP<=0) {
						it.remove();
						explosions.add(new Explosion(zo.chara_x+70,zo.chara_y+70,explosion3img,80,80));
						//seZombiedown.stop();
						seZombiedown.setFramePosition(0);
						seZombiedown.start();
						magic=magic+1;
						if(magic>MAGIC_LIMIT)magic = MAGIC_LIMIT;
					}
					it2.remove();


					break;
				}
			}
		}

		//当たり判定 魔法陣とゾンビ
		it=zombies.iterator();
		while(it.hasNext()==true){
			Zombie zo = (Zombie) it.next();
			Iterator<GameChara> it2 =magics.iterator();
			while(it2.hasNext()==true) {
				MagicCircle ma= (MagicCircle)it2.next();
				if(ma.isColided(zo)==true&&zo.interval==0) {
					//explosions.add(new Explosion(tk.chara_x,tk.chara_y,charaimage));
					zo.HP=zo.HP - 3;
					zo.interval=5;
					if(zo.HP<=0) {
						it.remove();
						explosions.add(new Explosion(zo.chara_x+70,zo.chara_y+70,explosion3img,80,80));
						//seZombiedown.stop();
						seZombiedown.setFramePosition(0);
						seZombiedown.start();
					}
					break;
				}
			}
		}

		//当たり判定 魔女とカボチャ
		if(isWitch==true) {
			it=pumpkins.iterator();
			while(it.hasNext()==true){
				Scepter pu = (Scepter)it.next();
				if(pu.isColided(witch)) {
					it.remove();
					witch.HP -=1;
					if(witch.HP<0)
					{
						goStageClear();
					}
					break;
				}
			}
		}

		//当たり判定 魔女と魔法陣
		if(isWitch==true) {
			it=magics.iterator();
			while(it.hasNext()==true){
				MagicCircle ma = (MagicCircle)it.next();
				if(ma.isColided(witch)&&witch.interval==0) {
					witch.HP -=3;
					witch.interval = 5;
					System.out.println(witch.HP);
					if(witch.HP<0)
					{
						goStageClear();
					}
					break;
				}
			}
		}

		//当たり判定 魔女とプレイヤー
		if(isWitch==true) {
			if(witch.isColided(player)) {
				//seDamaged.stop();
				seDamaged.setFramePosition(0);
				seDamaged.start();
				if(player.damaged(1)) {
					explosions.add(new Explosion(player.chara_x,player.chara_y,explosion4img));
				}
				if(player.player_life<0)
				{
					goGameOver();
				}
			}
		}

		
		//カボチャの表示と削除
		it = pumpkins.iterator();
		while(it.hasNext()==true) {
			Scepter pu = (Scepter)it.next();
			pu.draw(g, frame1);
			if(pu.isOutofScreen()) it.remove();
		}

		//batの表示と削除
		it = bats.iterator();
		while(it.hasNext()==true) {
			Bat ba = (Bat)it.next();
			ba.draw(g, frame1);
			if(ba.isOutofScreen()) it.remove();
		}

		//ヴァンパイアの表示と削除
		it = vampires.iterator();
		while(it.hasNext()==true) {
			Vampire va = (Vampire)it.next();
			va.draw(g, frame1);
			if(va.isOutofScreen()) it.remove();
		}

		//ゾンビの表示と削除
		it = zombies.iterator();
		while(it.hasNext()==true) {
			Zombie zo = (Zombie)it.next();
			zo.draw(g, frame1);
			if(zo.isOutofScreen()) it.remove();
		}
		//魔女の表示と削除
		if(isWitch==true) {
			witch.draw(g, frame1);
		}

		//魔女の偽物の表示と削除
		it = witchFakes.iterator();
		while(it.hasNext()==true) {
			WitchFake wf = (WitchFake)it.next();
			wf.draw(g, frame1);
		}

		//魔法陣の表示と削除
		it = magics.iterator();
		while(it.hasNext()==true) {
			MagicCircle ma = (MagicCircle)it.next();
			ma.draw(g, frame1);
			if(ma.isOutofScreen()) it.remove();
		}

		//爆発の表示と削除
		it = explosions.iterator();
		while(it.hasNext()==true) {
			Explosion ex = (Explosion)it.next();
			ex.draw(g, frame1);
			if(ex.isOutofScreen()) it.remove();
		}

		//無双モード
		if(mode==3) {
			if(player.player_life<=3) {
				player.player_life=3;
			}
		}

	}

	/**
	 * @param difficulty 1:Easy 2:Normal 3:Hard
	 */
	void changeDifficulty(int difficulty) {
		switch(difficulty) {
		case 1:
			bat_rate = 80;
			vampire_rate = 350;
			zombie_rate = 400;
			bat_num =30;
			zombie_num =1;
			vampire_num = 1;
			goal_distance = 3000;
			break;
		case 2:
			bat_rate = 50;
			vampire_rate = 300;
			zombie_rate = 400;
			bat_num =40;
			zombie_num =2;
			vampire_num=2;
			goal_distance = 8000;
			break;
		case 3:
			bat_rate = 30;
			vampire_rate = 100;
			zombie_rate = 300;
			bat_num =50;
			zombie_num =3;
			vampire_num=3;
			goal_distance = 20000;
			break;
		}
	}
	/**
	 *ゲームオーバー画面を表示する
	 */
	@Override
	void runGameOver(Graphics g) {
		// TODO Auto-generated method stub
		g.clearRect(0,0,SCREEN_W,SCREEN_H);
		g.drawImage(gameoverimg,0,0,frame1);
		g.setColor(backcolor);
		g.fillRect(0, 0, SCREEN_W, SCREEN_H);
		g.setColor(Color.BLACK);
		g.fillRect(205,325,230,40);
		g.setColor(Color.RED);
		g.setFont(new Font("SansSerif",Font.BOLD,40));
		drawStringCenter("ゲームオーバー",200,g);
		g.setColor(Color.BLUE);
		g.setFont(new Font("SansSerif",Font.BOLD,20));
		drawStringCenter("Push \"P\" to Title",350,g);
	}

	/**
	 * メニューを起動する処理
	 * @param g
	 * @param num　メニューを分岐 1なら通常モード 2,3なら開発者モード
	 */
	@Override
	void runMenu(Graphics g, int num) {
		// TODO Auto-generated method stub
		//g.clearRect(0,0,SCREEN_W,SCREEN_H);
		switch(num) {
		case 1:
			g.setColor(Color.BLACK);
			g.fillRect(100,60,450,400);
			g.setColor(Color.BLACK);
			g.setFont(new Font("SansSerif",Font.BOLD,20));
			drawStringCenter("Menu",100,g);
			g.setColor(Color.RED);
			g.fillRect(250,150,150,30);
			g.setColor(Color.BLUE);
			g.setFont(new Font("SansSerif",Font.BOLD,20));
			drawStringCenter("EASY",190,g);
			g.setColor(Color.RED);
			g.fillRect(250,240,150,30);
			g.setColor(Color.BLUE);
			g.setFont(new Font("SansSerif",Font.BOLD,20));
			drawStringCenter("NORMAL",270,g);
			g.setColor(Color.RED);
			g.fillRect(250,330,150,30);
			g.setColor(Color.BLUE);
			g.setFont(new Font("SansSerif",Font.BOLD,20));
			drawStringCenter("HARD",360,g);
			g.setColor(Color.RED);
			g.fillRect(120,380,150,30);
			g.setColor(Color.BLUE);
			g.setFont(new Font("SansSerif",Font.BOLD,20));
			g.drawString("CONTINUE",150,420);
			g.setColor(Color.RED);
			g.fillRect(380,380,150,30);
			g.setColor(Color.BLUE);
			g.setFont(new Font("SansSerif",Font.BOLD,20));
			g.drawString("EXIT",420,420);
			break;
		case 2,3:
			g.setColor(Color.BLACK);
		g.fillRect(140,70,360,360);
		g.setColor(Color.RED);
		g.setFont(new Font("SansSerif",Font.BOLD,20));
		drawStringCenter("Developer Menu",100,g);
		g.setColor(Color.BLUE);
		g.setFont(new Font("SansSerif",Font.BOLD,20));
		drawStringCenter("Push \"1\" to Continue",150,g);
		g.setColor(Color.BLUE);
		g.setFont(new Font("SansSerif",Font.BOLD,20));
		drawStringCenter("Push \"2\" to GameEnd",200,g);
		g.setColor(Color.BLUE);
		g.setFont(new Font("SansSerif",Font.BOLD,20));
		drawStringCenter("Push \"3\" to NormalMenu",250,g);
		g.setColor(Color.BLUE);
		g.setFont(new Font("SansSerif",Font.BOLD,20));
		drawStringCenter("Push \"4\" to Gameover",300,g);
		g.setColor(Color.BLUE);
		g.setFont(new Font("SansSerif",Font.BOLD,20));
		drawStringCenter("Push \"5\" to GameClear",350,g);
		g.setColor(Color.RED);
		g.setFont(new Font("SansSerif",Font.BOLD,20));
		drawStringCenter("Push \"6\" to Godmode ",400,g);
		break;
		}
	}

	/**
	 *ゲームを始める直前の処理
	 *各インスタンスの初期化とBGMの起動をしている
	 *キー入力の初期化もここで行う。
	 */
	@Override
	void initStageStart() {
		// TODO Auto-generated method stub

		bgmMain.stop();
		bgmMain.setFramePosition(0);
		bgmMain.loop(Clip.LOOP_CONTINUOUSLY);
		player = new Player(30,210,charaimage);
		witch = new Witch(360,210,snakeimage);
		isWitch=false;
		magic=0;
		distance=0;
		bats = new ArrayList<GameChara>();
		zombies = new ArrayList<GameChara>();
		vampires = new ArrayList<GameChara>();
		pumpkins = new ArrayList<GameChara>();
		explosions = new ArrayList<GameChara>();
		items_wand = new ArrayList<GameChara>();
		items_cross = new ArrayList<GameChara>();
		witchFakes = new ArrayList<GameChara>();
		upkey=false;downkey=false;rightkey=false;leftkey=false;
		shiftkey=false; spacekey=false;esckey=false;
		starttime=System.currentTimeMillis();
		selaugh2.stop();
		selaugh2.setFramePosition(0);
		selaugh2.start();

	}

	/**
	 * ゲームクリア画面に行く前の処理
	 * 各BGMやSEを停止
	 */
	@Override
	void initStageClear() {
		// TODO Auto-generated method stub
		bgmMain.stop();
		selaugh0.stop();
		selaugh1.stop();
		selaugh2.stop();
		selaugh3.stop();
		seGameClear.stop();
		seGameClear.setFramePosition(0);
		seGameClear.start();
	}

	/**
	 *ゲームオーバー画面に行く前の処理
	 *BGMを停止
	 */
	@Override
	void initGameOver() {

		// TODO Auto-generated method stub
		bgmMain.stop();
		seGameOver.stop();
		seGameOver.setFramePosition(0);
		seGameOver.start();
	}


	/**
	 *システム音を再生するための関数
	 */
	@Override
	void SystemSoundPlay() {
		seButton.stop();
		seButton.setFramePosition(0);
		seButton.start();
	}

	/**
	 * メイン関数 
	 * @param args コマンドライン引数
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		@SuppressWarnings("unused")//warningを出さないために追加
		ShyotokuShoot hsm = new ShyotokuShoot();
	}

}
