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
	 * �삤釉뚯젥�듃 �벑濡�
	 * �뿬湲곗뿉 �벑濡앺빐�빞 �궘�젣�맆 �븣 �삤釉뚯젥�듃�룄 �젙�긽�쟻�쑝濡� �궘�젣�맖. �븘留덈룄
	 * @param obj �벑濡앺븷 �삤釉뚯젥�듃
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
	 * �벑濡앺븳 �삤釉뚯젥�듃 �궘�젣
	 * @param obj �궘�젣�븷 �삤釉뚯젥�듃
	 */
	protected final void removeObject(GameObject obj) {
		obj.destroyObject();
		m_ObjectList.remove(obj);
	}
	
	/**
	 * �젅踰� �궘�젣�맆�븣 �샇異쒕릺�뒗 �븿�닔
	 * �뿏吏꾩씠 �샇異쒗븯�땲 �궗�슜�븯吏� 留� 寃�
	 */
	public final void destroyLevel() {
		Input input = Input.getInstance();
		if(m_KeyListener != null) {
			input.removeKeyListener(m_KeyListener);
		}
		if(m_MouseListener != null) {
			input.removeMouseListener(m_MouseListener);
		}
		
//		Iterator<GameObject> i = m_ObjectList.iterator();
//		while(i.hasNext()){
//			i.remove();
//		}
		
		ArrayList<GameObject> tempList = new ArrayList<GameObject>(m_ObjectList);
		for(GameObject obj : tempList) {
			removeObject(obj);
		}
	}
	
	/**
	 * �씠踰ㅽ듃 由ъ뒪�꼫 �꽕�젙
	 * �쁽�옱 �몢踰덊샇異쒗븯硫� �긽�깭 �씠�긽�븯�땲 �븳踰덈쭔 �샇異쒗븷寃�
	 * @param key �궎蹂대뱶 由ъ뒪�꼫, �뾾�쑝硫� null�꽔�쑝�뀍
	 * @param mouse 留덉슦�뒪 踰꾪듉 由ъ뒪�꽣, �뾾�쑝硫� null�꽔�쑝�뀍
	 */
	protected final void setEventListener(KeyListener key, MouseListener mouse) {
		Input input = Input.getInstance();
		if(key != null) {
			input.addKeyListener(key);
			m_KeyListener = key;
		}
		if(mouse != null) {
			input.addMouseListener(mouse);
			m_MouseListener = mouse;
		}
	}
	
}
