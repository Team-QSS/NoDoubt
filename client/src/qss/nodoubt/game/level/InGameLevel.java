package qss.nodoubt.game.level;

import static org.lwjgl.glfw.GLFW.*;

import java.util.Random;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.json.simple.JSONObject;

import protocol.Protocol;
import qss.nodoubt.game.GameState;
import qss.nodoubt.game.object.Background;
import qss.nodoubt.game.object.ingame.*;
import qss.nodoubt.network.Network;

import room.Room;
import room.User;
import util.KeyValue;
import util.Util;

public class InGameLevel extends GameLevel{
	private static final Vector3f UI_COLOR = new Vector3f(0x80/255f, 0x43/255f, 0x1b/255f);
	private static final Random RANDOM = new Random();
	private static final Vector3f[] m_Colors = new Vector3f[]{
			new Vector3f(0xff / 255f, 0x39 / 255f, 0x39 / 255f), 
			new Vector3f(0x23 / 255f, 0x75 / 255f, 0xeb / 255f), 
			new Vector3f(0x36 / 255f, 0x8c / 255f, 0x49 / 255f), 
			new Vector3f(0xff / 255f, 0xf4 / 255f, 0x1f / 255f), 
			new Vector3f(0xff / 255f, 0xff / 255f, 0xff / 255f), 
			new Vector3f(0xa9 / 255f, 0x24 / 255f, 0xff / 255f)
	};
	
	private enum State {
		DICEROLL, DECLARE, DOUBT, STEPPUSH
	}
	
	public class TurnInfo
	{
		public String name;
		public char color;
		public User user;
	}
	
	private GameBoard m_Board;
	private DiceResultPanel m_DiceResultPanel;
	private Bike[] m_Bikes = new Bike[6];
	
	private float m_AcSeconds = 0.0f;
	private int m_AcMinites = 0;
	
	private State m_State = State.DICEROLL;
	private int m_Turn;
	private TurnInfo m_TurnInfo[];
	private boolean m_IsAnimating = false;
	
	private int m_DiceResult = 0;
	private int m_DeclareNum = 0;
	
	private boolean m_IsTabPushed = false;
	private TabPanel m_TabPanel;
	private TabLabel m_TabLabels[] = new TabLabel[6];
	
	private double m_RoomID;
	
	private IButton m_StepButton = null;
	private IButton m_PushButton = null;
	
	private Room m_Room;
	
	private boolean m_IsInitialized;
	
	private int m_PlayerCount = 0;
	
