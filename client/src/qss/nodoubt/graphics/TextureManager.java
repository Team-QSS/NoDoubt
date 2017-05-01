package qss.nodoubt.graphics;

import java.util.HashMap;
import java.util.Map;

public class TextureManager {
	private static TextureManager s_Instance = null;
	private Map<String, Texture> m_Textures = new HashMap<String, Texture>();
	
	public static TextureManager getInstance() {
		if(s_Instance == null) {
			s_Instance = new TextureManager();
		}
		return s_Instance;
	}
	
	public Texture getTexture(String path) {
		if(m_Textures.containsKey(path)) {
			return m_Textures.get(path);
		}else {
			return null;
		}
	}
	
}
