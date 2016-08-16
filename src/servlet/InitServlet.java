package servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import thread.WeixinThread;

/**
 * Servlet implementation class InitServlet
 * 随tomcat启动而启动
 * 自动生成全局变量token、ticket供全服务器调用
 */
@WebServlet("/InitServlet")
public class InitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	
    public InitServlet() {
    	WeixinThread wThread = new WeixinThread();
    	new Thread(wThread).start();
    }

    
    
}
