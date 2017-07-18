package qss.nodoubt.game.level;

import qss.nodoubt.game.object.*;

public class InGameLevel extends GameLevel{
	
	private enum InGameState {
		IDLE, SETORDER, ROLLDICE, CALLDOUBT, STEPOTHER, PROCESSITEM, 
	}
	public InGameLevel() {
<<<<<<< HEAD
		addObject(new Background("InGameBackground"));
		addObject(new Label());
=======
		m_GameField = new Background("Test");
		addObject(m_GameField);
>>>>>>> refs/remotes/origin/master
	}

	@Override
	public void update(float deltaTime) {
		updateObjects(deltaTime);
		
	}

}
