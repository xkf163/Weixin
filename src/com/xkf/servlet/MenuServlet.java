package com.xkf.servlet;

/*
 * 
 */

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import net.sf.json.JSONObject;

import com.xkf.po.AccessToken;
import com.xkf.util.MessageUtil;
//import com.xkf.util.MessageUtil;
import com.xkf.util.WeixinUtil;

public class MenuServlet extends HttpServlet {

	public static String myToken;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
			req.setCharacterEncoding("UTF-8");
			resp.setCharacterEncoding("gb2312");
			
			PrintWriter out = resp.getWriter();
		
			String action = req.getParameter("action");
			if (action == null ||"".equals(action)){
				System.out.println("MenuServlet doGet action is null!exit!");
				out.print("MenuServlet doGet action is null!\n创建菜单失败!");
				return;
			}
			
			System.out.println("MenuServlet doGet action:"+action);
			
			String menu=null;
			int result=9;
	//		AccessToken token=null;
			myToken=AccessTokenThread.accessToken.getAccess_token();
		
		
		switch (action) {
		case "sendtempmsg":
			//String templateMessageString = MessageUtil.initTemplateMessage("oFS0jwhEJdivOpYQGI2bC3vpKV_M","这是模板消息");
			String ret_status = WeixinUtil.sendTemplateMessage(myToken, "oFS0jwhEJdivOpYQGI2bC3vpKV_M");
		
			System.out.println("发送模板消息结果是："+ret_status);
			break;
			
		case "createmenu":
//			2）创建菜单
			//获取access_token值
//			token = WeixinUtil.getAccessToken();
			
			
			menu =  JSONObject.fromObject(WeixinUtil.initMenu()).toString();
			System.out.println("开始创建菜单。。。。。。。。。。");
			System.out.println(menu);
			
			result = WeixinUtil.createMenu(myToken, menu);
			if(result == 0){
				System.out.println("菜单创建成功！");
			}else{
				System.out.println("菜单创建错误码："+result);
			}
			break;
			
		case "sendnewsmsg":
//			7）群发图文消息
			//获取access_token值
//			 token = WeixinUtil.getAccessToken();
			menu="群发图文消息成功！";
			result = WeixinUtil.sendAllNews(myToken,"06n6eSB4_uSkQZkUdbawSBWGz36yj5xMU0eQq8EqSONJdrsLlwDa_RL4uwpXvtcq");
			if(result == 0){
				System.out.println("群发图文消息成功！");
			}else{
				System.out.println("群发图文消息错误吗："+result);
				menu="群发图文消息错误吗："+result;
			}
			break;
			
		case "sendtextmsg":
			//5）群发文本消息
			
			menu="群发文本消息成功！";
			result = WeixinUtil.sendAlltext(myToken);
			if(result == 0){
				System.out.println("群发文本消息成功！");
			}else{
				System.out.println("群发文本消息错误吗："+result);
				menu="群发文本消息错误吗："+result;
			}
			
			break;
			
			
		default:
			break;
		}
		
		

		//1）删除菜单
//		String menu="菜单删除成功！<br/>";
//		int delresult = WeixinUtil.delMenu(token.getAccess_token());
//		if(delresult == 0){
//			out.print("菜单删除成功！<br/>");
//		}else{
//			out.print("菜单删除错误码："+delresult);
//			menu="菜单删除错误码："+delresult;
//		}
		


		
//		3）查询菜单
//		String menu=JSONObject.fromObject(WeixinUtil.queryMenu(token.getAccess_token())).toString();
//		System.out.println(menu);
		
		
//		4)上传图文消息
//		String articles = MessageUtil.initArticleMessage();
//		String menu = WeixinUtil.uploadNews(token.getAccess_token(), articles).toString();
//成功返回值
//{"type":"news","media_id":"06n6eSB4_uSkQZkUdbawSBWGz36yj5xMU0eQq8EqSONJdrsLlwDa_RL4uwpXvtcq","created_at":1446190137}
		
		

		
		

		
		
//		6)上传素材：图片.得到图片的media_id
//		String filePath = "c:/weixinimg.jpg";
//		String menu=WeixinUtil.upload(filePath, token.getAccess_token(), "thumb");
//	 返回的值：   mediaID = poUZoPJlntFAKDpyoANluSUiCHaDm2VsS1v6_7CQ-VAfuKHdRhAiN1sVIejBMhcP
//	 返回的值:  Thumb_media_id = Ft3uL5qN8_frADPj7XviPlqdDQ7ZO9tKR_wnCC-r51zMs1pZE4Qd9d43A5sqiUyk
		
		out.print(menu);
		

	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	
		
	}
}