	/**
	 * @param roomID 방 식별번호
	 */
	public InGameLevel(double roomID) {
		addObject(new Background("InGameBackground"));
		for(int i = 1; i <= 6; i++) {
			final int t = i;
			addObject(new IButton(i, () -> declare(t)));
			m_TabLabels[i - 1] = null;
		}
		addObject(new IButton("Doubt", () -> doubt()));
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
			if(action == GLFW_PRESS) {
				if(key == GLFW_KEY_R) m_Board.moveBike(0, 1);
				if(key == GLFW_KEY_B) m_Board.moveBike(1, 1);
				if(key == GLFW_KEY_G) m_Board.moveBike(2, 1);
				if(key == GLFW_KEY_Y) m_Board.moveBike(3, 1);
				if(key == GLFW_KEY_W) m_Board.moveBike(4, 1);
				if(key == GLFW_KEY_P) m_Board.moveBike(5, 1);
				if(key == GLFW_KEY_TAB) {
					m_IsTabPushed = true;
					addObject(m_TabPanel = new TabPanel());
					for(int i = 0; i < 6; i++)
					{
						if(m_TurnInfo[i] != null) addObject(m_TabLabels[i] = new TabLabel(m_TurnInfo[i].name, m_TurnInfo[i].color));
					}
				}
				
			}else if(action == GLFW_RELEASE) {
				if(key == GLFW_KEY_TAB) {
					m_IsTabPushed = false;
					removeObject(m_TabPanel);
					for(int i = 0; i < 6; i++)
					{
						if(m_TabLabels[i] != null) removeObject(m_TabLabels[i]);
						m_TabLabels[i] = null;
					}
				}
			}
		}, null);
		
		m_DiceResultPanel.setResult(5);
		
		m_IsInitialized = false;
		m_RoomID=roomID;
		m_Turn = 0;
		
		networkInit();
	}
	
	private void networkInit(){
		JSONObject msg=Util.packetGenerator(Protocol.DUMMY_PACKET);
		Network.getInstance().pushMessage(msg);
		//초기화
		msg=Util.packetGenerator(Protocol.GET_ROOM_DATA, new KeyValue("RoomID",m_RoomID));
		Network.getInstance().pushMessage(msg);
	}
	
	@Override
	public void update(float deltaTime) {
		JSONObject msg = Network.getInstance().pollMessage();
		if(msg != null) {
			protocolProcess(msg);
		}
		
		updateObjects(deltaTime);
		updateTime(deltaTime);
		
		drawTextCall("fontB11", "Turn of", new Vector2f(465, 401), UI_COLOR);
		drawTextCall("fontB11", "Result is", new Vector2f(465, -302), UI_COLOR);
		m_Board.update(deltaTime);
		
		if(m_IsAnimating && m_Board.isIdle()) {
			m_IsAnimating = false;
		}
		
		if(m_Board.getState().isConflict) {
			m_State = State.STEPPUSH;
			if(m_StepButton != null && isMyTurn()) {
				addObject(m_StepButton = new IButton("Step", () -> step()));
				addObject(m_PushButton = new IButton("Push", () -> push()));
			}
		}
		
		if(m_IsInitialized) {
			drawTextCall("fontB11", m_TurnInfo[m_Turn].name, new Vector2f(465, 347), m_Colors[m_TurnInfo[m_Turn].user.getRoomIndex()]);
		}
		
	}
	
	private void protocolProcess(JSONObject data){
		System.out.println(data);
		
		switch((String)data.get("Protocol")){
		
			case Protocol.DUMMY_PACKET:{
			}break;
			
			case Protocol.GET_ROOM_DATA:{
				m_Room=Network.gson.fromJson((String)data.get("Room"), Room.class);
				m_IsInitialized = true;
				setRoomData();
			}break;
			
			case Protocol.DECLARE_REPORT:{
				recieveDeclare((Integer) data.get("Value"));
			}break;
			
			case Protocol.DOUBT_CHECK:{
				recieveDoubtCheck();
			}break;
			
			default:{
				System.out.println("unknownProtocol");
				System.out.println(data);
			}break;
		
		}
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
		if(m_State.equals(State.DICEROLL) && !m_IsTabPushed){
			Random r = RANDOM;
			int n = r.nextInt(6) + 1;
			m_DiceResultPanel.setResult(n);
			m_DiceResult = n;
		}
	}
	
	private void moveBike(int n, int movingDistance) {
		if(m_State.equals(State.DICEROLL) && !m_IsAnimating) {
			m_Board.moveBike(n, movingDistance);
			m_IsAnimating = true;
		}
	}
	
	private void declare(int n) {
		if(m_State.equals(State.DECLARE) && isMyTurn() && !m_IsTabPushed) {
			JSONObject msg = new JSONObject();
			
			msg.put("Protocol", "DeclareRequest");
			msg.put("Value", n);
			
			System.out.println(msg.toJSONString());
			
			Network.getInstance().pushMessage(msg);
			m_DeclareNum = n;
			m_State = State.DOUBT;
		}
	}
	
	private void recieveDeclare(int n) {
		m_State = State.DOUBT;
		m_DeclareNum = n;
		m_DiceResultPanel.setResult(n);
	}
	
	private boolean isMyTurn() {
		return m_TurnInfo[m_Turn].name.equals(GameState.getInstance().m_Me);
	}
	
	private void doubt() {
		if(m_State.equals(State.DOUBT) && isMyTurn() && !m_IsTabPushed) {
			JSONObject msg = new JSONObject();
			msg.put("Protocol", "DoubtRequest");
			Network.getInstance().pushMessage(msg);
		}
	}
	
	private void recieveDoubtCheck() {
		JSONObject msg = new JSONObject();
		msg.put("Protocol", "DoubtResult");
		if(m_DiceResult == m_DeclareNum) {
			msg.put("Result", false);
		}else {
			msg.put("Result", true);
		}
		Network.getInstance().pushMessage(msg);
	}
	
	private void step() {
		removeObject(m_PushButton);
		removeObject(m_StepButton);
		m_PushButton = null;
		m_StepButton = null;
		
		JSONObject msg = new JSONObject();
	}
	
	private void push() {
		removeObject(m_PushButton);
		removeObject(m_StepButton);
		m_PushButton = null;
		m_StepButton = null;
		
		JSONObject msg = new JSONObject();
	}
	
	private void setRoomData() {
		m_TurnInfo = new TurnInfo[6];
		
		for(int i = 0; i < 6; i++)
		{
			m_TurnInfo[i] = null;
		}
		
		for(String name : m_Room.list.keySet())
		{
			m_TurnInfo[m_Room.list.get(name).getRoomIndex()] = new TurnInfo();
			m_TurnInfo[m_Room.list.get(name).getRoomIndex()].user = m_Room.list.get(name);
			m_TurnInfo[m_Room.list.get(name).getRoomIndex()].name = name;
			m_TurnInfo[m_Room.list.get(name).getRoomIndex()].color = getColorCharacter(m_Room.list.get(name).getRoomIndex());
		}
		
		m_PlayerCount = m_Room.list.size();
		
		for(int i = 0; i < 6; i++)
		{
			if(m_TurnInfo[i] != null) 
			{
				System.out.println(m_TurnInfo[i].name + " : " + m_TurnInfo[i].color + "Color");
			}else
			{
				removeObject(m_Bikes[i]);
			}
		}
	}
	
	private void goNextTurn() {
		do {
			m_Turn += 1;
			m_Turn %= 6;
		}while (m_TurnInfo[m_Turn] == null);
	}
	
	private char getColorCharacter(int color)
	{
		switch(color)
		{
		case 0: return 'R';
		case 1: return 'B';
		case 2: return 'G';
		case 3: return 'Y';
		case 4: return 'W';
		case 5: return 'P';
		}
		System.out.println("0~5이외의 숫자로 오류 판정 시도");
		return 'F';
	}
}
