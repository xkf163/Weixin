<%@page import="thread.WeixinThread"%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@ page import="util.*" %>
<%@ page import="entity.JSSignature" %>
<%@ page import="config.*" %>
<%
//String path = request.getContextPath();
//String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

String url = request.getScheme()+"://"+ request.getServerName()+request.getRequestURI();
JSSignature jss =  WeixinUtil.getJSSignature(url);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>微信导航</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
    <h1>微信导航演示：</h1><br>
    <p>
    	<button id="btn" style="font-size:50px" onclick="goNavigation()">开始导航</button>
    </p>
    
    
    
  
  <script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
  <script type="text/javascript">  
     wx.config({  
         debug: false,  
         appId: '<%=Config.APPID%>',  
         timestamp: <%=jss.getTimestamp() %>,  
         nonceStr: '<%=jss.getNonceStr() %>',   
         signature: '<%=jss.getSignature() %>',  
         jsApiList: ['openLocation','getLocation']  
     });  
     
      function goNavigation(){
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
