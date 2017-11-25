package qss.nodoubt.game.object.ingame;

import qss.nodoubt.game.object.GameObject;

public class StepPanel extends GameObject{
	private float m_AcTime = 0;
	
	public StepPanel() {
		super("StepPanel", 0);
		
	}

	@Override
	public void update(float deltaTime) {
		m_AcTime += deltaTime;
		
		if(m_AcTime > 2) {
			setTexture("Blank");
		}else
		{
			setTexture("StepPanel");
		}
	}
	
	public void show() {
		m_AcTime = 0;
	}

}

