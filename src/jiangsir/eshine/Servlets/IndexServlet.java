package jiangsir.eshine.Servlets;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import jiangsir.eshine.DAOs.JobDAO;

@WebServlet(urlPatterns = { "/Index" })
public class IndexServlet extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException {
	JobDAO jobDao = new JobDAO();
	request.setAttribute("job", jobDao.getActiveJob());
	request.getRequestDispatcher("Index.jsp").forward(request, response);
    }
}
