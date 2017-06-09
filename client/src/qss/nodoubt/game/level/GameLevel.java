package qss.nodoubt.game.level;

import qss.nodoubt.game.object.*;
import java.util.*;

public abstract class GameLevel {
	private Set<GameObject> m_ObjectSet = null;
	
	public GameLevel() {
		m_ObjectSet = new HashSet<GameObject>();
	}
	
	public abstract void update(float deltaTime);
	
	public abstract void draw();
	
	/**
	 * 
	 * @param deltaTime
	 */
	protected final void updateObjects(float deltaTime) {
		for(GameObject obj : m_ObjectSet) {
			obj.update(deltaTime);
		}
	}
	
	protected final void drawObjects() {
		for(GameObject obj : m_ObjectSet) {
			obj.draw();
		}
	}
	
	protected final void addObject(GameObject obj) {
		m_ObjectSet.add(obj);
	}
	
	protected final void removeObject(GameObject obj) {
		m_ObjectSet.remove(obj);
	}
	
	public final void destroyLevel() {
		for(GameObject obj : m_ObjectSet) {
			removeObject(obj);
		}
	}
	
}
