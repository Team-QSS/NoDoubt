package qss.nodoubt.game.level;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

import java.util.ArrayList;
import java.util.LinkedList;

import org.json.simple.JSONObject;

import protocol.Protocol;
import qss.nodoubt.game.Game;
import qss.nodoubt.game.object.Background;
import qss.nodoubt.game.object.Button;
import qss.nodoubt.game.object.Player;
import qss.nodoubt.game.object.RoomObject;
import qss.nodoubt.input.Input;
import qss.nodoubt.network.Network;
import room.Room;
import room.RoomManager;
import room.User;
import util.KeyValue;
import util.Util;

public class WaitingRoomLevel extends GameLevel{

//	private String m_GameName;
//	private String m_Owner;
//	private int m_Players;
	
	private float mouseX;
	private float mouseY;
	
	//각 플레이어의 UI를 출력할 때 사용하는 리스트
	private ArrayList<Player> m_PlayerList;
	
	private Button m_StartButton;
	private Button m_BackButton;
	
//	private Button[] m_PlusButtons; 초대버튼
	private Background m_WaitingRoomBG;
	
	private Network m_Network;
	
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
								m_StartButton.focus();
							}
							if(action == GLFW_RELEASE){
							//	Game.getInstance().setNextLevel(new InGameLevel(null));
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
		
		addObject(m_StartButton);
		addObject(m_BackButton);
		addObject(m_WaitingRoomBG);
		
		networkInit();
	}
	
	private void networkInit(){
		//초기화
		JSONObject msg=Util.packetGenerator(Protocol.GET_ROOM_DATA, new KeyValue("RoomID",m_Roomid));
		Network.getInstance().pushMessage(msg);
	}
	
	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub
		mouseX = Input.getInstance().getCursorPosition().x;
		mouseY = Input.getInstance().getCursorPosition().y;
		
		
		JSONObject msg = Network.getInstance().pollMessage();
		if(msg != null) {
			protocolProcess(msg);
		}
		
		/*
		 * 서버에서 정보를 받아 갱신해야 함 
		 * m_Users (User들을 담는 연결리스트)		 
		 */
	}
	
	private void protocolProcess(JSONObject data){
		System.out.println(data);
		switch((String)data.get("Protocol")){
		
		case Protocol.GET_ROOM_DATA:{
			room=Network.gson.fromJson((String)data.get("Room"), Room.class);
		}break;
		
		case Protocol.JOIN_ROOM_RESULT:{
			User joinUser=Network.gson.fromJson((String)data.get("User"), User.class);
			room.enterUser(joinUser);
			//ui처리
		}break;
		
		case Protocol.QUIT_ROOM_REPORT:{
			String quitUserID=(String)data.get("UserID");
			room.removeUser(quitUserID);
			//
		}break;
		
		default:{
			System.out.println("unknownProtocol");
			System.out.println(data);
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
