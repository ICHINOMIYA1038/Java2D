package jp.ichinomiya.minigame;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;

/**
 * ゲームテンプレートのクラス
 * @author a50609
 */
public abstract class GameTemplate {
	/**
	 * タイトル画面の状態を表す整数
	 */
	public static final int GS_TITLE = 0;
	/**
	 * ステージがスタートする前の状態を現した整数
	 */
	public static final int GS_STAGESTART = 1;
	/**
	 * ステージがクリアする前の状態を現した整数
	 */
	public static final int GS_STAGECLEAR = 2;
	/**
	 * ゲームオーバー画面の状態を表す整数
	 */
	public static final int GS_GAMEOVER = 3;
	/**
	 * ゲームメイン画面の状態を表す整数
	 */
	public static final int GS_GAMEMAIN = 4;
	/**
	 * メニュー画面の状態を表す整数
	 */
	public static final int GS_MENU =5;
	/**
	 * 現在の画面状態を表す整数を格納する変数
	 */
	int gamestate;
	/**
	 * 画面遷移の待機時間
	 */
	int waittimer;
	/**
	 * 使用中のバッファストラテジー
	 */
	BufferStrategy bstrategy;
	/**
	 * フレーム
	 */
	JFrame frame1;
	/**
	 * タイマーオブジェクトのインスタンス
	 */
	Timer t;
	/**
	 * 自分で作ったタイマータスクのオブジェクト
	 */
	MyTimerTask mytask;
	/**
	 * メニューにおけるモードを表す
	 * 1で通常 2で開発者モード 3で無双モード
	 */
	int mode=1; 
	/**
	 * 難易度を格納する。初期値は2
	 * 1:易しい,2:普通,3:難しい
	 */
	int difficulty=2; 
	/**
	 * ウィンドウを作成するコンストラクタ
	 * @param w 画面の横幅
	 * @param h 画面の縦
	 * @param title ウィンドウのタイトル
	 */
	int player_x,player_y;
	boolean upkey=false, downkey=false, rightkey=false, leftkey=false, shiftkey=false, spacekey=false,esckey=false;
	GameTemplate(int w, int h, String title){
		frame1 = new JFrame(title);
		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame1.setBackground(Color.WHITE);
		frame1.setResizable(false);
		frame1.setVisible(true);
		Insets insets = frame1.getInsets();
		//frame1.setSize(w + insets.left + insets.right,h + insets.top + insets.bottom);
		frame1.setSize(w,h+insets.top);
		frame1.setLocationRelativeTo(null);
		frame1.createBufferStrategy(2);
		bstrategy = frame1.getBufferStrategy();
		frame1.setIgnoreRepaint(true);
		frame1.addKeyListener(new MyKeyAdapter());
		frame1.addMouseListener(new MyMouseAdapter());
		frame1.addMouseListener(new MyMouseAdapter());
		frame1.addMouseMotionListener(new MyMouseMotionAdapter());
	}

