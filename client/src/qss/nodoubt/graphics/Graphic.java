package qss.nodoubt.graphics;

import static org.lwjgl.opengl.GL11.*;

public class Graphic {
	private Graphic() {}
	
	/**
	 * OpenGL초기화
	 */
	public static void initialize() {
		
	}
	
	/**
	 * OpenGL 종료
	 */
	public static void shutdown() {
		
	}
	
	/**
	 * 각 프레임별 초기화 수행
	 * 매 프레임마다 무언가가 그려지기 전에 수행되어야 함
	 */
	public static void beginDraw() {
		glClear(GL_COLOR_BUFFER_BIT);
		glClearColor(0.0f, 1.0f, 1.0f, 0.0f);
	}
}
