package qss.nodoubt.graphics;

import org.joml.Vector2f;
import org.lwjgl.stb.STBTTAlignedQuad;
import org.lwjgl.stb.STBTTBakedChar;
import org.lwjgl.system.MemoryStack;

import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.stb.STBTruetype.*;
import java.nio.FloatBuffer;

import qss.nodoubt.utils.FileUtils;

public class Font {
	private STBTTBakedChar.Buffer m_Buffer;
	private Texture m_Texture;
	private int m_Size;
	
	Font(int size) {
		m_Buffer = FileUtils.loatFontFile("", size);
		m_Texture = TextureManager.getInstance().getTexture("Font" + size);
		m_Size = size;
	}
	
	void draw(Vector2f pos, String str) {
		try (MemoryStack stack = stackPush()) {
			FloatBuffer x = stack.floats(0.0f);
			FloatBuffer y = stack.floats(0.0f);

			STBTTAlignedQuad q = STBTTAlignedQuad.mallocStack(stack);

			for (int i = 0; i < str.length(); i++) {
					char c = str.charAt(i);
					stbtt_GetBakedQuad(m_Buffer, 1024, 1024, c - 32, x, y, q, true);
			}
	    }
	}
}