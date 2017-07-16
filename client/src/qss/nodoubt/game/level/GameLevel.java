package qss.nodoubt.game.level;

import qss.nodoubt.game.object.*;
import qss.nodoubt.input.Input;
import qss.nodoubt.input.KeyListener;
import qss.nodoubt.input.MouseListener;

import java.util.*;

public abstract class GameLevel {
	private List<GameObject> m_ObjectList = null;
	private KeyListener m_KeyListener = null;
	private MouseListener m_MouseListener = null;
	private boolean m_IsActive = false;
	
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
		obj.destroyObject();
		m_ObjectList.remove(obj);
	}
	
	/**
	 * 레벨 삭제될때 호출되는 함수
	 * 엔진이 호출하니 사용하지 말 것
	 */
	public final void destroyLevel() {
		Input input = Input.getInstance();
		if(m_KeyListener != null) {
			input.removeKeyListener(m_KeyListener);
		}
		if(m_MouseListener != null) {
			input.removeMouseListener(m_MouseListener);
		}
		
		for(GameObject obj : m_ObjectList) {
			obj.destroyObject();
		}
		
	}
	
	/**
	 * 이벤트 리스너 설정
	 * 현재 두번호출하면 상태 이상하니 한번만 호출할것
	 * @param key 키보드 리스너, 없으면 null넣으셈
	 * @param mouse 마우스 버튼 리스터, 없으면 null넣으셈
	 */
	protected final void setEventListener(KeyListener key, MouseListener mouse) {
		Input input = Input.getInstance();
		
		m_KeyListener = key;
		m_MouseListener = mouse;
		
		if(key != null && m_IsActive) {
			input.addKeyListener(key);
		}
		if(mouse != null && m_IsActive) {
			input.addMouseListener(mouse);
		}
	}
	
	/**
	 * 레벨 초기화
	 * 레벨이 처음 동작하기 전 프레임의 마지막에 호출
	 * 엔진이 호출하니 쓰지 말 것
	 */
	public final void act() {
		m_IsActive = true;
		setEventListener(m_KeyListener, m_MouseListener);
		
		for(GameObject obj : m_ObjectList) {
			obj.act();
		}
	}
}
