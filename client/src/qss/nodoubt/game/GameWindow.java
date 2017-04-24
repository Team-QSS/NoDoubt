package qss.nodoubt.game;

import org.lwjgl.glfw.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class GameWindow {
	private static GameWindow s_Instance  = null;
	private long m_Window;
	private GLFWErrorCallback m_ErrorCallback = null;
	
	/**
	 * @exception IllegalStateException glfw초기화에 실패했을 때 호출
	 * @exception RuntimeException 윈도우 생성에 실패했을 때 호출
	 */
	public static GameWindow getInstance() {
		if(s_Instance == null) {
			s_Instance = new GameWindow();
		}
		return s_Instance;
	}
	
	/**
	 *  윈도우 종료
	 */
	public void shutdown() {
		glfwTerminate();
		glfwSetErrorCallback(null);
		m_ErrorCallback.free();
		m_ErrorCallback = null;
	}
	
	
	private GameWindow() {
		m_ErrorCallback = GLFWErrorCallback.createPrint(System.err);
		glfwSetErrorCallback(m_ErrorCallback);
		
		if(!glfwInit()) {
			throw new IllegalStateException("Unable to initialize GLFW");
		}
		
		long monitor = glfwGetPrimaryMonitor();
		GLFWVidMode vidMode = glfwGetVideoMode(monitor);
		int monitorWidth = vidMode.width();
		int monitorHeight = vidMode.height();
		
		m_Window = glfwCreateWindow(monitorWidth, monitorHeight, "No Doubt", monitor, NULL);
		if(m_Window == NULL) {
			glfwTerminate();
			throw new RuntimeException("Fail to create GLFW window");
		}
		
		glfwMakeContextCurrent(m_Window);
		
		glfwCreateStandardCursor(GLFW_ARROW_CURSOR);
	}
	
	/**
	 * 윈도우 입력과 입력장치 입력 받음
	 * 이 메소드가 호출 된 뒤 Input클래스에 넣은 Callback들이 호출됨
	 */
	public void pollEvents() {
		glfwPollEvents();
	}
	
	/**
	 * 윈도우 업데이트
	 * 프레임 등이 갱신됨
	 */
	public void updateWindow() {
		glfwSwapBuffers(m_Window);
	}
	
	/**
	 * 윈도우가 닫힐지 여부를 확인
	 * @return true일시 닫힐 예정, false일시 닫히지 않을 예정
	 */
	public boolean getWindowShouldClose() {
		return glfwWindowShouldClose(m_Window);
	}
	
	/**
	 * Input클래스에서 콜백메소드 지정용
	 * @param keyCallback 키보드 콜백 메소드
	 * @param mouseCallback 마우스버튼 콜백 메소드
	 */
	public void setCallback(GLFWKeyCallback keyCallback, GLFWMouseButtonCallback mouseCallback) {
		glfwSetKeyCallback(m_Window, keyCallback);
		glfwSetMouseButtonCallback(m_Window, mouseCallback);
	}
}