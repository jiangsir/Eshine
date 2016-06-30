package tw.jiangsir.Utils.Scopes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Locale;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import tw.jiangsir.Utils.Servlets.ShowSessionsServlet;

import jiangsir.eshine.Objects.OnlineUser;
import jiangsir.eshine.Servlets.LoginServlet;
import jiangsir.eshine.Servlets.LogoutServlet;

public class SessionScope implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HttpSession session = null;
	private String sessionid = "";
	private String session_ip = "";
	private LinkedHashSet<String> session_privilege = new LinkedHashSet<String>();
	private Locale session_locale = null;
	private String session_useragent = "";
	private HashMap<String, String> session_requestheaders = new HashMap<String, String>();
	private OnlineUser onlineUser = null;
	private Date lastsubmission = new Date();
	private ArrayList<String> histories = new ArrayList<String>() {
		private static final long serialVersionUID = 1L;
		{
			add("");
			add("");
		}
	};

	public SessionScope(HttpServletRequest request) {
		this(request.getSession(false) == null ? request.getSession() : request.getSession(false));
	}

	@SuppressWarnings("unchecked")
	public SessionScope(HttpSession session) {
		if (session == null) { // 避免第一次剛進來, session 尚未建立，會產生 NullException
			return;
		}
		this.session = session;
		this.setSessionid(session.getId());
		this.setSession_ip((String) session.getAttribute("session_ip"));
		this.setSession_privilege((LinkedHashSet<String>) session.getAttribute("session_privilege"));
		this.setSession_locale((Locale) session.getAttribute("session_locale"));
		this.setSession_useragent((String) session.getAttribute("session_useragent"));
		this.setSession_requestheaders((HashMap<String, String>) session.getAttribute("session_requestheaders"));
		this.setOnlineUser((OnlineUser) session.getAttribute("onlineUser"));
		this.setLastsubmission((Date) session.getAttribute("lastsubmission"));
		this.setHistories((ArrayList<String>) session.getAttribute("histories"));
	}

	public String getSessionid() {
		return sessionid;
	}

	public void setSessionid(String sessionid) {
		session.setAttribute("sessionid", sessionid);
		this.sessionid = sessionid;
	}

	public String getSession_ip() {
		return session_ip;
	}

	public void setSession_ip(String session_ip) {
		if (session_ip == null) {
			return;
		}
		session.setAttribute("session_ip", session_ip);
		this.session_ip = session_ip;
	}

	public LinkedHashSet<String> getSession_privilege() {
		return session_privilege;
	}

	public void setSession_privilege(LinkedHashSet<String> session_privilege) {
		if (session_privilege == null) {
			return;
		}
		session.setAttribute("session_privilege", session_privilege);
		this.session_privilege = session_privilege;
	}

	public Locale getSession_locale() {
		return session_locale;
	}

	public void setSession_locale(Locale session_locale) {
		if (session_locale == null) {
			return;
		}
		session.setAttribute("session_locale", session_locale);
		this.session_locale = session_locale;
	}

	public String getSession_useragent() {
		return session_useragent;
	}

	public void setSession_useragent(String session_useragent) {
		if (session_useragent == null) {
			return;
		}
		session.setAttribute("session_useragent", session_useragent);
		this.session_useragent = session_useragent;
	}

	public HashMap<String, String> getSession_requestheaders() {
		return session_requestheaders;
	}

	public void setSession_requestheaders(HashMap<String, String> session_requestheaders) {
		if (session_requestheaders == null) {
			return;
		}
		session.setAttribute("session_requestheaders", session_requestheaders);
		this.session_requestheaders = session_requestheaders;
	}

	public OnlineUser getOnlineUser() {
		return onlineUser;
	}

	public void setOnlineUser(OnlineUser onlineUser) {
		if (onlineUser == null) {
			return;
		}
		session.setAttribute("onlineUser", onlineUser);
		this.onlineUser = onlineUser;
	}

	public Date getLastsubmission() {
		return lastsubmission;
	}

	public void setLastsubmission(Date lastsubmission) {
		if (lastsubmission == null) {
			return;
		}
		session.setAttribute("lastsubmission", lastsubmission);
		this.lastsubmission = lastsubmission;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<String> getHistories() {
		if (session != null && session.getAttribute("histories") != null) {
			this.histories = (ArrayList<String>) session.getAttribute("histories");
		}
		return this.histories;
	}

	public void setHistories(ArrayList<String> histories) {
		if (session == null || histories == null) {
			return;
		}
		this.histories = histories;
		session.setAttribute("histories", histories);
	}

	public void setHistories(String servletPath, String querystring) {
		if (servletPath.startsWith(LoginServlet.class.getAnnotation(WebServlet.class).urlPatterns()[0])
				|| servletPath.startsWith(LogoutServlet.class.getAnnotation(WebServlet.class).urlPatterns()[0])
				|| servletPath.startsWith(ShowSessionsServlet.class.getAnnotation(WebServlet.class).urlPatterns()[0])
				|| servletPath.startsWith("/Update") || servletPath.startsWith("/Insert")
				|| servletPath.startsWith("/api/") || servletPath.endsWith(".ajax") || servletPath.endsWith(".api")) {
			return;
		}
		ArrayList<String> histories = this.getHistories();
		String returnPage = servletPath + (querystring == null ? "" : "?" + querystring);
		System.out.println("1returnPages=" + histories);
		if (!returnPage.equals(histories.get(0))) {
			histories.remove(histories.size() - 1);
			System.out.println("2returnPages=" + histories);
			histories.add(0, servletPath + (querystring == null ? "" : "?" + querystring));
			System.out.println("3returnPages=" + histories);
			this.setHistories(histories);
		}
	}

	// @SuppressWarnings("unchecked")
	// public ArrayList<String> getReturnPages() {
	// if (session != null && session.getAttribute("returnPages") != null) {
	// this.returnPages = (ArrayList<String>)
	// session.getAttribute("returnPages");
	// }
	// return this.returnPages;
	// }

	@SuppressWarnings("unchecked")
	public void setHistories(Object histories) {
		if (session == null) {
			return;
		}
		if (histories == null || !(histories instanceof ArrayList)) {
			this.histories = new ArrayList<String>();
			this.histories.add("/");
			this.histories.add("/");
			session.setAttribute("histories", this.histories);
			return;
		}
		this.histories = (ArrayList<String>) histories;
		session.setAttribute("histories", this.histories);
	}

	/**
	 * 回到前一頁
	 * 
	 * @return
	 */
	public String getPreviousPage() {
		return this.getHistories().get(1);
	}

	/**
	 * 回到同一頁。
	 * 
	 * @return
	 */
	public String getCurrentPage() {
		return this.getHistories().get(0);
	}

}
