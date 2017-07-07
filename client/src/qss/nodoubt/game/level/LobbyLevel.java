package qss.nodoubt.game.level;
import static org.lwjgl.glfw.GLFW.*;

import java.util.Set;

import qss.nodoubt.game.Game;
import qss.nodoubt.game.object.*;
import org.lwjgl.glfw.*;


public class LobbyLevel extends GameLevel{
	//버튼
	private Button m_GameJoinButton = null;
	private Button m_SettingButton = null;
	private Button m_StoreButton = null;
	private Button m_CreditButton = null;
	private Button m_QuitButton = null;
	//배경
	private Background m_LobbyBG = null;
	//버튼 텍스처 경로
	private String[] m_ButtonTexturePath = {
		"GameJoinButton2",
		"SettingButton2",
		"StoreButton2",
		"CreditButton2",
		"QuitButton2"
	};	
	
	//생성자
	public LobbyLevel(){
		
		
						
		//배경 생성
		m_LobbyBG = new Background("LobbyBackground");
		addObject(m_LobbyBG);
		
		//버튼 생성

		m_GameJoinButton = new Button(m_ButtonTexturePath[0], -760, -360, 
				(GLFW_PRESS, GLFW_KEY_ENTER) -> {Game.getInstance().setNextLevel(new WaitingRoomLevel());},
				(GLFW_PRESS, GLFW_MOUSE_BUTTON_LEFT) -> {Game.getInstance().setNextLevel(new WaitingRoomLevel());});
		m_SettingButton = new Button(m_ButtonTexturePath[1], -380, -360,
				(GLFW_PRESS, GLFW_KEY_ENTER) -> {Game.getInstance().setNextLevel(new SettingLevel());},
				(GLFW_PRESS, GLFW_MOUSE_BUTTON_LEFT) -> {Game.getInstance().setNextLevel(new SettingLevel());});
		m_StoreButton = new Button(m_ButtonTexturePath[2], 0, -360,
				(GLFW_PRESS, GLFW_KEY_ENTER) -> {Game.getInstance().setNextLevel(new StoreLevel());},
				(GLFW_PRESS, GLFW_MOUSE_BUTTON_LEFT) -> {Game.getInstance().setNextLevel(new StoreLevel());});
		m_CreditButton = new Button(m_ButtonTexturePath[3], 380, -360,
				(GLFW_PRESS, GLFW_KEY_ENTER) -> {Game.getInstance().setNextLevel(new CreditLevel());},
				(GLFW_PRESS, GLFW_MOUSE_BUTTON_LEFT) -> {Game.getInstance().setNextLevel(new CreditLevel());});
		m_QuitButton = new Button(m_ButtonTexturePath[4], 760, -360,
				(GLFW_PRESS, GLFW_KEY_ENTER) -> {Game.getInstance().goodBye();},
				(GLFW_PRESS, GLFW_MOUSE_BUTTON_LEFT) -> {Game.getInstance().goodBye();});
		
		Button[] buttons = {
			m_GameJoinButton,
			m_SettingButton,
			m_StoreButton,
			m_CreditButton,
			m_QuitButton
		};
		
		for(int i = 0; i < buttons.length; i++){
			addObject(buttons[i]);
		}
		
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
