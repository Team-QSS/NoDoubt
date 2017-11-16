package qss.nodoubt.util;


import org.json.simple.JSONObject;

public class Util {
	
	//모든 서버상의 로그는 이함수들을 이용하여 출력
	public static void printLog(Object content){
		System.out.println(content);
	}
	
	public static JSONObject packetGenerator(String protocol,KeyValue ...kvs){
		JSONObject data=new JSONObject();
		data.put("Protocol", protocol);
		for(KeyValue kv:kvs){
			data.put(kv.key, kv.value);
		}
		return data;
	}
	
	public static String printJSONLookSimple(String json){
		int depth=0;
		StringBuilder sb=new StringBuilder();
		for(int i=0;i<json.length();i++){
			
			if(json.charAt(i)=='}'){
				sb.append('\n');
				depth--;
				for(int j=0;j<depth;j++){
					sb.append("  ");
				}
			}
			
			sb.append(json.charAt(i));
			
			if(json.charAt(i)=='{'){
				sb.append('\n');
				depth++;
				for(int j=0;j<depth;j++){
					sb.append("  ");
				}
			}
			
			if(json.charAt(i)==','){
				sb.append('\n');
				for(int j=0;j<depth;j++){
					sb.append("  ");
				}
			}
		}
		return sb.toString();
	}
	
}
