package qss.nodoubt.game.level;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_BACKSPACE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ENTER;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

import java.util.LinkedList;

import org.json.simple.JSONObject;

import protocol.Protocol;
import qss.nodoubt.game.Game;
import qss.nodoubt.game.object.Background;
import qss.nodoubt.game.object.Button;
import qss.nodoubt.game.object.RoomObject;
import qss.nodoubt.input.Input;
import qss.nodoubt.network.Network;
import room.Room;
import room.RoomManager;
/*
 * 이 클래스는 대기실을 들어가기 전,
 * 들어갈 수 있는 다른 대기실을 골라 들어가거나
 * 대기실을 생성할 수 있는 레벨로 갈 수 있는 역할을 하는
 * 레벨이다.
 * */
import util.Util;

public class LoadingLevel extends GameLevel{
	
	//배경
	private Background m_LoadingBG = null;
	
	private Button m_Create = null;
	private Button m_Back = null;
	
	private LinkedList<RoomObject> RoomList = new LinkedList<RoomObject>();
	
	//getCursor로 마우스의 좌표를 구함
	
	private float mouseX;
	private float mouseY;
	private float time;
	
	private RoomManager rm;
	
	public LoadingLevel(){
		m_Create = new Button("CreateButton1", "CreateButton2", 326, 414);
		m_Back = new Button("BackButton1", "BackButton2", 677, 414);
		
		setEventListener((action,  key) -> { 
			if(action == GLFW_PRESS){ 
				if(key == GLFW_KEY_BACKSPACE) {
					Game.getInstance().setNextLevel(new LobbyLevel());
				}
				if(key == GLFW_KEY_ENTER){
					Game.getInstance().setNextLevel(new WaitingRoomLevel());
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
		
		RoomList.add(new RoomObject(0,0,"asd","aasd",2));
	}

	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub
		time += deltaTime;
		
		JSONObject msg = Network.getInstance().pollMessage();
		if(msg != null) {
			protocolProcess(msg);
		}
		
		mouseX = Input.getInstance().getCursorPosition().x;
		mouseY = Input.getInstance().getCursorPosition().y;
		
		for(RoomObject R : RoomList){
			R.update(deltaTime);
		}
	}
	
	private void protocolProcess(JSONObject data){
		System.out.println(data);
		switch((String)data.get("Protocol")){
		case Protocol.GET_ROOMMANAGER:{
			rm=Network.gson.fromJson((String)data.get("RoomManager"), RoomManager.class);
			
			int i=0;
			for(double id:rm.list.keySet()){
				if(id==RoomManager.LOBBY)
					continue;
				Room room=rm.list.get(id);
				RoomList.add(new RoomObject(0,i++,room.getName(),room.getMaster().getName(),room.list.size()));
				System.out.println(RoomList.get(i-1));
			}
		}break;
		
		default:System.out.println("unknownProtocol");
		}
	}

}
