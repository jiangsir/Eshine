package jiangsir.eshine.Servlets;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import jiangsir.eshine.DAOs.JobDAO;
import jiangsir.eshine.Objects.Job;
import jiangsir.eshine.Utils.Utils;

@WebServlet(urlPatterns = { "/InsertJob" })
public class InsertJobServlet extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException {
	request.setAttribute("jobs", new JobDAO().getJobs());
	request.getRequestDispatcher("InsertJob.jsp")
		.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException {

	Job job = new Job();
	job.setNiandu(Integer.parseInt(request.getParameter("niandu")));
	job.setTitle(request.getParameter("title"));
	job.setStarttime(Utils.parseDatetime(request.getParameter("starttime")));
	job.setFinishtime(Utils.parseDatetime(request
		.getParameter("finishtime")));
	job.setComment(request.getParameter("comment"));
	new JobDAO().insert_PSTMT(job);
	response.sendRedirect("Admin");
    }
}
