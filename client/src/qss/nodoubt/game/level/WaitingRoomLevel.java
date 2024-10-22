package qss.nodoubt.game.level;

import static org.lwjgl.glfw.GLFW.*;

import java.util.ArrayList;

import org.json.simple.JSONObject;

import protocol.Protocol;
import qss.nodoubt.game.Game;
import qss.nodoubt.game.GameState;
import qss.nodoubt.game.object.Background;
import qss.nodoubt.game.object.Button;
import qss.nodoubt.game.object.Player;
import qss.nodoubt.input.Input;
import qss.nodoubt.network.Network;
import room.Room;
import room.User;
import util.KeyValue;
import util.Util;

public class WaitingRoomLevel extends GameLevel{

	private float mouseX;
	private float mouseY;
	
	//각 플레이어의 UI를 출력할 때 사용하는 리스트
	private Player[] m_PlayerList = new Player[6];
	
	private Button m_StartButton;
	private Button m_BackButton;
	private boolean isInitialed = false;
	
//	private Button[] m_PlusButtons; 초대버튼
	private Background m_WaitingRoomBG;
	
	private Room room;

	private double m_Roomid;
	
	/**
	 * CreateRoomLevel에서 방을 만들거나 LoadingLevel에서 방을 클릭하면 호출하게 되는 생성자이다.
	 * @param gamename
	 * 현재 입장한 방의 이름을 나타내는 임시 변수
	 * @param roomid
	 * 현재 입장한 방의 아이디를 나타내는 임시 변수
	 */
	public WaitingRoomLevel(double roomid){
		
		m_StartButton = new Button("GameJoinButton1", "GameJoinButton2", 320, 414);
		m_StartButton.setListener(null,
				(action, button) -> {
						if(button == GLFW_MOUSE_BUTTON_LEFT){
							if(m_StartButton.onButton(mouseX, mouseY)){
								if(action == GLFW_PRESS){
									System.out.println("press");
									m_StartButton.focus();
								}
								if(action == GLFW_RELEASE){
									System.out.println("release");
									if(room.list.size() >= 3) {
										System.out.print(m_PlayerList.length);
										JSONObject msg=Util.packetGenerator(Protocol.START_GAME_REQUEST);
										Network.getInstance().pushMessage(msg);
		//								Game.getInstance().setNextLevel(new InGameLevel(m_Roomid));
									}
									else {
										m_StartButton.unfocus();
									}
								}
							}
							else{
								if(action == GLFW_RELEASE){
									m_StartButton.unfocus();
								}
							}
							
						}
				});
		
		m_BackButton = new Button("BackButton1", "BackButton2", 667, 414);
		m_BackButton.setListener(null,
				(action, button) -> {
					if(button == GLFW_MOUSE_BUTTON_LEFT){
						if(m_BackButton.onButton(mouseX, mouseY)){
							if(action == GLFW_PRESS){
								m_BackButton.focus();
							}
							if(action == GLFW_RELEASE){
								JSONObject msg=Util.packetGenerator(Protocol.QUIT_ROOM_REQUEST, new KeyValue("RoomID",m_Roomid));
								Network.getInstance().pushMessage(msg);
								Game.getInstance().setNextLevel(new LoadingLevel());
							}
						}
						else{
							if(action == GLFW_RELEASE){
								m_BackButton.unfocus();
							}
						}
						
					}
				});
		
		m_WaitingRoomBG = new Background("WaitingBG");
		
		m_Roomid = roomid;
		room = null;
		
//		addObject(m_StartButton);
		addObject(m_BackButton);
		addObject(m_WaitingRoomBG);
		
		networkInit();
	}
	
	private void networkInit(){
		JSONObject msg=Util.packetGenerator(Protocol.DUMMY_PACKET);
		Network.getInstance().pushMessage(msg);
		//초기화
		msg=Util.packetGenerator(Protocol.GET_ROOM_DATA, new KeyValue("RoomID",m_Roomid));
		Network.getInstance().pushMessage(msg);
	}
	
	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub
		mouseX = Input.getInstance().getCursorPosition().x;
		mouseY = Input.getInstance().getCursorPosition().y;
		
