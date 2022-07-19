import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

class PaintCanvas extends Canvas implements MouseListener,
    MouseMotionListener {

public PaintCanvas() {
    // MouseListener・MouseMotionListenerを設定
    addMouseListener(this);
    addMouseMotionListener(this);
    // キャンバスの背景を白に設定
    setBackground(Color.white);
}

public void paint(Graphics g) {
}

@Override
public void mouseDragged(MouseEvent e) {
    System.out.println(e);
}

@Override
public void mouseMoved(MouseEvent e) {
}

@Override
public void mouseClicked(MouseEvent e) {
}

@Override
public void mousePressed(MouseEvent e) {
}

@Override
public void mouseReleased(MouseEvent e) {
}

@Override
public void mouseEntered(MouseEvent e) {
}

@Override
public void mouseExited(MouseEvent e) {
}

}
