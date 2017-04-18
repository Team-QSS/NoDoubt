package qss.nodoubt.game;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class GameWindow {
	private GameWindow() { }
	private static long monitor;
	private static long window;
	private static GLFWErrorCallback errorCallback = GLFWErrorCallback.createPrint(System.err);
	
	
	public static void initialize() {
		glfwSetErrorCallback(errorCallback);
		
		if(!glfwInit()) {
			throw new IllegalStateException("Unable to initialize GLFW");
		}
		
		monitor = glfwGetPrimaryMonitor();
		GLFWVidMode vidMode = glfwGetVideoMode(monitor);
		int monitorWidth = vidMode.width();
		int monitorHeight = vidMode.height();
		
		window = glfwCreateWindow(monitorWidth, monitorHeight, "No Doubt", monitor, NULL);
		if(window == NULL) {
			glfwTerminate();
			throw new RuntimeException("Fail to create GLFW window");
		}
		
		glfwMakeContextCurrent(window);
		
		glfwCreateStandardCursor(GLFW_ARROW_CURSOR);
	}
	
	public static void shutdown() {
		glfwSetErrorCallback(null);
		errorCallback.free();
	}

	public static void pollEvents() {
		glfwPollEvents();
	}
	
	public static void updateWindow() {
		glfwSwapBuffers(window);
	}
	
	public static boolean getWindowShouldClose() {
		return glfwWindowShouldClose(window);
	}
}
