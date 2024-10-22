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

public class LoadingLevel extends GameLevel {

	// 배경
	private Background m_LoadingBG = null;

	private Button m_Create = null;
	private Button m_Back = null;
	private Button m_Up = null;
	private Button m_Down = null;

	// getCursor로 마우스의 좌표를 구함

	private float mouseX;
	private float mouseY;
	private int curPage = 0;
	private int maxPage = 0;

	private RoomManager rm;

	private RoomList roomList = new RoomList();

	public LoadingLevel() {
		m_Create = new Button("CreateButton1", "CreateButton2", 326, 414);
		m_Back = new Button("BackButton1", "BackButton2", 677, 414);
		m_Up = new Button("UpButton", null, 840, 278, 30, 30);
		m_Down = new Button("DownButton", null, 840, -411, 30, 30);

		setEventListener((action, key) -> {
			if (action == GLFW_PRESS) {
				if (key == GLFW_KEY_BACKSPACE) {
					Game.getInstance().setNextLevel(new LobbyLevel());
				}
			}
		}, null);

		m_Create.setListener(null, (action, button) -> {
			if (button == GLFW_MOUSE_BUTTON_LEFT) {
				if (m_Create.onButton(mouseX, mouseY)) {
					if (action == GLFW_PRESS) {
						m_Create.focus();
						m_Create.setPressedin(true);
					}
					if (action == GLFW_RELEASE && m_Create.getPressedin()) {
						if (m_Create.getPressedin()) {
							Game.getInstance().setNextLevel(new CreateRoomLevel());
							m_Create.unfocus();
							m_Create.setPressedin(false);
						}
					}
				} else {
					if (action == GLFW_RELEASE) {
						m_Create.unfocus();
						m_Create.setPressedin(false);
					}
				}

			}
		});
		m_Back.setListener(null, (action, button) -> {
			if (button == GLFW_MOUSE_BUTTON_LEFT) {
				if (m_Back.onButton(mouseX, mouseY)) {
					if (action == GLFW_PRESS) {
						m_Back.focus();
						m_Back.setPressedin(true);
					}
					if (action == GLFW_RELEASE && m_Back.getPressedin()) {
						Game.getInstance().setNextLevel(new LobbyLevel());
						m_Back.unfocus();
						m_Back.setPressedin(false);
					}
				} else {
					if (action == GLFW_RELEASE) {
						m_Back.unfocus();
						m_Back.setPressedin(false);
					}
				}
			}
		});
		m_Up.setListener(null, (action, button) -> {
			if (button == GLFW_MOUSE_BUTTON_LEFT) {
				if (m_Up.onButton(mouseX, mouseY)) {
					if (action == GLFW_PRESS) {
						m_Up.setPressedin(true);

					}
					if (action == GLFW_RELEASE && m_Up.getPressedin()) {
						// action
						if (curPage > 0) {
							int max = roomList.getListSize() - (curPage) * 6;
							System.out.println("ListSize : " + max);
							System.out.println("max : " + max);
							for (int i = 0; i < max; i++) {
								deleteRoom(i + curPage * 6);
								System.out.println("asdf");
							}
							curPage--;
							for (int i = 0; i < 6; i++) {
								roomList.getIndex(i + curPage * 6).setActive(true);
							}
						}
						m_Up.setPressedin(false);
					}
				} else {
					if (action == GLFW_RELEASE) {
						m_Up.setPressedin(false);
					}
				}
			}
		});
		m_Down.setListener(null, (action, button) -> {
			if (button == GLFW_MOUSE_BUTTON_LEFT) {
				if (m_Down.onButton(mouseX, mouseY)) {
					if (action == GLFW_PRESS) {
						m_Down.setPressedin(true);

					}
					if (action == GLFW_RELEASE && m_Down.getPressedin()) {
						// action
						if (curPage < maxPage) {
							for (int i = 0; i < 6; i++) {
								deleteRoom(i + curPage * 6);
							}
							curPage++;
							int max = roomList.getListSize() - (curPage) * 6;
							for (int i = 0; i < max; i++) {
								roomList.getIndex(i + curPage * 6).setActive(true);
							}
						}
						m_Down.setPressedin(false);
					}
				} else {
					if (action == GLFW_RELEASE) {
						m_Down.setPressedin(false);
					}
				}
			}
		});

		m_LoadingBG = new Background("LoadBG");

		rm = null;

		addObject(m_Create);
		addObject(m_Back);
		addObject(m_Up);
		addObject(m_Down);
		addObject(m_LoadingBG);
		initAction();
	}

