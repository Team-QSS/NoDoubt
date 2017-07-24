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
		addFont("../res/font/NanumR", "fontR");
		addFont("../res/font/NanumB", "fontB");
	}
	
	public void addFont(String path, String name) {
		TextureManager.getInstance().addTexture(name, path + ".png");
		Font font = new Font(path + ".fnt", name);
		m_Fonts.put(name, font);
	}
	
	public Font getFont(String name) {
		return m_Fonts.get(name);
	}
}
