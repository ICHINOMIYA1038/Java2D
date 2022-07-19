package jp.ichinomiya.minigame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.Clip;

public class FusenGameMain extends GameHoneGumi {

	int cy=200;
	boolean spkey = false;
	BufferedImage backimage, pimage, eimage, startimage;
	double speed;
	Karasu[] karasus = new Karasu[15];
	Clip clip1, clip2;
	long stagetimer;
	int tekikazu = 1;
	
	public FusenGameMain(int arg0, int arg1, String arg2) {
		super(arg0, arg1, arg2);
		// TODO Auto-generated constructor stub
	}
	
	FusenGameMain(){
		super(600,400,"風船ゲーム"); //必ず先頭にかく
		try {
			pimage = ImageIO.read(getClass().getResource("fusenman.png"));
			eimage = ImageIO.read(getClass().getResource("karasu.png"));
			backimage = ImageIO.read(getClass().getResource("back.jpg"));
			startimage = ImageIO.read(getClass().getResource("back.jpg"));
		}catch(IOException e) {
			e.printStackTrace();
		}
		clip1 = otoYomikomi("fall01.wav");
		clip2 = otoYomikomi("power05.wav");
		midiYomikomi("toccata.mid");
		
		goStartGamen();
	}

	@Override
	void initGameOver() {
		// TODO Auto-generated method stub
		midiseq.stop();
		clip1.setFramePosition(0);
		clip1.start();
	}

	@Override
	void initStageClear() {
		// TODO Auto-generated method stub
		midiseq.stop();
		tekikazu = tekikazu +1;
		if(tekikazu>15) tekikazu =1;
	}

	@Override
	void initStageStart() {
		// TODO Auto-generated method stub
		cy =200;
		speed = 0;
		spkey = false;
		for (int i=0; i<tekikazu; i=i+1) {
			karasus[i] = new Karasu(eimage, i);
		}
		midiseq.setTickPosition(0);
		midiseq.start();
		stagetimer = System.currentTimeMillis();
	}

	@Override
	void keyPressedGameMain(int keycode) {
		// TODO Auto-generated method stub
		if(keycode == KeyEvent.VK_SPACE) {
			spkey = true;
		}
	}

	@Override
	void keyReleasedGameMain(int keycode) {
		if(keycode == KeyEvent.VK_SPACE) {
			spkey = false;
		}
	}

	@Override
	void runGameMain(Graphics g) {
		// TODO Auto-generated method stub
		if(spkey==true) {
			speed -=0.25;
			if(clip2.isRunning()==false) {
				clip2.setFramePosition(0);
				clip2.start();
			}
		}else {
			speed = speed + 0.25;
		}
		if(speed < -6) speed = -6;
		if(speed > 6) speed =6;
		cy = cy +(int)speed;
		if(cy<0) cy=0;
		if(cy>448) goGameOver();
		
		g.drawImage(backimage, 0, 0, frame1);
		g.drawImage(pimage, 270, cy, frame1);
		for(int i=0; i<tekikazu; i=i+1) {
			karasus[i].draw(g,frame1);
		}
		
		for(int i=0; i<tekikazu; i+=1) {
			if(karasus[i].isAtari(270, cy)==true) goGameOver();
		}
		if(System.currentTimeMillis() - stagetimer >40000) goStageClear();
		
	}

	@Override
	void runGameOver(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(Color.RED);
		g.setFont(new Font("SansSerif",Font.BOLD,80));
		drawStringCenter("GAMEOVER",220,g);
		g.setFont(new Font("SansSerif",Font.BOLD,30));
		drawStringCenter("Push R Key",280,g);
	}

	@Override
	void runStageClear(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(Color.BLACK);
		g.setFont(new Font("SansSerif",Font.BOLD,50));
		drawStringCenter("ステージクリア",220,g);
	}

	@Override
	public void runStageStart(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(Color.BLACK);
		g.fillRect(0,0,600,400);
		g.setColor(Color.GREEN);
		g.setFont(new Font("SansSerif",Font.BOLD,50));
		drawStringCenter("ステージ開始",220,g);
		
	}

	@Override
	void runStartGamen(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(startimage,0,0,frame1);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FusenGameMain gtm = new FusenGameMain();
	}

}
