package qss.nodoubt.graphics;

import static org.lwjgl.BufferUtils.*;
import static org.lwjgl.opengl.GL11.*;
import static qss.nodoubt.utils.FileUtils.*;
import java.nio.*;


public class Texture {
	private int m_Width, m_Height;
	private int m_Texture;
	
	
	public Texture(String path) {
		m_Texture = load(path);
	}
	
	public Texture(int texture, int width, int height) {
		m_Texture = texture;
		m_Width = width;
		m_Height = height;
	}
	
	public Texture(ByteBuffer buffer, int width, int height) {
		int result = glGenTextures();
		
		m_Width = width;
		m_Height = height;
		
		glBindTexture(GL_TEXTURE_2D, result);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_ALPHA, m_Width, m_Height, 0, GL_ALPHA, GL_UNSIGNED_BYTE, buffer);
	}
	
	public int load(String path) {
		ByteBuffer imageBuffer = null;
		IntBuffer x = createIntBuffer(1);
		IntBuffer y = createIntBuffer(1);
		IntBuffer comp = createIntBuffer(1);
		imageBuffer = loadImageFile(path, x, y, comp);

		m_Width = x.get(0);
		m_Height = y.get(0);
		
		int result = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, result);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, m_Width, m_Height, 0, GL_RGBA, GL_UNSIGNED_BYTE, imageBuffer);
		return result;
	}
	
	public void bind() {
		glBindTexture(GL_TEXTURE_2D, m_Texture);
	}
	
	public void unbind() {
		glBindTexture(GL_TEXTURE_2D, 0);
	}
	
	public int getWidth() {
		return m_Width;
	}
	
	public int getHeight() {
		return m_Height;
	}
}