package jiangsir.eshine.Servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import jiangsir.eshine.DAOs.JobDAO;
import jiangsir.eshine.Objects.Job;
import tw.jiangsir.Utils.Annotations.RoleSetting;
import tw.jiangsir.Utils.Exceptions.DataException;

@WebServlet(urlPatterns = {"/InsertJob"})
@RoleSetting
public class InsertJobServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("jobs", new JobDAO().getJobs());
		// request.setAttribute("niandu",
		// Calendar.getInstance().get(Calendar.YEAR) - 1911);
		request.getRequestDispatcher("InsertJob.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Job job = new Job();
		job.setNiandu(Integer.parseInt(request.getParameter("niandu")));
		job.setTitle(request.getParameter("title"));
		job.setStarttime(request.getParameter("starttime"));
		job.setFinishtime(request.getParameter("finishtime"));
		job.setComment(request.getParameter("comment"));
		try {
			new JobDAO().insert(job);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataException(e);
		}
		response.sendRedirect("./" + AdminServlet.class.getAnnotation(WebServlet.class).urlPatterns()[0]);
	}
}
