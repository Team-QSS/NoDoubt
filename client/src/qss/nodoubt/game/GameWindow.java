package qss.nodoubt.game;

import org.lwjgl.glfw.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class GameWindow {
	private GameWindow() { }
	private static long monitor;
	private static long window;
	private static GLFWErrorCallback errorCallback = null;
	
	/**
	 * 윈도우 초기화 및 생성
	 * 이 메소드를 실행 한 이후부터 윈도우가 생성됨
	 * 
	 * @exception IllegalStateException glfw초기화에 실패했을 때 호출
	 * @exception RuntimeException 윈도우 생성에 실패했을 때 호출
	 */
	public static void initialize() {
		errorCallback = GLFWErrorCallback.createPrint(System.err);
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
	
	/**
	 *  윈도우 종료
	 */
	public static void shutdown() {
		glfwTerminate();
		glfwSetErrorCallback(null);
		errorCallback.free();
		errorCallback = null;
	}

	/**
	 * 윈도우 입력과 입력장치 입력 받음
	 * 이 메소드가 호출 된 뒤 Input클래스에 넣은 Callback들이 호출됨
	 */
	public static void pollEvents() {
		glfwPollEvents();
	}
	
	/**
	 * 윈도우 업데이트
	 * 프레임 등이 갱신됨
	 */
	public static void updateWindow() {
		glfwSwapBuffers(window);
	}
	
	/**
	 * 윈도우가 닫힐지 여부를 확인
	 * @return true일시 닫힐 예정, false일시 닫히지 않을 예정
	 */
	public static boolean getWindowShouldClose() {
		return glfwWindowShouldClose(window);
	}
	
	/**
	 * Input클래스에서 콜백메소드 지정용
	 * @param keyCallback 키보드 콜백 메소드
	 * @param mouseCallback 마우스버튼 콜백 메소드
	 */
	public static void setCallback(GLFWKeyCallback keyCallback, GLFWMouseButtonCallback mouseCallback) {
		glfwSetKeyCallback(window, keyCallback);
		glfwSetMouseButtonCallback(window, mouseCallback);
	}
}