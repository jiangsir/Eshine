package jiangsir.eshine.Servlets;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import jiangsir.eshine.DAOs.ArticleDAO;
import jiangsir.eshine.DAOs.JobDAO;
import jiangsir.eshine.Objects.Job;

@WebServlet(urlPatterns = { "/Admin" })
public class AdminServlet extends HttpServlet {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException {
	// int page = 1;
	// try {
	// page = Integer.parseInt(request.getParameter("page"));
	// } catch (Exception e) {
	// // TODO: handle exception
	// }
	JobDAO jobDao = new JobDAO();
	Job job = jobDao.getActiveJob();
	request.setAttribute("job", job);
	String type = request.getParameter("type");
	request.setAttribute("articles",
		new ArticleDAO().getArticles(job.getId(), type));
	request.setAttribute("jobs", jobDao.getJobs());

	request.getRequestDispatcher("Admin.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException {
    }
}
