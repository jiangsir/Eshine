package jiangsir.eshine.Servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import jiangsir.eshine.DAOs.ArticleDAO;
import jiangsir.eshine.DAOs.JobDAO;
import jiangsir.eshine.Objects.Article;
import jiangsir.eshine.Objects.Job;
import jiangsir.eshine.Objects.Message;
import jiangsir.eshine.Utils.MyException;
import jiangsir.eshine.Utils.Uploader;
import jiangsir.eshine.DAOs.UpfileDAO;
import jiangsir.eshine.Objects.Upfile;
import org.apache.commons.fileupload.FileItem;

@WebServlet(urlPatterns = { "/InsertArticle" })
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

	request.setAttribute("article", new Article());
	request.setAttribute("job", job);
	request.getRequestDispatcher("InsertArticle.jsp").forward(request,
		response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException {
	this.request = request;
	this.response = response;
	Job job = new JobDAO().getActiveJob();
	if (!this.isAvailable(job)) {
	    return;
	}

	Uploader uploader;
	int articleid;
	try {
	    uploader = new Uploader(request, response);

	    Article newarticle = new Article();
	    newarticle.setAuthor(uploader.getFormField("author"));
	    newarticle.setClassname(uploader.getFormField("classname"));
	    // newarticle.setClassnum(Integer.parseInt(uploader
	    // .getFormField("classnum")));
	    newarticle
		    .setGrade(Integer.parseInt(uploader.getFormField("grade")));
	    newarticle.setType(uploader.getFormField("type"));
	    newarticle.setTitle(uploader.getFormField("title"));
	    newarticle.setEmail(uploader.getFormField("email"));
	    newarticle.setComment(uploader.getFormField("comment"));
	    // Iterator<?> it = uploader.getFileItems("file").iterator();
	    // FileItem item = (FileItem) it.next();
	    // if (item.getName() == null || item.getName().equals("")) {
	    // throw new MyException("您沒有上傳檔案，請將您的投稿文章製作成檔案的形式並且上傳！");
	    // }

	    // newarticle.setFilename(item.getName());
	    // newarticle.setFiletype(item.getContentType());
	    // newarticle.setFilesize(item.getSize());
	    newarticle
		    .setJobid(Integer.parseInt(uploader.getFormField("jobid")));
	    articleid = new ArticleDAO().insert_PSTMT(newarticle);
	    newarticle.setId(articleid);
	    // 將檔案上傳
	    // uploader.uploadFile(item, newarticle.getINNER_PATH(), newarticle
	    // .getINNER_FILENAME());

	    for (FileItem item : uploader.getFileItems("file")) {
		if (item.getName() == null || item.getName().equals("")) {
		    continue;
		}
		// IE 會傳上完整的路徑，不好
		String filename = item.getName();
		filename = filename.substring(filename.lastIndexOf("\\") + 1);

		// 再 加入資料庫記錄
		Upfile newupfile = new Upfile();
		newupfile.setArticleid(articleid);
		newupfile.setFilename(filename);
		newupfile.setFiletype(item.getContentType());
		newupfile.setFilesize((long) item.getSize());
		newupfile.setBinary(item.getInputStream());

		// newupfile.setBinary(item.getInputStream());
		int upfileid = new UpfileDAO().insert(newupfile);
		newupfile.setId(upfileid);
		// 檔案是否就不用上傳了。
		// FileUploader.write2file(item, new
		// File(newupfile.getINNER_PATH(),
		// newupfile.getINNER_FILENAME()));
	    }

	} catch (MyException e) {
	    e.printStackTrace();
	    Message message = new Message();
	    message.setType(Message.MessageType_ALERT);
	    message.setPlainTitle(e.getLocalizedMessage());
	    request.setAttribute("message", message);
	    request.getRequestDispatcher("./Message.jsp").forward(request,
		    response);
	    return;
	} catch (SQLException e) {
	    e.printStackTrace();
	    Message message = new Message();
	    message.setType(Message.MessageType_ALERT);
	    message.setPlainTitle(e.getLocalizedMessage());
	    request.setAttribute("message", message);
	    request.getRequestDispatcher("./Message.jsp").forward(request,
		    response);
	    return;
	}
	// Message message = new Message();
	// message.setType(Message.MessageType_INFOR);
	// message.setLinks("ShowArticles", "看其他已投稿件");
	// message.setPlainTitle("恭喜您已成功投稿完成(#" + articleid + ")");
	// request.setAttribute("message", message);
	// request.getRequestDispatcher("./Message.jsp")
	// .forward(request, response);
	response.sendRedirect("ShowArticles");
    }
}
