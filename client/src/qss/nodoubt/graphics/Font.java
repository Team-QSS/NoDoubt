package qss.nodoubt.graphics;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.stb.STBTTAlignedQuad;
import org.lwjgl.stb.STBTTBakedChar;
import org.lwjgl.system.MemoryStack;

import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.stb.STBTruetype.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.BufferUtils.createIntBuffer;
import static org.lwjgl.opengl.GL11.*;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import qss.nodoubt.utils.FileUtils;

public class Font {
	private STBTTBakedChar.Buffer m_Buffer;
	private Texture m_Texture;
	private int m_Size;
	private IntBuffer m_IndexBuffer;
	private float[] m_Vertices;
	private float[] m_UVs;
	private float[] m_WorldMatrixBuffer = new float[16];
	
	Font(int size) {
		m_Buffer = FileUtils.loatFontFile("../res/font/font.ttf", size);
		m_Texture = TextureManager.getInstance().getTexture("Font" + size);
		m_Size = size;
				
		int indices[] = new int[]{
				0, 1, 2,
				0, 2, 3
		};
		m_IndexBuffer = createIntBuffer(indices.length);
		m_IndexBuffer.put(indices);
		m_IndexBuffer.flip();
	}
	
	void draw(Vector2f pos, String str) {
		try (MemoryStack stack = stackPush()) {
			FloatBuffer x = stack.floats(0.0f);
			FloatBuffer y = stack.floats(0.0f);

			STBTTAlignedQuad q = STBTTAlignedQuad.mallocStack(stack);
			
			glUniform4f(5, 1.0f, 1.0f, 0.0f, 1.0f);

			for (int i = 0; i < str.length(); i++) {
					char c = str.charAt(i);
					stbtt_GetBakedQuad(m_Buffer, 1024, 1024, c - 32, x, y, q, true);

	                m_Vertices = new float[]{
	                		q.x0(), q.y0(), 0.0f,
	                		q.x1(), q.y0(), 0.0f,
	                		q.x1(), q.y1(), 0.0f,
	                		q.x0(), q.y1(), 0.0f
	                };
	                
	                m_UVs = new float[] {
	                		q.s0(), q.t0(),
	                		q.s1(), q.t0(),
	                		q.s1(), q.t1(),
	                		q.s0(), q.t1()
	                };
	                
	                m_Texture.bind();
	                
	                glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, m_Vertices);
	        		glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, m_UVs);
	        		
	        		Matrix4f world = new Matrix4f().translate(new Vector3f(pos, 0.0f));
					glUniformMatrix4fv(3, false, world.get(m_WorldMatrixBuffer));
					
					glEnableVertexAttribArray(0);
					glEnableVertexAttribArray(1);
					glDrawElements(GL_TRIANGLES, m_IndexBuffer);
					glDisableVertexAttribArray(0);
					glDisableVertexAttribArray(1);
			}
	    }
	}
}