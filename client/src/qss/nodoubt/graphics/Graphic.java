package qss.nodoubt.graphics;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;

import org.lwjgl.opengl.GL;

import org.joml.*;

public class Graphic {
	private static Graphic s_Instance = null;
	
	private Matrix4f m_OrthoMatrix;
	private int m_Program;
	private float[] m_OrthoMatrixBuffer = new float[16];
	
	public static Graphic getInstance() {
		if(s_Instance == null) {
			s_Instance = new Graphic();
		}
		
		return s_Instance;
	}
	
	private Graphic() {
		GL.createCapabilities();
		m_OrthoMatrix = new Matrix4f().ortho(-960, 960, -540, 540, 0, 10);
		m_Program = glCreateProgram();
		Shader.loadShader(m_Program, "./shader/");
		glUseProgram(m_Program);
	}
	
	/**
	 * 각 프레임별 초기화 수행
	 * 매 프레임마다 무언가가 그려지기 전에 수행되어야 함
	 */
	public void beginDraw() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glUseProgram(m_Program);
		glClearColor(0.0f, 1.0f, 1.0f, 1.0f);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glUniformMatrix4fv(4, false, m_OrthoMatrix.get(m_OrthoMatrixBuffer));
	}
	
	/**
	 * 
	 * @return 직교투영행렬 리턴
	 */
	public Matrix4f getOrtho() {
		return m_OrthoMatrix;
	}
	
}
