package qss.nodoubt.network;

import org.json.simple.JSONArray;
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
	
	public Message addStringValue(String key, String value) {
		m_Json.put(key, value);
		return this;
	}

	public Message addIntValue(String key, int value) {
		m_Json.put(key, value);
		return this;
	}
	
	public Message addBoolValue(String key, boolean value) {
		m_Json.put(key, value);
		return this;
	}
	
	public String getStringValue(String key) {
		return (String) m_Json.get(key);
	}
	
	public boolean getBoolValue(String key) {
		return (boolean) m_Json.get(key);
	}
	
	public int getIntValue(String key) {
		return (int) m_Json.get(key);
	}
	
	public JSONArray getList(String key) {
		return (JSONArray) m_Json.get(key);
	}
	
	public String getProtocol() {
		return (String) m_Json.get("Protocol");
	}
}
