package qss.nodoubt;

import java.io.BufferedWriter;
import java.io.IOException;

import org.json.simple.parser.JSONParser;

import com.google.gson.Gson;

public class Network {
	
	public static JSONParser jsonParser=new JSONParser();
	public static Gson gson=new Gson();
	
	public static void send(BufferedWriter writer,Object content){
		try {
			writer.write(content.toString());
			writer.newLine();
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
