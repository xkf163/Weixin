<%@page import="net.sf.json.JSONObject"%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@ page import="util.*" %>
<%@ page import="entity.*" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

//获取用户信息
String webCode=request.getParameter("code");
JSONObject jsonObject = WeixinUtil.getWxUserInfo(webCode);

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>模板消息推送</title>
    
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
   <h1>模板消息功能：</h1>
   <form action="push.do" method="POST">
  		<p>你的openid: <input type="text" name="openid" value="<%=jsonObject.getString("openid") %>" /></p>
 		<p>templateMessageType: <input type="text" name="templateMessageType" value="requesting" /></p>
  		<input type="submit" style="font-size:50px" value="开始推送" />
   </form>
  </body>
</html>
