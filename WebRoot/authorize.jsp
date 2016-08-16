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
    
    <title>微信网页授权</title>
    
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
  
   <h1>微信网页授权</h1>
   <h3>您的用户信息</h3>
   <p>当前URL：<%=basePath %> </p>
	<table>
		<tr>
			<td>昵称：</td>
			<td><%=jsonObject.getString("nickname") %></td>
		</tr>
		<tr>
			<td>openid：</td>
			<td><%=jsonObject.getString("openid") %></td>
		</tr>
		<tr>
			<td>城市：</td>
			<td><%=jsonObject.getString("city") %></td>
		</tr>
		<tr>
			<td>省份：</td>
			<td><%=jsonObject.getString("province") %></td>
		</tr>
		<tr>
			<td>国家：</td>
			<td><%=jsonObject.getString("country") %></td>
		</tr>
		<tr>
			<td>头像：</td>
			<td><img width=90px height=90px src=<%=jsonObject.getString("headimgurl") %> ></td>
		</tr>
	</table>

  </body>
</html>
