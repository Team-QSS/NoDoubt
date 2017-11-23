package qss.nodoubt.game.object.ingame;

import qss.nodoubt.game.object.GameObject;
import qss.nodoubt.sounds.Sound;

public class CountDownPanel extends GameObject{
	
	public interface CountDownListener {
		void onZero();
	}
	
	private CountDownListener m_Listener;
	private float m_RTime;
	private int m_prevN = 0;
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
			
			if(m_RTime > 0) {
				setTexture("CountPanel" + (Math.round(m_RTime + 0.5f)));
				if(m_prevN != Math.round(m_RTime + 0.5f)) Sound.getInstance().play("beep");
				m_prevN = Math.round(m_RTime + 0.5f);
				
			}
			
			
			if(m_RTime < 0) {
				m_RTime = 0;
				m_IsCountDowning = false;
				m_Listener.onZero();
				setTexture("Blank");
			}
			
		}
	}
	
	public void countDownStop() {
		m_RTime = 0;
		m_IsCountDowning = false;
		setTexture("Blank");
	}
	
	public void setCountDown(float seconds) {
		m_RTime = seconds;
		m_prevN = 5;
		Sound.getInstance().play("beep");
		m_IsCountDowning = true;
	}

}
