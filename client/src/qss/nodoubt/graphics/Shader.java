package qss.nodoubt.graphics;

import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glDeleteShader;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;

import qss.nodoubt.utils.FileUtils;

public class Shader {
	
	public static void loadShader(int program, String path){
		int vertexShader = glCreateShader(GL_VERTEX_SHADER);
		int fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
		
		String vertexSource = FileUtils.loadTextFile(path + "VertexShader");
		String fragmentSource = FileUtils.loadTextFile(path + "FragmentShader");
		
		glShaderSource(vertexShader, vertexSource);
		glShaderSource(fragmentShader, fragmentSource);
		
		glCompileShader(vertexShader);
		glCompileShader(fragmentShader);
		
		glAttachShader(program, vertexShader);
		glAttachShader(program, fragmentShader);
		
		glLinkProgram(program);
		
		glDeleteShader(vertexShader);
		glDeleteShader(fragmentShader);
	}

}
