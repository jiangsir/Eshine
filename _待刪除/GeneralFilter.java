package jiangsir.eshine.Filters;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;

import jiangsir.eshine.Objects.Message;
import jiangsir.eshine.Utils.MyProperties;

@WebFilter(filterName = "GeneralFilter", urlPatterns = { "/*" }, asyncSupported = true)
public class GeneralFilter implements Filter {

    public void init(FilterConfig config) throws ServletException {

    }

    /**
     * GeneralFilter 過濾全部的頁面，包含 .jsp, .css, doGET, doPOST 等
     */
    public void doFilter(ServletRequest req, ServletResponse res,
	    FilterChain chain) throws IOException, ServletException {
	HttpServletRequest request = (HttpServletRequest) req;
	HttpServletResponse response = (HttpServletResponse) res;
	String requestURI = request.getRequestURI();
	requestURI = requestURI.substring(requestURI.lastIndexOf('/') + 1);
	// 20091207 解決 IE 暫存問題
	response.setHeader("Cache-Control", "no-cache"); // HTTP 1.1
	// or response.setHeader("Cache-Control","no-store");//HTTP 1.1
	response.setHeader("Pragma", "no-cache"); // HTTP 1.0
	response.setDateHeader("Expires", 0); // prevents caching at the
	// proxy

	if (requestURI.matches(".*\\.[Cc][Ss][Ss]$")
		|| requestURI.matches(".*\\.[Jj][Ss]$")
		|| requestURI.matches(".*\\.[Gg][Ii][Ff]$")
		|| requestURI.matches(".*\\.[Pp][Nn][Gg]$")
		|| requestURI.matches(".*\\.[Hh][Tt][Mm]$")
		|| requestURI.matches(".*\\.[Hh][Tt][Mm][Ll]$")
		|| requestURI.matches(".*\\.[Tt][Xx][Tt]$")
		|| requestURI.matches(".*Include.*")
		|| requestURI.matches(".*Login$")
		|| requestURI.matches(".*Logout$")
		|| requestURI.matches(".*Admin$")) {
	    chain.doFilter(request, response);
	    return;
	}

	MyProperties myprop = new MyProperties();
	if (!"yes".equals(myprop.getProperty("IS_SYSTEMOPEN"))) {
	    Message message = new Message();
	    message.setType(Message.MessageType_ERROR);
	    message.setPlainTitle(myprop.getProperty("IS_SYSTEMOPEN"));
	    request.setAttribute("message", message);
	    request.getRequestDispatcher("Message.jsp").forward(request,
		    response);
	    return;
	}

	chain.doFilter(request, response);

	HttpSession session = request.getSession();
	String CurrentPage = "";
	if ("GET".equals(request.getMethod()) && !requestURI.matches(".*\\..*")
		&& !requestURI.matches(".*SystemClosed.jsp$")
		&& !requestURI.matches(".*ShowSessions$")
		&& !requestURI.matches(".*Download$")
		&& !requestURI.matches(".*Logout.*")
		&& !requestURI.matches(".*Login.*")) {
	    // qx 為何 Login 要記錄? 暫時 不把 Login 列入 PreviousFilter 觀察看看
	    CurrentPage = (String) session.getAttribute("CurrentPage");
	    if (request.getQueryString() != null) {
		requestURI += "?" + request.getQueryString();
	    }

	    if (!requestURI.equals(CurrentPage)) {
		session.setAttribute("PreviousPage",
			session.getAttribute("CurrentPage"));
		session.setAttribute("CurrentPage", requestURI);
	    }
	}
    }

    public void destroy() {

    }

}
