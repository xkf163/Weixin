package com.xkf.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.dom4j.DocumentException;

import com.xkf.po.AccessToken;
import com.xkf.po.TextMessage;
import com.xkf.util.CheckUtil;
import com.xkf.util.MessageUtil;
import com.xkf.util.WeixinUtil;

public class WeixinServlet extends HttpServlet {
	
	private static String myToken;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		
		String signature = req.getParameter("signature");
		String timestamp = req.getParameter("timestamp");
		String nonce = req.getParameter("nonce");
		String echostr = req.getParameter("echostr");

		PrintWriter out = resp.getWriter();
		if(CheckUtil.checkSignature(signature, timestamp, nonce)){
			out.print(echostr);
		}
		
		
		
		
//		
//		String action = req.getParameter("action");
//		if ("push".equals(action)){
//			
//			String toUserName = req.getParameter("openid");
//			//out.print("PushMessageServlet doPost openid:"+toUserName);
//			//toUserName="oFS0jwulxzentP4nt_frMot0VYD4";
//			String message = MessageUtil.initTextMessage("gh_a01627dbdba9",toUserName,"申请成功");
//			out.print(message);
//			out.close();
//			
//		}
		
	}
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		System.out.println("dopost");
		
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
	
		if(myToken==null){
//				AccessToken token = WeixinUtil.getAccessToken();
//				myToken=token.getAccess_token();				
			myToken=AccessTokenThread.accessToken.getAccess_token();
			System.out.println("token is not exist,get new now!");
		}else{
			System.out.println("token is exist:"+myToken);
		}
		PrintWriter out = resp.getWriter();

		
		
		
		try {
			Map<String, String> map = MessageUtil.xmlToMap(req);
			String toUserName = map.get("ToUserName");
			String fromUserName = map.get("FromUserName");
//			String createTime = map.get("CreateTime");
			String msgType = map.get("MsgType");
			String content = map.get("Content");
//			String msgId = map.get("MsgId");
			
//			 <xml>
//			 <ToUserName><![CDATA[toUser]]></ToUserName>
//			 <FromUserName><![CDATA[fromUser]]></FromUserName> 
//			 <CreateTime>1348831860</CreateTime>
//			 <MsgType><![CDATA[text]]></MsgType>
//			 <Content><![CDATA[this is a test]]></Content>
//			 <MsgId>1234567890123456</MsgId>
//			 </xml>
		
			System.out.println("--->当前的消息类型是msgType:"+msgType);
			
			String message = null;
			if(MessageUtil.MESSAGE_TEXT.equals(msgType)){				
				if("1".equals(content)){
					message = MessageUtil.initTextMessage(toUserName, fromUserName, MessageUtil.oneMsg());
				}else if("2".equals(content)){
					message = MessageUtil.initTextMessage(toUserName, fromUserName, MessageUtil.twoMsg());
				}else if("3".equals(content)){
					message = MessageUtil.initNewsMessage(toUserName, fromUserName);
				}else if("4".equals(content)){
					JSONObject jsonObject=WeixinUtil.getUserInfo(myToken, fromUserName);
					message=MessageUtil.initTextMessage(toUserName, fromUserName, jsonObject.toString());
//				}else if("?".equals(content) || "？".equals(content)){
				}else{
					message = MessageUtil.initTextMessage(toUserName, fromUserName, MessageUtil.subscibeMsg());
				}
				
//				TextMessage textMessage = new TextMessage();
//				textMessage.setFromUserName(toUserName);
//				textMessage.setToUserName(fromUserName);
//				textMessage.setMsgType("text");
//				textMessage.setCreateTime(new Date().getTime());
//				textMessage.setContent("您发送的消息是:"+content);				
//				message = MessageUtil.textMessageToXML(textMessage);
//				System.out.println(message);
				
			}else if(MessageUtil.MESSAGE_EVENT.equals(msgType)){
				String eventType = map.get("Event");
				System.out.println("\neventType:"+eventType);
				if (MessageUtil.MESSAGE_SUBSCRIBE.equals(eventType)){
					//用户关注动作后返回的内容
					message = MessageUtil.initTextMessage(toUserName, fromUserName, MessageUtil.subscibeMsg());
				}else if (MessageUtil.MESSAGE_CLICK.equals(eventType)){					
					// 1）事件KEY值，与创建自定义菜单时指定的KEY值对应  
                    String eventKey = map.get("EventKey");  
                    if (eventKey.equals("11")) {  
//                    	message = MessageUtil.initNewsMessage(toUserName, fromUserName);
//                    	message = "菜单1被点击！";  
//                    	message=MessageUtil.initTextMessage(toUserName, fromUserName, message);
                    	//获取当前用户信息并推送给用户
                    	JSONObject jsonObject=WeixinUtil.getUserInfo(myToken, fromUserName);
    					message=MessageUtil.initTextMessage(toUserName, fromUserName, jsonObject.toString());
                    } else {
					//2）弹出菜单
                    	message = MessageUtil.initTextMessage(toUserName, fromUserName, MessageUtil.subscibeMsg());
                    }
                    //3）显示用户信息
//					JSONObject jsonObject=WeixinUtil.getUserInfo(myToken, fromUserName);
//					message=MessageUtil.initTextMessage(toUserName, fromUserName, jsonObject.toString());
				}else if (MessageUtil.MESSAGE_VIEW.equals(eventType)) {
					message = map.get("EventKey");
					message = "<![CDATA["+message+"]]>";
					//这里不会得到回复
					message=MessageUtil.initTextMessage(toUserName, fromUserName, message);
				}else if (MessageUtil.MESSAGE_SCANCODE.equals(eventType)) {
					message = map.get("EventKey");
					//这里不会得到回复
					message=MessageUtil.initTextMessage(toUserName, fromUserName, "扫码事件被点击，EventKey："+message);
//				}else if (MessageUtil.MESSAGE_LOCATION.equals(eventType)) {
//					message = map.get("Latitude");
//					message=MessageUtil.initTextMessage(toUserName, fromUserName, "地理位置纬度 ："+message);
				}
			}else if(MessageUtil.MESSAGE_LOCATION.equals(msgType)){
				message = map.get("Label");
            	message=MessageUtil.initTextMessage(toUserName, fromUserName,"获取地理位置被点击：" + message);
			}
			
			
			out.print(message);
			
			System.out.println("----------->微信消息发送完成");
			
		} catch (DocumentException e) {
			e.printStackTrace();
		}finally{
			out.close();
		}
		
		
		
	}
}
