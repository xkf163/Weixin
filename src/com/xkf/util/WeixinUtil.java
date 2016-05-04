package com.xkf.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.Signature;
import java.util.HashMap;
import java.util.Map;

import javax.naming.spi.DirStateFactory.Result;
import javax.net.ssl.HttpsURLConnection;

import net.sf.json.JSON;
import net.sf.json.JSONObject;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.thoughtworks.xstream.io.binary.Token;
import com.xkf.memu.Button;
import com.xkf.memu.ClickButton;
import com.xkf.memu.Menu;
import com.xkf.memu.ViewButton;
import com.xkf.po.AccessToken;
import com.xkf.po.JSSignature;
import com.xkf.po.JSTicket;
import com.xkf.po.WebAccessToken;
import com.xkf.po.WxUser;
import com.xkf.servlet.AccessTokenThread;

/**
 * @author F
 *
 */
public class WeixinUtil {

//	private static final String APPID="wxe32893a01df1a22a";
//	private static final String APPSECRET="6ceab90659735b7ac7760067c11dedeb";
	
	private static final String APPID=AccessTokenThread.APPID;
	private static final String APPSECRET=AccessTokenThread.APPSECRET;
	
//	private static final String APPID="wx21be7e575c22bb11";
//	private static final String APPSECRET="c49d54c6faaa9b2362feb969a5bfcb20";
	private static final String ACCESS_TOKEN_URL="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	private static final String CREATE_MENU_URL="https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	private static final String DEL_MENU_URL="https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";
	private static final String GET_USERINFO_URL="https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
	private static final String QUERY_MENU_URL="https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";
	private static final String SENDALL_NEWS_URL="https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token=ACCESS_TOKEN";
	private static final String UPLOAD_URL="https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
	private static final String UPLOAD_NEW_URL="https://api.weixin.qq.com/cgi-bin/media/uploadnews?access_token=ACCESS_TOKEN";
	private static final String GET_JSTICKET_URL="https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";
	private static final String GET_WEBACCESSTOKEN_URL="https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
	private static final String GET_WEB_USERINFO_URL="https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
	
	private static final String SEND_TEMPLATE_MESSAGE_URL="https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
	
	
	public static String upload(String filePath,String accessToken,String type) throws IOException{
		File file = new File(filePath);
		if(!file.exists() || !file.isFile()){
			throw new IOException("要上传的文件不存在！");
		}
		String url = UPLOAD_URL.replace("ACCESS_TOKEN", accessToken).replace("TYPE", type);
		URL urlObj = new URL(url);
		//连接
		HttpsURLConnection con = (HttpsURLConnection) urlObj.openConnection();
		con.setRequestMethod("POST");
		// 设置是否从httpUrlConnection读入，默认情况下是true; 
		con.setDoInput(true);
		// 设置是否向httpUrlConnection输出，因为这个是post请求，参数要放在http正文内，因此需要设为true, 默认情况下是false; 
		con.setDoOutput(true);
		// Post 请求不能使用缓存 
		con.setUseCaches(false);
		//设置请求头信息
		con.setRequestProperty("Connect", "Keep-Alive");
		con.setRequestProperty("Charset", "UTF-8");


		//boundary就是request头和上传文件内容的分隔符  
		String BOUNDARY = "------------------"+System.currentTimeMillis(); 
		// 设定传送的内容类型是可序列化的java对象 
		// (如果不设此项,在传送序列化对象时,当WEB服务默认的不是这种类型时可能抛java.io.EOFException) 
		con.setRequestProperty("Content-type", "multipart/form-data; boundary=" + BOUNDARY); 
		
		//获得输出流
		OutputStream out = new DataOutputStream(con.getOutputStream());  
 
        StringBuffer strBuf = new StringBuffer();  
        strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");  
        strBuf.append("Content-Disposition: form-data; name=\"file\";filename=\"" + file.getName() + "\"\r\n");  
        strBuf.append("Content-Type:application/octet-stream\r\n\r\n");  
        //输出表头
        out.write(strBuf.toString().getBytes());  
         
		DataInputStream in = new DataInputStream(new FileInputStream(file));
		
		int bytes = 0;
		byte[] bufferOut = new byte[1024];
		while((bytes=in.read(bufferOut))!=-1){
			out.write(bufferOut,0,bytes);
		}
		in.close();
		
		//结尾部分
		//定义最后数据分割线
		byte[] foot = ("\r\n--"+BOUNDARY+"--\r\n").getBytes("utf-8");
		out.write(foot);
		out.flush();
		out.close();
		
		// 读取返回数据    
        StringBuffer strsBuf = new StringBuffer(); 
        BufferedReader reader =null;
        String result=null;
		try{
	        reader = new BufferedReader(new InputStreamReader(con.getInputStream()));  
	        String line = null;  
	        while ((line = reader.readLine()) != null) {  
	        	strsBuf.append(line).append("\n");  
	        }  
	        result = strsBuf.toString();  
	        reader.close();  
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			if(reader!=null){
				reader = null;  
			}
		}
	
		JSONObject jsonObject = JSONObject.fromObject(result);
		System.out.println(jsonObject);
//		String mediaId = jsonObject.getString("media_id");
//		return mediaId;
		
		return jsonObject.toString();
	}
	
