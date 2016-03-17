package jiangsir.eshine.Objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpSession;

import tw.jiangsir.Utils.Annotations.Persistent;

import jiangsir.eshine.Utils.DataBase;
import jiangsir.eshine.Utils.ENV;
import jiangsir.eshine.Utils.Utils;

/**
 *  - User.java
 * 2008/4/29 下午 05:41:54
 * jiangsir
 */

/**
 * @author jiangsir
 * 
 */
public class User {
	@Persistent(name = "id")
	private Integer id = 0;
	@Persistent(name = "account")
	private String account = "";
	@Persistent(name = "name")
	private String name = "";
	// @Persistent(name = "usergroup")
	// private String usergroup = "GroupUser";

	public enum ROLE {
		ADMIN, // 管理權限
		MANAGER, // 一般管理員
		USER, // 一般使用者
		GUEST; // 訪客，或未登入者
	}

	@Persistent(name = "role")
	private ROLE role = ROLE.GUEST;

	@Persistent(name = "passwd")
	private String passwd = "";
	public static final Integer visible_TRUE = 1;
	public static final Integer visible_FALSE = 0;
	@Persistent(name = "visible")
	private Integer visible = visible_TRUE;

	public User() {

	}

	// public User(Integer userid) {
	// String sql = "SELECT * FROM users WHERE id=" + userid;
	// ArrayList<?> list = new DataBase().executeQuery(sql);
	// if (list.size() == 0) {
	// return;
	// }
	// this.init((HashMap<?, ?>) list.get(0));
	// }
	//
	// public User(String account) {
	// if (account == null || "".equals(account)) {
	// return;
	// }
	// String sql = "SELECT * FROM users WHERE account='" +
	// Utils.intoSQL(account) + "'";
	// ArrayList<?> list = new DataBase().executeQuery(sql);
	// if (list.size() == 0) {
	// return;
	// }
	// this.init((HashMap<?, ?>) list.get(0));
	// }

	// public User(HashMap<?, ?> map) {
	// if (map == null) {
	// return;
	// }
	// this.init(map);
	// }

	// private void init(Map<?, ?> map) {
	// this.setId((Integer) map.get("id"));
	// this.setAccount((String) map.get("account"));
	// this.setName((String) map.get("name"));
	// this.setUsergroup((String) map.get("usergroup"));
	// this.setPasswd((String) map.get("passwd"));
	// this.setVisible((Integer) map.get("visible"));
	// }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public ROLE getRole() {
		return role;
	}

	public void setRole(ROLE role) {
		this.role = role;
	}

	public void setRole(String role) {
		if (role == null) {
			return;
		}
		this.setRole(ROLE.valueOf(role));
	}

	public Integer getVisible() {
		return visible;
	}

	public void setVisible(Integer visible) {
		this.visible = visible;
	}

	// public String getUsergroup() {
	// return usergroup;
	// }
	//
	// public void setUsergroup(String usergroup) {
	// this.usergroup = usergroup;
	// }

	/**
	 * 登出相關動作, 包含 session 逾時也執行 doLogout <br>
	 */
	public void Logout(HttpSession session) {

		session.invalidate();
	}

	// /**
	// *
	// *
	// */
	// public static void relogin(User user, HttpSession session) {
	// // 在 context restart 後，上線人數會歸零，這時可以透過其他 servlet 讓這些人慢慢重新加入
	// session.setAttribute("session_account", user.getAccount());
	// session.setAttribute("session_usergroup", user.getUsergroup());
	// session.setAttribute("passed", "true");
	// session.setAttribute("UserObject", user);
	// // ENV.OnlineUsers.put(session.getId(), session);
	// }

	// /**
	// * 這個 privilege 結合了 Group 裡的權限加上 extraprvilege
	// *
	// * @return
	// */
	// public String getPrivilege() {
	// return ENV.context.getInitParameter(usergroup);
	// }
}
