package spenpal;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import net.sourceforge.jdatepicker.JDateComponentFactory;
import net.sourceforge.jdatepicker.JDatePanel;
import net.sourceforge.jdatepicker.impl.UtilDateModel;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JButton;
import java.awt.BorderLayout;

public class DateinputDialog extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DateinputDialog(final JLabel jLabel, int screen_x, int screen_y) {
		setTitle("choose date");
		Toolkit tk = Toolkit.getDefaultToolkit();
		this.setIconImage(tk.getImage("img\\ic.png"));
		getContentPane().setFont(new Font("Arial", Font.PLAIN, 12));
		final JDatePanel jp = JDateComponentFactory.createJDatePanel(new UtilDateModel(new Date()));
		jp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					jLabel.setText(new SimpleDateFormat("yyyy/MM/dd").format(jp.getModel().getValue()));
					if (jLabel.getName().compareTo("s") == 0) {
						DatabaseManager.startdate = jLabel.getText();
					} else if (jLabel.getName().compareTo("e") == 0) {
						DatabaseManager.enddate = jLabel.getText();
					}
				} catch (Exception ex) {
					jLabel.setText(ex.toString());
					// ex.printStackTrace();
				}
			}
		});
		JPanel jpanel = (JPanel) jp;
		getContentPane().add(jpanel);

		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.SOUTH);

		JButton okButton = new JButton("OK");
		okButton.addMouseListener(new ok(this));
		panel.add(okButton);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setBounds(screen_x, screen_y, 300, 320);
		this.setVisible(true);
	}

	private class ok implements MouseListener {
		JDialog j;

		public ok(JDialog j) {
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
			j.dispose();
		}
	}

}
