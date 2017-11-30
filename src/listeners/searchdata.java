package listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;

import spenpal.MainFrame;
import spenpal.SpenPalMain;

public class searchdata implements MouseListener {
	JFrame j;

	public searchdata(JFrame j) {
		this.j = j;
	}

	public void mouseClicked(MouseEvent arg0) {
	}

	public void mouseEntered(MouseEvent arg0) {
	}

	public void mouseExited(MouseEvent arg0) {
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
		SpenPalMain.mainframe = new MainFrame();
		j.dispose();
	}
}
