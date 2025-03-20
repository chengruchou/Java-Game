package final_project;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseHandler implements MouseListener {
	public boolean mouse_clicked = false;
	public long mousePressedTime,mouseReleasedTime;

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		mouse_clicked = true;
		mousePressedTime = System.currentTimeMillis();
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mouse_clicked = false;
		mouseReleasedTime = System.currentTimeMillis();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
