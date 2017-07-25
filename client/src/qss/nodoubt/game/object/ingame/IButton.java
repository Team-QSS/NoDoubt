package qss.nodoubt.game.object.ingame;

import qss.nodoubt.game.object.GameObject;
import qss.nodoubt.input.Input;

import static org.lwjgl.glfw.GLFW.*;

import org.joml.Vector2f;

public class IButton extends GameObject{
	private interface ClickListener {
		void onClick();
	}
	
	private ClickListener m_Listener;
	
	private String m_TextureName;

	public IButton(int n, ClickListener listener) {
		super("IButton" + n, 1);
		m_Listener = listener;
		m_TextureName = "IButton" + n;
		
		setPosition(-777, 405 - (n - 1) * 135);
		setEventListener(null, (action, button) -> {
			if(isMouseOnButton()) {
				if(action == GLFW_PRESS) {
					focus();
				}else if(action == GLFW_RELEASE) {
					click();
				}
			}
			if(action == GLFW_RELEASE) {
				unfocus();
			}
		});
		
		
	}
	
	public IButton(String type, ClickListener listener) {
		super("IButton" + type, 1);
		m_Listener = listener;
		m_TextureName = "IButton" + type;
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
					click();
				}
			}
			if(action == GLFW_RELEASE) {
				unfocus();
			}
		});
	}

	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub
		
	}
	
	private boolean isMouseOnButton() {
		Vector2f mousePosition = Input.getInstance().getCursorPosition();
		Vector2f position = getPosition();
		
		if(Math.abs(mousePosition.x - position.x) <= 111) {
			if(Math.abs(mousePosition.y - position.y) <= 83) {
				return true;
			}
		}
		return false;
	}
	
	private void focus() {
		setTexture(m_TextureName + "F");
	}
	
	private void unfocus() {
		setTexture(m_TextureName);
	}
	
	private void click() {
		m_Listener.onClick();
	}
	
}