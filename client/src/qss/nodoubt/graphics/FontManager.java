package qss.nodoubt.graphics;

import java.util.HashMap;
import java.util.Map;

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
	
	public void addFont(String name, String path) {
		TextureManager.getInstance().addTexture(name, path + ".png");
		Font font = new Font(path + ".fnt", name);
		m_Fonts.put(name, font);
	}
	
	public Font getFont(String name) {
		return m_Fonts.get(name);
	}
}
