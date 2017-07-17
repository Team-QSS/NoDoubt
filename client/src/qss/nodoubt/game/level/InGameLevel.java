package qss.nodoubt.game.level;

import qss.nodoubt.game.object.*;

public class InGameLevel extends GameLevel{
	
	private Background m_GameField = null;	//게임이 진행될 보드를 의미한다.
	
	private enum InGameState {
		IDLE, SETORDER, ROLLDICE, CALLDOUBT, STEPOTHER, PROCESSITEM, 
	}
	public InGameLevel() {
		m_GameField = new Background("InGameBackground");
		addObject(m_GameField);
	}

	@Override
	public void update(float deltaTime) {
		updateObjects(deltaTime);
		
	}

}
