package qss.nodoubt.game.object.ingame;

import qss.nodoubt.game.object.GameObject;

public class NoDoubtPanel extends GameObject{
	private float m_AcTime = 10;
	
	public NoDoubtPanel() {
		super("NoDoubtPanel", 0);
		
	}

	@Override
	public void update(float deltaTime) {
		m_AcTime += deltaTime;
		
		if(m_AcTime > 2) {
			setTexture("Blank");
		}else
		{
			setTexture("NoDoubtPanel");
		}
	}
	 
	public void show() {
		m_AcTime = 0;
	}

}

