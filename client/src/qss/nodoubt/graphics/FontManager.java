package qss.nodoubt.graphics;

import java.util.HashMap;
import java.util.Map;

import org.joml.Vector2f;

public class FontManager {
	private static FontManager s_Instance = null;
	
	private Map<String, Font> m_Fonts = new HashMap<String, Font>();

	
	public static FontManager getInstance() {
		if(s_Instance == null) {
			s_Instance = new FontManager();
		}
		return s_Instance;
	}
	
	private FontManager() {
		
	}
	
	public void initFont(String path, String name) {
		
	}
	
	public Font getFont(String name) {
		return null;
	}
}
