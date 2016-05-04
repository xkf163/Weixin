<%@page import="com.xkf.po.JSSignature"%>
<%@page import="com.xkf.po.WxUser"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="com.xkf.po.WebAccessToken"%>
<%@page import="com.xkf.servlet.AccessTokenThread"%>
<%@page import="com.xkf.po.AccessToken"%>
<%@page import="com.xkf.po.JSTicket"%>
<%@page language="java" import="com.xkf.util.*"   contentType="text/html; charset=UTF-8"%>
<%


String url = request.getScheme()+"://"+ request.getServerName()+request.getRequestURI();

JSSignature jt =  WeixinUtil.getJSSignature(url);

%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>微信导航页面</title>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>

<script type="text/javascript">  
     wx.config({  
         debug: false,  
         appId: 'wx86c37be7116e10cf',  
         timestamp: <%=jt.getTimestamp() %>,  
         nonceStr: '<%=jt.getNonceStr() %>',   
         signature: '<%=jt.getSignature() %>',  
         jsApiList: ['openLocation','getLocation']  
     });  
 </script>  
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
</head>



<body>
<br>
<h1>导航演示</h1><br><b><%=url %></b><br>
<button id="btn" style="font-size:50px" onclick="gethaha()">点击这里</button>

<!--  <a href="javascript:gethaha()" style="font-size:25px">hahahahaha</a> -->


      
            
</body>
</html>