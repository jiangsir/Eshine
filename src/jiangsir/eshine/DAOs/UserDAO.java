/**
 * idv.jiangsir.DAOs - UserDAO.java
 * 2008/4/29 下午 05:46:51
 * jiangsir
 */
package jiangsir.eshine.DAOs;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import jiangsir.eshine.Objects.OnlineUser;
import jiangsir.eshine.Objects.User;
import tw.jiangsir.Utils.DAO.SuperDAO;
import tw.jiangsir.Utils.Exceptions.DataException;

/**
 * @author jiangsir
 * 
 */
public class UserDAO extends SuperDAO<User> {

	/**
	 * 取得所有 user
	 * 
	 * @return
	 */
	public ArrayList<User> getUsersByVisibleTrue() {
		String sql = "SELECT * FROM users WHERE visible=" + User.visible_TRUE;
		// String sql = "SELECT * FROM users";
		try {
			PreparedStatement pstmt = this.getConnection().prepareStatement(sql);
			return this.executeQuery(pstmt, User.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 用帳號密碼取得一個 User
	 * 
	 * @param account
	 * @param passwd
	 * @return
	 */
	protected User getUserByAccountPasswd(String account, String passwd) throws DataException {
		String sql = "SELECT * FROM users WHERE account=? AND passwd=MD5(?)";
		PreparedStatement pstmt;
		try {
			pstmt = this.getConnection().prepareStatement(sql);
			pstmt.setString(1, account);
			pstmt.setString(2, passwd);
			for (User user : this.executeQuery(pstmt, User.class)) {
				return user;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataException(e);
		}
		throw new DataException("帳號密碼有誤！");
	}

	/**
	 * 用帳號密碼取得一個 User
	 * 
	 * @param account
	 * @param passwd
	 * @return
	 */
	protected User getUserByAccount(String account) throws DataException {
		String sql = "SELECT * FROM users WHERE account=?";
		PreparedStatement pstmt;
		try {
			pstmt = this.getConnection().prepareStatement(sql);
			pstmt.setString(1, account);
			for (User user : this.executeQuery(pstmt, User.class)) {
				return user;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataException(e);
		}
		throw new DataException("該帳號不存在！(" + account + ")");
	}
	/**
	 * 取得 onlineUser
	 * 
	 * @param userid
	 * @param session
	 * @return
	 */
	public OnlineUser getOnlineUserById(long userid, HttpSession session) {
		String sql = "SELECT * FROM users WHERE id=?";
		try {
			PreparedStatement pstmt = this.getConnection().prepareStatement(sql);
			pstmt.setLong(1, userid);
			for (User user : this.executeQuery(pstmt, User.class)) {
				// currentUser.setSession(session);
				return new OnlineUser(session, user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * 取得一個 User
	 * 
	 * @return
	 */
	protected User getUserById(long userid) throws DataException {
		String sql = "SELECT * FROM users WHERE id=?";
		PreparedStatement pstmt;
		try {
			pstmt = this.getConnection().prepareStatement(sql);
			pstmt.setLong(1, userid);
			for (User user : this.executeQuery(pstmt, User.class)) {
				return user;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataException(e);
		}
		throw new DataException("該 ID 不存在！(" + userid + ")");
	}

	@Override
	protected boolean delete(long i) throws SQLException {
		return false;
	}

	@Override
	protected int insert(User user) throws SQLException {
		String sql = "INSERT INTO users (account, passwd, role, visible) VALUES (?,MD5(?),?, ?);";
		PreparedStatement pstmt = this.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		pstmt.setString(1, user.getAccount());
		pstmt.setString(2, user.getPasswd());
		pstmt.setString(3, user.getRole().name());
		pstmt.setInt(4, user.getVisible());
		return this.executeInsert(pstmt);
	}

	@Override
	protected int update(User user) throws SQLException {
		String sql = "UPDATE users SET account=?, passwd=MD5(?), role=?, visible=? WHERE id=?";
		int result = -1;
		PreparedStatement pstmt = this.getConnection().prepareStatement(sql);
		pstmt.setString(1, user.getAccount());
		pstmt.setString(2, user.getPasswd());
		pstmt.setString(3, user.getRole().name());
		pstmt.setInt(4, user.getVisible());
		pstmt.setLong(5, user.getId());
		result = this.executeUpdate(pstmt);
		pstmt.close();
		return result;
	}

}
