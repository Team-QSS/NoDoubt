package qss.nodoubt.game.level;

import qss.nodoubt.game.object.Background;

public class InGameLevel extends GameLevel{
	private enum InGameState {
		Idle, SetOrder, RollDice, CallDoubt, StepOther, ProcessItem, UI
	}
	private InGameState m_GameState;
	
	public InGameLevel() {
		addObject(new Background("InGameBackground"));
	}

	@Override
	public void update(float deltaTime) {
		updateObjects(deltaTime);
		
	}

}