		for(int i = 0; i < m_PlayerList.length; i++) {
			if(m_PlayerList[i] != null)
				m_PlayerList[i].update(deltaTime);
		}
		
		JSONObject msg = Network.getInstance().pollMessage();
		if(msg != null) {
			protocolProcess(msg);
		}
		
		/*
		 * 서버에서 정보를 받아 갱신해야 함 
		 * m_Users (User들을 담는 연결리스트)		 
		 */
		updateObjects(deltaTime);
	}
	
	private void protocolProcess(JSONObject data){
		System.out.println(room + "WaitingRoomLevel");
		System.out.println(data + "WaitingRoomLevel");
		switch((String)data.get("Protocol")){
		
		case Protocol.DUMMY_PACKET:{
		}break;
		
		case Protocol.GET_ROOM_DATA:{
			if(!isInitialed) {
				while(room == null) {
					room = Network.gson.fromJson((String)data.get("Room"), Room.class);
				}
				int index = -1;
				for(String key:room.list.keySet()) {				
					User user=room.list.get(key);
					index=user.getRoomIndex();
					m_PlayerList[index] = new Player(user.getID(), index);
					addObject(m_PlayerList[index]);
					addObject(m_PlayerList[index].m_Name);
					addObject(m_PlayerList[index].m_MotorCycle);
				}
				
				if(m_PlayerList[0].m_Name.m_Text.toString().equals(GameState.getInstance().m_Me)) {
					addObject(m_StartButton);		//StartButton 에서 Listener가 작동하지 않는 오류 발생
				}
				isInitialed = true;
			}
		}break;
		
		case Protocol.JOIN_ROOM_RESULT:{
			if(isInitialed) {
				User joinUser = null;
				
				if(joinUser == null) {
					joinUser = Network.gson.fromJson((String)data.get("User"), User.class);
				}
			
				if(room == null) {
					System.out.println("Room이 Null");
				}
				if(joinUser == null) {
					System.out.println("User가 Null");
				}
				
				room.enterUser(joinUser);
				//ui처리
				int i=joinUser.getRoomIndex();
				if(m_PlayerList[joinUser.getRoomIndex()] == null) {
					m_PlayerList[i] = new Player(joinUser.getID(), i);
					System.out.println(m_PlayerList[i].m_Name.m_Text.toString());
					addObject(m_PlayerList[i]);
					addObject(m_PlayerList[i].m_Name);
					addObject(m_PlayerList[i].m_MotorCycle);
					break;
				}else{
					System.err.println("조인룸 오류");
				}
			}
		}break;
		
		case Protocol.QUIT_ROOM_REPORT:{
			if(isInitialed) {
				String quitUserID=(String)data.get("UserID");
				if(room.getUser(quitUserID) != null) {
					room.removeUser(quitUserID);
				}
				
				//ui처리
				for(int i = 0; i < 6; i++) {
					if(m_PlayerList[i] != null) {
						if(m_PlayerList[i].m_Name.m_Text.toString().equals(quitUserID)) {
							removeObject(m_PlayerList[i].m_MotorCycle);
							removeObject(m_PlayerList[i].m_Name);
							removeObject(m_PlayerList[i]);
							m_PlayerList[i].m_MotorCycle = null;
							m_PlayerList[i].m_Name = null;
							m_PlayerList[i] = null;
							break;
						}
					}
				}
			}
		}break;
		
		case Protocol.KICK_ROOM_REPORT:{
			Game.getInstance().setNextLevel(new LoadingLevel());
		}break;
		
		case Protocol.START_GAME_REPORT:{
			Game.getInstance().setNextLevel(new InGameLevel(m_Roomid));
		}break;
		
		default:{
			System.out.println("unknownProtocol");
			System.out.println(data + "WaitingRoomLevel");
		}break;
		
		}
	}

	/**
	 * InGameLevel로 넘어가는 함수
	 * 게임 스타트
	 */
	public void HelloNodoubt() {
//		Game.getInstance().setNextLevel(new InGameLevel(ROOM));
	}
}
