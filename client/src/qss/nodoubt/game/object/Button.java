package qss.nodoubt.game.object;

import qss.nodoubt.input.*;

public class Button extends GameObject {
	private final static int HALFWIDTH = 150;
	private final static int HALFHEIGHT = 72;
	
	private boolean m_Actived = false;
	private String textureName = null;
	private String activetextureName = null;
	
	public Button(String textureName, String activetextureName, float x, float y) {
		super(textureName, 0);
		setPosition(x, y);
		this.textureName = textureName;
		this.activetextureName = activetextureName;
	}
	
	public void setListener(KeyListener key, MouseListener mouse){
		setEventListener(key, mouse);
	}

	public boolean onButton(float mouseX, float mouseY){
		if((mouseX >= getPosition().x-HALFWIDTH)&&(mouseX <= getPosition().x+HALFWIDTH)){
			if((mouseY >= getPosition().y-HALFHEIGHT)&&(mouseY <= getPosition().y+HALFHEIGHT)){
				return true;
			}
			else{
				return false;
			}
		}
		else{
			return false;
		}
	}
	
	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub

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
