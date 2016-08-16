package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.dom4j.DocumentException;

import thread.WeixinThread;
import util.MessageFactory;
import util.Verify;
import util.WeixinUtil;

/**
 * Servlet implementation class WeixinServlet
 */
@WebServlet("/WeixinServlet")
public class WeixinServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WeixinServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echostr = request.getParameter("echostr");

		PrintWriter out = response.getWriter();
		if(Verify.checkSignature(signature, timestamp, nonce)){
			out.print(echostr);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();

		
	
		
		try {
			Map<String, String> map = MessageFactory.xmlToMap(request);
			String toUserName = map.get("ToUserName");
			String fromUserName = map.get("FromUserName");
//			String createTime = map.get("CreateTime");
			String msgType = map.get("MsgType");
			String content = map.get("Content");
//			String msgId = map.get("MsgId");
			
//			 <xml>
//			 <ToUserName><![CDATA[toUser]]></ToUserName>
//			 <FromUserName><![CDATA[fromUser]]></FromUserName> 
//			 <CreateTime>1348831860</CreateTime>
//			 <MsgType><![CDATA[text]]></MsgType>
//			 <Content><![CDATA[this is a test]]></Content>
//			 <MsgId>1234567890123456</MsgId>
//			 </xml>
		
			System.out.println("2����ʼ������յ�����Ϣ:��ǰ����Ϣ������:"+msgType);
			
			String message = null;
			if(MessageFactory.MESSAGE_TEXT.equals(msgType)){				
				if("1".equals(content)){
					message = MessageFactory.initTextMessage(toUserName, fromUserName, MessageFactory.oneMsg());
				}else if("2".equals(content)){
					message = MessageFactory.initTextMessage(toUserName, fromUserName, MessageFactory.twoMsg());
				}else if("3".equals(content)){
					message = MessageFactory.initNewsMessage(toUserName, fromUserName);
				}else if("4".equals(content)){
					JSONObject jsonObject=WeixinUtil.getUserInfo(WeixinThread.accessToken.getAccess_token(), fromUserName);
					message=MessageFactory.initTextMessage(toUserName, fromUserName, jsonObject.toString());
//				}else if("?".equals(content) || "��".equals(content)){
				}else{
					message = MessageFactory.initTextMessage(toUserName, fromUserName, MessageFactory.subscibeMsg());
				}
				
//				TextMessage textMessage = new TextMessage();
//				textMessage.setFromUserName(toUserName);
//				textMessage.setToUserName(fromUserName);
//				textMessage.setMsgType("text");
//				textMessage.setCreateTime(new Date().getTime());
//				textMessage.setContent("�����͵���Ϣ��:"+content);				
//				message = MessageUtil.textMessageToXML(textMessage);
//				System.out.println(message);
				
			}else if(MessageFactory.MESSAGE_EVENT.equals(msgType)){
				String eventType = map.get("Event");
				System.out.println("\neventType:"+eventType);
				if (MessageFactory.MESSAGE_SUBSCRIBE.equals(eventType)){
					//�û���ע�����󷵻ص�����
					message = MessageFactory.initTextMessage(toUserName, fromUserName, MessageFactory.subscibeMsg());
				}else if (MessageFactory.MESSAGE_CLICK.equals(eventType)){					
					// 1���¼�KEYֵ���봴���Զ���˵�ʱָ����KEYֵ��Ӧ  
                    String eventKey = map.get("EventKey");  
                    if (eventKey.equals("11")) {  
//                    	message = MessageUtil.initNewsMessage(toUserName, fromUserName);
//                    	message = "�˵�1�������";  
//                    	message=MessageUtil.initTextMessage(toUserName, fromUserName, message);
                    	//��ȡ��ǰ�û���Ϣ�����͸��û�
                    	JSONObject jsonObject=WeixinUtil.getUserInfo(WeixinThread.accessToken.getAccess_token(), fromUserName);
    					message=MessageFactory.initTextMessage(toUserName, fromUserName, jsonObject.toString());
                    } else {
					//2�������˵�
                    	message = MessageFactory.initTextMessage(toUserName, fromUserName, MessageFactory.subscibeMsg());
                    }
                    //3����ʾ�û���Ϣ
//					JSONObject jsonObject=WeixinUtil.getUserInfo(myToken, fromUserName);
//					message=MessageUtil.initTextMessage(toUserName, fromUserName, jsonObject.toString());
				}else if (MessageFactory.MESSAGE_VIEW.equals(eventType)) {
					message = map.get("EventKey");
					message = "<![CDATA["+message+"]]>";
					//���ﲻ��õ��ظ�
					message=MessageFactory.initTextMessage(toUserName, fromUserName, message);
				}else if (MessageFactory.MESSAGE_SCANCODE.equals(eventType)) {
					message = map.get("EventKey");
					//���ﲻ��õ��ظ�
					message=MessageFactory.initTextMessage(toUserName, fromUserName, "ɨ���¼��������EventKey��"+message);
//				}else if (MessageUtil.MESSAGE_LOCATION.equals(eventType)) {
//					message = map.get("Latitude");
//					message=MessageUtil.initTextMessage(toUserName, fromUserName, "����λ��γ�� ��"+message);
				}
			}else if(MessageFactory.MESSAGE_LOCATION.equals(msgType)){
				message = map.get("Label");
            	message=MessageFactory.initTextMessage(toUserName, fromUserName,"��ȡ����λ�ñ������" + message);
			}
			
			
			out.print(message);
			System.out.println("3���ظ�΢�ŷ���������Ϣ�壺\n"+message);
			System.out.println("-->--->---->----->��Ϣ����------����-------------");
			
		} catch (DocumentException e) {
			e.printStackTrace();
		}finally{
			out.close();
		}
		
		
	}

}
