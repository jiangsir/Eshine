package jiangsir.eshine.Servlets;

import java.io.IOException;
import java.util.Date;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import jiangsir.eshine.DAOs.ArticleDAO;
import jiangsir.eshine.DAOs.JobDAO;
import jiangsir.eshine.Objects.Job;
import jiangsir.eshine.Objects.Message;

@WebServlet(urlPatterns = { "/ShowArticles" })
public class ShowArticlesServlet extends HttpServlet {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    HttpServletRequest request;
    HttpServletResponse response;

    boolean isAvailable(Job job) throws ServletException, IOException {
	if (job.getId().intValue() == 0) {
	    Message message = new Message();
	    message.setType(Message.MessageType_ERROR);
	    message.setPlainTitle("目前沒有任何投稿作業！");
	    request.setAttribute("message", message);
	    request.getRequestDispatcher("Message.jsp").forward(request,
		    response);
	    return false;
	} else if (new Date().getTime() > job.getFinishtime().getTime()) {
	    Message message = new Message();
	    message.setType(Message.MessageType_ERROR);
	    message.setPlainTitle("投稿作業已結束！");
	    request.setAttribute("message", message);
	    request.getRequestDispatcher("Message.jsp").forward(request,
		    response);
	    return false;
	} else if (new Date().getTime() < job.getStarttime().getTime()) {
	    Message message = new Message();
	    message.setType(Message.MessageType_ERROR);
	    message.setPlainTitle("投稿作業尚未開始！");
	    request.setAttribute("message", message);
	    request.getRequestDispatcher("Message.jsp").forward(request,
		    response);
	    return false;
	}
	return true;
    }

    protected void doGet(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException {
	this.request = request;
	this.response = response;

	Job job = new JobDAO().getActiveJob();
	if (!this.isAvailable(job)) {
	    return;
	}

	String type = request.getParameter("type");
	request.setAttribute("articles",
		new ArticleDAO().getArticles(job.getId(), type));
	request.getRequestDispatcher("ShowArticles.jsp").forward(request,
		response);
    }
}
