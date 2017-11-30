package spenpal;

import network.ServerConnector;

public class SpenPalMain {
	public static DatabaseManager dbm;
	public static MainFrame mainframe;
	public static boolean haslogin = false;
	public static String nowusr = "";
	public static boolean online = false;
	public static ServerConnector scon;
	public static final String ipaddr ="128.199.130.45";
//	public static final String ipaddr ="127.0.0.1";
	public static final int port = 12313;

	public static void main(String[] args) {
		connect();
		mainframe = new MainFrame();
		mainframe.setVisible(true);
		SpenPalMain.dbm = new DatabaseManager("database//spenpaldb.db");
	}

	public static boolean connect() 		{
		try {
			scon = new ServerConnector(ipaddr, port);
			online = true;
			return true;
		} catch (Exception e) {
			System.out.println("can not log in:" + e);
			return false;
		}
	}
}
