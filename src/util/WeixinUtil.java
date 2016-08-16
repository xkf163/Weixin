package util;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;
import socket.HttpTransfer;
import thread.WeixinThread;
import config.MenuTree;
import entity.JSSignature;
import entity.WebAccessToken;

public class WeixinUtil {

	private static final String GET_USERINFO_URL="https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
	private static final String CREATE_MENU_URL="https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	private static final String SEND_TEMPLATE_MESSAGE_URL="https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";

	/*
	 * 网页授权功能：获取用户信息
	 */
	public static JSONObject getWxUserInfo(String webCode) {
		JSONObject jsonObject;
		WebAccessToken webAT = Verify.getWebAccessToken(webCode);
		String url =  GET_USERINFO_URL.replace("ACCESS_TOKEN",WeixinThread.accessToken.getAccess_token()).replace("OPENID", webAT.getOpenid());
		jsonObject = HttpTransfer.doGetUrl(url);
		if(jsonObject!=null){
			//遍历用户信息
		}
		return jsonObject;
	}
	
	/*
	 * 非网页授权功能：获取用户信息
	 */
	public static JSONObject getUserInfo(String token,String openid){
		String url =  GET_USERINFO_URL.replace("ACCESS_TOKEN", token).replace("OPENID", openid);
		JSONObject jsonObject = HttpTransfer.doGetUrl(url);
		if(jsonObject!=null){
			System.out.println("用户信息：<br>"+jsonObject.toString());
		}
		return jsonObject;
	}
	
	
	/*
	 * 微信菜单操作
	 */
	public static String doMenu(String action) {
		String f= new String();
		String r= new String();
		switch (action) {
			case "d": //delete
				break;
	
			case "c": //create
				String url = CREATE_MENU_URL.replace("ACCESS_TOKEN", WeixinThread.accessToken.getAccess_token());
				f = MenuTree.initTree();
				System.out.println(f);
				JSONObject jsonObject = HttpTransfer.doPostStr(url, f);
				int result = jsonObject.getInt("errcode");
				System.out.println("WeixinUtil createMenu errcode:"+result);
				
				if(result == 0 ){
					r = "<h1>微信公众号菜单创建成功</h1><p>"+f+"</p>";
				}else{
					r = "<h1>微信公众号菜单创建失败</h1><p>错误返回码为："+result+"</p>";
				}
				break;
		}
		
		return r;
		
	}
	
	/*
	 * JS SDK 验证返回（如一键导航）
	 */
	public static JSSignature getJSSignature(String url){  
		JSSignature jsSignature = new JSSignature();
		System.out.println("ticket:"+WeixinThread.jsTicket.getJsTicket()+"\n\n");
//		Sign sign = new Sign();
		Sign.setJsapi_ticket(WeixinThread.jsTicket.getJsTicket());
		Sign.setUrl(url); //url是调用页面的地址 如http://www.abc.com/Weixin/xxx.jsp
		Map<String, String> ticketMap=new HashMap<String, String>();
		ticketMap = Sign.sign();
//		jsSignature.setJsTicket(ticketMap.get("jsapi_ticket"));
		jsSignature.setTimestamp(ticketMap.get("timestamp"));
		jsSignature.setNonceStr(ticketMap.get("nonceStr"));
		jsSignature.setSignature(ticketMap.get("signature"));
		
		return jsSignature;
	}
	
	/*
	 * 模板消息推送
	 */
	public static int sendTemplateMessage(String templateMessageJson){
		String url = SEND_TEMPLATE_MESSAGE_URL.replace("ACCESS_TOKEN", WeixinThread.accessToken.getAccess_token());
		JSONObject jsonObject = HttpTransfer.doPostStr(url, templateMessageJson);
		if(jsonObject != null){
			System.out.println("\nTemplateMessage返回的结果：\n"+jsonObject.toString());
			int status = jsonObject.getInt("errcode");
			return status;
		}
		return 999;
	}
	
	
}
