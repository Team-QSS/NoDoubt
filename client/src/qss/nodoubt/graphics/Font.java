package qss.nodoubt.graphics;

import java.util.Map;
import java.util.TreeMap;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import qss.nodoubt.utils.FileUtils;

import static org.lwjgl.opengl.GL20.*;

public class Font {
	private Map<String, String> m_Info = new TreeMap<String, String>();
	private Map<String, String> m_Common = new TreeMap<String, String>();
	private Map<String, Integer> m_Chars[] = new TreeMap[128];
	private Map<Integer, Integer> m_Kernings = new TreeMap<Integer, Integer>();
	private VertexArray m_VertexArrays[] = new VertexArray[128];
	
	private String m_TextureName;
	
	Font(String path, String textureName) {
		String fnt = FileUtils.loadTextFile(path);
		String lines[] = fnt.split("\n");
		for(String line : lines) {
			String args[] = line.split(" ");
			if(args[0].equals("info")) {
				readInfo(args);
			}else if(args[0].equals("common")) {
				readCommon(args);
			}else if(args[0].equals("char")) {
				readChar(args);
			}else if(args[0].equals("kerning")) {
				readKerning(args);
			}
		}
		
		m_TextureName = textureName;
	}
	
	public int getStringWidth(String str) {
		int length = 0;
		
		for(int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			Map<String, Integer> tags = m_Chars[c];
			
			int width = tags.get("width");
			int xoffset = tags.get("xoffset");
			
			if(i == str.length() - 1) {
				length += width;
			}else {
				length += xoffset;
			}
			
			if(i != 0) {
				int prev = str.charAt(i - 1);
				int cur = str.charAt(i);
				int key = prev << 8 + cur;
				
				int kerning = 0;
				
				if(m_Kernings.containsKey(key)) {
					kerning = m_Kernings.get(key);
				}
				
				length += kerning;
			}
		}
		return length;
	}
	
	public void draw(Vector2f pos, String str, Vector3f color) {
		Matrix4f translate = new Matrix4f();
		float x = pos.x;
		float y = pos.y;
		
		TextureManager.getInstance().getTexture(m_TextureName).bind();
		glUniform4f(5, color.x, color.y, color.z, 1.0f);
		for(int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			Map<String, Integer> tags = m_Chars[c];
			
			int width = tags.get("width");
			int height = tags.get("height");
			int xoffset = tags.get("xoffset");
			int yoffset = tags.get("yoffset");
			
			float drawx = x + width/2 + xoffset;
			float drawy = y - height/2 - yoffset;
			
			if(i != 0) {
				int prev = str.charAt(i - 1);
				int cur = str.charAt(i);
				int key = prev << 8 + cur;
				
				int kerning = 0;
				
				if(m_Kernings.containsKey(key)) {
					kerning = m_Kernings.get(key);
				}
				
				drawx += kerning;
			}
			
			translate.identity().translate(drawx, drawy, 0.0f);
			
			m_VertexArrays[c].draw(translate);
			
			x += tags.get("xadvance");
		}
	}
	
	private void readInfo(String args[]) {
		for(String arg : args) {
			if(arg.equals("info")) {
				continue;
			}
			
			String key = arg.split("=")[0];
			String val = arg.split("=")[1];
			
			if(key.equals("padding")) {
				String vals[] = val.split(",");
				m_Info.put(key + "up", vals[0]);
				m_Info.put(key + "right", vals[1]);
				m_Info.put(key + "down", vals[2]);
				m_Info.put(key + "left", vals[3]);
				continue;
			}else if(key.equals("spacing")) {
				String vals[] = val.split(",");
				m_Info.put(key + "horizontal", vals[0]);
				m_Info.put(key + "vertical", vals[1]);
				continue;
			}
			
			val.replaceAll("\"", "");
			
			m_Info.put(key, val);
		}
	}
	
	private void readCommon(String args[]) {
		for(String arg : args) {
			if(arg.equals("common")) {
				continue;
			}
			
			String key = arg.split("=")[0];
			String val = arg.split("=")[1];
			
			val.replaceAll("\"", "");
			
			m_Common.put(key, val);
		}
	}
	
	private void readChar(String args[]) {
		int id = 0;
		for(String arg : args) {
			if(arg.equals("char")) {
				continue;
			}
			
			if(arg.equalsIgnoreCase("\"")) {
				continue;
			}
			
			String key = arg.split("=")[0];
			String val = arg.split("=")[1];
			
			if(key.equals("count")) {
				return;
			}
			if(key.equals("id")) {
				id = Integer.parseInt(val);
				m_Chars[id] = new TreeMap<String, Integer>();
			}
			
			val.replaceAll("\"", "");
			if(key.equals("letter")) {
				continue;
			}
			m_Chars[id].put(key, Integer.parseInt(val));
		}
		
		Map<String, Integer> c = m_Chars[id];
		int width = c.get("width");
		int height = c.get("height");
		
		int tw = Integer.parseInt(m_Common.get("scaleW"));
		int th = Integer.parseInt(m_Common.get("scaleH"));
		
		int x = c.get("x");
		int y = c.get("y");
		m_VertexArrays[id] = new VertexArray(width, height,
				((float) y) / th, ((float) y + height) / th,
				((float) x) / tw, ((float) x + width) / tw);
	}
	
	private void readKerning(String args[]) {
		int first = 0;
		int second = 0;
		int amount = 0;
		
		
		for(String arg : args) {
			if(arg.equals("kerning")) {
				continue;
			}
			
			String key = arg.split("=")[0];
			String val = arg.split("=")[1];
			
			if(key.equals("count")) {
				return;
			}
			
			if(key.equals("first")) {
				first = Integer.parseInt(val);
			}else if(key.equals("second")) {
				second = Integer.parseInt(val);
			}else if(key.equals("amount")) {
				amount = Integer.parseInt(val);
			}
		}
		
		int key = (first << 8) + second;
		
		m_Kernings.put(key, amount);
	}
}