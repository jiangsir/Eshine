package jiangsir.eshine.Servlets;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import jiangsir.eshine.DAOs.ArticleDAO;
import jiangsir.eshine.DAOs.JobDAO;
import jiangsir.eshine.Objects.Job;
import tw.jiangsir.Utils.Annotations.RoleSetting;

@WebServlet(urlPatterns = {"/ShowJob"})
@RoleSetting
public class ShowJobServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String jobid = request.getParameter("id");
		JobDAO jobDao = new JobDAO();
		Job job = jobDao.getJobById(jobid == null ? 0 : Integer.parseInt(jobid));
		request.setAttribute("job", job);
		String type = request.getParameter("type");
		request.setAttribute("articles", new ArticleDAO().getArticles(job.getId(), type));
		request.getRequestDispatcher("ShowJob.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}
}
