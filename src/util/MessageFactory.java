package util;
/**
 * 消息初始化、拼装、转换
 */
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import message.Article;
import message.Articles;
import message.Image;
import message.ImageMessage;
import message.News;
import message.NewsMessage;
import message.TextMessage;
import net.sf.json.JSONObject;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.thoughtworks.xstream.XStream;


public class MessageFactory {

	public static final String MESSAGE_TEXT="text";
	public static final String MESSAGE_NEWS="news";
	public static final String MESSAGE_IMAGE="image";
	public static final String MESSAGE_VOICE="voice";
	public static final String MESSAGE_VIDEO="video";
	public static final String MESSAGE_LINK="link";
	public static final String MESSAGE_LOCATION="location";
	public static final String MESSAGE_EVENT="event";
	public static final String MESSAGE_SUBSCRIBE="subscribe";
	public static final String MESSAGE_UNSUBSCRIBE="unsubscribe";
	public static final String MESSAGE_CLICK="CLICK";
	public static final String MESSAGE_VIEW="VIEW";
	public static final String MESSAGE_SCANCODE="scancode_push";
	
	

	@SuppressWarnings("unchecked")
	public static Map<String, String> xmlToMap(HttpServletRequest request) throws IOException, DocumentException {
		Map<String, String> map = new HashMap<String, String>();
		SAXReader reader = new SAXReader();
		
		InputStream ins = request.getInputStream();
		Document document = reader.read(ins);
		Element root = document.getRootElement();
		List<Element> list = root.elements();
		System.out.println("-->--->---->----->消息处理------开始-------------");
		System.out.println("1）微信服务器发送过来的消息体");
		for (Element element:list){
			map.put(element.getName(), element.getText());
			System.out.println(element.getName()+"  "+element.getText());
		}
		
		ins.close();
		return map;		
	}
	
	

	public static String textMessageToXML(TextMessage textMessage) {
		XStream xStream =new XStream();
		xStream.alias("xml", textMessage.getClass());
		String rString=xStream.toXML(textMessage);
		return xStream.toXML(textMessage);
	}
	
	

	public static String newsMessageToXML(NewsMessage newsMessage) {
		XStream xStream =new XStream();
		xStream.alias("xml", newsMessage.getClass());
		xStream.alias("item", new News().getClass());
		return xStream.toXML(newsMessage);
	}
	

	public static String imageMessageToXML(ImageMessage imageMessage) {
		XStream xStream =new XStream();
		xStream.alias("xml", imageMessage.getClass());
		return xStream.toXML(imageMessage);
	}
	
	
	
	public static String subscibeMsg() {
		StringBuffer sbBuffer = new StringBuffer();
		sbBuffer.append("感谢您的关注：\n\n");
		sbBuffer.append("1)希斯菲尔德\n");
		sbBuffer.append("2)斯科尔斯\n");
		sbBuffer.append("3)图文消息\n");
		sbBuffer.append("4)你的信息\n");
		sbBuffer.append("回复？调出此菜单。");
		return sbBuffer.toString();
	}
	
	
	
	public static String oneMsg() {
		StringBuffer sbBuffer = new StringBuffer();
		sbBuffer.append("【希斯菲尔德：瓜迪奥拉应与拜仁续约一年】您输入的是1。");
		return sbBuffer.toString();
	}
	
	public static String twoMsg() {
		StringBuffer sbBuffer = new StringBuffer();
		sbBuffer.append("【斯科尔斯：英超外援太多，阻碍英格兰足球的发展】您输入了2。");
		return sbBuffer.toString();
	}
	
	
	public static String initTextMessage(String toUserName,String fromUserName,String content) {
		TextMessage textMessage = new TextMessage();
		textMessage.setFromUserName(toUserName);
		textMessage.setToUserName(fromUserName);
		textMessage.setMsgType(MessageFactory.MESSAGE_TEXT);
		textMessage.setCreateTime(new Date().getTime());
		textMessage.setContent(content);
		return textMessageToXML(textMessage);
	}
	
