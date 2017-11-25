package qss.nodoubt.game.object.ingame;

import qss.nodoubt.game.object.GameObject;

public class PushPanel extends GameObject{
	private float m_AcTime = 0;
	
	public PushPanel() {
		super("PushPanel", 0);
		
	}

	@Override
	public void update(float deltaTime) {
		m_AcTime += deltaTime;
		
		if(m_AcTime > 2) {
			setTexture("Blank");
		}else
		{
			setTexture("PushPanel");
		}
	}
	
	public void show() {
		m_AcTime = 0;
	}

}

