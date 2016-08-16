package message;


import java.util.Map;

import net.sf.json.JSONObject;

public class TemplateMessage {
	private String touser;
	private String template_id;
	private String url;
	private String topcolor;
	private String data = "{'User':{'value':'06月07日 19时24分','color':'#173177'},'CardNumber': {'value':'0426','color':'#173177'}";

	
	public String getTouser() {
		return touser;
	}
	public void setTouser(String touser) {
		this.touser = touser;
	}
	public String getTemplate_id() {
		return template_id;
	}
	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTopcolor() {
		return topcolor;
	}
	public void setTopcolor(String topcolor) {
		this.topcolor = topcolor;
	}




	
	
}
