package message;
/**
 * 文本消息对象
 * @author F
 *
 */
public class TextMessage extends BaseMessage {
	
	private String Content;
	private String MsgId;
	
	public String getContent() {
		return Content;
	}
	public void setContent(String content) {
		Content = content;
	}
	public String getMsgId() {
		return MsgId;
	}
	public void setMsgId(String msgId) {
		MsgId = msgId;
	}

	
//	 <xml>
//	 <ToUserName><![CDATA[toUser]]></ToUserName>
//	 <FromUserName><![CDATA[fromUser]]></FromUserName> 
//	 <CreateTime>1348831860</CreateTime>
//	 <MsgType><![CDATA[text]]></MsgType>
//	 <Content><![CDATA[this is a test]]></Content>
//	 <MsgId>1234567890123456</MsgId>
//	 </xml>
	
	
	
	
}
