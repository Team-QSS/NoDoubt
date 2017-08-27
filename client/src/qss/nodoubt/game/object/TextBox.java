package qss.nodoubt.game.object;

import org.joml.Vector2f;
import org.joml.Vector3f;

/*LoadingLevel 에서 출력될 방 목록들이 이 클래스의 객체들이다.*/

public class TextBox extends GameObject{

	public StringBuffer m_Text = null;
	private StringBuffer m_Star = null;
	
	private Vector2f m_Location;
	private float m_Width;
	private boolean m_IsStar;
	private String m_Label;
	private Vector3f m_Color;
	
	public TextBox(float depth, float x, float y, float width, float startX, float startY, boolean isStar, String label, Vector3f color) {
		super("Blank", depth);
		// TODO Auto-generated constructor stub
		setPosition(x,y);
		m_Width = width;
		m_Location.x = startX;
		m_Location.y = startY;
		m_IsStar = isStar;
		m_Label = label;
		m_Color = color;
		
		m_Text = new StringBuffer();
		if(isStar){
			m_Star = new StringBuffer();
		}
	}

	@Override
	public void update(float deltaTime) {
		if(m_Label != null){
			if(m_Text.length() == 0){
				drawTextCall("fontR11", m_Label, m_Location, new Vector3f(0x82/255f, 0x82/255f, 0x82/255f));
			}
		}
		if(!m_IsStar){
			drawTextCall("fontR11", m_Text.toString(), m_Location, m_Color);
		}
		else{
			if(m_Star.length() > m_Text.length()){
				for(int i = 0; i < m_Star.length() - m_Text.length(); i++){
					m_Star.deleteCharAt(m_Star.length()-1);
				}
			}
			else if(m_Star.length() < m_Text.length()){
				for(int i = 0; i < m_Text.length() - m_Star.length(); i++){
					m_Star.append("*");
				}
			}
			drawTextCall("fontR11", m_Star.toString(), m_Location, m_Color);
		}
	}
	
}
