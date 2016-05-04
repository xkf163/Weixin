package com.xkf.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xkf.util.MessageUtil;
import com.xkf.util.WeixinUtil;



public class PushMessageServlet extends HttpServlet {

	public static final String MESSAGE_Templat_ID="mivZadaQLmVtX0gp8Y50FRxiX0k9CZKMRZsYEar9uzE";
	public static String myToken;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("push doget");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String openid = request.getParameter("openid");
		openid = "oFS0jwhEJdivOpYQGI2bC3vpKV_M";
		String templateMessageType = request.getParameter("templateMessageType");
		myToken=AccessTokenThread.accessToken.getAccess_token();
		PrintWriter out = response.getWriter();
		System.out.println(templateMessageType);
		switch (templateMessageType) {
			case "requesting":
				String templateMessageJson  = MessageUtil.initTemplateMessage(openid,MESSAGE_Templat_ID );
				String status = WeixinUtil.sendTemplateMessage(myToken, templateMessageJson);
				out.print("PushMessage requesting Status："+status);
				
				
			break;
			
			
			case "responsing":
			break;	
		}
		
		
		
	}

}
