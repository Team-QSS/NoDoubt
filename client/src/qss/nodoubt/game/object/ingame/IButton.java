package qss.nodoubt.game.object.ingame;

import qss.nodoubt.game.object.GameObject;

import static org.lwjgl.glfw.GLFW.*;

public class IButton extends GameObject{
	private interface ClickListener {
		void onClick();
	}
	
	private ClickListener m_Listener;

	public IButton(int n, ClickListener listener) {
		super("IButton" + n, 1);
		setPosition(-777, 405 - (n - 1) * 135);
		setEventListener(null, (action, button) -> {
			if(isMouseOnButton()) {
				if(action == GLFW_PRESS) {
					focus();
				}else if(action == GLFW_RELEASE) {
					unfocus();
					click();
				}
			}
		});
		
		m_Listener = listener;
	}
	
	public IButton(String type, ClickListener listener) {
		super("IButton" + type, 1);
		if(type.equals("Doubt")) {
			setPosition(-777, -405);
		}else if(type.equals("Roll")) {
			setPosition(714, -204);
		}
		
		setEventListener(null, (action, button) -> {
			if(isMouseOnButton()) {
				if(action == GLFW_PRESS) {
					focus();
				}else if(action == GLFW_RELEASE) {
					unfocus();
					click();
				}
			}
		});
		
		m_Listener = listener;
	}

	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub
		
	}
	
	private boolean isMouseOnButton() {
		return true;
	}
	
	private void focus() {
		
	}
	
	private void unfocus() {
		
	}
	
	private void click() {
		m_Listener.onClick();
	}
	
}
