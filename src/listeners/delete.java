package listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import spenpal.DatabaseManager;
import spenpal.SpenPalMain;

public class delete implements MouseListener {
	public void mouseClicked(MouseEvent arg0) {
	}

	public void mouseEntered(MouseEvent arg0) {

	}

	public void mouseExited(MouseEvent arg0) {
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {

		DatabaseManager.delete(SpenPalMain.mainframe.rowData[SpenPalMain.mainframe.table.getSelectedRow()][5],
				SpenPalMain.mainframe.rowData[SpenPalMain.mainframe.table.getSelectedRow()][2]);
		SpenPalMain.mainframe.refresh();
	}
}