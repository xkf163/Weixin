package servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import thread.WeixinThread;

/**
 * Servlet implementation class InitServlet
 * ��tomcat����������
 * �Զ�����ȫ�ֱ���token��ticket��ȫ����������
 */
@WebServlet("/InitServlet")
public class InitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	
    public InitServlet() {
    	WeixinThread wThread = new WeixinThread();
    	new Thread(wThread).start();
    }

    
    
}
