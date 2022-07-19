package jp.ichinomiya.minigame;

import java.awt.Point;
import java.util.StringTokenizer;

/**
 *文字列の配列から情報を読み取るクラス
 * @author a50609
 *
 */
public class PatternReader {
	String patstr;
	StringTokenizer tokenizer;
	Point movexy = new Point();
	int num = 0;
	
	PatternReader(String str){
		patstr = str;
		tokenizer = new StringTokenizer(patstr,",");
	}
	
	Point next() {
		if(num==0) {
			if(tokenizer.hasMoreTokens()==false) {
				tokenizer = new StringTokenizer(patstr,",");
			}
		
		movexy.x = Integer.parseInt(tokenizer.nextToken());
		movexy.y = Integer.parseInt(tokenizer.nextToken());
		num = Integer.parseInt(tokenizer.nextToken());
		}else {
			num = num-1;
		}
		return movexy;
	}
}
