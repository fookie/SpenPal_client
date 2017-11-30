package listeners;

import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;

import spenpal.DatabaseManager;

public class selectdate_alltimes implements MouseListener {
	JLabel j, date1, date2;

	public selectdate_alltimes(JLabel j, JLabel date1, JLabel date2) {
		this.j = j;
		this.date1 = date1;
		this.date2 = date2;
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
		date1.setText("very beginning");
		DatabaseManager.startdate = "very beginning";
		date2.setText("very ending");
		DatabaseManager.enddate = "very ending";
	}
}
