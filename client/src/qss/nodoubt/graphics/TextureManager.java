package qss.nodoubt.graphics;

import java.util.HashMap;
import java.util.Map;

public class TextureManager {
	private static TextureManager s_Instance = null;
	private Map<String, Texture> m_Textures = new HashMap<String, Texture>();
	
	/**
	 * 싱글턴 객체 얻음
	 * @return 객체 리턴
	 */
	public static TextureManager getInstance() {
		if(s_Instance == null) {
			s_Instance = new TextureManager();
		}
		return s_Instance;
	}
	
	/**
	 * 텍스쳐 얻음
	 * @param name 텍스쳐 이름
	 * @return 텍스쳐
	 */
	public Texture getTexture(String name) {
		if(m_Textures.containsKey(name)) {
			return m_Textures.get(name);
		}else {
			return null;
		}
	}
	
	/**
	 * 텍스쳐 추가
	 * @param name 텍스쳐 이름
	 * @param path 텍스쳐 경로
	 * @return 추가한 텍스쳐
	 */
	public Texture addTexture(String name, String path) {
		Texture tex = new Texture(path);
		
		m_Textures.put(name, tex);
		return tex;
	}
	
}
