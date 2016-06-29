package jiangsir.eshine.Servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import jiangsir.eshine.DAOs.JobDAO;
import tw.jiangsir.Utils.Annotations.RoleSetting;

@WebServlet(urlPatterns = {"/DeleteJob"})
@RoleSetting
public class DeleteJobServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int jobid = Integer.parseInt(request.getParameter("id"));
		try {
			new JobDAO().delete(jobid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		response.sendRedirect("./" + AdminServlet.class.getAnnotation(WebServlet.class).urlPatterns()[0]);
	}

}
