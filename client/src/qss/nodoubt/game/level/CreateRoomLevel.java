package qss.nodoubt.game.level;

import static org.lwjgl.glfw.GLFW.*;
import org.joml.Vector3f;
import org.json.simple.JSONObject;
import qss.nodoubt.game.object.*;
import qss.nodoubt.input.Input;
import util.Util;
import protocol.Protocol;

public class CreateRoomLevel extends GameLevel{
	
	private Background m_CreateRoomBG = null;
	private TextBox m_GameName = null; 
	private Button m_CreateRoom = null;
	private float mouseX;
	private float mouseY;
	
	public CreateRoomLevel(){
		m_CreateRoomBG = new Background("CreateBG");
		m_GameName = new TextBox(0, 0, 2, 679, -313, 26, false, "Room Name", new Vector3f(0, 0, 0));
		m_CreateRoom = new Button("RoomCreateButton", null, 0, -144);
		
		m_CreateRoom.setListener(
				(action, key) -> {
					if(action == GLFW_PRESS && key == GLFW_KEY_ENTER){
						if(!(m_GameName.m_Text.length() < 1)){
							//JSONObject 채우기
						}
						else{
							m_GameName.m_Text.delete(0, m_GameName.m_Text.length());
						}
					}
				},
				(action, button) -> {
					if(action == GLFW_RELEASE && button == GLFW_MOUSE_BUTTON_LEFT){
						if(m_CreateRoom.onButton(mouseX, mouseY)){
							if(!(m_GameName.m_Text.length() < 1)){
								//JSONObject 채우기
							}
							else{
								m_GameName.m_Text.delete(0, m_GameName.m_Text.length());
							}
						}
					}
				});
		
		m_GameName.setActive();
		
		addObject(m_CreateRoom);
		addObject(m_CreateRoomBG);
		addObject(m_GameName);
	}
	
	@Override
	public void update(float deltaTime) {
		mouseX = Input.getInstance().getCursorPosition().x;
		mouseY = Input.getInstance().getCursorPosition().y;
		
		//JSONObject 메시지 받고 처리하기
		
		m_GameName.update(deltaTime);
	}

}
