package qss.nodoubt.game.level;
import static org.lwjgl.glfw.GLFW.*;

import java.util.Set;

import qss.nodoubt.game.Game;
import qss.nodoubt.game.object.*;
import qss.nodoubt.input.Input;


public class LobbyLevel extends GameLevel{
	//버튼
	private Button[] m_Buttons = new Button[5];
	private static int m_ActiveIndex = 0;
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
		m_LobbyBG = new Background("LobbyBG");
		addObject(m_LobbyBG);
		
		
		//버튼 생성
		
		for(int i = 0; i < m_Buttons.length; i++){
			m_Buttons[i] = new Button(m_ButtonTexturePath[2*i], m_ButtonTexturePath[2*i+1], -760 + 380*i,-360);
		}
		m_Buttons[0].setListener(
				(action, key) ->{
					if(action == GLFW_PRESS && m_Buttons[0].isActived()){
						if(key == GLFW_KEY_ENTER){
							Game.getInstance().setNextLevel(new LoadingLevel());
						}
					}
				},
				(action, button) ->{
					mouseOnButton(button, action, 0);
				}
		);
		m_Buttons[1].setListener(
//				(action, key) ->{
//					if(action == GLFW_PRESS && m_Buttons[1].isActived()){
//						if(key == GLFW_KEY_ENTER){
//						//	Game.getInstance().setNextLevel(new SettingLevel());
//						}
//					}
//				}
				null,
				(action, button) ->{
					mouseOnButton(button, action, 1);
				}
		);
		
		m_Buttons[2].setListener(
//				(action, key) ->{
//					if(action == GLFW_PRESS && m_Buttons[2].isActived()){
//						if(key == GLFW_KEY_ENTER){
//						//	Game.getInstance().setNextLevel(new StoreLevel());
//						}
//					}
//				}
				null,
				(action, button) ->{
					mouseOnButton(button, action, 2);
				}
		);
		
		m_Buttons[3].setListener(
				(action, key) ->{
					if(action == GLFW_PRESS && m_Buttons[3].isActived()){
						if(key == GLFW_KEY_ENTER){
							Game.getInstance().setNextLevel(new CreditLevel());
						}
					}
				},
				(action, button) ->{
					mouseOnButton(button, action, 3);
				});
		
		m_Buttons[4].setListener(
				(action, key) ->{
					if(action == GLFW_PRESS && m_Buttons[4].isActived()){
						if(key == GLFW_KEY_ENTER){
							Game.getInstance().goodBye();
						}
					}
				},
				(action, button) ->{
					mouseOnButton(button, action, 4);
				}
		);
		
		
		this.setEventListener(
			(action, key) -> {
				if(action == GLFW_PRESS && m_Buttons[m_ActiveIndex].isActived()){
					if(key == GLFW_KEY_RIGHT){
						if(m_ActiveIndex < 4){
							m_Buttons[m_ActiveIndex].toggle();
							m_Buttons[++m_ActiveIndex].toggle();
							
						}
					}
					if(key == GLFW_KEY_LEFT){
						if(m_ActiveIndex > 0){
							m_Buttons[m_ActiveIndex].toggle();
							m_Buttons[--m_ActiveIndex].toggle();
						}
					}
					if(key == GLFW_KEY_Q) {
						Game.getInstance().setNextLevel(new InGameLevel());
					}
				}
			},
		null);

		m_Buttons[m_ActiveIndex].toggle();
		
		for(int i = 0; i < m_Buttons.length; i++){
			addObject(m_Buttons[i]);
		}

	}

	private void mouseOnButton(int button, int action, int index){
		if(m_Buttons[index].onButton(mouseX, mouseY)){
			if(button == GLFW_MOUSE_BUTTON_LEFT){
				if(action == GLFW_PRESS){
					if(m_ActiveIndex == 0)	m_Buttons[index].toggle();
					else {m_Buttons[index].toggle(); m_Buttons[m_ActiveIndex].toggle();}
				}
				if(action == GLFW_RELEASE){
					switch(index){
					case 0:
						Game.getInstance().setNextLevel(new LoadingLevel());
						break;
					case 1:
						break;
					case 2:
						break;
					case 3:
						Game.getInstance().setNextLevel(new CreditLevel());
					case 4:
						Game.getInstance().goodBye();
					}
				}
			}
		}else{
			m_Buttons[index].toggle();
			m_Buttons[m_ActiveIndex].toggle();
		}
	}
	@Override
	public void update(float deltaTime) {
		updateObjects(deltaTime);
		mouseX = Input.getInstance().getCursorPosition().x;
		mouseY = Input.getInstance().getCursorPosition().y;
		
		System.out.println(mouseX +" " + mouseY);
	}
	
}
