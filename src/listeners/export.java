package listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import spenpal.DatabaseManager;
import spenpal.MyFilter;

public class export implements MouseListener {
	JFrame j;

	public export(JFrame j) {
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
		JFileChooser jfc = new JFileChooser();
		jfc.addChoosableFileFilter(new MyFilter());
		jfc.showSaveDialog(null);
		File toexport = jfc.getSelectedFile();
		if (toexport == null) {
			return;
		}
		String name=toexport.getPath();
		if(name.endsWith(".csv")){
			toexport = new File(toexport.getPath() );
		}
		else
		{
			toexport = new File(toexport.getPath() + ".csv");
		}
		
		System.out.println(toexport);
		int ex = 0;
		if (toexport.exists()) {// 已存在文件
			ex = JOptionPane.showConfirmDialog(j, toexport + "The file is already exist, do you want to overwrite it?",
					"file already exist", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);// 显示一个对话框来实现是否覆盖源文件
		}
		if (ex == JOptionPane.YES_OPTION) {
			try {
				DataOutputStream dos = new DataOutputStream(new FileOutputStream(toexport));
				ResultSet rs = DatabaseManager.localData();
				try {
					dos.writeChars(" category, amount, date,comment\n");
					while (rs.next()) {
						try {
							for (int j = 1; j < 5; j++) {
								dos.writeChars(rs.getString(j + 1) + ",");

							}
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						dos.writeChars("\n");
						dos.flush();
					}
					rs.close();
					dos.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
}