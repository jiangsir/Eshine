package jiangsir.eshine.Servlets;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import jiangsir.eshine.DAOs.ArticleDAO;
import jiangsir.eshine.DAOs.UpfileDAO;
import jiangsir.eshine.Objects.*;
import tw.jiangsir.Utils.Scopes.SessionScope;

@WebServlet(urlPatterns = {"/Download"})
public class DownloadServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private HttpServletRequest request;

	private HttpServletResponse response;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// qx 記錄下誰 Download，增加一個 table downloads
		this.request = request;
		this.response = response;
		int articleid = Integer.parseInt(request.getParameter("articleid"));
		// int upfileid = Integer.parseInt(request.getParameter("upfileid"));
		if (articleid < 1410) {
			this.doLocalDownload(articleid);
		} else {
			int upfileid = new ArticleDAO().getArticleById(articleid).getUpfiles().get(0).getId();
			this.doBlobDownload(upfileid);
		}
		return;
	}

	/**
	 * 檔案下載的權限完全開放，任何人都可以下載
	 * 
	 * @return
	 */
	public boolean isAccessable() {
		return true;
	}

	public void doBlobDownload(int upfileid) {
		ServletOutputStream out = null;

		Upfile upfile = new UpfileDAO().getUpfile(upfileid);
		try {
			out = response.getOutputStream();
			// response.setContentType("application/octet-stream");
			response.setContentType(upfile.getFiletype());
			String filename = new String(upfile.getFilename().getBytes("Big5"), "ISO8859_1");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
			// stream = new FileInputStream(file);
			InputStream is = new UpfileDAO().getUpfileInputStream(upfileid);
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = is.read(buffer, 0, 8192)) != -1) { // writeatserverside
				out.write(buffer, 0, bytesRead);
			}
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		new UpfileDAO().updateHitnum(upfileid);
	}

	/**
	 * 下載系統內部檔案，該檔案可以不放在 webapps 內，可以增加檔案的安全性
	 * 
	 * @throws IOException
	 * @throws ServletException
	 */
	private void doLocalDownload(int articleid) {
		Article article = new ArticleDAO().getArticleById(articleid);
		BufferedInputStream in = null;
		ServletOutputStream out = null;
		FileInputStream stream = null;
		File file = new File(article.getINNER_PATH(), article.getINNER_FILENAME());
		if (!file.exists()) {
			OnlineUser onlineUser = new SessionScope(request).getOnlineUser();
			// ExceptionDAO exceptionDao = new ExceptionDAO();
			String exception = "articleid=" + articleid + "\n";
			exception += "filepath=" + file.getPath() + "\n";
			// exceptionDao.insert_PSTMT(request.getRequestURI(),
			// onlineUser.getAccount(), request.getRemoteAddr(),
			// "檔案不存在！", exception);
			Message message = new Message();
			message.setType(Message.MessageType_ERROR);
			message.setPlainTitle("檔案不存在！");
			request.setAttribute("message", message);
			try {
				request.getRequestDispatcher("Message.jsp").forward(request, response);
			} catch (ServletException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return;
		}
		try {
			out = response.getOutputStream();
			response.setContentType("application/octet-stream");
			String filename = article.getId() + "_" + article.getTitle() + "_" + article.getFilename();
			filename = new String(filename.getBytes("Big5"), "ISO8859_1");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
			System.out.println("filepath=" + file.getPath());
			stream = new FileInputStream(file);

			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) { // writeatserverside
				out.write(buffer, 0, bytesRead);
			}
			out.flush();
			out.close();
		} catch (IOException e) {

		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}
}
