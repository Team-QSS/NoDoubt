package qss.nodoubt.game.level;

import org.joml.Vector2f;
import org.joml.Vector3f;

import qss.nodoubt.game.object.*;
import qss.nodoubt.game.object.ingame.*;

public class InGameLevel extends GameLevel{
	private static final Vector3f UI_COLOR = new Vector3f(0x9a / 255f, 0x6f / 255f, 0x52 / 255f);
	private static final Vector3f RED = new Vector3f(0xff / 255f, 0x39 / 255f, 0x39 / 255f);
	private static final Vector3f BLUE = new Vector3f(0x23 / 255f, 0x75 / 255f, 0xeb / 255f);
	private static final Vector3f GREEN = new Vector3f(0x36 / 255f, 0x8c / 255f, 0x49 / 255f);
	private static final Vector3f YELLOW = new Vector3f(0xff / 255f, 0xf4 / 255f, 0x1f / 255f);
	private static final Vector3f WHITE = new Vector3f(0xff / 255f, 0xff / 255f, 0xff / 255f);
	private static final Vector3f PURPLE = new Vector3f(0xa9 / 255f, 0x24 / 255f, 0xff / 255f);
	
	private class Board {
		Vector2f pos;
		int bikeNum = 0;
		Bike[] bikes;
	}
	
	private Board[][] m_Board;
	private DiceResult m_DiceResult;
	
	public InGameLevel() {
		m_Board = new Board[6][];
		for(int i = 0; i < 6; i++) {
			m_Board[i] = new Board[6];
			for(int j = 0; j < 6; j++) {
				m_Board[i][j] = new Board();
				m_Board[i][j].pos = new Vector2f(-480 + 160 * i, 399 - 160 * j);
			}
		}

		addObject(new Background("InGameBackground"));
		for(int i = 1; i <= 6; i++) {
			addObject(new IButton(i, null));
		}
		addObject(new IButton("Doubt", null));
		addObject(new IButton("Roll", null));
		addObject(m_DiceResult = new DiceResult());
		addObject(new Stump());
		addObject(new Bike('R', m_Board[5][5].pos));
		addObject(new Bike('B', m_Board[5][5].pos.add(0, 15)));
		addObject(new Bike('G', m_Board[5][5].pos.add(0, 15)));
		addObject(new Bike('Y', m_Board[5][5].pos.add(0, 15)));
		addObject(new Bike('W', m_Board[5][5].pos.add(0, 15)));
		addObject(new Bike('P', m_Board[5][5].pos.add(0, 15)));
		m_Board[5][5].pos.add(0, -75);
	}

	@Override
	public void update(float deltaTime) {
		updateObjects(deltaTime);
		drawTextCall("fontB21", "59 : 59", new Vector2f(578, 520), UI_COLOR);
		drawTextCall("fontB11", "Turn of", new Vector2f(465, 401), UI_COLOR);
		drawTextCall("fontB11", "Result is", new Vector2f(465, -302), UI_COLOR);
	}

}
