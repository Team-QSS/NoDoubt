package qss.nodoubt.input;

public interface KeyListener {
	
	/**
	 * @param action GLFW_PRESS, GLFW_RELEASE, GLFW_REPEAT 중 하나
	 * @param key GLFW_KEY_들중 하나
	 */
	void invoke(int action, int key);
	
}
