package jiangsir.eshine.DAOs;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import jiangsir.eshine.Objects.OnlineUser;
import jiangsir.eshine.Objects.User;
import tw.jiangsir.Utils.Config.SessionScope;
import tw.jiangsir.Utils.Exceptions.DataException;

public class UserService {

	public boolean isExitedUser(String account, String passwd) {
		if (new UserDAO().getUserByAccountPasswd(account, passwd) == null) {
			return false;
		}
		return true;
	}

	public boolean isExitedAccount(String account) {
		if (new UserDAO().getUserByAccount(account) == null) {
			return false;
		}
		return true;
	}

	/**
	 * 只判斷這個 session 是否有登入。
	 * 
	 * @param session
	 * @return
	 */
	public boolean isUserOnline(HttpSession session) {
		return new SessionScope(session).getOnlineUser() == null ? false : true;
	}

	public User getUserByAccount(String account) {
		return new UserDAO().getUserByAccount(account);
	}

	public ArrayList<User> getUsersByVisibleTrue() {
		return new UserDAO().getUsersByVisibleTrue();
	}

	public User getUserById(long userid) {
		return new UserDAO().getUserById(userid);
	}

	public User getUserByAccountPasswd(String account, String passwd) {
		return new UserDAO().getUserByAccountPasswd(account, passwd);
	}

	/**
	 * 用 userid 到資料庫中取出 user 製作成 OnlineUser。準備放入 SessionScope 當中。
	 *
	 * @param userid
	 * @param session
	 * @return
	 */
	public OnlineUser createOnlineUser(long userid, HttpSession session) {
		return new UserDAO().getOnlineUserById(userid, session);
	}

	public int insert(User user) throws DataException {
		try {
			int userid = new UserDAO().insert(user);
			if (userid == 0) {
				throw new DataException("沒有新增！");
			}
			return userid;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataException(e);
		}
	}

	public void update(User user) throws DataException {
		try {
			if (new UserDAO().update(user) == 0) {
				throw new DataException("沒有更新！");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataException(e);
		}
	}

	public void delete(int userid) throws DataException {
		try {
			if (!new UserDAO().delete(userid)) {
				throw new DataException("刪除失敗！");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataException(e);
		}
	}

}
