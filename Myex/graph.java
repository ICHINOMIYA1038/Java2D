import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class graph {
	public static void main(String[] args) {
		
	JFrame frame = new JFrame();
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	 frame.setTitle("ウィンドウ1");
     frame.setSize(450, 300);
     frame.setResizable(false);
     //frame.setLayout(new GridLayout(11, 3, 10, 10));
     /*
     for (int i = 0; i < 11; i++) {
         frame.add(new JLabel("ラベル" + i));
         frame.add(new JLabel("****"));

     }
     */
     PaintCanvas canvas = new PaintCanvas();
     
     JPanel pane = new JPanel();
     frame.getContentPane().add(pane);
     canvas.setPreferredSize(new Dimension(450, 300));
     pane.add(canvas);
     frame.setLocationRelativeTo(null); //画面の中央に配置
     frame.setVisible(true);
	
}
}


