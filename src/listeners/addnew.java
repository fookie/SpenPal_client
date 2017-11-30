package listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import spenpal.InputDialog;

public class addnew implements MouseListener {
	public void mouseClicked(MouseEvent arg0) {
	}

	public void mouseEntered(MouseEvent arg0) {

	}

	public void mouseExited(MouseEvent arg0) {
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			InputDialog id = new InputDialog();
			id.setVisible(true);
		}
	}
}