package jp.ichinomiya.minigame;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;

public abstract class GameTemplate {
	public static final int GS_STARTGAMEN = 0;
	public static final int GS_STAGESTART = 1;
	public static final int GS_STAGECLEAR = 2;
	public static final int GS_GAMEOVER = 3;
	public static final int GS_GAMEMAIN = 4;
	int gamestate;
	int waittimer;
	BufferStrategy bstrategy;
	JFrame frame1;
	Sequencer midiseq = null;
	
/**
 * ウィンドウを作成するコンストラクタ
 * @param w 画面の横幅
 * @param h 画面の縦
 * @param title ウィンドウのタイトル
 */
GameTemplate(int w, int h, String title){
	frame1 = new JFrame(title);
	frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame1.setBackground(Color.WHITE);
	frame1.setResizable(false);
	frame1.setVisible(true);
	Insets insets = frame1.getInsets();
	frame1.setSize(w + insets.left + insets.right,h + insets.top + insets.bottom);
	frame1.setLocationRelativeTo(null);
	
	frame1.createBufferStrategy(2);
	bstrategy = frame1.getBufferStrategy();
	frame1.setIgnoreRepaint(true);
	
	frame1.addKeyListener(new MyKeyAdapter());
}
	
/**
 * .midiのファイルから音声を読み取る
 * @param fname midiのファイル名
 */
void readMidi(String fname) {
	if(midiseq == null) {
		try {
			midiseq = MidiSystem.getSequencer();
			midiseq.open();
		} catch (MidiUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	Sequence seq;
	try {
		seq = MidiSystem.getSequence(getClass().getResource(fname));
		midiseq.setSequence(seq);
	} catch (InvalidMidiDataException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}

	/**
	 * 音声を読み込み、クリッピうオブジェクトに格納する
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
	 * @param g
	 */
	void drawStringCenter(String str, int y, Graphics g)
	{
		int fw =frame1.getWidth()/2;
		FontMetrics fm = g.getFontMetrics();
		int strw = fm.stringWidth(str)/2;
		g.drawString(str, fw-strw, y); //fw-strwで文字の左端の座標を出している。
	}
	
	/**
	 * @author a50609
	 * キー入力を受付け、現在のゲームステートに応じての処理を呼び出す
	 */
	class MyKeyAdapter extends KeyAdapter{
		public void keyPressed(KeyEvent ev) {
			if(gamestate == GS_GAMEMAIN) {
				keyPressedGameMain(ev.getKeyCode());
			}
		}
		public void keyReleased(KeyEvent ev) {
			int keycode = ev.getKeyCode();
			switch(gamestate) {
			case GS_GAMEMAIN:
				keyReleasedGameMain(keycode);
				break;
			case GS_STARTGAMEN:
				if(keycode == KeyEvent.VK_P) goStageStart();
				break;
			case GS_GAMEOVER:
				if(keycode == KeyEvent.VK_R) goStageStart();
				
			}
		}
	}
	
	/**
	 * @author a50609
	 *
	 */
	class MyTimerTask extends TimerTask{
		public void run() {
			Graphics g = bstrategy.getDrawGraphics();
			if(bstrategy.contentsLost()==false) {
				Insets insets = frame1.getInsets();
				g.translate(insets.left+insets.right,insets.top+insets.bottom);
				
				switch(gamestate) {
				case GS_STARTGAMEN:
					runStartGamen(g);
					break;
				case GS_STAGESTART:
					runStageStart(g);
					waittimer = waittimer-1;
					if(waittimer<0) goGameMain();
					break;
				case GS_STAGECLEAR:
					runStageClear(g);
					waittimer = waittimer - 1;
					if(waittimer<0) goStageStart();
					break;
				case GS_GAMEMAIN:
					runGameMain(g);
					break;
				case GS_GAMEOVER:
					runGameOver(g);
					break;
				}
				bstrategy.show();
				g.dispose();
			}
		}
	}
	abstract void keyPressedGameMain(int keycode);
	abstract void keyReleasedGameMain(int keycode);
	abstract void runStartGamen(Graphics g);
	abstract void runStageStart(Graphics g) ;
	abstract void runStageClear(Graphics g) ;
	abstract void runGameMain(Graphics g);
	abstract void runGameOver(Graphics g);
	
	/**
	 * スタート画面の状態へとgamestateを遷移させ、タイマーイベントを呼び出す
	 * タイマーイベントの中でさらに画面の遷移を繰り返す
	 */
	void goStartGamen() {
		gamestate = GS_STARTGAMEN;
		Timer t =new Timer();
		t.schedule(new MyTimerTask(), 10 , 30);
	}
	/**
	 * ステージがスタートの状態へと遷移
	 */
	void goStageStart() {
		initStageStart();
		waittimer = 100;
		gamestate = GS_STAGESTART;
	}
	/**
	 * ゲームがクリアされた時に呼び出され、ステージクリアの状態へと遷移する
	 */
	void goStageClear() {
		initStageClear();
		waittimer = 100;
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
	abstract void initStageStart();
	abstract void initStageClear();
	abstract void initGameOver();
}
