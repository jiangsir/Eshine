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
		// CurrentUser currentUser = SessionFactory.getCurrentUser(session);
		System.out.println("Logout");
		OnlineUser onlineUser = new SessionScope(session).getOnlineUser();
		onlineUser.doLogout();
		response.sendRedirect("./");
	}
}
