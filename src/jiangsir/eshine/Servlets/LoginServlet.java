package jiangsir.eshine.Servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jiangsir.eshine.DAOs.UserService;
import jiangsir.eshine.Objects.*;
import tw.jiangsir.Utils.Scopes.SessionScope;

@WebServlet(urlPatterns = {"/Login"})
public class LoginServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("Login.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);

		String account = request.getParameter("account");
		String passwd = request.getParameter("passwd");
		User user = new UserService().getUserByAccountPasswd(account, passwd);
		if (user != null) {
			SessionScope sessionScope = new SessionScope(session);
			sessionScope.setOnlineUser(new UserService().createOnlineUser(user.getId(), session));
			response.sendRedirect(request.getContextPath() + sessionScope.getPreviousPage());
			return;
		} else {
			request.getRequestDispatcher("Login.jsp").forward(request, response);
			return;
		}

	}
}
