package jiangsir.eshine.Utils.Listeners;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;

import jiangsir.eshine.Utils.*;
import tw.jiangsir.Utils.Config.ApplicationScope;

@WebListener
public class InitializedListener implements ServletContextListener {
	Daemon daemon = null;

	/**
	 *
	 */
	public void contextInitialized(ServletContextEvent event) {
		// this.daemon = new Daemon();
		// Thread daemonthread = new Thread(daemon);
		// daemonthread.start();
		// qx 暫不進行應用程式初始化<br>
		// qx 可考慮在此將 properties.xml 全部讀入 Context Initialized
		ServletContext servletContext = event.getServletContext();
		ApplicationScope.setAllAttributes(servletContext);

		ENV.context = servletContext;
		ENV.LastContextRestart = new Date();
		ENV.setAPP_REAL_PATH(servletContext.getRealPath("/"));
		ENV.setAPP_NAME(servletContext.getContextPath());
		// ENV.setMyPropertiesPath(ENV.APP_REAL_PATH +
		// "META-INF/properties.xml");
		// ENV.resource = ResourceBundle.getBundle("resource");
		servletContext.setAttribute("OnlineUsers", new Hashtable<String, HttpSession>());
		new DataBase(event).initConnection(servletContext);
	}

	public void contextDestroyed(ServletContextEvent event) {
		ServletContext context = event.getServletContext();
		while (!ENV.ThreadPool.isEmpty()) {
			Thread thread = ENV.ThreadPool.get(ENV.ThreadPool.firstKey());
			thread.interrupt();
			// System.out.println("關閉 thread: " + thread);
			ENV.ThreadPool.remove(ENV.ThreadPool.firstKey());
		}
		// TODO_DONE! qx 結束連結

		Connection conn = (Connection) context.getAttribute("conn");
		context.removeAttribute("conn");
		try {
			if (conn != null && !conn.isClosed())
				conn.close();
			System.out.println(ENV.logHeader() + "資料庫連結關閉");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
