package qss.nodoubt.input;

import java.util.*;

import org.lwjgl.glfw.*;

import qss.nodoubt.game.GameWindow;

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
}
