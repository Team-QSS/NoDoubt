package qss.nodoubt.network;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Message {
	JSONObject m_Json = null;
	
	public Message() {
		
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
}
