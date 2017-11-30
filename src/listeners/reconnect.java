package listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;

import spenpal.SpenPalMain;

public class reconnect implements MouseListener {
	JFrame j;

	public reconnect(JFrame j) {
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
		if(!SpenPalMain.haslogin){
		if (SpenPalMain.connect()) {
			SpenPalMain.mainframe.refresh();
			}
		}
		else{
			//SpenPalMain.scon.disconnect();
			SpenPalMain.haslogin=false;
			SpenPalMain.nowusr=null;
			SpenPalMain.mainframe.refresh();
			//SpenPalMain.connect();
		}
	}
}
