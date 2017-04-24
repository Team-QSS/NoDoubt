package qss.nodoubt.graphics;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.opengl.GL;

public class Graphic {
	private static Graphic s_Instance = null;
	
	public static Graphic getInstance() {
		if(s_Instance == null) {
			s_Instance = new Graphic();
		}
		
		return s_Instance;
	}
	
	private Graphic() {
		GL.createCapabilities();
	}
	
	/**
	 * 각 프레임별 초기화 수행
	 * 매 프레임마다 무언가가 그려지기 전에 수행되어야 함
	 */
	public void beginDraw() {
		glClear(GL_COLOR_BUFFER_BIT);
		glClearColor(0.0f, 1.0f, 1.0f, 0.0f);
	}
}
