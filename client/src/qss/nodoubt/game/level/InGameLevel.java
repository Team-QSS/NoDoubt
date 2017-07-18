package qss.nodoubt.game.level;

import qss.nodoubt.game.object.*;

public class InGameLevel extends GameLevel{
	
	private enum InGameState {
		IDLE, SETORDER, ROLLDICE, CALLDOUBT, STEPOTHER, PROCESSITEM, 
	}
	public InGameLevel() {
		addObject(new Background("InGameBackground"));
		addObject(new Label());
	}

	@Override
	public void update(float deltaTime) {
		updateObjects(deltaTime);
		
	}

}
