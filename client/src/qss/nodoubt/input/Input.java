package qss.nodoubt.input;

import java.util.*;

import org.joml.Vector2f;
import org.joml.Vector2i;
import org.lwjgl.glfw.*;

import qss.nodoubt.game.GameWindow;

import static org.lwjgl.glfw.GLFW.*;

public class Input {
	private static Input s_Instance = null;
	
	private Set<KeyListener> m_KeyListenerSet = new HashSet<KeyListener>();
	private Set<MouseListener> m_MouseListenerSet = new HashSet<MouseListener>();
	
	public static Input getInstance() {
		if(s_Instance == null) {
			s_Instance = new Input();
		}
		return s_Instance;
	}
	
	private Input() {
		GameWindow gameWindow = GameWindow.getInstance();
		gameWindow.setCallback(new GLFWKeyCallback(){

			@Override
			public void invoke(long window, int key, int scancode, int action, int mods) {
				for(KeyListener listener : m_KeyListenerSet) {
					listener.invoke(action, key);
				}
			}
			
		}, new GLFWMouseButtonCallback(){

			@Override
			public void invoke(long window, int button, int action, int mods) {
				for(MouseListener listener : m_MouseListenerSet) {
					listener.invoke(action, button);
				}
			}
			
		});
	}
	
	public void addKeyListener(KeyListener listener) {
		if(!m_KeyListenerSet.contains(listener)) {
			m_KeyListenerSet.add(listener);
		}
	}
	
	public void addMouseListener(MouseListener listener) {
		if(!m_MouseListenerSet.contains(listener)) {
			m_MouseListenerSet.add(listener);
		}
	}
	
	public void removeKeyListener(KeyListener listener) {
		if(m_KeyListenerSet.contains(listener)) {
			m_KeyListenerSet.remove(listener);
		}
	}
	
	public void removeMouseListener(MouseListener listener) {
		if(m_MouseListenerSet.contains(listener)) {
			m_MouseListenerSet.remove(listener);
		}
	}
	
	public Vector2f getCursorPosition() {
		GameWindow window = GameWindow.getInstance();
		double xpos[] = new double[1];
		double ypos[] = new double[1];
		glfwGetCursorPos(window.getWindow(), xpos, ypos);
		
		Vector2i size = window.getSize();
		
		double x = xpos[0] / size.x;
		double y = ypos[0] / size.y;
		
		x = x * 2 - 1;
		y = y * -2 + 1;
		
		x *= 1920;
		y *= 1080;
		
		
		return new Vector2f((float) x, (float) y);
	}
}
