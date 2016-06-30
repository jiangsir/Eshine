package jiangsir.eshine.Utils.Listeners;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.WebListener;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import jiangsir.eshine.Utils.*;
import tw.jiangsir.Utils.Scopes.ApplicationScope;

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
		try {
			ServletContext servletContext = event.getServletContext();
			ApplicationScope.setAllAttributes(servletContext);

			Map<String, ? extends ServletRegistration> registrations = servletContext.getServletRegistrations();
			for (String key : registrations.keySet()) {
				String servletClassName = registrations.get(key).getClassName();
				WebServlet webServlet;
				try {
					if (Class.forName(servletClassName).newInstance() instanceof HttpServlet) {
						HttpServlet httpServlet = (HttpServlet) Class.forName(servletClassName).newInstance();
						webServlet = httpServlet.getClass().getAnnotation(WebServlet.class);
						if (webServlet != null) {
							for (String urlpattern : webServlet.urlPatterns()) {
								ApplicationScope.getUrlpatterns().put(urlpattern, httpServlet);
							}
						}
					}
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
			for (String urlpattern : ApplicationScope.getUrlpatterns().keySet()) {
				System.out.println(
						"urlpattern=" + urlpattern + ", servlet=" + ApplicationScope.getUrlpatterns().get(urlpattern));
			}
			servletContext.setAttribute("urlpatterns", ApplicationScope.getUrlpatterns());

			ENV.context = servletContext;
			ENV.LastContextRestart = new Date();
			ENV.setAPP_REAL_PATH(servletContext.getRealPath("/"));
			ENV.setAPP_NAME(servletContext.getContextPath());
			// ENV.setMyPropertiesPath(ENV.APP_REAL_PATH +
			// "META-INF/properties.xml");
			// ENV.resource = ResourceBundle.getBundle("resource");
			// servletContext.setAttribute("OnlineUsers", new Hashtable<String,
			// HttpSession>());
			// new DataBase(event).initConnection(servletContext);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
