package jiangsir.eshine.DAOs;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import jiangsir.eshine.Utils.ENV;
import jiangsir.eshine.Utils.Utils;

public class LoginlogDAO {

	public LoginlogDAO() {

	}

	private int executeUpdate(PreparedStatement pstmt, String SQL) {
		long starttime = System.currentTimeMillis();
		int result = -1;
		try {
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			new ExceptionDAO().insert_PSTMT("DataBase.java", "unknown", "unknown IP", e.getLocalizedMessage(),
					Utils.printStackTrace(e, SQL));
			e.printStackTrace();
		}
		System.out.println(
				ENV.logHeader() + "PSTMT_SQL=" + SQL + " 共耗時 " + (System.currentTimeMillis() - starttime) + " ms");
		return result;
	}

	public int insert(Loginlog loginlog) {
		String sql = "INSERT INTO loginlogs (userid, useraccount, ipaddr, "
				+ "message, logintime, logouttime)VALUES(?,?,?,?,?,?);";
		int id = 0;
		try {
			PreparedStatement pstmt = new DataBase().getConnection().prepareStatement(sql,
					Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, loginlog.getUserid());
			pstmt.setString(2, loginlog.getUseraccount());
			pstmt.setString(3, loginlog.getIpaddr());
			pstmt.setString(4, loginlog.getMessage());
			pstmt.setTimestamp(5, new Timestamp(loginlog.getLogintime().getTime()));
			pstmt.setTimestamp(6, new Timestamp(loginlog.getLogouttime().getTime()));
			this.executeUpdate(pstmt, sql);
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
}
