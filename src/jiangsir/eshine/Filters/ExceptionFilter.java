package jiangsir.eshine.Filters;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;

import jiangsir.eshine.DAOs.*;
import jiangsir.eshine.Objects.OnlineUser;
import tw.jiangsir.Utils.Scopes.SessionScope;

@WebFilter(filterName = "ExceptionFilter", urlPatterns = {"/*"}, asyncSupported = true)
public class ExceptionFilter implements Filter {

	public void init(FilterConfig config) throws ServletException {
	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
		if (throwable == null) {
			System.out.println("throwable == null null");
			chain.doFilter(request, response);
			return;
		}
		String uri = request.getAttribute("javax.servlet.error.request_uri").toString();

		StackTraceElement[] trace;
		trace = throwable.getStackTrace();
		StringBuffer tracestring = new StringBuffer(5000);
		for (int i = 0; i < trace.length; i++) {
			tracestring.append(trace[i] + "\n");
		}

		HttpSession session = request.getSession();
		OnlineUser onlineUser = new SessionScope(session).getOnlineUser();
		if (onlineUser == null) {
			throw new javax.servlet.ServletException("session 無效，請稍候再試。 系統管理員敬上");
		}

		String s = tracestring.toString();
		if (!s.contains("SQL ERROR: INSERT INTO exceptions")) {
			new ExceptionDAO().insert_PSTMT(uri, onlineUser.getAccount(), request.getRemoteAddr(), throwable.toString(),
					s);
		}
		chain.doFilter(request, response);
	}

	public void destroy() {
	}
}
