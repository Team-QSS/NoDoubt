package qss.nodoubt.game.level;

import qss.nodoubt.game.object.*;
import java.util.*;

import org.joml.Matrix4f;

public class GameLevel {
	private Set<GameObject> m_ObjectSet = null;
	
	public GameLevel() {
		m_ObjectSet = new HashSet<GameObject>();
	}
	
	protected final void update(float deltaTime) {
		for(GameObject obj : m_ObjectSet) {
			obj.update(deltaTime);
		}
	}
	
	protected final void draw(Matrix4f viewOrtho) {
		for(GameObject obj : m_ObjectSet) {
			obj.draw(viewOrtho);
		}
	}
	
	protected final void addObject(GameObject obj) {
		m_ObjectSet.add(obj);
	}
	
	protected final void removeObject(GameObject obj) {
		m_ObjectSet.remove(obj);
	}
}
