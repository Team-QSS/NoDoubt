package qss.nodoubt.game.level;


public class InGameLevel extends GameLevel{
	private enum InGameState {
		Idle, SetOrder, RollDice, CallDoubt, StepOther, ProcessItem, 
	}
	public InGameLevel() {
		
	}

	@Override
	public void update(float deltaTime) {
		updateObjects(deltaTime);
		
	}

}
