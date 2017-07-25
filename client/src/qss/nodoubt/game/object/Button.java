package qss.nodoubt.game.object;

import qss.nodoubt.input.*;

public class Button extends GameObject {
	private int m_HalfWidth = 150;
	private int m_HalfHeight = 72;
	
	private boolean m_Actived = false;
	private boolean m_Pressedin = false;
	private String textureName = null;
	private String activetextureName = null;
	
	public Button(String textureName, String activetextureName, float x, float y) {
		super(textureName, 0);
		setPosition(x, y);
		this.textureName = textureName;
		this.activetextureName = activetextureName;
	}

	public Button(String textureName, String activetextureName, float x, float y, int width, int height) {
		super(textureName, 0);
		setPosition(x, y);
		this.textureName = textureName;
		this.activetextureName = activetextureName;
		m_HalfWidth = width/2;
		m_HalfHeight = height/2;
	}
	
	public void setListener(KeyListener key, MouseListener mouse){
		setEventListener(key, mouse);
	}

	/**
	 * m_Pressedin 변수를 설정하는 메서드이다.
	 * @param b 마우스 클릭이 버튼 속에서 이루어졌다면 true, 아니면 false
	 */
	public void setPressedin(boolean b){
		m_Pressedin = b;
	}
	/**
	 * @return m_Pressedin의 결과를 리턴한다. 
	 */
	public boolean getPressedin(){
		return m_Pressedin;
	}
	
	/**
	 * 마우스가 버튼 위에 있는지 아닌지를 리턴하는 메서드이다.
	 */
	public boolean onButton(float mouseX, float mouseY){
		if((mouseX >= getPosition().x-m_HalfWidth)&&(mouseX <= getPosition().x+m_HalfWidth)){
			if((mouseY >= getPosition().y-m_HalfHeight)&&(mouseY <= getPosition().y+m_HalfHeight)){
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