	private void initAction() {
		JSONObject msg = Util.packetGenerator(Protocol.DUMMY_PACKET);
		Network.getInstance().pushMessage(msg);

		JSONObject getRoomManager = Util.packetGenerator(Protocol.GET_ROOMMANAGER);
		Network.getInstance().pushMessage(getRoomManager);
	}

	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub

		updateObjects(deltaTime);

		JSONObject msg = Network.getInstance().pollMessage();
		if (msg != null) {
			protocolProcess(msg);
		}

		mouseX = Input.getInstance().getCursorPosition().x;
		mouseY = Input.getInstance().getCursorPosition().y;

		maxPage = (roomList.getListSize() - 1) / 6;
		if (maxPage < 0) {
			maxPage = 0;
		}
		if (curPage > maxPage) {
			curPage = maxPage;
		}

		int max = roomList.getListSize();

		roomList.update(deltaTime);

		for (int i = 0; i < max; i++) {
			if (roomList.getIndex(i).getActive())
				addRoomObject(i);
		}
	}

	private void addRoomObject(int index) {
		roomList.getIndex(index).setListener(null, (action, button) -> {
			if (action == GLFW_PRESS && button == GLFW_MOUSE_BUTTON_LEFT) {
				if ((roomList.getIndex(index)).onObject(mouseX, mouseY)) {
					double roomID = roomList.getIndex(index).getID();
					// RoomObject를 좌클릭했을 시 Protocol.JOIN_ROOM_REQUEST 프로토콜의 메시지를
					// 보낸다.
					JSONObject msg = Util.packetGenerator(Protocol.JOIN_ROOM_REQUEST, new KeyValue("RoomID", roomID));
					Network.getInstance().pushMessage(msg);
					System.out.println("리스트" + index + "클릭");
					System.out.println(mouseX + " " + mouseY);
					Game.getInstance().setNextLevel(new WaitingRoomLevel(roomID));
				}
			}
		});
		addObject(roomList.getIndex(index));
		addObject(roomList.getIndex(index).m_GameName);
		addObject(roomList.getIndex(index).m_Owner);
		addObject(roomList.getIndex(index).m_Players);
		roomList.getIndex(index).setActive(false);
	}

	private void protocolProcess(JSONObject data) {
		System.out.println(data + "LoadingLevel");
		switch ((String) data.get("Protocol")) {

		case Protocol.DUMMY_PACKET: {
		}
			break;

		case Protocol.GET_ROOMMANAGER: {
			rm = Network.gson.fromJson((String) data.get("RoomManager"), RoomManager.class);
			int i = 0;
			for (double id : rm.list.keySet()) {
				if (id == RoomManager.LOBBY)
					continue;
				Room room=rm.list.get(id);
				
				/**
				 * 11.25. 5시 25분에 if(!room.isPlaying()) 조건문 추가함. 김경민
				 */
				if(!room.isPlaying()) {
					roomList.addRoomObject(i, new RoomObject(0, room.getName(), room.getMaster().getID(), room.list.size(), room.id, i));
					if(i >= curPage*6 && i < curPage*6 + 6) {
						roomList.getIndex(i).setActive(true);
					}
					else {
						roomList.getIndex(i).setActive(false);
					}
					i++;
				}
			}
		}break;
		
		case Protocol.ADD_ROOM:{
			Room room = Network.gson.fromJson((String)data.get("Room"), Room.class);
			if(rm == null) {
				System.out.println("RoomManager가 Null : LoadingLevel");
			}
			if (room == null) {
				System.out.println("Room이 Null : LoadingLevel");
			}
			
			rm.addRoom(room);

			int i = roomList.getListSize();
			roomList.addRoomObject(i,
					new RoomObject(0, room.getName(), room.getMaster().getID(), room.list.size(), room.id, i));

			if (i >= curPage * 6 && i < curPage * 6 + 6) {
				roomList.getIndex(i).setActive(true);
			} else {
				roomList.getIndex(i).setActive(false);
			}
		}
			break;

		case Protocol.REMOVE_ROOM: {
			double roomID = (double) data.get("RoomID");
			rm.removeRoom(roomID);

			int i = 0;
			for (i = 0; i < roomList.roomList.size(); i++) {
				if (roomList.roomList.get(i).getID() == roomID) {
					System.out.println("deleteRoom" + i);
					if (i >= curPage * 6 && i < curPage * 6 + 6) {
						deleteRoom(i);
					}
					roomList.removeRoomObject(i);
					break;
				}
			}

			maxPage = (roomList.getListSize() - 1) / 6;
			if (maxPage < 0) {
				maxPage = 0;
			}
			if (curPage > maxPage) {
				curPage = maxPage;
			}

			System.out.println("maxPage : " + maxPage);
			System.out.println("curPage : " + curPage);

			for (int j = 0; j < roomList.getListSize(); j++) {
				roomList.getIndex(j).setIndex(j);
				if (j >= curPage * 6 && j < (curPage + 1) * 6) {
					roomList.getIndex(j).setActive(true);
				} else {
					roomList.getIndex(j).setActive(false);
				}
			}
		}break;
		
		case Protocol.UPDATE_ROOM_CURRENT_USER_NUM:{
			double roomID = (double)data.get("RoomID");
			int currentUserNum=((Long)data.get("UserNum")).intValue();
			for(RoomObject room:roomList.roomList){
				if(room.getID()==roomID)
					room.setPlayers(currentUserNum);
			}
		}break;
		
		case Protocol.SET_ROOM_PLAYING:{
			double roomID = (double)data.get("RoomID");
			//룸매니저의 해당 방을 조회하여 방의 isPlaying을 true로 바꿔야함
			
			/**
			 * 11.25. 5시 25분에 SET_ROOM_PLAYING 구현함. 김경민
			 */
			rm.getRoom(roomID).setPlaying(true);
			
			int i=0;
			for(i=0;i<roomList.roomList.size();i++){
				if(roomList.roomList.get(i).getID()==roomID){
					System.out.println("deleteRoom"+i);
					if(i >= curPage*6 && i < curPage*6 + 6) {
						deleteRoom(i);
					}
					roomList.removeRoomObject(i);
					break;
				}
			}
			
			maxPage = (roomList.getListSize()-1)/6;
			if(maxPage < 0) {
				maxPage = 0;
			}
			if(curPage > maxPage) {
				curPage = maxPage;
			}
			
			System.out.println("maxPage : " + maxPage);
			System.out.println("curPage : " + curPage);
			
			for(int j = 0; j < roomList.getListSize(); j++) {
				roomList.getIndex(j).setIndex(j);
				if(j >= curPage*6 && j < (curPage+1) * 6) {
					roomList.getIndex(j).setActive(true);
				}
				else {
					roomList.getIndex(j).setActive(false);
				}
			}
		}break;
		
		default:{
			System.out.println("unknownProtocol");
			System.out.println(data + "LoadingLevel");
		}
			break;

		}
	}

	public void deleteRoom(int index) {
		removeObject(roomList.getIndex(index).m_GameName);
		removeObject(roomList.getIndex(index).m_Owner);
		removeObject(roomList.getIndex(index).m_Players);
		removeObject(roomList.getIndex(index));
		// roomList.removeRoomObject(index);
	}

}
