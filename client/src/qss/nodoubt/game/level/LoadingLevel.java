package qss.nodoubt.game.level;

import static org.lwjgl.glfw.GLFW.*;

import java.util.*;

import org.json.simple.*;

import qss.nodoubt.game.Game;
import qss.nodoubt.game.object.*;
import qss.nodoubt.input.Input;
import qss.nodoubt.network.Network;
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
	
	private Network m_Network = null;
	
	//getCursor로 마우스의 좌표를 구함
	
	private float mouseX;
	private float mouseY;
	private float time;
	
	public LoadingLevel(){
		m_Create = new Button("CreateButton1", "CreateButton2", 326, 414);
		m_Back = new Button("BackButton1", "BackButton2", 677, 414);
		
		m_Network = Network.getInstance();
		
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
	}

	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub
		time += deltaTime;
		
		mouseX = Input.getInstance().getCursorPosition().x;
		mouseY = Input.getInstance().getCursorPosition().y;
		
	}

}
