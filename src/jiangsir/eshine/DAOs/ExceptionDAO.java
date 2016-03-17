package jiangsir.eshine.DAOs;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import jiangsir.eshine.Utils.DataBase;
import jiangsir.eshine.Utils.ENV;
import jiangsir.eshine.Utils.Utils;


public class ExceptionDAO {

	public ExceptionDAO() {

	}

	public int getCount() {
		String SQL = "SELECT * FROM exceptions";
		return new DataBase().executeCount(SQL);
	}

	public ArrayList getErrors(int count) {
		String SQL = "SELECT * FROM exceptions ORDER BY id DESC LIMIT 0,"
				+ count;
		return new DataBase().executeQuery(SQL);
	}

	public ArrayList getErrorsByIP(String ip) {
		String SQL = "SELECT * FROM exceptions WHERE ipaddr='" + ip
				+ "' ORDER BY id DESC";
		return new DataBase().executeQuery(SQL);
	}

	public int insert_PSTMT(String uri, String account, String ipaddr,
			String exceptiontype, String exception) {
		String sql = "INSERT INTO exceptions (uri, account, ipaddr, "
				+ "exceptiontype, exception, exceptiontime) VALUES(?,?,?,?,?,?)";
		int id = 0;
		try {
			PreparedStatement pstmt = new DataBase().getConnection()
					.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, uri);
			pstmt.setString(2, account);
			pstmt.setString(3, ipaddr);
			pstmt.setString(4, exceptiontype);
			pstmt.setString(5, exception);
			pstmt.setTimestamp(6, new Timestamp(new Date().getTime()));
			new DataBase().executeUpdate(pstmt, sql);
			ResultSet rs = pstmt.getGeneratedKeys();
			rs.next();
			id = rs.getInt(1);
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return id;
	}

	public int insert_OLD(String uri, String account, String ipaddr,
			String exceptiontype, String exception) {
		if (exception.contains("INSERT INTO exceptions")) {
			return 0;
		}
		uri = Utils.intoSQL(uri);
		if (uri != null && uri.length() > 100) {
			uri = uri.substring(uri.length() - 100);
		}
		if (exceptiontype != null && exceptiontype.length() > 200) {
			exceptiontype = exceptiontype.substring(0, 200);
			exceptiontype += "訊息太長(>200)，到此省略...";
		}
		if (exception != null && exception.length() > 100000) {
			exception = exception.substring(0, 10000);
			exception += "訊息太長(>100000)，到此省略...";
		}
		exceptiontype = Utils.intoSQL(exceptiontype);
		exception = Utils.intoSQL(exception);
		String SQL = "INSERT INTO exceptions (uri, account, ipaddr, "
				+ "exceptiontype, exception, exceptiontime) VALUES('" + uri
				+ "', '" + account + "', '" + ipaddr + "', '" + exceptiontype
				+ "', '" + exception + "', '" + ENV.getNow() + "')";
		return new DataBase().executeInsert(SQL);
	}
}
