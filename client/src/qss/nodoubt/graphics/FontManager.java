package qss.nodoubt.graphics;

import java.util.HashMap;
import java.util.Map;

import org.joml.Vector2f;

public class FontManager {
	private static FontManager s_Instance = null;
	
	private Map<Integer, Font> m_Fonts = new HashMap<Integer, Font>();

	
	public static FontManager getInstance() {
		if(s_Instance == null) {
			s_Instance = new FontManager();
		}
		return s_Instance;
	}
	
	private FontManager() {
		
	}
	
	public void drawString(int size, Vector2f pos, String str) {
		if(!m_Fonts.containsKey(size)) {
			m_Fonts.put(size, new Font(size));
		}
		
		m_Fonts.get(size).draw(pos, str);
	}
}
