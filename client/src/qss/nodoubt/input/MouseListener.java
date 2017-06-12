package qss.nodoubt.input;

public interface MouseListener {
	
	/**
	 * @param action GLFW_PRESS, GLFW_RELEASE, GLFW_REPEAT 중 하나
	 * @param button GLFW_BUTTON_중 하나 아마도
	 */
	void invoke(int action, int button);
	
}
