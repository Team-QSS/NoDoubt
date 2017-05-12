package qss.nodoubt.graphics;

import static org.lwjgl.opengl.GL11.*;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL;

public class Graphic {
	private static Graphic s_Instance = null;
	
	private Matrix4f m_OrthoMatrix;
	
	public static Graphic getInstance() {
		if(s_Instance == null) {
			s_Instance = new Graphic();
		}
		
		return s_Instance;
	}
	
	private Graphic() {
		GL.createCapabilities();
		m_OrthoMatrix = new Matrix4f().ortho(-960, 960, -540, 540, 0, 11);
	}
	
	/**
	 * 각 프레임별 초기화 수행
	 * 매 프레임마다 무언가가 그려지기 전에 수행되어야 함
	 */
	public void beginDraw() {
		glClear(GL_COLOR_BUFFER_BIT);
		glClearColor(0.0f, 1.0f, 1.0f, 0.0f);
	}
	
	/**
	 * 
	 * @return 직교투영행렬 리턴
	 */
	public Matrix4f getOrtho() {
		return m_OrthoMatrix;
	}
	
}
