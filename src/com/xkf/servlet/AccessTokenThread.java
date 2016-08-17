package com.xkf.servlet;

import java.text.SimpleDateFormat;
import java.util.Date;
import com.xkf.po.AccessToken;
import com.xkf.po.JSTicket;
import com.xkf.util.WeixinUtil;

/*
 * 初始化servlet：自动生成accessToken及jsToken
 */
public class AccessTokenThread implements Runnable {
	public static AccessToken accessToken;
    public static final String rootUrl="http://101.201.69.249";
    public static JSTicket jsTicket;
    public static final String APPID="wx86c37be7116e10cf";
    public static final String APPSECRET="d4624c36b6795d1d99dcf0547af5443d";
    
    @Override
    public void run() 
    {
        while(true) 
        {
            AccessToken token = WeixinUtil.getAccessToken(); // 从微信服务器刷新access_token
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
            Date now=new Date();
            
			if(token != null){
			    accessToken = token;
			    System.out.println(simpleDateFormat.format(now)+">>>AccessToken: "+accessToken.getAccess_token());
			    jsTicket = WeixinUtil.getJsTicket(accessToken.getAccess_token());
			    System.out.println(simpleDateFormat.format(now)+">>>JSTicket: "+jsTicket.getJsTicket());
			}else{
			    System.out.println("ERROR:get access_token failed");
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