	/**
	 * 音声を読み込み、クリップオブジェクトに格納する
	 * @param fname ファイル名
	 * @return クリップオブジェクト
	 */
	Clip readSound(String fname) {
		Clip clip =null;
		try {
			AudioInputStream aistream = AudioSystem.getAudioInputStream(getClass().getResource(fname));
			DataLine.Info info =
					new DataLine.Info(Clip.class, aistream.getFormat());
			clip = (Clip)AudioSystem.getLine(info);
			clip.open(aistream);
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return clip;

	}

	/**
	 * 文字を中央に配置するためのメソッド
	 * @param str 文字列
	 * @param y y座標
	 * @param g グラフィックス
	 */
	void drawStringCenter(String str, int y, Graphics g)
	{
		int fw =frame1.getWidth()/2;
		FontMetrics fm = g.getFontMetrics();
		int strw = fm.stringWidth(str)/2;
		g.drawString(str, fw-strw, y); //fw-strwで文字の左端の座標を出している。
	}

	
	class MyMouseMotionAdapter extends MouseAdapter{
		public void mouseMoved(MouseEvent e) {
			System.out.println("upkey:"+upkey);
			System.out.println("upkey:"+downkey);
			if (e.getY()<player_y) {
				upkey = true;
				downkey = false;
			}
			else if (e.getY()>=player_y) {
				upkey = false;
				downkey = true;
			}
		}
	}
	class MyMouseAdapter extends MouseAdapter{
		public void mousePressed(MouseEvent e) {
			if (e.getButton()==1) {
				shiftkey = true;
			}
			if (e.getButton()==3) {
				spacekey = true;
			}
		}
		public void mouseReleased(MouseEvent e) {
			if (e.getButton()==1) {
				shiftkey = false;
			}
			if (e.getButton()==3) {
				spacekey = false;
			}
		}
	}
	/**
	 * @author a50609
	 * キー入力を受付け、現在のゲームステートに応じての処理を呼び出す
	 */
	class MyKeyAdapter extends KeyAdapter{
		/**
		 *キーが押された時
		 */
		public void keyPressed(KeyEvent ev) {
			if(gamestate == GS_GAMEMAIN) {
				keyPressedGameMain(ev.getKeyCode());
			}

		}
		/**
		 *キーが離された時
		 */
		public void keyReleased(KeyEvent ev) {
			int keycode = ev.getKeyCode();
			switch(gamestate) {
			case GS_GAMEMAIN:
				keyReleasedGameMain(keycode);
				break;
			case GS_TITLE:
				if(keycode == KeyEvent.VK_SPACE) {
					SystemSoundPlay();
					goStageStart();
				}
				break;
			case GS_STAGECLEAR:
				if(keycode == KeyEvent.VK_P) {
					SystemSoundPlay();
					goTitle();
				}
				break;
			case GS_GAMEOVER:
				if(keycode == KeyEvent.VK_P) {
					SystemSoundPlay();
					goTitle();
				}
				break;
			case GS_MENU:

				if(keycode == KeyEvent.VK_1) {
					SystemSoundPlay();
					gamestate = GS_GAMEMAIN;
				}
				if(keycode == KeyEvent.VK_2) {
					System.exit(0);
				}
				if(keycode == KeyEvent.VK_3) {
					SystemSoundPlay();
					switch(mode) {
					case 1: 
						mode=2;
						break;
					case 2:
						mode=1;
						break;
					case 3:
						mode=1;
						break;

					}
				}
				if(keycode == KeyEvent.VK_4&&mode==2) {
					SystemSoundPlay();
					goGameOver();
				}
				if(keycode == KeyEvent.VK_5&&mode==2) {
					SystemSoundPlay();
					goStageClear();
				}
				if(keycode == KeyEvent.VK_6&&mode==2) {
					mode=3;
					SystemSoundPlay();

					gamestate = GS_GAMEMAIN;


				}
				if(keycode == KeyEvent.VK_7) {
					SystemSoundPlay();
					goGameOver();
				}
				if(keycode == KeyEvent.VK_E) {
					SystemSoundPlay();
					difficulty=1;
					gamestate = GS_GAMEMAIN;
				}
				if(keycode == KeyEvent.VK_N) {
					SystemSoundPlay();
					difficulty=2;
					gamestate = GS_GAMEMAIN;
				}
				if(keycode == KeyEvent.VK_H) {
					SystemSoundPlay();
					difficulty=3;
					gamestate = GS_GAMEMAIN;
				}
			}
		}
	}

	/**
	 * @author a50609
	 * 繰り返し実行するためのクラス
	 * gamestateに応じて、繰り返す関数を決定する
	 */
	class MyTimerTask extends TimerTask{
		public void run() {
			Graphics g = bstrategy.getDrawGraphics();
			if(bstrategy.contentsLost()==false) {
				Insets insets = frame1.getInsets();
				g.translate(0,insets.top);

				switch(gamestate) {
				case GS_TITLE:
					runTitle(g);
					break;
				case GS_STAGESTART:
					runStageStart(g);
					waittimer = waittimer-1;
					if(waittimer<0) goGameMain();
					break;
				case GS_STAGECLEAR:
					runStageClear(g);
					break;
				case GS_GAMEMAIN:
					runGameMain(g,mode,difficulty);
					break;
				case GS_GAMEOVER:
					runGameOver(g);
					break;
				case GS_MENU:
					runMenu(g,mode);
				}

				bstrategy.show();
				g.dispose();
			}
		}
	}
	abstract void keyPressedGameMain(int keycode);
	abstract void keyReleasedGameMain(int keycode);
	abstract void runTitle(Graphics g);
	abstract void runStageStart(Graphics g) ;
	abstract void runStageClear(Graphics g) ;
	abstract void runGameMain(Graphics g,int mode,int difficulty);
	abstract void runGameOver(Graphics g);
	abstract void runMenu(Graphics g, int num);

	/**
	 * スタート画面の状態へとgamestateを遷移させ、タイマーイベントを呼び出す
	 * タイマーイベントの中でさらに画面の遷移を繰り返す
	 */
	void gameInit() {
		mode=1;
		t =new Timer();//timerクラスはtimertaskを繰り返し実行します
		mytask= new MyTimerTask();//timertaskでは、具体的な処理が記述されます。
		t.schedule(mytask, 10 , 30);//10msの遅延の後に30ms間隔でタスクが呼ばれる
		goTitle();
	}
	/**
	 * gamestateをタイトル画面にする
	 */
	void goTitle() {
		gamestate = GS_TITLE;
	}
	/**
	 * gamestateをスタートの状態へと遷移
	 */
	void goStageStart() {
		initStageStart();
		waittimer = 30;
		gamestate = GS_STAGESTART;
	}
	/**
	 * ゲームがクリアされた時に呼び出され、ステージクリアの状態へと遷移する
	 */
	void goStageClear() {
		initStageClear();
		waittimer = 30;
		gamestate = GS_STAGECLEAR;
	}
	/**
	 * ゲームメインの状態へと遷移する
	 */
	void goGameMain() {
		gamestate = GS_GAMEMAIN;
	}

	/**
	 * ゲームオーバーの時に呼びだされ、ゲームオーバーの状態へ遷移する
	 */
	void goGameOver() {
		initGameOver();
		gamestate = GS_GAMEOVER;
	}

	/**
	 * メニューの状態へと遷移させるs
	 */
	void goMenu() {
		gamestate = GS_MENU;
	}
	/**
	 * ステージが開始するときの抽象関数
	 */
	abstract void initStageStart();
	/**
	 * ゲームクリア画面に遷移するときの抽象関数
	 */
	abstract void initStageClear();
	/**
	 * ゲームオーバー画面に遷移する時の抽象関数
	 */
	abstract void initGameOver();
	/**
	 * システム音を再生するための抽象関数
	 */
	abstract void SystemSoundPlay();
}
