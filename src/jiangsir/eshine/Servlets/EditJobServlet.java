package jiangsir.eshine.Servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import jiangsir.eshine.DAOs.JobDAO;
import jiangsir.eshine.Objects.Job;
import jiangsir.eshine.Utils.Utils;
import tw.jiangsir.Utils.Annotations.RoleSetting;
import tw.jiangsir.Utils.Exceptions.DataException;

@WebServlet(urlPatterns = {"/EditJob"})
@RoleSetting
public class EditJobServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("jobs", new JobDAO().getJobs());
		int jobid = Integer.parseInt(request.getParameter("id"));
		request.setAttribute("job", new JobDAO().getJobById(jobid));
		request.getRequestDispatcher("InsertJob.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int jobid = Integer.parseInt(request.getParameter("id"));
		Job job = new JobDAO().getJobById(jobid);
		job.setNiandu(Integer.parseInt(request.getParameter("niandu")));
		job.setTitle(request.getParameter("title"));
		job.setStarttime(Utils.parseDatetime(request.getParameter("starttime")));
		job.setFinishtime(Utils.parseDatetime(request.getParameter("finishtime")));
		job.setComment(request.getParameter("comment"));
		try {
			new JobDAO().update(job);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataException(e);
		}
		response.sendRedirect("./" + AdminServlet.class.getAnnotation(WebServlet.class).urlPatterns()[0]);
	}
}
