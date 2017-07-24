package qss.nodoubt.game.level;

import org.joml.Vector2f;
import org.joml.Vector3f;
import qss.nodoubt.game.object.*;

public class InGameLevel extends GameLevel{
	
	private enum InGameState {
		IDLE, SETORDER, ROLLDICE, CALLDOUBT, STEPOTHER, PROCESSITEM, 
	}
	public InGameLevel() {
		addObject(new Background("InGameBackground"));
	}

	@Override
	public void update(float deltaTime) {
		updateObjects(deltaTime);
		drawTextCall("fontB", "Hello world!", new Vector2f(0.0f, 0.0f), new Vector3f(0.0f, 1.0f, 1.0f));
	}

}
