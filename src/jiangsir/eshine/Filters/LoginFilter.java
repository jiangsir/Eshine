package jiangsir.eshine.Filters;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;
import jiangsir.eshine.DAOs.UserService;
import jiangsir.eshine.Objects.Message;
import jiangsir.eshine.Objects.User;
import jiangsir.eshine.Utils.ENV;
import jiangsir.eshine.Utils.Utils;

@WebFilter(filterName = "LoginFilter", urlPatterns = {"/*"}, asyncSupported = true)
public class LoginFilter implements Filter {
	private String LoginURI;

	private String FilterName;

	public void init(FilterConfig config) throws ServletException {
		LoginURI = config.getServletContext().getInitParameter("LoginURI");
		FilterName = config.getFilterName();
		config.getServletContext().log(FilterName + "進入初始化... by qx");
	}

	@SuppressWarnings("unchecked")
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("進入 LoginFilter");
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		HttpSession session = request.getSession();
		// qx 測試在 filter 內部再來過濾 如 regex 的判斷
		String requestURI = request.getRequestURI();
		requestURI = requestURI.substring(requestURI.lastIndexOf('/') + 1);
		System.out.println("requestURI=" + requestURI);
		System.out.println("session=" + session);

		if (session != null) {
			String passed = (String) session.getAttribute("passed");
			String privilege = (String) session.getAttribute("session_privilege");
			String parseprivilege = Utils.parsePrivilege(privilege, requestURI);
			System.out.println(
					"passed=" + passed + ", session_privilege=" + privilege + ", parseprivilege=" + parseprivilege);
			if (privilege != null && !"allowed".equals(parseprivilege)) {
				// String[] param = { requestURI };
				// request.setAttribute("ResourceMessage_param", param);
				// request.setAttribute("ResourceMessage", parseprivilege);
				Message message = new Message();
				message.setType(Message.MessageType_ERROR);
				message.setPlainTitle("您沒有權限處理!!");
				request.setAttribute("message", message);
				request.getRequestDispatcher("Message.jsp").forward(request, response);
				return;
			} else if ("allowed".equals(parseprivilege) || requestURI.matches(".*\\..*")) {
				chain.doFilter(request, response);
				return;
			} else if ("passing".equals(passed)) {
				if (requestURI.equals("/" + ENV.APP_DIR + "/" + this.LoginURI)) {
					chain.doFilter(request, response);
					return;
				}
			} else {
				System.out.println(ENV.logHeader() + "LoginFilter 進入 Unknown");
				// HashMap Userinfo = (HashMap)
				// session.getAttribute("Userinfo");
				String session_account = (String) session.getAttribute("session_account");
				User user = (User) session.getAttribute("UserObject");
				if (user == null) {
					// user = new User(session_account);
					user = new UserService().getUserByAccount(session_account);
				}
				System.out.println(ENV.logHeader() + "UserObject=" + user);
				System.out.println(ENV.logHeader() + "privilege=" + privilege);
				System.out.println(ENV.logHeader() + "passeprivilege=" + parseprivilege);
				System.out.println(ENV.logHeader() + "passed=" + passed);
			}
			session.removeAttribute("passed");
		}
		// StringBuffer requestURL = new StringBuffer(5000);
		// requestURL.append(request.getRequestURL());
		// qx 把經過 filter 的頁面再導回原來的頁面, 就不用處理參數傳遞的問題了
		// requestURL.append(session.getAttribute("CurrentPage"));
		// String query = request.getQueryString();
		// if (query != null) {
		// requestURL.append("?" + query); // qx 加了 ? 會否造成影響?
		// }
		// request.setAttribute("originalURI", requestURL.toString());
		// request.setAttribute("parammap", request.getParameterMap());
		// qx 在 filter 中把 前一頁的 parammap 放入 session 裡, 以便通過 Login之後取用
		// qx 暫時廢止
		// HashMap parammap = (HashMap) request.getParameterMap();
		// if (parammap.size() != 0) {
		// session.setAttribute("parammap", parammap.clone());
		// }
		if (request.getQueryString() != null) {
			requestURI += "?" + request.getQueryString();
		}
		session.setAttribute("OriginalURI", requestURI);
		request.getRequestDispatcher(LoginURI).forward(request, response);
	}

	public void destroy() {

	}

}
