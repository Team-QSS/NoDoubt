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
	
	/**
	 * 오브젝트 등록
	 * 여기에 등록해야 삭제될 때 오브젝트도 정상적으로 삭제됨. 아마도
	 * @param obj 등록할 오브젝트
	 */
	protected final void addObject(GameObject obj) {
		m_ObjectSet.add(obj);
	}
	
	/**
	 * 등록한 오브젝트 삭제
	 * @param obj 삭제할 오브젝트
	 */
	protected final void removeObject(GameObject obj) {
		m_ObjectSet.remove(obj);
	}
	
	/**
	 * 레벨 삭제될때 호출되는 함수
	 * 엔진이 호출하니 사용하지 말 것
	 */
	public final void destroyLevel() {
		for(GameObject obj : m_ObjectSet) {
			removeObject(obj);
		}
	}
	
}
