package jiangsir.eshine.Servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import jiangsir.eshine.DAOs.UserService;
import jiangsir.eshine.Objects.User;

@WebServlet(urlPatterns = {"/Logout"})
public class LogoutServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		String session_account = (String) session.getAttribute("session_account");
		User session_user = (User) session.getAttribute("UserObject");
		if (session_user == null) {
			// session_user = new User(session_account);
			session_user = new UserService().getUserByAccount(session_account);
		}

		session_user.Logout(session);
		response.sendRedirect("./");
	}
}
