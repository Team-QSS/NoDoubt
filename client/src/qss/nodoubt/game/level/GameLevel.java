package qss.nodoubt.game.level;

import qss.nodoubt.game.object.*;
import java.util.*;

public abstract class GameLevel {
	private List<GameObject> m_ObjectList = null;
	
	public GameLevel() {
		m_ObjectList = new ArrayList<GameObject>();
	}
	
	public abstract void update(float deltaTime);
	
	public abstract void draw();
	
	protected final void updateObjects(float deltaTime) {
		for(GameObject obj : m_ObjectList) {
			obj.update(deltaTime);
		}
	}
	
	protected final void drawObjects() {
		for(GameObject obj : m_ObjectList) {
			obj.draw();
		}
	}
	
	/**
	 * 오브젝트 등록
	 * 여기에 등록해야 삭제될 때 오브젝트도 정상적으로 삭제됨. 아마도
	 * @param obj 등록할 오브젝트
	 */
	protected final void addObject(GameObject obj) {
		m_ObjectList.add(obj);
		int i = m_ObjectList.size() - 2;
		for(; i >= 0 && m_ObjectList.get(i).getDepth() < obj.getDepth(); i--) {
			m_ObjectList.set(i + 1, m_ObjectList.get(i));
		}
		m_ObjectList.set(i + 1, obj);
	}
	
	/**
	 * 등록한 오브젝트 삭제
	 * @param obj 삭제할 오브젝트
	 */
	protected final void removeObject(GameObject obj) {
		m_ObjectList.remove(obj);
	}
	
	/**
	 * 레벨 삭제될때 호출되는 함수
	 * 엔진이 호출하니 사용하지 말 것
	 */
	public final void destroyLevel() {
		for(GameObject obj : m_ObjectList) {
			removeObject(obj);
		}
	}
	
}