	/**
	 * get请求
	 * @param url
	 * @return
	 */
	public static JSONObject doGetUrl(String url) {
		JSONObject jsonObject=null;
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		try {
			HttpResponse response = httpClient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			if(entity !=null){
				String result = EntityUtils.toString(entity,"UTF-8");
				jsonObject = JSONObject.fromObject(result);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonObject;
	}

	/**
	 * post请求
	 * @param url
	 * @return
	 */
	public static JSONObject doPostStr(String url,String outStr){
		
		JSONObject jsonObject=null;
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		try {
			httpPost.setEntity(new StringEntity(outStr,"UTF-8"));
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			if(entity != null){
				String result = EntityUtils.toString(entity,"UTF-8");
				jsonObject = JSONObject.fromObject(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return jsonObject;
	}
	
	
	
	
	/**
	 * get template id
	 *
	 */
	public static String sendTemplateMessage(String token,String templateMessageJson){
		String url = SEND_TEMPLATE_MESSAGE_URL.replace("ACCESS_TOKEN", token);
		JSONObject jsonObject = doPostStr(url, templateMessageJson);
		String status=null;
		if(jsonObject != null){
			System.out.println("\nTemplateMessage返回的结果：\n"+jsonObject.toString());
			status = jsonObject.getString("errcode");
		}
		return status;
	}
	
	
	
	/**
	 * 获取token
	 * @return
	 */
	public static AccessToken getAccessToken(){
		AccessToken token = new AccessToken();
		String url = ACCESS_TOKEN_URL.replace("APPID", APPID).replace("APPSECRET", APPSECRET);
		JSONObject jsonObject = doGetUrl(url);
		if(jsonObject != null){
			token.setAccess_token(jsonObject.getString("access_token"));
			token.setExpires_in(jsonObject.getInt("expires_in"));
		}
		return token;
	}
	
	
	public static JSSignature getJSSignature(String url){  
		JSSignature jsSignature = new JSSignature();
//		System.out.println("ticket:"+jsonObject.getString("ticket")+"\n\n");
		Sign sign = new Sign();
		sign.setJsapi_ticket(AccessTokenThread.jsTicket.getJsTicket());
//		sign.setUrl(AccessTokenThread.rootUrl+"/Weixin/getlocation.jsp");
		sign.setUrl(url);
		Map<String, String> ticketMap=new HashMap<String, String>();
		ticketMap = sign.sign();

//		jsSignature.setJsTicket(ticketMap.get("jsapi_ticket"));
		jsSignature.setTimestamp(ticketMap.get("timestamp"));
		jsSignature.setNonceStr(ticketMap.get("nonceStr"));
		jsSignature.setSignature(ticketMap.get("signature"));
		
		return jsSignature;
	}
	
    /** 
     * 获取jsticket 
     * @return 
     */  
    public static JSTicket getJsTicket(String token){  
        try {  
        	JSTicket jsTicket = new JSTicket();
        	
            String url = GET_JSTICKET_URL.replace("ACCESS_TOKEN", token);
    		JSONObject jsonObject = doGetUrl(url);
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
     
    
    
    
    /**
     * @param 网页授权：根据code获取webaccesstoken
     * @return
     */
    public static WebAccessToken getWebAccessToken(String codeString){
    		 WebAccessToken webAT = new WebAccessToken();
			 String url = GET_WEBACCESSTOKEN_URL.replace("APPID", APPID).replace("SECRET", APPSECRET).replace("CODE", codeString);
			 JSONObject jsonObject = doGetUrl(url);
			 
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
	
    
    public static WxUser getWebUserInfo(WebAccessToken webAT){
//    	 https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN
    	 String acctoken=webAT.getAccess_token();
   	 	 String openid=webAT.getOpenid();
   	 	 
   	     System.out.println("webacctoken:"+acctoken+"&openid:"+openid+"\n");
   	 	 
    	 String url = GET_WEB_USERINFO_URL.replace("ACCESS_TOKEN",acctoken).replace("OPENID", openid);
    	 System.out.println(url);
		 JSONObject jsonObject = doGetUrl(url);
    	 WxUser wxUser = new WxUser();
    	 

    	 System.out.println(jsonObject.toString());
    	 
    	 if(jsonObject !=null & jsonObject.has("openid")){
    		 wxUser.setOpenid(jsonObject.getString("openid"));
    		 wxUser.setNickname(jsonObject.getString("nickname"));
    	 }
		 
    	 return wxUser;
    }
    
	
	/**
	 * 组装菜单
	 * @return
	 */
	public static Menu initMenu(){
		
		Menu menu = new Menu();
		
		String redirectUrl=AccessTokenThread.rootUrl+"/Weixin/getuserinfo.jsp?";
		String ENCODING="UTF-8";
		try {
			redirectUrl= URLEncoder.encode(redirectUrl, ENCODING);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		ViewButton viewButton11 = new ViewButton();
		viewButton11.setName("网页授权跳转");
		viewButton11.setType("view");
		viewButton11.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+APPID+"&redirect_uri="+redirectUrl+"&response_type=code&scope=snsapi_base&state=123456abcdef#wechat_redirect");
				
		ViewButton viewButton12 = new ViewButton();
		viewButton12.setName("一键导航");
		viewButton12.setType("view");
		viewButton12.setUrl(AccessTokenThread.rootUrl+"/Weixin/getlocation.jsp");
		
		ClickButton clickButton11 = new ClickButton();
		clickButton11.setName("我的信息");
		clickButton11.setType("click");
		clickButton11.setKey("11");
		
		Button button1=new Button();
		button1.setName("功能演示");
		button1.setSub_button(new Button[]{viewButton11,viewButton12,clickButton11});
				
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
		
		menu.setButton(new Button[]{button1,button2,button3});		
		return menu;
	}
	
//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ 菜单操作 START
	
	/**
	 * 创建或者修改自定义菜单
	 * @param token
	 * @param menu
	 * @return
	 */
	public static int createMenu(String token,String menu) {
		String url = CREATE_MENU_URL.replace("ACCESS_TOKEN", token);
		JSONObject jsonObject = doPostStr(url, menu);
		int result = 0;
		if(jsonObject != null){
			 result = jsonObject.getInt("errcode");
			 System.out.println("WeixinUtil createMenu errcode:"+result);
		}
		return result;
	}
		
	/**
	 * 查询当前公众号的菜单，返回json格式
	 * @param token
	 * @return
	 */
	public static JSONObject queryMenu(String token){
		String url = QUERY_MENU_URL.replace("ACCESS_TOKEN", token);
		JSONObject jsonObject = doGetUrl(url);
		return jsonObject;
	}
	
	/**
	 * 删除当前公众号菜单
	 * @param token
	 * @return
	 */
	public static int delMenu(String token) {
		String url = DEL_MENU_URL.replace("ACCESS_TOKEN", token);
		JSONObject jsonObject = doGetUrl(url);
		int result = 0;
		if(jsonObject != null){
			 result = jsonObject.getInt("errcode");
		}
		return result;
	}
	
	//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ 菜单操作 END	
	
	
	
	public static JSONObject getUserInfo(String token,String openid){
		String url =  GET_USERINFO_URL.replace("ACCESS_TOKEN", token).replace("OPENID", openid);
		JSONObject jsonObject = doGetUrl(url);
		if(jsonObject!=null){
//			System.out.println("国家："+jsonObject.getString("country"));
			System.out.println("所在城市："+jsonObject.getString("province"));
		}
		return jsonObject;
	}
	

	
	/**
	 * 上传图文消息
	 * @param token
	 * @param articles
	 * @return
	 */
	public static JSONObject uploadNews(String token,String articles) {
		String url = UPLOAD_NEW_URL.replace("ACCESS_TOKEN", token);
		JSONObject jsonObject = doPostStr(url, articles);
		if(jsonObject!=null){
			System.out.println(jsonObject.toString());
		}
		
		return jsonObject;
	}
	
	
	/**
	 * 群发图文消息
	 * @param token
	 * @return
	 */
	public static int sendAllNews(String token,String mediaID){
		String url = SENDALL_NEWS_URL.replace("ACCESS_TOKEN", token);
		String articles="{\"filter\":{\"is_to_all\":false,\"group_id\":\"0\"},\"mpnews\":{\"media_id\":\""+mediaID+"\"},\"msgtype\":\"mpnews\"}";
		JSONObject jsonObject = doPostStr(url,articles);
		System.out.println("群发图文："+articles);
		int result = 0;
		if(jsonObject != null){
			 System.out.println(jsonObject.toString());
			 result = jsonObject.getInt("errcode");
		}
		return result;
	}
	
	/**
	 * 群发纯文本消息
	 * @param token
	 * @return
	 */
	public static int sendAlltext(String token) {
		String url = SENDALL_NEWS_URL.replace("ACCESS_TOKEN", token);
		
		String textMsg="{\"filter\":{\"is_to_all\":true},\"text\":{\"content\":\"【这是条群发消息】国务院任命中国人民大学校长陈雨露为中国央行副行长。目前，人民银行共有4位副行长：易纲、潘功胜、范一飞和郭庆平。\"},\"msgtype\":\"text\"}";
		JSONObject jsonObject = doPostStr(url,textMsg);
		
		int result = 0;
		if(jsonObject != null){
			 System.out.println(jsonObject.toString());
			 result = jsonObject.getInt("errcode");
		}
		return result;
	}
	
}
