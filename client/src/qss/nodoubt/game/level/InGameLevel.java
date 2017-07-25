package qss.nodoubt.game.level;

import org.joml.Vector2f;
import org.joml.Vector3f;

import qss.nodoubt.game.object.*;
import qss.nodoubt.game.object.ingame.*;

public class InGameLevel extends GameLevel{
	
	public InGameLevel() {
		addObject(new Background("InGameBackground"));
		for(int i = 1; i <= 6; i++) {
			addObject(new IButton(i, null));
		}
		addObject(new IButton("Doubt", null));
		addObject(new IButton("Roll", null));
		addObject(new DiceResult());
		addObject(new Stump());
	}

	@Override
	public void update(float deltaTime) {
		updateObjects(deltaTime);
		drawTextCall("fontB21", "59 : 59", new Vector2f(578, 520), new Vector3f(0.6015625f, 0.43359375f, 0.3203125f));
	}

}
