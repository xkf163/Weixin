package com.xkf.servlet;

import com.xkf.po.AccessToken;
import com.xkf.po.JSTicket;
import com.xkf.util.WeixinUtil;

public class AccessTokenThread implements Runnable {
	
	public static AccessToken accessToken;
    public static final String rootUrl="http://tiagoxu.imwork.net";
    public static JSTicket jsTicket;
    public static final String APPID="wx86c37be7116e10cf";
    public static final String APPSECRET="d4624c36b6795d1d99dcf0547af5443d";
    
    
    @Override
    public void run() 
    {
        while(true) 
        {
            AccessToken token = WeixinUtil.getAccessToken(); // 从微信服务器刷新access_token
			if(token != null){
			    accessToken = token;
			    System.out.println(">>>initTOKEN : "+accessToken.getAccess_token());
			    jsTicket = WeixinUtil.getJsTicket(accessToken.getAccess_token());
			    System.out.println(">>>initJSTicket : "+jsTicket.getJsTicket()+"\n");
			}else{
			    System.out.println("get access_token failed------------------------------");
			}
             
            try{
                if(null != accessToken){
                    Thread.sleep((accessToken.getExpires_in() - 200) * 1000);    // 休眠7000秒
                }else{
                    Thread.sleep(60 * 1000);    // 如果access_token为null，60秒后再获取
                }
            }catch(InterruptedException e){
                try{
                    Thread.sleep(60 * 1000);
                }catch(InterruptedException e1){
                    e1.printStackTrace();
                }
            }
        }
    }
}