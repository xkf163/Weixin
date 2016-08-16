package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import config.Config;
import thread.WeixinThread;
import util.MessageFactory;
import util.WeixinUtil;



public class PushMessageServlet extends HttpServlet {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		String openid = request.getParameter("openid");
//		openid = "oFS0jwhEJdivOpYQGI2bC3vpKV_M";
		String templateMessageType = request.getParameter("templateMessageType");
		
		PrintWriter out = response.getWriter();
		switch (templateMessageType) {
			case "requesting":
				String templateMessageJson  = MessageFactory.initTemplateMessage(openid,Config.MESSAGE_Templat_ID );
				int status = WeixinUtil.sendTemplateMessage(templateMessageJson);
				
				if(status == 0){
					out.print("<h1>模板消息已成功推送,请注意查收！</h1>");
				}else{
					out.print("<h1>模板消息推送失败！</h1><p>返回错误码:"+status+"</p>");
				}
			break;
			
			
			case "responsing":
			break;	
		}
		
		
		
	}

}
