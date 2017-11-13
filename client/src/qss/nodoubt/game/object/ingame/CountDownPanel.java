package qss.nodoubt.game.object.ingame;

import qss.nodoubt.game.object.GameObject;

public class CountDownPanel extends GameObject{
	public interface CountDownListener {
		void onZero();
	}
	
	private CountDownListener m_Listener;
	private float m_RTime;
	private boolean m_IsCountDowning;

	public CountDownPanel(CountDownListener listener) {
		super("Blank", 1);
		setPosition(0, 0);
		m_IsCountDowning = false;
		m_Listener = listener;
	}

	@Override
	public void update(float deltaTime) {
		if(m_IsCountDowning) {
			m_RTime -= deltaTime;
			
			if(m_RTime < 0) {
				m_RTime = 0;
				m_IsCountDowning = false;
				m_Listener.onZero();
				
			}
			setTexture("CountPanel" + (Math.round(m_RTime + 0.5f)));
		}
	}
	
	public void setCountDown(float seconds) {
		m_RTime = seconds;
		m_IsCountDowning = true;
	}

}
