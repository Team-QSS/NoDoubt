package qss.nodoubt.network;

import org.json.simple.JSONObject;

public class Message {
	JSONObject m_Json;
	
	public Message() {
		
	}
	
	public String toJSONString() {
		return m_Json.toJSONString();
	}
}
