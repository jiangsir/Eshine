package tw.jiangsir.Utils.Config;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;

import jiangsir.eshine.Objects.OnlineUser;
import jiangsir.eshine.Objects.User;

public class ApplicationScope {
	public static ServletContext servletContext = null;
	private static String built = null;
	private static File appRoot = null;
	private static HashMap<String, HttpSession> onlineSessions = new HashMap<String, HttpSession>();
	private static HashMap<String, OnlineUser> onlineUsers = new HashMap<String, OnlineUser>();
	private static HashMap<String, HttpServlet> urlpatterns = new HashMap<String, HttpServlet>();
	private static HashMap<User.ROLE, HashSet<Class<? extends HttpServlet>>> roleMap = new HashMap<User.ROLE, HashSet<Class<? extends HttpServlet>>>();
	// private static AppConfig appConfig = null;
	private static Hashtable<String, User> HashUsers = null;

	public static void setAllAttributes(ServletContext servletContext) {
		ApplicationScope.servletContext = servletContext;

		ApplicationScope.setBuilt();
		ApplicationScope.setAppRoot();
		ApplicationScope.setOnlineSessions(onlineSessions);
		ApplicationScope.setOnlineUsers(onlineUsers);
		ApplicationScope.setUrlpatterns(urlpatterns);
		ApplicationScope.setRoleMap(roleMap);
	}

	public static HashMap<String, HttpSession> getOnlineSessions() {
		return onlineSessions;
	}

	public static void setOnlineSessions(HashMap<String, HttpSession> onlineSessions) {
		ApplicationScope.onlineSessions = onlineSessions;
		ApplicationScope.servletContext.setAttribute("onlineSessions", onlineSessions);
	}

	public static HashMap<String, OnlineUser> getOnlineUsers() {
		return onlineUsers;
	}

	public static void setOnlineUsers(HashMap<String, OnlineUser> onlineUsers) {
		ApplicationScope.onlineUsers = onlineUsers;
		ApplicationScope.servletContext.setAttribute("onlineUsers", onlineUsers);
	}

	public static HashMap<String, HttpServlet> getUrlpatterns() {
		return urlpatterns;
	}

	public static void setUrlpatterns(HashMap<String, HttpServlet> urlpatterns) {
		ApplicationScope.urlpatterns = urlpatterns;
		ApplicationScope.servletContext.setAttribute("urlpatterns", urlpatterns);
	}

	public static HashMap<User.ROLE, HashSet<Class<? extends HttpServlet>>> getRoleMap() {
		return roleMap;
	}

	public static void setRoleMap(HashMap<User.ROLE, HashSet<Class<? extends HttpServlet>>> roleMap) {
		ApplicationScope.roleMap = roleMap;
		ApplicationScope.servletContext.setAttribute("roleMap", roleMap);
	}

	public static String getBuilt() {
		if (ApplicationScope.built == null) {
			setBuilt();
		}
		return ApplicationScope.built;
	}

	public static void setBuilt() {
		ApplicationScope.built = new SimpleDateFormat("yyMMdd")
				.format(new Date(ApplicationScope.getAppRoot().lastModified()));
		servletContext.setAttribute("built", ApplicationScope.built);
	}

	public static File getAppRoot() {
		if (ApplicationScope.appRoot == null) {
			setAppRoot();
		}
		return ApplicationScope.appRoot;
	}

	public static void setAppRoot() {
		ApplicationScope.appRoot = new File(servletContext.getRealPath("/"));
		ApplicationScope.servletContext.setAttribute("appRoot", appRoot);
	}

	/**
	 * 直接指定 AppRoot，在單機直接執行的時候使用。因此不具備 serveltContext
	 * 
	 * @param appRoot
	 */
	public static void setAppRoot(File appRoot) {
		ApplicationScope.appRoot = appRoot;
		// ApplicationScope.servletContext.setAttribute("appRoot", appRoot);
	}

	// public static AppConfig getAppConfig() {
	// return appConfig;
	// }
	//
	// public static void setAppConfig(AppConfig appConfig) {
	// ApplicationScope.appConfig = appConfig;
	// ApplicationScope.servletContext.setAttribute("appConfig", appConfig);
	// }

	public static Hashtable<String, User> getHashUsers() {
		return HashUsers;
	}

	public static void setHashUsers(Hashtable<String, User> hashUsers) {
		HashUsers = hashUsers;
		ApplicationScope.servletContext.setAttribute("CacheUsers", hashUsers);
	}

}
