package tw.jiangsir.Utils.Scopes;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;

import jiangsir.eshine.DAOs.AppConfigService;
import jiangsir.eshine.Objects.AppConfig;
import jiangsir.eshine.Objects.OnlineUser;
import jiangsir.eshine.Objects.User;

public class ApplicationScope {
	public static ServletContext servletContext = null;
	private static String version = "Undefined Version";
	private static String built = "Undefined BuiltNumber";
	private static File appRoot = null;
	private static AppConfig appConfig = null;

	private static HashMap<String, HttpSession> onlineSessions = new HashMap<String, HttpSession>();
	private static HashMap<String, OnlineUser> onlineUsers = new HashMap<String, OnlineUser>();
	private static HashMap<String, HttpServlet> urlpatterns = new HashMap<String, HttpServlet>();
	private static HashMap<User.ROLE, HashSet<Class<? extends HttpServlet>>> roleMap = new HashMap<User.ROLE, HashSet<Class<? extends HttpServlet>>>();
	// private static AppConfig appConfig = null;
	private static Hashtable<String, User> HashUsers = null;

	public static void setAllAttributes(ServletContext servletContext) {
		ApplicationScope.servletContext = servletContext;

		ApplicationScope.setAppRoot();
		ApplicationScope.setVersion();
		ApplicationScope.setBuilt();
		ApplicationScope.setOnlineSessions(onlineSessions);
		ApplicationScope.setOnlineUsers(onlineUsers);
		ApplicationScope.setUrlpatterns(urlpatterns);
		ApplicationScope.setRoleMap(roleMap);
		ApplicationScope.setAppConfig(new AppConfigService().getAppConfig());
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
	public static File getAppRoot() {
		return appRoot;
	}

	/**
	 * 直接指定 AppRoot，在單機直接執行的時候使用。因此不具備 serveltContext
	 * 
	 * @param appRoot
	 */
	public static void setAppRoot(File appRoot) {
		ApplicationScope.appRoot = appRoot;
		// servletContext.setAttribute("appRoot", appRoot);
	}

	public static void setAppRoot() {
		ApplicationScope.appRoot = new File(servletContext.getRealPath("/"));
		ApplicationScope.servletContext.setAttribute("appRoot", appRoot);
	}

	/**
	 * 取得目前系統的版本。
	 */
	public static String getVersion() {
		if (ApplicationScope.version == null) {
			setVersion();
		}
		return ApplicationScope.version;
	}

	/**
	 * 取得目前系統的版本。
	 */
	public static void setVersion() {
		try {
			ApplicationScope.version = FileUtils
					.readFileToString(new File(ApplicationScope.appRoot + File.separator + "META-INF", "Version.txt"))
					.trim();
		} catch (IOException e) {
			e.printStackTrace();
			// ApplicationScope.version = "";
		}
		servletContext.setAttribute("version", ApplicationScope.version);
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

	public static AppConfig getAppConfig() {
		return appConfig;
	}

	public static void setAppConfig(AppConfig appConfig) {
		ApplicationScope.appConfig = appConfig;
		servletContext.setAttribute("appConfig", appConfig);
	}
}