	/**
	 * 图文消息的组装
	 * @param toUserName
	 * @param fromUserName
	 * @return
	 */
	public static String initNewsMessage(String toUserName,String fromUserName) {
		List<News> newsList = new ArrayList<News>();
		News new1 = new News();
		new1.setTitle("南非联赛凯撒酋长队门将库内复制伊基塔蝎子摆尾扑救。");
		new1.setDescription("news1 description");
		new1.setPicUrl("http://www.cnedu.cn/upload/html/2014/05/29/liyangzi88feaf0f9ea049838905c0bd4ecc9680.jpg");
		new1.setUrl("www.baidu.com");
		
		newsList.add(new1);
		
		News new2 = new News();
		new2.setTitle("天赋几何？ 森林狼新秀琼斯之弟赛场集锦");
		new2.setDescription("特雷-琼斯年仅15岁，是森林狼新秀泰尔斯-琼斯的亲弟弟，他在篮球场上司职后卫，对场面的把控能力不错、助攻和自己得分的能力也值得称赞，不过小琼斯的身体天赋并不出众。");
		new2.setPicUrl("http://img3.imgtn.bdimg.com/it/u=1240207920,2748008529&fm=15&gp=0.jpg");
		new2.setUrl("www.baidu.com");
		
		newsList.add(new2);
		
		News new3 = new News();
		new3.setTitle("难说再见!邵佳一正式宣布退役 泪洒发布会");
		new3.setDescription("29日下午，北京国安足球俱乐部召开新闻发布会，宣布队中35岁老将邵佳一将正式退役。发布会上，邵佳一面对球迷以及队友情绪异常激动，在发言开始就几度哽咽落泪，场面极其感人。");
		new3.setPicUrl("http://img1.imgtn.bdimg.com/it/u=510337982,1153845941&fm=21&gp=0.jpg");
		new3.setUrl("www.baidu.com");
		
		newsList.add(new3);
		
		NewsMessage newsMessage  = new NewsMessage();
		newsMessage.setFromUserName(toUserName);
		newsMessage.setToUserName(fromUserName);
		newsMessage.setMsgType(MessageFactory.MESSAGE_NEWS);
		newsMessage.setCreateTime(new Date().getTime());
		newsMessage.setArticles(newsList);
		newsMessage.setArticleCount(newsList.size());
		
		return newsMessageToXML(newsMessage);
	}
	
	
	

	public static String initImageMessage(String toUserName,String fromUserName){
		String message = null;
		Image image = new Image();
		image.setMediaId("djdkdhsdhsjdhsjdhs");
		
		ImageMessage im = new ImageMessage();
		im.setFromUserName(toUserName);
		im.setToUserName(fromUserName);
		im.setCreateTime(new Date().getTime());
		im.setMsgType(MESSAGE_IMAGE);
		im.setImage(image);
		
		message=imageMessageToXML(im);
		return message;
	}
	

	

/**
 * 模板消息的组装
 * @param touser
 * @param content
 * @return
 */
	public static String initTemplateMessage(String touser,String templateId) {
		JSONObject jObject = new JSONObject();
		jObject.put("touser", touser);
		jObject.put("template_id", templateId);
//		jObject.put("url", "");
		jObject.put("data", "{\"info\":{\"value\":\"模板消息功能[MessageUtil.initTemplateMessage()]\",\"color\":\"#173177\"}}");		
		return jObject.toString();
	}
	
	
	

	/**
	 * 群发消息组装
	 * @return
	 */
	public static String initArticleMessage(){
		Articles articles =new Articles();
		
//"thumb_media_id":"qI6_Ze_6PtV7svjolgs-rN6stStuHIjs9_DidOHaj0Q-mwvBelOXCFZiq2OsIU-p",
//"author":"xxx",
//"title":"Happy Day",
//"content_source_url":"www.qq.com",
//"content":"content",
//"digest":"digest",
//"show_cover_pic":"1"
		Article article1 = new Article();
//		article1.setThumb_media_id("poUZoPJlntFAKDpyoANluSUiCHaDm2VsS1v6_7CQ-VAfuKHdRhAiN1sVIejBMhcP");
		article1.setThumb_media_id("Ft3uL5qN8_frADPj7XviPlqdDQ7ZO9tKR_wnCC-r51zMs1pZE4Qd9d43A5sqiUyk");
		article1.setAuthor("xukf");
		article1.setTitle("群发消息1");
		article1.setContent_source_url("www.ifeng.com");
		article1.setContent("消息正文内容");
		article1.setDigest("这是今天10月30日的群发消息描述");
		article1.setShow_cover_pic("1");

		Article article2 = new Article();
//		article2.setThumb_media_id("poUZoPJlntFAKDpyoANluSUiCHaDm2VsS1v6_7CQ-VAfuKHdRhAiN1sVIejBMhcP");
		article2.setThumb_media_id("Ft3uL5qN8_frADPj7XviPlqdDQ7ZO9tKR_wnCC-r51zMs1pZE4Qd9d43A5sqiUyk");
		article2.setAuthor("xukf2");
		article2.setTitle("群发消息2");
		article2.setContent_source_url("www.ifeng.com");
		article2.setContent("消息正文内容2");
		article2.setDigest("这是今天10月31日的群发消息描述");
		article2.setShow_cover_pic("0");
		
		articles.setArticles(new Article[]{article1,article2});
		
		String message = JSONObject.fromObject(articles).toString();
		System.out.println("群发图文消息："+message);
		
		return message;
		
	}

	
}
