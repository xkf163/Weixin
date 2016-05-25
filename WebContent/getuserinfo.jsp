<%@page import="com.xkf.po.WxUser"%>
<%@page import="com.xkf.po.JSSignature"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="com.xkf.po.WebAccessToken"%>
<%@page import="com.xkf.servlet.AccessTokenThread"%>
<%@page import="com.xkf.po.AccessToken"%>
<%@page import="com.xkf.po.JSTicket"%>
<%@page language="java" import="com.xkf.util.*"   contentType="text/html; charset=UTF-8"%>
<%

String baseUrl=request.getScheme()+"://"+ request.getServerName()+request.getRequestURI();
String url = baseUrl+"?"+request.getQueryString();

//获取用户信息
String webCode=request.getParameter("code");
WebAccessToken webAT = WeixinUtil.getWebAccessToken(webCode);
JSONObject jsonObject = WeixinUtil.getUserInfo(AccessTokenThread.accessToken.getAccess_token(), webAT.getOpenid());

JSSignature jt =  WeixinUtil.getJSSignature(url);
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>网页授权跳转</title>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript">  
     wx.config({  
         debug: false,  
         appId: '<%=AccessTokenThread.APPID %>',  
         timestamp: <%=jt.getTimestamp() %>,  
         nonceStr: '<%=jt.getNonceStr() %>',   
         signature: '<%=jt.getSignature() %>',  
         jsApiList: ['openLocation','getLocation']  
     });  
 </script>  
 
</head>



<body>
<h3>获取用户信息</h3><br><%=url %><br>
<table>
	<tr><td>昵称：</td><td><%=jsonObject.getString("nickname") %></td></tr>
	<tr><td>openid：</td><td><%=jsonObject.getString("openid") %></td></tr>
	<tr><td>城市：</td><td><%=jsonObject.getString("city") %></td></tr>
	<tr><td>省份：</td><td><%=jsonObject.getString("province") %></td></tr>
	<tr><td>国家：</td><td><%=jsonObject.getString("country") %></td></tr>
	<tr><td>头像：</td><td><img width=90px height=90px src=<%=jsonObject.getString("headimgurl") %> ></td></tr>
	<tr><td>timestamp: </td><td><%=jt.getTimestamp() %> </td></tr>
	<tr><td>nonceStr: </td><td><%=jt.getNonceStr() %> </td></tr>
	<tr><td>signature: </td><td><%=jt.getSignature() %> </td></tr>
	
</table>
<br><br>
<button id="btn" style="font-size:50px" onclick="gethaha()">开始导航</button>
<br>
<br>
<form action="push.do?action=push" method="POST">
  <p><h4>模板消息功能：</h4></p>
  <p>openid: <input type="text" name="openid" value="<%=jsonObject.getString("openid") %>" /></p>
  <p>templateMessageType: <input type="text" name="templateMessageType" value="requesting" /></p>
  <input type="submit" style="font-size:50px" value="提交申请" />
</form>



 <script type="text/javascript">  
     function gethaha(){
    	 wx.ready(function(){      		
    		 wx.openLocation({
   	      	    latitude: 30.333458, // 纬度，浮点数，范围为90 ~ -90
   	      	    longitude: 120.180138, // 经度，浮点数，范围为180 ~ -180。
   	      	    name: '中大银泰停车场', // 位置名
   	      	    address: '石祥路东新路交叉口', // 地址详情说明
   	      	    scale: 15, // 地图缩放级别,整形值,范围从1~28。默认为最大
   	      	    //infoUrl: 'www.rostensoft.com' // 在查看位置界面底部显示的超链接,可点击跳转
   	      	});
  	      });
     };

 </script>       
            
</body>
</html>