package jiangsir.eshine.Servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import jiangsir.eshine.Objects.OnlineUser;
import tw.jiangsir.Utils.Scopes.SessionScope;

@WebServlet(urlPatterns = {"/Logout"})
public class LogoutServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		//
		OnlineUser onlineUser = new SessionScope(session).getOnlineUser();
		if (onlineUser != null) {
			onlineUser.doLogout();
		}
		response.sendRedirect("./");
	}
}
