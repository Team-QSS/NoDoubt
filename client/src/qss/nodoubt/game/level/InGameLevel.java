package qss.nodoubt.game.level;

import org.joml.Vector2f;
import org.joml.Vector2i;
import org.joml.Vector3f;

import qss.nodoubt.game.object.*;
import qss.nodoubt.game.object.ingame.*;

import static org.lwjgl.glfw.GLFW.*;

public class InGameLevel extends GameLevel{
	private static final Vector3f UI_COLOR = new Vector3f(0x9a / 255f, 0x6f / 255f, 0x52 / 255f);
	private static final Vector3f RED = new Vector3f(0xff / 255f, 0x39 / 255f, 0x39 / 255f);
	private static final Vector3f BLUE = new Vector3f(0x23 / 255f, 0x75 / 255f, 0xeb / 255f);
	private static final Vector3f GREEN = new Vector3f(0x36 / 255f, 0x8c / 255f, 0x49 / 255f);
	private static final Vector3f YELLOW = new Vector3f(0xff / 255f, 0xf4 / 255f, 0x1f / 255f);
	private static final Vector3f WHITE = new Vector3f(0xff / 255f, 0xff / 255f, 0xff / 255f);
	private static final Vector3f PURPLE = new Vector3f(0xa9 / 255f, 0x24 / 255f, 0xff / 255f);
	
	private GameBoard m_Board;
	private DiceResult m_DiceResult;
	private Bike[] m_Bikes = new Bike[6];
	
	public InGameLevel() {
		addObject(new Background("InGameBackground"));
		for(int i = 1; i <= 6; i++) {
			addObject(new IButton(i, null));
		}
		addObject(new IButton("Doubt", null));
		addObject(new IButton("Roll", null));
		addObject(m_DiceResult = new DiceResult());
		addObject(new Stump());
		addObject(m_Bikes[0] = new Bike('R'));
		addObject(m_Bikes[1] = new Bike('B'));
		addObject(m_Bikes[2] = new Bike('G'));
		addObject(m_Bikes[3] = new Bike('Y'));
		addObject(m_Bikes[4] = new Bike('W'));
		addObject(m_Bikes[5] = new Bike('P'));
		
		m_Board = new GameBoard(6, m_Bikes);
		
		setEventListener((action, key) -> {
			if(action == GLFW_PRESS && key == GLFW_KEY_R) {
				m_Board.moveBike(0, 4);
			} else if(action == GLFW_PRESS && key == GLFW_KEY_B) {
				m_Board.moveBike(1, 4);
			} else if(action == GLFW_PRESS && key == GLFW_KEY_G) {
				m_Board.moveBike(2, 4);
			} else if(action == GLFW_PRESS && key == GLFW_KEY_Y) {
				m_Board.moveBike(3, 4);
			} else if(action == GLFW_PRESS && key == GLFW_KEY_W) {
				m_Board.moveBike(4, 4);
			} else if(action == GLFW_PRESS && key == GLFW_KEY_P) {
				m_Board.moveBike(5, 4);
			} else if(action == GLFW_PRESS && key == GLFW_KEY_Q) {
				m_Board.push(0);
			}
		}, null);
		
		m_DiceResult.setResult(5);
	}

	@Override
	public void update(float deltaTime) {
		updateObjects(deltaTime);
		drawTextCall("fontB21", "59 : 59", new Vector2f(578, 520), UI_COLOR);
		drawTextCall("fontB11", "Turn of", new Vector2f(465, 401), UI_COLOR);
		drawTextCall("fontB11", "Result is", new Vector2f(465, -302), UI_COLOR);
		m_Board.update(deltaTime);
		
		if(m_Board.getState().isConflict) {
			System.out.println("Conflict pos : " + m_Board.getState().conflictPos);
			System.out.println("Conflict bike : " + m_Board.getState().conflictBike);
		}
	}
}
