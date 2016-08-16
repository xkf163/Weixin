package config;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.ServletConfig;

import net.sf.json.JSONObject;
import entity.menu.*;



public class MenuTree {

	public static String initTree() {

		String redirectUrl=Config.rootUrl+"/Weixin/authorize.jsp?";
		String templateUrl=Config.rootUrl+"/Weixin/templatemsg.jsp?";
		String ENCODING="UTF-8";
		try {
			redirectUrl= URLEncoder.encode(redirectUrl, ENCODING);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		

		ViewButton viewButton11 = new ViewButton();
		viewButton11.setName("网页授权");
		viewButton11.setType("view");
		viewButton11.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+Config.APPID+"&redirect_uri="+redirectUrl+"&response_type=code&scope=snsapi_base&state=123456abcdef#wechat_redirect");
				
		ViewButton viewButton12 = new ViewButton();
		viewButton12.setName("一键导航");
		viewButton12.setType("view");
		viewButton12.setUrl(Config.rootUrl+"/Weixin/navigation.jsp");
		
		ViewButton viewButton13 = new ViewButton();
		viewButton13.setName("模板消息");
		viewButton13.setType("view");
		viewButton13.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+Config.APPID+"&redirect_uri="+templateUrl+"&response_type=code&scope=snsapi_base&state=123456abcdef#wechat_redirect");
	
		
		
		ClickButton clickButton11 = new ClickButton();
		clickButton11.setName("我的信息");
		clickButton11.setType("click");
		clickButton11.setKey("11");
		
		Button button1=new Button();
		button1.setName("功能演示");
		button1.setSub_button(new Button[]{viewButton11,viewButton12,viewButton13,clickButton11});
				
		ViewButton button2 = new ViewButton();
		button2.setName("微官网");
		button2.setType("view");
		button2.setUrl("http://www.rostensoft.com");
				
		ClickButton clickButton31 = new ClickButton();
		clickButton31.setName("扫二维码");
		clickButton31.setType("scancode_push");
		clickButton31.setKey("31");
		
		ClickButton clickButton32 = new ClickButton();
		clickButton32.setName("发送位置");
		clickButton32.setType("location_select");
		clickButton32.setKey("32");
		
		Button button3=new Button();
		button3.setName("关于我们");
		button3.setSub_button(new Button[]{clickButton31,clickButton32});
		
		Menu m = new Menu();
		m.setButton(new Button[]{button1,button2,button3});
		

		
		
		return JSONObject.fromObject(m).toString();
		
		
	}

}

