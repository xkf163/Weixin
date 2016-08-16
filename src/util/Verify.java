package util;

import java.util.Arrays;

import net.sf.json.JSONObject;
import socket.HttpTransfer;
import config.Config;
import entity.AccessToken;
import entity.JSTicket;
/*
 * 身份验证工具类：
 * 获取各种token ticket 等
 */
import entity.WebAccessToken;

public class Verify {
	
	private static final String GET_WEBACCESSTOKEN_URL="https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
	private static final String GET_JSTICKET_URL="https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";
	private static final String ACCESS_TOKEN_URL="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	private static final String APPID = Config.APPID;
	private static final String APPSECRET = Config.APPSECRET;
	
	/**
	 * 获取token
	 * @return
	 */
	public static AccessToken getAccessToken(){
		AccessToken token = new AccessToken();
		String url = ACCESS_TOKEN_URL.replace("APPID", APPID).replace("APPSECRET", APPSECRET);
		JSONObject jsonObject = HttpTransfer.doGetUrl(url);
		if(jsonObject != null){
			token.setAccess_token(jsonObject.getString("access_token"));
			token.setExpires_in(jsonObject.getInt("expires_in"));
		}
		return token;
	}
	
    /** 
     * 获取jsticket 
     * @return 
     */  
    public static JSTicket getJsTicket(String token){  
        try {  
        	JSTicket jsTicket = new JSTicket();
            String url = GET_JSTICKET_URL.replace("ACCESS_TOKEN", token);
    		JSONObject jsonObject = HttpTransfer.doGetUrl(url);
    		if(jsonObject != null){
    			jsTicket.setJsTicket(jsonObject.getString("ticket"));
    			jsTicket.setExpires_in(jsonObject.getInt("expires_in"));
    		}
    		return jsTicket;
        } catch (Exception e) {  
            e.printStackTrace(); 
            return null;  
        }  
    }  
     
	
	
	
	
	
	/*
	 * 微信公众平台要求的验证方法
	 */
	public static boolean checkSignature(String signature,String timestamp,String nonce) {
		String[] arr = new String[]{Config.MyToken,timestamp,nonce};
		//排序
		Arrays.sort(arr);
		//拼凑字符串
		StringBuffer content = new StringBuffer();
		for (int i = 0; i < arr.length; i++) {
			content.append(arr[i]);
		}
		
		//sha1加密
		String temp=SHA1.hex_sha1(content.toString());
		
		//比较
		return temp.equals(signature);

	}
	
	
    /**
     * @param 网页授权用：根据code获取webaccesstoken
     * @return
     */
    public static WebAccessToken getWebAccessToken(String codeString){
    		 WebAccessToken webAT = new WebAccessToken();
			 String url = GET_WEBACCESSTOKEN_URL.replace("APPID", APPID).replace("SECRET", APPSECRET).replace("CODE", codeString);
			 JSONObject jsonObject = HttpTransfer.doGetUrl(url);
			 
			 if(jsonObject != null & jsonObject.has("access_token")){
				 webAT.setAccess_token(jsonObject.getString("access_token"));
				 webAT.setExpires_in(jsonObject.getInt("expires_in"));
				 webAT.setOpenid(jsonObject.getString("openid"));
				 webAT.setRefresh_token(jsonObject.getString("refresh_token"));
				 webAT.setScope(jsonObject.getString("scope"));
			 } 
			 
			 System.out.println(webAT.toString()+"\n\n");
			 
			 return webAT;
    }
	
	
}


