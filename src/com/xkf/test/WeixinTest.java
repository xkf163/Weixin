package com.xkf.test;

import net.sf.json.JSONObject;

import com.xkf.po.AccessToken;
import com.xkf.util.WeixinUtil;

public class WeixinTest {

	public static void main(String[] args) {

		AccessToken token=WeixinUtil.getAccessToken();
		System.out.println("车票密钥："+token.getAccess_token());
		System.out.println("有效时间："+token.getExpires_in());
		
		
		String menu =  JSONObject.fromObject(WeixinUtil.initMenu()).toString();
		int result = WeixinUtil.createMenu(token.getAccess_token(), menu);
		if(result == 0){
			System.out.println("菜单创建成功！");
		}else{
			System.out.println("菜单创建错误码："+result);
		}
		
	}

}
