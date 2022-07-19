import java.awt.Color;
import javax.swing.JFrame;

public class Mymath {
	JFrame frame1;

	Mymath(){
		frame1 =new JFrame("数学"); //JFrameクラスの作成,引数つきのコンストラクタでウィンドウ名を表示
		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame1.setBackground(Color.WHITE);
		frame1.setResizable(false);
		frame1.setVisible(true);
		frame1.setSize(640, 480);
		frame1.setLocationRelativeTo(null);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Mymath mmt = new Mymath();
	}

}
