package qss.nodoubt.game.object;

import qss.nodoubt.input.*;

public class Button extends GameObject {
	private boolean m_Actived = false;
	private String textureName = null;
	private String activetextureName = null;
	
	public interface ClickListener {
		void onClick();
	}
	
	private ClickListener m_Listener = null;

	public Button(String textureName, String activetextureName, float x, float y, KeyListener key, MouseListener mouse) {
		super(textureName, 0);
		setPosition(x, y);
		setEventListener(key, mouse);
		this.textureName = textureName;
		this.activetextureName = activetextureName;
	}
	
	public void setListner(ClickListener listener){
		m_Listener = listener;
	}

	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub

	}
	
	public void click() {
		if(m_Listener != null){
			m_Listener.onClick();
		}
	}
	
	/**
	 * 활성화된 버튼인지 체크
	*/
	public boolean isActived(){
		return m_Actived;				//활성화된 버튼
	}
	
	/**
	 * 버튼 활성화 상태 반전
	 */
	public void toggle(){
		if(m_Actived){
			setTexture(textureName);
			m_Actived = false;
		}
		else{
			setTexture(activetextureName);
			m_Actived = true;
		}
	}
}
