package qss.nodoubt.network;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Message {
	JSONObject m_Json = null;
	
	public Message() {
		m_Json = new JSONObject();
		m_Json.put("Protocol", "DefaultProtocol");
	}
	
	public Message(String str) {
		try {
			m_Json = (JSONObject) new JSONParser().parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public String toJSONString() {
		if(m_Json != null) {
			return m_Json.toJSONString();
		}else {
			return null;
		}
	}
	
	public Message setProtocol(String protocol) {
		m_Json.replace("Protocol", protocol);
		return this;
	}
	
	public Message addValue(String key, String value) {
		m_Json.put(key, value);
		return this;
	}
}
