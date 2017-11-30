package spenpal;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;
import javax.swing.event.MouseInputListener;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.RingPlot;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.DefaultTableXYDataset;

import listeners.addnew;
import listeners.changeshare;
import listeners.delete;
import listeners.export;
import listeners.reconnect;
import listeners.searchdata;
import listeners.selectdate;
import listeners.selectdate_alltimes;
import listeners.update_lis;

import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JMenuBar;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.BoxLayout;
import java.awt.Color;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import java.awt.Font;
import javax.swing.JTabbedPane;
import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

public class MainFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4894599226412440845L;
	private JPanel contentPane;
	private String[] columnName = { "category", "amount", "date", "comment", "shared" };
	public String[][] rowData = new String[2][6];
	public JTable table;
	private JLabel label_end, label_status;
	private JLabel label_start_date, label_end_date;
	public JScrollPane scrollPane;
	private JTextField textField_username;
	private JTextField textField_password;

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		if (SpenPalMain.haslogin) {
			try {
				DatabaseManager.createlocaldatabase();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		setResizable(false);
		Toolkit tk = Toolkit.getDefaultToolkit();
		this.setIconImage(tk.getImage("img\\ic.png"));
		String windows = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
		try {
			UIManager.setLookAndFeel(windows);
		} catch (ClassNotFoundException e) {
			// e.printStackTrace();
		} catch (InstantiationException e) {
			// e.printStackTrace();
		} catch (IllegalAccessException e) {
			// e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// e.printStackTrace();
		}
		setTitle("SpenPal");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 698, 551);

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(new Color(255, 255, 255));
		setJMenuBar(menuBar);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(240, 240, 240));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel toolbarPanel = new JPanel();
		contentPane.add(toolbarPanel, BorderLayout.NORTH);
		toolbarPanel.setLayout(new BoxLayout(toolbarPanel, BoxLayout.X_AXIS));
		JPanel synchronizePanel = new JPanel();
		synchronizePanel.setBackground(new Color(240, 240, 240));
		synchronizePanel.setLayout(new GridLayout(3, 2, 0, 0));
		if (!SpenPalMain.haslogin && SpenPalMain.online) {
			JLabel lblUsername = new JLabel("Username:   ");
			lblUsername.setFont(new Font("Arial", Font.PLAIN, 12));
			synchronizePanel.add(lblUsername);

			textField_username = new JTextField();
			synchronizePanel.add(textField_username);
			textField_username.setColumns(10);

			JLabel lblPassword = new JLabel("Password:   ");
			lblPassword.setFont(new Font("Arial", Font.PLAIN, 12));
			synchronizePanel.add(lblPassword);

			textField_password = new JPasswordField();
			synchronizePanel.add(textField_password);
			textField_password.setColumns(10);

			JButton Button_reg = new JButton("register");
			Button_reg.setFont(new Font("Arial", Font.PLAIN, 12));
			Button_reg.addMouseListener(new register(this));
			synchronizePanel.add(Button_reg);

			JButton btnLogin = new JButton("login");
			btnLogin.setFont(new Font("Arial", Font.PLAIN, 12));
			btnLogin.addMouseListener(new login_new(this));
			synchronizePanel.add(btnLogin);
		} else {
			JLabel lblNewLabel = new JLabel("loged in as:");
			lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 12));
			synchronizePanel.add(lblNewLabel);

			JLabel nowuser = new JLabel(SpenPalMain.nowusr);
			nowuser.setFont(new Font("Arial", Font.PLAIN, 12));
			synchronizePanel.add(nowuser);

			JLabel lblNewLabel_2 = new JLabel("updated:");
			lblNewLabel_2.setFont(new Font("Arial", Font.PLAIN, 12));
			synchronizePanel.add(lblNewLabel_2);

			JLabel lblNewLabel_3 = new JLabel(SpenPalMain.haslogin ? DatabaseManager.gettime() : "0");
			lblNewLabel_3.setFont(new Font("Arial", Font.PLAIN, 12));
			synchronizePanel.add(lblNewLabel_3);
			JButton logoutButton = new JButton(SpenPalMain.haslogin ? "logout" : "reconnect");
			JButton updateButton = new JButton("update");
			updateButton.setEnabled(SpenPalMain.haslogin);
			updateButton.addMouseListener(new update_lis());
			logoutButton.addMouseListener(new reconnect(this));
			synchronizePanel.add(updateButton);
			synchronizePanel.add(logoutButton);
		}

		toolbarPanel.add(synchronizePanel);

		JSeparator separator_1 = new JSeparator();
		separator_1.setForeground(Color.GRAY);
		separator_1.setOrientation(SwingConstants.VERTICAL);
		toolbarPanel.add(separator_1);

		JPanel dateselectPanel = new JPanel();
		dateselectPanel.setBackground(new Color(240, 240, 240));
		toolbarPanel.add(dateselectPanel);
		dateselectPanel.setLayout(new GridLayout(3, 2, 0, 0));

		JLabel lblStratFrom = new JLabel("Strat from  ");
		lblStratFrom.setForeground(Color.BLUE);
		lblStratFrom.setBackground(Color.WHITE);
		lblStratFrom.setFont(new Font("Arial", Font.PLAIN, 12));

		dateselectPanel.add(lblStratFrom);

		label_start_date = new JLabel(DatabaseManager.startdate);
		label_start_date.setFont(new Font("Arial", Font.PLAIN, 12));
		label_start_date.setName("s");
		dateselectPanel.add(label_start_date);

		label_end = new JLabel("End by");
		label_end.setForeground(Color.BLUE);
		label_end.setFont(new Font("Arial", Font.PLAIN, 12));
		dateselectPanel.add(label_end);

		label_end_date = new JLabel(DatabaseManager.enddate);
		label_end_date.setFont(new Font("Arial", Font.PLAIN, 12));
		label_end_date.setName("e");
		dateselectPanel.add(label_end_date);

		JLabel label_selectdate = new JLabel("select date         ");
		label_selectdate.setFont(new Font("Arial", Font.PLAIN, 12));
		label_selectdate.setForeground(Color.GRAY);
		dateselectPanel.add(label_selectdate);

		JLabel label_alltimes = new JLabel("all times");
		label_alltimes.setFont(new Font("Arial", Font.PLAIN, 12));
		label_alltimes.setForeground(Color.BLUE);
		label_alltimes.addMouseListener(new selectdate_alltimes(label_alltimes, label_start_date, label_end_date));
		dateselectPanel.add(label_alltimes);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(240, 240, 240));
		toolbarPanel.add(panel);

		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(240, 240, 240));
		panel.add(panel_2);

		lblStratFrom.addMouseListener(new selectdate(lblStratFrom, label_start_date));
		label_end.addMouseListener(new selectdate(label_end, label_end_date));
		panel_2.setLayout(new BorderLayout(0, 0));

		JButton botton_export = new JButton("export");
		botton_export.setEnabled(SpenPalMain.haslogin);
		botton_export.setFont(new Font("Arial", Font.PLAIN, 12));
		if (SpenPalMain.haslogin) {
			botton_export.addMouseListener(new export(this));
		}
		panel_2.add(botton_export, BorderLayout.SOUTH);

		JPanel panel_1 = new JPanel();
		panel_2.add(panel_1, BorderLayout.WEST);
		JButton Button_search = new JButton("search");
		Button_search.setEnabled(SpenPalMain.haslogin);
		panel_2.add(Button_search, BorderLayout.NORTH);
		Button_search.setFont(new Font("Arial", Font.PLAIN, 12));
		if (SpenPalMain.haslogin) {
			Button_search.addMouseListener(new searchdata(this));
		}
		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setForeground(Color.GRAY);
		toolbarPanel.add(separator);

		JPanel newdataPanel = new JPanel();
		newdataPanel.setBackground(new Color(240, 240, 240));
		toolbarPanel.add(newdataPanel);

		JPanel panel_4 = new JPanel();
		panel_4.setBackground(new Color(240, 240, 240));
		newdataPanel.add(panel_4);

		JButton Button_new = new JButton("New..");
		panel_4.add(Button_new);
		Button_new.setEnabled(SpenPalMain.haslogin);
		Button_new.setFont(new Font("Arial", Font.PLAIN, 12));
		if (SpenPalMain.haslogin) {
			Button_new.addMouseListener(new addnew());
		}
		JPanel bottompanel = new JPanel();
		contentPane.add(bottompanel, BorderLayout.SOUTH);

		label_status = new JLabel(SpenPalMain.online ? "Spenpal" : "offline");
		label_status.setFont(new Font("Arial", Font.PLAIN, 12));
		bottompanel.add(label_status);
		if (SpenPalMain.haslogin) {
			JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.BOTTOM);
			tabbedPane.setFont(new Font("Arial", Font.PLAIN, 13));
			tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
			contentPane.add(tabbedPane, BorderLayout.WEST);
			settabledata();
			table = new JTable(new DefaultTableModel(rowData, columnName) {

				private static final long serialVersionUID = -569709047798337242L;

				public boolean isCellEditable(int row, int column) {
					return false;
				}
			});
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			table.setFont(new Font("Arial", Font.PLAIN, 16));
			table.setRowHeight(40);
			scrollPane = new JScrollPane(table);
			tabbedPane.addTab("Total", null, scrollPane, null);
			final MouseInputListener mouseInputListener = getMouseInputListener(table);

			ChartPanel totalpiePanel = new ChartPanel(create_total_piechart());
			totalpiePanel.setMouseWheelEnabled(true);
			tabbedPane.addTab("pie chart", null, totalpiePanel, null);
			ChartPanel totallinePanel = new ChartPanel(create_total_linechart());
			tabbedPane.addTab("line chart", null, totallinePanel, null);

			// JPanel panel_3 = new JPanel();
			// tabbedPane.addTab("New tab", null, panel_3, null);
			// panel_3.setLayout(new BorderLayout(0, 0));

			// JPanel panel_5 = new JPanel();
			// panel_3.add(panel_5, BorderLayout.NORTH);
			//
			// JLabel lblNewLabel_1 = new JLabel("friend's spend");
			// panel_5.add(lblNewLabel_1);

			// JScrollPane scrollPane_1 = new JScrollPane();
			// panel_3.add(scrollPane_1, BorderLayout.CENTER);

			// JPanel settings = new JPanel();
			// tabbedPane.addTab("Settings", null, settings, null);
			// settings.setLayout(new BorderLayout(0, 0));

			table.addMouseListener(mouseInputListener);
			table.addMouseMotionListener(mouseInputListener);

		}
		this.setVisible(true);
	}

	private static MouseInputListener getMouseInputListener(final JTable jTable) {
		return new MouseInputListener() {
			public void mouseClicked(MouseEvent e) {
				processEvent(e);
			}

			public void mousePressed(MouseEvent e) {
				processEvent(e);
			}

			public void mouseReleased(MouseEvent e) {
				processEvent(e);
				if ((e.getModifiers() & MouseEvent.BUTTON3_MASK) != 0 && !e.isControlDown() && !e.isShiftDown()) {
					JPopupMenu tablemenu = new JPopupMenu();

					JMenuItem firstItem = new JMenuItem("delete");
					JMenuItem secondItem = new JMenuItem("share it");
					firstItem.addMouseListener(new delete());
					secondItem.addMouseListener(new changeshare());
					tablemenu.add(firstItem);
					tablemenu.add(secondItem);
					tablemenu.show(jTable, e.getX(), e.getY());// ”“º¸≤Àµ•œ‘ æ
																// reference:http://home.open-open.com/space-130-do-thread-id-20.html
				}
			}

			public void mouseEntered(MouseEvent e) {
				processEvent(e);
			}

			public void mouseExited(MouseEvent e) {
				processEvent(e);
			}

			public void mouseDragged(MouseEvent e) {
				processEvent(e);
			}

			public void mouseMoved(MouseEvent e) {
				processEvent(e);
			}

			private void processEvent(MouseEvent e) {
				if ((e.getModifiers() & MouseEvent.BUTTON3_MASK) != 0) {
					int modifiers = e.getModifiers();
					modifiers -= MouseEvent.BUTTON3_MASK;
					modifiers |= MouseEvent.BUTTON1_MASK;
					MouseEvent ne = new MouseEvent(e.getComponent(), e.getID(), e.getWhen(), modifiers, e.getX(),
							e.getY(), e.getClickCount(), false);
					jTable.dispatchEvent(ne);
				}
			}
		};
	}

	public void settabledata() {

		rowData = new String[DatabaseManager.localData_count()][6];
		int i = 0;

		ResultSet rs = DatabaseManager.localData();
		try {
			while (rs.next()) {
				try {
					for (int j = 0; j < 5; j++) {
						rowData[i][j] = rs.getString(j + 2);
					}
					rowData[i][5] = rs.getString(1);// id
				} catch (SQLException e) {
					e.printStackTrace();
				}
				i++;

			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void refresh() {
		SpenPalMain.mainframe = new MainFrame();
		this.dispose();
	}

	private JFreeChart create_total_piechart() {
		DefaultPieDataset dpd = new DefaultPieDataset(); // set a default pie chart
		ResultSet rs = DatabaseManager.groupbycategory();

		try {
			while (rs.next()) {
				dpd.setValue(rs.getString(1), (double) rs.getFloat(2));
			}
			rs.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		String title = "From " + DatabaseManager.startdate + " to " + DatabaseManager.enddate;

		JFreeChart chart = ChartFactory.createRingChart(title, dpd, true, true, false);
		RingPlot ringplot = (RingPlot) chart.getPlot();
		ringplot.setSectionDepth(0.4d);
		return chart;

	}

	private JFreeChart create_total_linechart() {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		new DefaultTableXYDataset();
		ResultSet rs = DatabaseManager.groupbydate();
		try {
			while (rs.next()) {
				dataset.addValue(rs.getFloat(2), "spending", rs.getString(1));
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String title = "From " + DatabaseManager.startdate + " to " + DatabaseManager.enddate;

		JFreeChart chart = ChartFactory.createLineChart(title, "time", "amount", dataset);

		return chart;

	}

	private class register implements MouseListener {
		JFrame j;

		public register(JFrame j) {
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
			if (textField_username.getText().compareTo("") == 0 || textField_password.getText().compareTo("") == 0) {
				label_status.setText("You can't regiser an user without username or password!");
				label_status.setForeground(Color.RED);
			} else {
				Boolean success = SpenPalMain.scon.register(textField_username.getText(), textField_password.getText());

				if (success) {
					JOptionPane.showMessageDialog(j, "success", "regiser", JOptionPane.ERROR_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(j, "regiser faild:duplicate username", "regiser",
							JOptionPane.PLAIN_MESSAGE);
				}
			}
		}
	}

	private class login_new implements MouseListener {
		JFrame j;

		public login_new(JFrame j) {
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
			if (textField_username.getText().compareTo("") == 0 || textField_password.getText().compareTo("") == 0) {
				JOptionPane.showMessageDialog(j, "uesrname or password is empty", "can't login",
						JOptionPane.ERROR_MESSAGE);
			} else {
				System.out.println("login: " + textField_username.getText() + " " + textField_password.getText());
				SpenPalMain.haslogin = SpenPalMain.scon.login(textField_username.getText(),
						textField_password.getText());
				if (SpenPalMain.haslogin) {
					SpenPalMain.nowusr = textField_username.getText();
					SpenPalMain.mainframe = new MainFrame();
					j.dispose();
				} else {
					JOptionPane.showMessageDialog(j, "wrong username or password", "can't login",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}

}
