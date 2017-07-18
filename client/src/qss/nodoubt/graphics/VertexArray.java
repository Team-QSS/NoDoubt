package qss.nodoubt.graphics;

import static org.lwjgl.BufferUtils.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

import org.joml.*;

import java.nio.IntBuffer;

public class VertexArray {
	private float [] m_Vertices;
	private final int [] m_Indices;
	private float [] m_UVs;
	private IntBuffer m_IndexBuffer;
	private float [] m_WorldMatrixBuffer = new float[16];
	private static float[] s_Color = new float[]{0.0f, 0.0f, 0.0f, 0.0f};
	
	/**
	 * 사각형 생성
	 * @param width 사각형 너비
	 * @param height 사각형 높이
	 * @param top 텍스쳐 위쪽 좌표
	 * @param bottom 텍스쳐 아래쪽 좌표
	 * @param left 텍스쳐 왼쪽 좌표
	 * @param right 텍스쳐 오른쪽 좌표
	 */
	public VertexArray(float width, float height, float top, float bottom, float left, float right) {
		m_Vertices = new float[]{
			width / 2, height / 2, 0.0f,
			width / 2, -height / 2, 0.0f,
			-width / 2, height / 2, 0.0f,
			-width / 2, -height / 2, 0.0f
		};
			
		m_Indices = new int[]{
			0, 2, 1,
			2, 3, 1
		};
			
		m_UVs = new float[]{
				right, top,
				right, bottom,
				left, top,
				left, bottom
		};
		
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, m_Vertices);
		glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, m_UVs);
		
		m_IndexBuffer = createIntBuffer(m_Indices.length);
		m_IndexBuffer.put(m_Indices);
		m_IndexBuffer.flip();
	}
	
	/**
	 * 사각형 크기 재설정
	 * @param width 너비
	 * @param height 높이
	 */
	public void setSize(float width, float height) {
		m_Vertices = new float[]{
			width / 2, height / 2, 0.0f,
			width / 2, -height / 2, 0.0f,
			-width / 2, height / 2, 0.0f,
			-width / 2, -height / 2, 0.0f
		};
		
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, m_Vertices);
	}
	
	/**
	 * 텍스쳐 좌표 재설정
	 * @param top
	 * @param bottom
	 * @param left
	 * @param right
	 */
	public void setTexPos(float top, float bottom, float left, float right) {
		m_UVs = new float[]{
			right, top,
			right, bottom,
			left, top,
			left, bottom
		};
		
		glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, m_UVs);
	}
	
	/**
	 * 사각형 그리기
	 * @param world 모델공간에서 세계공간으로 변환하는 행렬
	 */
	public void draw(Matrix4f world){
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, m_Vertices);
		glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, m_UVs);
		//glUniformMatrix4fv(3, false, world.get(m_WorldMatrix));
		glUniformMatrix4fv(3, false, world.get(m_WorldMatrixBuffer));
		//glUniform4fv(5, s_Color);
		glUniform4f(5, 0.0f, 0.0f, 0.0f, 0.0f);
		
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glDrawElements(GL_TRIANGLES, m_IndexBuffer);
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
	}
}
