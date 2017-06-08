package qss.nodoubt.graphics;

import static org.lwjgl.BufferUtils.*;
import static org.lwjgl.opengl.GL11.*;
import static qss.nodoubt.utils.FileUtils.*;
import java.nio.*;


public class Texture {
	private int m_Width, m_Height;
	private int texture;
	
	
	public Texture(String path) {
		texture = load(path);
	}
	
	public int load(String path) {
		ByteBuffer imageBuffer = null;
		IntBuffer x = createIntBuffer(1);
		IntBuffer y = createIntBuffer(1);
		IntBuffer comp = createIntBuffer(1);
		imageBuffer = loadImageFile(path, x, y, comp);
		
		if(imageBuffer == null) {
			System.out.println("aaaa");
		}

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
		glBindTexture(GL_TEXTURE_2D, texture);
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