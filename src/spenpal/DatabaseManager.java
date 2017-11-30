package spenpal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
	private static String dbaddr;
	private static Connection conn;
	private static Statement stat;
	public static String startdate = "very beginning", enddate = "very ending";

	public DatabaseManager(String addr) {
		DatabaseManager.dbaddr = addr;
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:./" + dbaddr);
			stat = conn.createStatement();
		} catch (ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
		}
		System.out.println("loading database...");
//		try {
//			createlocaldatabase(addr);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
		System.out.println("finish");
	}

	public static void createlocaldatabase() throws SQLException {
		stat.execute("CREATE TABLE IF NOT EXISTS " + SpenPalMain.nowusr
				+ " (id INTEGER, category char(30), amount FLOAT, date char(30), comment char(30),shared INTEGER)");

		stat.execute("CREATE TABLE IF NOT EXISTS updatetime(user char(64),time char(64))");
		stat.execute("insert into updatetime values('" + SpenPalMain.nowusr + "','0')");
	}

	/**
	 * 
	 * @param id
	 * @param category
	 * @param amount
	 * @param date
	 * @param comment
	 * @param img
	 * @throws SQLException
	 */
	public static void addnew(int id, String category, float amount, String date, String comment, String img)
			throws SQLException {
		String sql = "insert into " + SpenPalMain.nowusr + " (id, category, amount,date,comment) values(" + id + ",'"
				+ category + "', " + amount + ",'" + date + "','" + comment + "');";

		stat.execute(sql);
		// String
		// tosend=id+"|"+category+"|"+amount+"|"+date+"|"+comment+"|"+img;
		// SpenPalMain.scon.save_query_toserver(sql);
		SpenPalMain.scon
				.send_data_toserver(id + " " + category + " " + amount + " " + date + " " + comment + " " + img);
	}

	public static ResultSet localData() {
		String time = ((startdate.compareTo("very beginning") != 0 || enddate.compareTo("very ending") != 0) ? "where "
				: "") + (startdate.compareTo("very beginning") == 0 ? "" : "date>='" + startdate + "' ")
				+ ((startdate.compareTo("very beginning") != 0 && enddate.compareTo("very ending") != 0) ? "and " : "")
				+ (enddate.compareTo("very ending") == 0 ? "" : "date<='" + enddate + "'");
		String query = "SELECT * from " + SpenPalMain.nowusr + " " + time + " order by date";
		try {
			ResultSet rs = stat.executeQuery(query);
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static int localData_count() {
		String time = ((startdate.compareTo("very beginning") != 0 || enddate.compareTo("very ending") != 0) ? "where "
				: "") + (startdate.compareTo("very beginning") == 0 ? "" : "date>='" + startdate + "' ")
				+ ((startdate.compareTo("very beginning") != 0 && enddate.compareTo("very ending") != 0) ? "and " : "")
				+ (enddate.compareTo("very ending") == 0 ? "" : "date<='" + enddate + "'");
		String query = "SELECT count(*) from " + SpenPalMain.nowusr + " " + time;
		try {
			ResultSet rs = stat.executeQuery(query);
			rs.next();
			int toreturn = rs.getInt(1);
			rs.close();
			return toreturn;
		} catch (SQLException e) {
			System.err.println(e);

		}
		return 0;
	}

	public static int localData_count_getid(String date) {
		String query = "SELECT count(*) from " + SpenPalMain.nowusr + " where date='" + date + "'; ";
		try {
			ResultSet rs = stat.executeQuery(query);
			rs.next();
			int toreturn = rs.getInt(1);
			rs.close();
			return toreturn;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static ResultSet groupbycategory() {
		String time = ((startdate.compareTo("very beginning") != 0 || enddate.compareTo("very ending") != 0) ? "where "
				: "") + (startdate.compareTo("very beginning") == 0 ? "" : "date>='" + startdate + "' ")
				+ ((startdate.compareTo("very beginning") != 0 && enddate.compareTo("very ending") != 0) ? "and " : "")
				+ (enddate.compareTo("very ending") == 0 ? "" : "date<='" + enddate + "'");
		String query = "SELECT category,sum(amount) from " + SpenPalMain.nowusr + " " + time + " group by category";
		try {
			ResultSet rs = stat.executeQuery(query);
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static ResultSet groupbydate() {
		String time = ((startdate.compareTo("very beginning") != 0 || enddate.compareTo("very ending") != 0) ? "where "
				: "") + (startdate.compareTo("very beginning") == 0 ? "" : "date>='" + startdate + "' ")
				+ ((startdate.compareTo("very beginning") != 0 && enddate.compareTo("very ending") != 0) ? "and " : "")
				+ (enddate.compareTo("very ending") == 0 ? "" : "date<='" + enddate + "'");
		String query = "SELECT date,sum(amount) from " + SpenPalMain.nowusr + " " + time + " group by date";
		try {
			ResultSet rs = stat.executeQuery(query);
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void updatetime(String t) {
		String sql = "UPDATE updatetime SET time = '" + t + "' WHERE user = '" + SpenPalMain.nowusr + "' ";
		try {
			stat.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void executeQuery(String query) {
		try {
			System.out.println("loading:" + query);
			stat.executeUpdate(query);
		} catch (SQLException e) {
			System.out.println("err" + e);
		}
	}

	public static String gettime() {
		String sql = "select time from updatetime where user='" + SpenPalMain.nowusr + "'";
		ResultSet rs;
		try {
			rs = stat.executeQuery(sql);
			String t = rs.getString(1);
			rs.close();
			return t;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "0";

	}

	public static void delete(String id, String date) {
		String sql = "delete from " + SpenPalMain.nowusr + " where id=" + id + " and date='" + date + "'";
		String changeid = "update " + SpenPalMain.nowusr + " set id=id-1 where id>" + id + " and date='" + date + "'";
		try {
			stat.executeUpdate(sql);
			stat.executeUpdate(changeid);
			SpenPalMain.scon.editdata(119, id, date);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	public static void change_share_status(String id, String date,String i) {
		String sql = "update "+ SpenPalMain.nowusr+" set shared=" + i + " where id="+id+" and date='" + date + "'";
		try {
			stat.executeUpdate(sql);
			SpenPalMain.scon.editdata(121, id, date);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
