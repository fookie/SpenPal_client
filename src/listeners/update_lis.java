package listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import spenpal.SpenPalMain;

public class update_lis implements MouseListener {
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
			SpenPalMain.scon.update();
		}
	}
}