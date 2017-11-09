package qss.nodoubt.game.level;

import static org.lwjgl.glfw.GLFW.*;

import org.json.simple.JSONObject;

import protocol.Protocol;
import qss.nodoubt.game.Game;
import qss.nodoubt.game.object.Background;
import qss.nodoubt.game.object.Button;
import qss.nodoubt.game.object.RoomList;
import qss.nodoubt.game.object.RoomObject;
import qss.nodoubt.input.Input;
import qss.nodoubt.network.Network;
import room.Room;
import room.RoomManager;
import util.Util;
import util.KeyValue;
/*
 * 이 클래스는 대기실을 들어가기 전,
 * 들어갈 수 있는 다른 대기실을 골라 들어가거나
 * 대기실을 생성할 수 있는 레벨로 갈 수 있는 역할을 하는
 * 레벨이다.
 * */

public class LoadingLevel extends GameLevel{
	
	//배경
	private Background m_LoadingBG = null;
	
	private Button m_Create = null;
	private Button m_Back = null;
	
	//getCursor로 마우스의 좌표를 구함
	
	private float mouseX;
	private float mouseY;
	
	private RoomManager rm;
	
	private RoomList roomList = new RoomList();
	
	public LoadingLevel(){
		m_Create = new Button("CreateButton1", "CreateButton2", 326, 414);
		m_Back = new Button("BackButton1", "BackButton2", 677, 414);
		
		setEventListener((action,  key) -> { 
			if(action == GLFW_PRESS){ 
				if(key == GLFW_KEY_BACKSPACE) {
					Game.getInstance().setNextLevel(new LobbyLevel());
				}
				if(key == GLFW_KEY_ENTER){
//					Game.getInstance().setNextLevel(new WaitingRoomLevel("Test"));
				}
			}
		}, 
		null);
		
		m_Create.setListener(null,
				(action, button) -> {
					if(button == GLFW_MOUSE_BUTTON_LEFT){
						if(m_Create.onButton(mouseX, mouseY)){
							if(action == GLFW_PRESS){
								m_Create.focus();
							}
							if(action == GLFW_RELEASE){
								Game.getInstance().setNextLevel(new CreateRoomLevel());
							}
						}
						else{
							if(action == GLFW_RELEASE){
								m_Create.unfocus();
							}
						}
						
					}
				});
		m_Back.setListener(null,
				(action, button) -> {
					if(button == GLFW_MOUSE_BUTTON_LEFT){
						if(m_Back.onButton(mouseX, mouseY)){
							if(action == GLFW_PRESS){
								m_Back.focus();
							}
							if(action == GLFW_RELEASE){
								Game.getInstance().setNextLevel(new LobbyLevel());
							}
						}
						else{
							if(action == GLFW_RELEASE){
								m_Back.unfocus();
							}
						}
					}
				});
		m_LoadingBG = new Background("LoadBG");
		addObject(m_Create);
		addObject(m_Back);
		addObject(m_LoadingBG);
		initAction();
	}
	
	private void initAction(){
		JSONObject getRoomManager=Util.packetGenerator(Protocol.GET_ROOMMANAGER);
		Network.getInstance().pushMessage(getRoomManager);
	}

	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub
		
		updateObjects(deltaTime);
		
		JSONObject msg = Network.getInstance().pollMessage();
		if(msg != null) {
			protocolProcess(msg);
		}
		
		mouseX = Input.getInstance().getCursorPosition().x;
		mouseY = Input.getInstance().getCursorPosition().y;
		
		
		roomList.update(deltaTime);
		
	}

	private void addRoomObject(int index){
		roomList.getIndex(index).setListener(null,
				(action, button) -> {
					if(action == GLFW_PRESS && button == GLFW_MOUSE_BUTTON_LEFT) {
						if((roomList.getIndex(index)).onObject(mouseX, mouseY)) {
							double roomID=roomList.getIndex(index).getID();
							//RoomObject를 좌클릭했을 시 Protocol.JOIN_ROOM_REQUEST 프로토콜의 메시지를 보낸다.
							JSONObject msg = Util.packetGenerator(Protocol.JOIN_ROOM_REQUEST, new KeyValue("RoomID", roomID));
							Network.getInstance().pushMessage(msg);
							System.out.println("리스트"+index+"클릭");
							System.out.println(mouseX+" "+mouseY);
							Game.getInstance().setNextLevel(new WaitingRoomLevel(roomID));
						}
					}
				});
		addObject(roomList.getIndex(index));
		addObject(roomList.getIndex(index).m_GameName);
		addObject(roomList.getIndex(index).m_Owner);
		addObject(roomList.getIndex(index).m_Players);
	}
	
	private void protocolProcess(JSONObject data){
		System.out.println(data + "LoadingLevel");
		switch((String)data.get("Protocol")){
		case Protocol.GET_ROOMMANAGER:{
			rm=Network.gson.fromJson((String)data.get("RoomManager"), RoomManager.class);
			int i = 0;

			for(double id:rm.list.keySet()){
				if(id==RoomManager.LOBBY)
					continue;
				Room room=rm.list.get(id);
				roomList.addRoomObject(i, new RoomObject(0, room.getName(), room.getMaster().getID(), room.list.size(), room.id));
				roomList.getIndex(i).setIndex(i);
				
				//RoomObject를 렌더링 대상으로 추가함
				addRoomObject(i);
				i++;
			}
		}break;
		
		case Protocol.ADD_ROOM:{
			Room room=Network.gson.fromJson((String)data.get("Room"), Room.class);
			rm.addRoom(room);
			
			int i = roomList.getListSize();
			roomList.addRoomObject(i, new RoomObject(0,room.getName(),room.getMaster().getID(),room.list.size(), room.id));
			roomList.getIndex(i).setIndex(i);
			addRoomObject(i);
		}break;
		
		case Protocol.REMOVE_ROOM:{
			double roomID=(double)data.get("RoomID");
			rm.removeRoom(roomID);
			
			int i=0;
			for(i=0;i<roomList.roomList.size();i++){
				if(roomList.roomList.get(i).getID()==roomID){
					System.out.println("deleteRoom"+i);
					deleteRoom(i);
				}
			}
		}break;
		
		case Protocol.UPDATE_ROOM_CURRENT_USER_NUM:{
			double roomID = (double)data.get("RoomID");
			int currentUserNum=((Long)data.get("UserNum")).intValue();
			//수정중임 아직 룸리스트의 인원수가 업데이트 되지 않음
			for(RoomObject room:roomList.roomList){
				if(room.getID()==roomID)
					room.setPlayers(currentUserNum);
			}
		}break;
		
		default:{
			System.out.println("unknownProtocol");
			System.out.println(data + "LoadingLevel");
		}break;
		
		}
	}
	
	public void deleteRoom(int index){
		removeObject(roomList.getIndex(index).m_GameName);
		removeObject(roomList.getIndex(index).m_Owner);
		removeObject(roomList.getIndex(index).m_Players);
		removeObject(roomList.getIndex(index));
		roomList.removeRoomObject(index);
	}

}

