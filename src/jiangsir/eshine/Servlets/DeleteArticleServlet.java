package jiangsir.eshine.Servlets;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import jiangsir.eshine.DAOs.ArticleDAO;
import tw.jiangsir.Utils.Annotations.RoleSetting;
import tw.jiangsir.Utils.Scopes.SessionScope;

@WebServlet(urlPatterns = {"/DeleteArticle.api"})
@RoleSetting
public class DeleteArticleServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int articleid = Integer.parseInt(request.getParameter("id"));
		try {
			new ArticleDAO().delete((long) articleid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		response.sendRedirect("./" + new SessionScope(request).getCurrentPage());
	}

}
