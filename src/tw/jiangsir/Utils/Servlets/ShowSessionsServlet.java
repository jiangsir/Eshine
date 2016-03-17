package tw.jiangsir.Utils.Servlets;

import java.io.IOException;
import java.util.Enumeration;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import jiangsir.eshine.Objects.Message;

@WebServlet(urlPatterns = { "/ShowSessions" })
public class ShowSessionsServlet extends HttpServlet {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException {
	HttpSession session = request.getSession(false);
	String usergroup = (String) session.getAttribute("session_usergroup");
	// if ("GroupAdmin".equals(usergroup)) {
	// String account = User.parseAccount(request.getParameter("account"));
	// Hashtable OnlineUsers = (Hashtable) ENV.context
	// .getAttribute("OnlineUsers");
	// session = (HttpSession) OnlineUsers.get(new User(account)
	// .getSessionid());
	// }

	Message message = new Message();
	String text = "";
	if (session == null) {
	    text = "目前 session = null";
	} else {
	    Enumeration enumeration = session.getAttributeNames();
	    while (enumeration.hasMoreElements()) {
		String name = enumeration.nextElement().toString();
		text += name + " = " + session.getAttribute(name) + "<br>";
	    }
	}
	if ("".equals(text)) {
	    text += "Session 內沒有任何資料";
	}
	message.setPlainTitle("列出所有的 sessions");
	message.setPlainMessage(text);
	request.setAttribute("message", message);
	request.getRequestDispatcher("/Message.jsp").forward(request, response);
    }
}
