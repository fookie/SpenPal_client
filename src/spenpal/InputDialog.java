	package spenpal;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.NumberFormatter;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import net.sourceforge.jdatepicker.JDateComponentFactory;
import net.sourceforge.jdatepicker.JDatePanel;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

import java.awt.Choice;
import javax.swing.JFormattedTextField;

public class InputDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7329419075885020373L;
	private final JPanel contentPanel = new JPanel();
	private JTextField comment_input;
	public JLabel lblChooseAImage;
	public JLabel label_date;
	public JFormattedTextField amountinput;
	public Choice choice;
	public JDatePanel jp = JDateComponentFactory.createJDatePanel(new UtilDateModel(new Date()));

	/**
	 * Create the dialog.
	 */
	public InputDialog() {
		Toolkit tk = Toolkit.getDefaultToolkit();
		this.setIconImage(tk.getImage("img\\ic.png"));
		String windows = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
		try {
			UIManager.setLookAndFeel(windows);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		setTitle("new..");
		setBounds(100, 100, 270, 304);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblNewLabel = new JLabel("catagory");
		lblNewLabel.setBounds(10, 10, 54, 15);
		contentPanel.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("amount");
		lblNewLabel_1.setBounds(10, 50, 54, 15);
		contentPanel.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("date");
		lblNewLabel_2.setBounds(10, 93, 54, 15);
		contentPanel.add(lblNewLabel_2);

		choice = new Choice();
		choice.add("Default");
		choice.add("Transport");
		choice.add("Daily");
		choice.add("Social");
		choice.add("Shopping");
		choice.add("Hobby");
		choice.add("Entertainment");
		choice.add("Drink");
		choice.add("Movie");
		choice.add("Personal");
		choice.add("Drink");
		
		choice.setBounds(70, 4, 97, 21);
		contentPanel.add(choice);

		label_date = new JLabel("datehere");
		label_date.setText(new SimpleDateFormat("yyyy/MM/dd").format(jp.getModel().getValue()));
		label_date.setBounds(74, 93, 105, 15);
		label_date.setName("d");
		contentPanel.add(label_date);

		JButton data_button = new JButton("choose");
		data_button.setBounds(151, 89, 93, 23);
		data_button.addMouseListener(new selectdate());
		contentPanel.add(data_button);

		JLabel lblComment = new JLabel("comment");
		lblComment.setBounds(10, 119, 54, 15);
		contentPanel.add(lblComment);

		comment_input = new JTextField();
		comment_input.setBounds(10, 144, 157, 21);
		contentPanel.add(comment_input);
		comment_input.setColumns(10);

		JLabel lblImage = new JLabel("image");
		lblImage.setBounds(10, 175, 54, 15);
		contentPanel.add(lblImage);

		JButton btnSelect = new JButton("select");
		btnSelect.setBounds(51, 175, 93, 23);
		btnSelect.addMouseListener(new selectimg());
		contentPanel.add(btnSelect);

		lblChooseAImage = new JLabel("choose a image");
		lblChooseAImage.setBounds(10, 208, 234, 15);
		contentPanel.add(lblChooseAImage);

		DecimalFormat decimalFormat = new DecimalFormat("###0.00");
		NumberFormatter textFormatter = new NumberFormatter(decimalFormat);
		textFormatter.setAllowsInvalid(false);
		amountinput = new JFormattedTextField(textFormatter);
		amountinput.setText("0.00");
		amountinput.setBounds(70, 47, 97, 21);
		contentPanel.add(amountinput);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				okButton.addMouseListener(new ok(this));
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
				cancelButton.addMouseListener(new cancel(this));
			}
		}
	}

	private class selectimg implements MouseListener {
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
				JFileChooser jfc = new JFileChooser();
				jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				jfc.showOpenDialog(null);
				File fileToAdd = jfc.getSelectedFile();
				String path = fileToAdd.getAbsolutePath();
				lblChooseAImage.setText(path);
			}
		}
	}

	private class selectdate implements MouseListener {
		public void mouseClicked(MouseEvent arg0) {
		}

		public void mouseEntered(MouseEvent arg0) {
		}

		public void mouseExited(MouseEvent arg0) {
		}

		public void mousePressed(MouseEvent e) {
		}

		public void mouseReleased(MouseEvent e) {
			new DateinputDialog(label_date,100,100);
		}
	}

	private class cancel implements MouseListener {
		JDialog j;

		public cancel(JDialog j) {
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
			if (e.getButton() == MouseEvent.BUTTON1) {
				j.dispose();
			}
		}
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
			String date = label_date.getText();
			int id = DatabaseManager.localData_count_getid(date);
			String category = choice.getSelectedItem();
			float amount = Float.valueOf(amountinput.getText());
			
			String comment = comment_input.getText();
			System.out.println(amount);
			try {
				DatabaseManager.addnew(id, category, amount, date, comment, null);
				j.dispose();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		//	SpanPalMain.mainframe.settabledata();
			SpenPalMain.mainframe.refresh();
			
		}
	}
}
