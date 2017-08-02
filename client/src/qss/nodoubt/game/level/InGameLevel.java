package qss.nodoubt.game.level;

import org.joml.Vector2f;
import org.joml.Vector2i;
import org.joml.Vector3f;

import qss.nodoubt.game.object.*;
import qss.nodoubt.game.object.ingame.*;
import qss.nodoubt.network.Message;
import qss.nodoubt.network.Network;

import static org.lwjgl.glfw.GLFW.*;

import java.util.Random;

public class InGameLevel extends GameLevel{
	private static final Vector3f UI_COLOR = new Vector3f(0x9a / 255f, 0x6f / 255f, 0x52 / 255f);
	private static final Random RANDOM = new Random();
	
	private enum State {
		DICEROLL, DECLARE, DOUBT, STEPPUSH, ANIMATING
	}
	
	private GameBoard m_Board;
	private DiceResultPanel m_DiceResultPanel;
	private Bike[] m_Bikes = new Bike[6];
	private TurnLabel m_TurnLabel;
	
	private float m_AcSeconds = 0.0f;
	private int m_AcMinites = 0;
	
	private State m_State = State.DICEROLL;
	private int m_Turn;
	
	private int m_DiceResult = 0;
	
	public InGameLevel() {
		addObject(new Background("InGameBackground"));
		addObject(new IButton(1, () -> declare(1)));
		addObject(new IButton(2, () -> declare(2)));
		addObject(new IButton(3, () -> declare(3)));
		addObject(new IButton(4, () -> declare(4)));
		addObject(new IButton(5, () -> declare(5)));
		addObject(new IButton(6, () -> declare(6)));
		addObject(new IButton("Doubt", () -> {}));
		addObject(new IButton("Roll", () -> rollDice()));
		addObject(m_DiceResultPanel = new DiceResultPanel());
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
				moveBike(0, 4);
			} else if(action == GLFW_PRESS && key == GLFW_KEY_B) {
				moveBike(1, 4);
			} else if(action == GLFW_PRESS && key == GLFW_KEY_G) {
				moveBike(2, 4);
			} else if(action == GLFW_PRESS && key == GLFW_KEY_Y) {
				moveBike(3, 4);
			} else if(action == GLFW_PRESS && key == GLFW_KEY_W) {
				moveBike(4, 4);
			} else if(action == GLFW_PRESS && key == GLFW_KEY_P) {
				moveBike(5, 4);
			} else if(action == GLFW_PRESS && key == GLFW_KEY_Q) {
				m_Board.push(0);
			} else if(action == GLFW_PRESS && key == GLFW_KEY_Z) {
				m_TurnLabel.nextTurn();
			}
		}, null);
		
		m_DiceResultPanel.setResult(5);
		
		m_TurnLabel = new TurnLabel(new String[] {
				"Test1", "Test2", "Test3", "Test4", "Test5", "Test6"
		}, 6);
	}
	
	@Override
	public void update(float deltaTime) {
		updateObjects(deltaTime);
		updateTime(deltaTime);
		drawTextCall("fontB11", "Turn of", new Vector2f(465, 401), UI_COLOR);
		drawTextCall("fontB11", "Result is", new Vector2f(465, -302), UI_COLOR);
		m_Board.update(deltaTime);
		
		if(m_State.equals(State.ANIMATING) && m_Board.isIdle()) {
			m_State = State.DICEROLL;
		}
		
		if(m_Board.getState().isConflict) {
			System.out.println("Conflict pos : " + m_Board.getState().conflictPos);
			System.out.println("Conflict bike : " + m_Board.getState().conflictBike);
		}
		
		drawTextCall("fontB11", m_TurnLabel.getID(), new Vector2f(465, 347), m_TurnLabel.getColor());
		
	}
	
	private void updateTime(float deltaTime) {
		m_AcSeconds += deltaTime;
		while(m_AcSeconds > 60) {
			m_AcMinites += 1;
			m_AcSeconds -= 60;
			m_AcMinites = m_AcMinites % 60;
			
		}
		if(m_AcSeconds >= 10) {
			drawTextCall("fontB21", m_AcMinites + " : " + ((int)m_AcSeconds) , new Vector2f(578, 520), UI_COLOR);
		}else {
			drawTextCall("fontB21", m_AcMinites + " : 0" + ((int)m_AcSeconds) , new Vector2f(578, 520), UI_COLOR);
		}
	}
	
	private void rollDice() {
		if(m_State.equals(State.DICEROLL)){
			Random r = RANDOM;
			int n = r.nextInt(6) + 1;
			m_DiceResultPanel.setResult(n);
			m_DiceResult = n;
		}
	}
	
	private void moveBike(int n, int movingDistance) {
		if(m_State.equals(State.DICEROLL)) {
			m_Board.moveBike(n, movingDistance);
			m_State = State.ANIMATING;
		}
	}
	
	private void declare(int n) {
		if(m_State.equals(State.DECLARE)) {
			Message msg = new Message();
			msg.setProtocol("DeclareRequest");
			msg.addIntValue("Value", n);
			msg.addStringValue("ID", "tempID");
			
			Network.getInstance().pushMessage(msg);
		}
	}
}
