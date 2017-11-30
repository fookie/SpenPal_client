package listeners;

import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;

import spenpal.DateinputDialog;

public class selectdate implements MouseListener {
	JLabel j, date;

	public selectdate(JLabel j, JLabel date) {
		this.j = j;
		this.date = date;
	}

	public void mouseClicked(MouseEvent arg0) {

	}

	public void mouseEntered(MouseEvent arg0) {
		j.setFont(new Font("Arial", Font.BOLD, 12));
	}

	public void mouseExited(MouseEvent arg0) {
		j.setFont(new Font("Arial", Font.PLAIN, 12));
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
		new DateinputDialog(date, 200, 100);
	}
}
