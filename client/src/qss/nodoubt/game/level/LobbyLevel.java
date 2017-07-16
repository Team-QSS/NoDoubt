package qss.nodoubt.game.level;
import static org.lwjgl.glfw.GLFW.*;

import java.util.Set;

import qss.nodoubt.game.Game;
import qss.nodoubt.game.object.*;
import qss.nodoubt.input.Input;


public class LobbyLevel extends GameLevel{
	//버튼
	private Button[] m_Buttons = new Button[5];
	//배경
	private Background m_LobbyBG = null;
	//버튼 텍스처 경로
	private String[] m_ButtonTexturePath = {
		"GameJoinButton1", "GameJoinButton2",
		"SettingButton1", "SettingButton2",
		"StoreButton1", "StoreButton2",
		"CreditButton1", "CreditButton2",
		"QuitButton1", "QuitButton2"
	};
	
	private float mouseX;
	private float mouseY;	
	
	//생성자
	public LobbyLevel(){
						
		//배경 생성
		m_LobbyBG = new Background("LobbyBackground");
		addObject(m_LobbyBG);
		
		//버튼 생성
		
		m_Buttons[0] = 
			new Button(m_ButtonTexturePath[0], m_ButtonTexturePath[1], -760, -360, 
			(action, key) -> {
				if(key == GLFW_KEY_ENTER && action == GLFW_PRESS && m_Buttons[0].isActived()){
					Game.getInstance().setNextLevel(new WaitingRoomLevel());
				}
				else if(key == GLFW_KEY_RIGHT && action == GLFW_PRESS && m_Buttons[0].isActived()){
					m_Buttons[0].toggle();
					m_Buttons[1].toggle();
				}
			},
			(action, button) -> {
				if(button == GLFW_MOUSE_BUTTON_LEFT && action == GLFW_PRESS){
					Game.getInstance().setNextLevel(new WaitingRoomLevel());
				}
			}
			);
		m_Buttons[1] = 
			new Button(m_ButtonTexturePath[2], m_ButtonTexturePath[3], -380, -360,
			(action, key) -> {
				if(key == GLFW_KEY_ENTER && action == GLFW_PRESS && m_Buttons[1].isActived()){
					Game.getInstance().setNextLevel(new SettingLevel());
				}
				else if(key == GLFW_KEY_RIGHT && action == GLFW_PRESS && m_Buttons[1].isActived()){
					m_Buttons[1].toggle();
					m_Buttons[2].toggle();
				}
				else if(key == GLFW_KEY_LEFT && action == GLFW_PRESS && m_Buttons[1].isActived()){
					m_Buttons[1].toggle();
					m_Buttons[0].toggle();
				}
			},
			(action, button) -> {
				if(button == GLFW_MOUSE_BUTTON_LEFT && action == GLFW_PRESS){
					Game.getInstance().setNextLevel(new SettingLevel());
				}
			}
			);
		m_Buttons[2] = 
			new Button(m_ButtonTexturePath[4], m_ButtonTexturePath[5], 0, -360,
			(action, key) -> {
				if(key == GLFW_KEY_ENTER && action == GLFW_PRESS && m_Buttons[2].isActived()){
					Game.getInstance().setNextLevel(new StoreLevel());
				}
				else if(key == GLFW_KEY_RIGHT && action == GLFW_PRESS && m_Buttons[2].isActived()){
					m_Buttons[2].toggle();
					m_Buttons[3].toggle();
				}
				else if(key == GLFW_KEY_LEFT && action == GLFW_PRESS && m_Buttons[2].isActived()){
					m_Buttons[2].toggle();
					m_Buttons[1].toggle();
				}
			},
			(action, button) -> {
				if(button == GLFW_MOUSE_BUTTON_LEFT && action == GLFW_PRESS){
					Game.getInstance().setNextLevel(new StoreLevel());
				}
			}
			);
		m_Buttons[3] = 
			new Button(m_ButtonTexturePath[6], m_ButtonTexturePath[7], 380, -360,
			(action, key) -> {
				if(key == GLFW_KEY_ENTER && action == GLFW_PRESS && m_Buttons[3].isActived()){
					Game.getInstance().setNextLevel(new CreditLevel());
				}
				else if(key == GLFW_KEY_RIGHT && action == GLFW_PRESS && m_Buttons[3].isActived()){
					m_Buttons[3].toggle();
					m_Buttons[4].toggle();
				}
				else if(key == GLFW_KEY_LEFT && action == GLFW_PRESS && m_Buttons[3].isActived()){
					m_Buttons[3].toggle();
					m_Buttons[2].toggle();
				}
			},
			(action, button) -> {
				if(button == GLFW_MOUSE_BUTTON_LEFT && action == GLFW_PRESS){
				Game.getInstance().setNextLevel(new CreditLevel());
				}
			}
			);
		m_Buttons[4] =
			new Button(m_ButtonTexturePath[8], m_ButtonTexturePath[9], 760, -360,
			(action, key) -> {
				if(key == GLFW_KEY_ENTER && action == GLFW_PRESS && m_Buttons[4].isActived()){
					Game.getInstance().goodBye();
				}
				else if(key == GLFW_KEY_LEFT && action == GLFW_PRESS && m_Buttons[4].isActived()){
					m_Buttons[4].toggle();
					m_Buttons[3].toggle();
				}
			},
			(action, button) -> {
				if(button == GLFW_MOUSE_BUTTON_LEFT && action == GLFW_PRESS){
					Game.getInstance().goodBye();
				}
			}
		);
		
		for(int i = 0; i < m_Buttons.length; i++){
			addObject(m_Buttons[i]);
		}
		m_Buttons[0].toggle();
		
		setEventListener((action, key) -> {if(key == GLFW_KEY_Q) Game.getInstance().setNextLevel(new InGameLevel());}, null);
	}

	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub
		drawObjects();
	}
	
}
