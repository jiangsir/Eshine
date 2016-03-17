package jiangsir.eshine.Servlets;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import jiangsir.eshine.DAOs.JobDAO;

@WebServlet(urlPatterns = { "/DeleteJob" })
public class DeleteJobServlet extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException {
	int jobid = Integer.parseInt(request.getParameter("id"));
	new JobDAO().delete(jobid);
	response.sendRedirect("Admin");
    }

}
