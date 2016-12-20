package jiangsir.eshine.Servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import jiangsir.eshine.DAOs.ArticleDAO;
import jiangsir.eshine.DAOs.JobDAO;
import jiangsir.eshine.Objects.Article;
import jiangsir.eshine.Objects.Job;
import jiangsir.eshine.Objects.Message;
import tw.jiangsir.Utils.Exceptions.DataException;
import jiangsir.eshine.DAOs.UpfileDAO;
import jiangsir.eshine.Objects.Upfile;

@MultipartConfig(maxFileSize = 20 * 1024 * 1024, maxRequestSize = 50 * 1024 * 1024)
@WebServlet(urlPatterns = {"/InsertArticle"})
public class InsertArticleServlet extends HttpServlet {
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
			request.getRequestDispatcher("Message.jsp").forward(request, response);
			return false;
		} else if (new Date().getTime() > job.getFinishtime().getTime()) {
			Message message = new Message();
			message.setType(Message.MessageType_ERROR);
			message.setPlainTitle("投稿作業已結束！");
			request.setAttribute("message", message);
			request.getRequestDispatcher("Message.jsp").forward(request, response);
			return false;
		} else if (new Date().getTime() < job.getStarttime().getTime()) {
			Message message = new Message();
			message.setType(Message.MessageType_ERROR);
			message.setPlainTitle("投稿作業尚未開始！");
			request.setAttribute("message", message);
			request.getRequestDispatcher("Message.jsp").forward(request, response);
			return false;
		}
		return true;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.request = request;
		this.response = response;

		Job job = new JobDAO().getActiveJob();
		if (!this.isAvailable(job)) {
			return;
		}
		request.setAttribute("maxFileSize", this.getClass().getAnnotation(MultipartConfig.class).maxFileSize());
		request.setAttribute("article", new Article());
		request.setAttribute("job", job);
		request.getRequestDispatcher("InsertArticle.jsp").forward(request, response);
	}

	public String getFilename(Part part) {
		String header = part.getHeader("Content-Disposition");
		String filename = header.substring(header.indexOf("filename=\"") + 10, header.lastIndexOf("\""));
		return filename;
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.request = request;
		this.response = response;
		Job job = new JobDAO().getActiveJob();
		if (!this.isAvailable(job)) {
			return;
		}

		int articleid;
		try {

			Article newarticle = new Article();
			newarticle.setAuthor(request.getParameter("author"));
			newarticle.setClassname(request.getParameter("classname"));
			newarticle.setGrade(request.getParameter("grade"));
			newarticle.setType(request.getParameter("type"));
			newarticle.setTitle(request.getParameter("title"));
			newarticle.setEmail(request.getParameter("email"));
			newarticle.setComment(request.getParameter("comment"));
			newarticle.setJobid(request.getParameter("jobid"));

			Part filepart = request.getPart("file");
			if ("".equals(this.getFilename(filepart))) {
				throw new DataException("請選擇要上傳的檔案！");
			}
			articleid = new ArticleDAO().insert(newarticle);
			newarticle.setId(articleid);
			// 將檔案上傳
			// uploader.uploadFile(item, newarticle.getINNER_PATH(), newarticle
			// .getINNER_FILENAME());

			for (Part part : request.getParts()) {
				if ("file".equals(part.getName())) {
					Upfile newupfile = new Upfile();
					newupfile.setArticleid(articleid);
					newupfile.setFilename(this.getFilename(part));
					newupfile.setFiletype(part.getContentType());
					newupfile.setFilesize(part.getSize());
					newupfile.setBinary(part.getInputStream());
					int upfileid = new UpfileDAO().insert(newupfile);
					newupfile.setId(upfileid);
				}
			}
			// for (FileItem item : uploader.getFileItems("file")) {
			// if (item.getName() == null || item.getName().equals("")) {
			// continue;
			// }
			// // IE 會傳上完整的路徑，不好
			// String filename = item.getName();
			// filename = filename.substring(filename.lastIndexOf("\\") + 1);
			//
			// // 再 加入資料庫記錄
			// Upfile newupfile = new Upfile();
			// newupfile.setArticleid(articleid);
			// newupfile.setFilename(filename);
			// newupfile.setFiletype(item.getContentType());
			// newupfile.setFilesize((long) item.getSize());
			// newupfile.setBinary(item.getInputStream());
			// int upfileid = new UpfileDAO().insert(newupfile);
			// newupfile.setId(upfileid);
			// }

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataException(e);
		} catch (IllegalStateException e) {
			e.printStackTrace();
			if (e.getLocalizedMessage().contains("FileSizeLimitExceededException")) {
				throw new DataException(
						"上傳檔案超過上限(" + this.getClass().getAnnotation(MultipartConfig.class).maxFileSize() / 1024 / 1024
								+ "MB)！請縮小。");
			}
			throw new DataException(e);
		}
		response.sendRedirect("./" + ShowArticlesServlet.class.getAnnotation(WebServlet.class).urlPatterns()[0]);
	}
}
