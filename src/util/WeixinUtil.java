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
	 * ��ҳ��Ȩ���ܣ���ȡ�û���Ϣ
	 */
	public static JSONObject getWxUserInfo(String webCode) {
		JSONObject jsonObject;
		WebAccessToken webAT = Verify.getWebAccessToken(webCode);
		String url =  GET_USERINFO_URL.replace("ACCESS_TOKEN",WeixinThread.accessToken.getAccess_token()).replace("OPENID", webAT.getOpenid());
		jsonObject = HttpTransfer.doGetUrl(url);
		if(jsonObject!=null){
			//�����û���Ϣ
		}
		return jsonObject;
	}
	
	/*
	 * ����ҳ��Ȩ���ܣ���ȡ�û���Ϣ
	 */
	public static JSONObject getUserInfo(String token,String openid){
		String url =  GET_USERINFO_URL.replace("ACCESS_TOKEN", token).replace("OPENID", openid);
		JSONObject jsonObject = HttpTransfer.doGetUrl(url);
		if(jsonObject!=null){
			System.out.println("�û���Ϣ��<br>"+jsonObject.toString());
		}
		return jsonObject;
	}
	
	
	/*
	 * ΢�Ų˵�����
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
					r = "<h1>΢�Ź��ںŲ˵������ɹ�</h1><p>"+f+"</p>";
				}else{
					r = "<h1>΢�Ź��ںŲ˵�����ʧ��</h1><p>���󷵻���Ϊ��"+result+"</p>";
				}
				break;
		}
		
		return r;
		
	}
	
	/*
	 * JS SDK ��֤���أ���һ��������
	 */
	public static JSSignature getJSSignature(String url){  
		JSSignature jsSignature = new JSSignature();
		System.out.println("ticket:"+WeixinThread.jsTicket.getJsTicket()+"\n\n");
//		Sign sign = new Sign();
		Sign.setJsapi_ticket(WeixinThread.jsTicket.getJsTicket());
		Sign.setUrl(url); //url�ǵ���ҳ��ĵ�ַ ��http://www.abc.com/Weixin/xxx.jsp
		Map<String, String> ticketMap=new HashMap<String, String>();
		ticketMap = Sign.sign();
//		jsSignature.setJsTicket(ticketMap.get("jsapi_ticket"));
		jsSignature.setTimestamp(ticketMap.get("timestamp"));
		jsSignature.setNonceStr(ticketMap.get("nonceStr"));
		jsSignature.setSignature(ticketMap.get("signature"));
		
		return jsSignature;
	}
	
	/*
	 * ģ����Ϣ����
	 */
	public static int sendTemplateMessage(String templateMessageJson){
		String url = SEND_TEMPLATE_MESSAGE_URL.replace("ACCESS_TOKEN", WeixinThread.accessToken.getAccess_token());
		JSONObject jsonObject = HttpTransfer.doPostStr(url, templateMessageJson);
		if(jsonObject != null){
			System.out.println("\nTemplateMessage���صĽ����\n"+jsonObject.toString());
			int status = jsonObject.getInt("errcode");
			return status;
		}
		return 999;
	}
	
	
}
