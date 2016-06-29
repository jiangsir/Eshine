package jiangsir.eshine.Objects;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import tw.jiangsir.Utils.Annotations.Persistent;
import tw.jiangsir.Utils.Exceptions.DataException;
import tw.jiangsir.Utils.Scopes.ApplicationScope;

public class OnlineUser extends User implements Serializable, HttpSessionBindingListener {
	private static final long serialVersionUID = 1L;
	// private String session_account = null;
	/**
	 * 紀錄該 SessionUser 所設定的 locale 語系。
	 */
	// private Locale locale = Problem.locale_en_US;
	// private String returnPage = "";
	private int loginid = 0;
	private String session_ip = "";
	private String sessionid = "";
	private HttpSession session = null;

	public OnlineUser() {

	}

	public HttpSession getSession() {
		return session;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}

	/**
	 * 將 user 轉成 SessionUserBean 進行向下轉型。
	 * 
	 * @param user
	 */
	public OnlineUser(HttpSession session, User user) {
		if (session != null) {
			// this.setSession(session);
			this.setSessionid(session.getId());
			// 這裡如果讀取 session_locale 會導致問題。因為系統一開始啓動的時候，根本還沒有 session_locale
			// 但是如果不在這邊幫 sessionUserBean 將 locale 訊息設定上去，後面登入測驗 ShowContest
			// 就會出問題。
			// String session_locale = (String) session
			// .getAttribute("session_locale");
			// if (session_locale != null) {
			// this.setLocale(new Locale(session_locale.split("_")[0],
			// session_locale.split("_")[1]));
			// }
		}

		// this.setSession_account(user.getAccount());

		Object value;
		// for (Field field : this.getFields().values()) {
		for (Field field : User.class.getDeclaredFields()) {
			if (field.getAnnotation(Persistent.class) == null) {
				continue;
			}
			Method getter;
			try {
				getter = User.class.getMethod(
						"get" + field.getName().toUpperCase().substring(0, 1) + field.getName().substring(1));
				value = getter.invoke((User) user);
				// logger.info("getter=" + getter.getName() + ", value=" +
				// value);
				Method setter = User.class.getMethod(
						"set" + field.getName().toUpperCase().substring(0, 1) + field.getName().substring(1),
						new Class[]{value.getClass()});
				setter.invoke(this, new Object[]{value});
				// logger.info("setter=" + setter.getName() + ", parameter="
				// + value);
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}

	public String getSessionid() {
		if (sessionid == null || "null".equals(sessionid.toLowerCase())) {
			return "";
		}
		return sessionid;
	}

	public void setSessionid(String sessionid) {
		if (sessionid == null || "null".equals(sessionid.toLowerCase())) {
			return;
		}
		this.sessionid = sessionid;
	}

	public int getLoginid() {
		return loginid;
	}

	public void setLoginid(int loginid) {
		this.loginid = loginid;
	}

	/**
	 * 登出相關動作, 包含 session 逾時也執行 doLogout <br>
	 * 
	 * @param session
	 * @throws DataException
	 * @throws AccessException
	 */
	public void doLogout() throws DataException {
		if (session != null) {
			session.invalidate();
		}
		// this.setSessionid("");
		// this.setSession(null);
	}

	// /**
	// * 登出相關動作, 包含 session 逾時也執行 doLogout <br>
	// */
	// public void Logout(HttpSession session) {
	// session.invalidate();
	// }

	public String getSession_ip() {
		return session_ip;
	}

	public void setSession_ip(String session_ip) {
		this.session_ip = session_ip;
	}

	public boolean getIsIpdenied() {
		// return ENV.IP_DENIED.contains(session_ip);
		return false;
	}

	public boolean isNullOnlineUser() {
		if (new OnlineUser().getSessionid().equals(sessionid)
				&& new OnlineUser().getAccount() == new User().getAccount()) {
			return true;
		}
		return false;
	}

	@Override
	public void valueBound(HttpSessionBindingEvent event) {
		ApplicationScope.getOnlineUsers().put(event.getSession().getId(), this);
	}

	@Override
	public void valueUnbound(HttpSessionBindingEvent event) {
		ApplicationScope.getOnlineUsers().remove(event.getSession().getId());
	}
}
