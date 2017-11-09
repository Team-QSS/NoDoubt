package qss.nodoubt.game.level;
import static org.lwjgl.glfw.GLFW.*;

import org.json.simple.JSONObject;

import protocol.Protocol;
import qss.nodoubt.game.Game;
import qss.nodoubt.game.object.Background;
import qss.nodoubt.game.object.Button;
import qss.nodoubt.input.Input;
import qss.nodoubt.network.Network;


public class LobbyLevel extends GameLevel{
	//버튼
	private Button[] m_Buttons = new Button[3];
	private static int m_ActiveIndex = 0;
	//배경
	private Background m_LobbyBG = null;
	//버튼 텍스처 경로
	private String[] m_ButtonTexturePath = {
		"GameJoinButton1", "GameJoinButton2",
//		"SettingButton1", "SettingButton2",
//		"StoreButton1", "StoreButton2",
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
//			m_Buttons[i] = new Button(m_ButtonTexturePath[2*i], m_ButtonTexturePath[2*i+1], -760 + 380*i,-360);
			m_Buttons[i] = new Button(m_ButtonTexturePath[2*i], m_ButtonTexturePath[2*i+1], -480 + 480 * i, -360);
		}
		m_Buttons[0].setListener(
				(action, key) ->{
					if(action == GLFW_PRESS && m_Buttons[0].isActived()){
						if(key == GLFW_KEY_ENTER){
							Game.getInstance().setNextLevel(new LoadingLevel());
						}
					}
				},
				null
		);
		m_Buttons[1].setListener(
				(action, key) ->{
					if(action == GLFW_PRESS && m_Buttons[1].isActived()){
						if(key == GLFW_KEY_ENTER){
							Game.getInstance().setNextLevel(new CreditLevel());
						}
					}
				},
				null
		);
		m_Buttons[2].setListener(
				(action, key) ->{
					if(action == GLFW_PRESS && m_Buttons[2].isActived()){
						if(key == GLFW_KEY_ENTER){
							Game.getInstance().goodBye();
						}
					}
				},
				null
		);
//		m_Buttons[1].setListener(
//				(action, key) ->{
//					if(action == GLFW_PRESS && m_Buttons[1].isActived()){
//						if(key == GLFW_KEY_ENTER){
//						//	Game.getInstance().setNextLevel(new SettingLevel());
//						}
//					}
//				}
//				null,
//				null
//		);
		
//		m_Buttons[2].setListener(
//				(action, key) ->{
//					if(action == GLFW_PRESS && m_Buttons[2].isActived()){
//						if(key == GLFW_KEY_ENTER){
//						//	Game.getInstance().setNextLevel(new StoreLevel());
//						}
//					}
//				}
//				null,
//				null
//		);
		
//		m_Buttons[3].setListener(
//				(action, key) ->{
//					if(action == GLFW_PRESS && m_Buttons[3].isActived()){
//						if(key == GLFW_KEY_ENTER){
//							Game.getInstance().setNextLevel(new CreditLevel());
//						}
//					}
//				},
//				null
//		);
		
//		m_Buttons[4].setListener(
//				(action, key) ->{
//					if(action == GLFW_PRESS && m_Buttons[4].isActived()){
//						if(key == GLFW_KEY_ENTER){
//							Game.getInstance().goodBye();
//						}
//					}
//				},
//				null
//		);
		
		
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
//					if(key == GLFW_KEY_Q) {
//						Game.getInstance().setNextLevel(new InGameLevel(null));
//					}
				}
			},
			(action, button) ->{
				if(action == GLFW_PRESS && button == GLFW_MOUSE_BUTTON_LEFT){
					for(int index = 0; index < m_Buttons.length; index++){
						if(m_Buttons[index].onButton(mouseX, mouseY)){
							if(m_ActiveIndex != index) {
								m_Buttons[index].toggle();
								m_Buttons[m_ActiveIndex].toggle();
							}
							m_Buttons[index].setPressedin(true);
						}else{
							m_Buttons[index].setPressedin(false);
						}
					}
				}
				else if(action == GLFW_RELEASE && button == GLFW_MOUSE_BUTTON_LEFT){
					for(int index = 0; index < m_Buttons.length; index++){
						if(m_Buttons[index].getPressedin()){
							if(m_Buttons[index].onButton(mouseX, mouseY)) {
								m_ActiveIndex = index;
								switch(index){
									case 0: Game.getInstance().setNextLevel(new LoadingLevel()); break;
//									case 1: break;
//									case 2: break;
//									case 3: Game.getInstance().setNextLevel(new CreditLevel()); break;
//									case 4: Game.getInstance().goodBye();
									case 1: Game.getInstance().setNextLevel(new CreditLevel()); break;
									case 2: Game.getInstance().goodBye(); break;
								}
							}
							else {
								m_Buttons[index].toggle();
								m_Buttons[m_ActiveIndex].toggle();
								m_Buttons[index].setPressedin(false);
							}
						}
					}
				}
			}
		);

		m_Buttons[m_ActiveIndex].toggle();
		
		for(int i = 0; i < m_Buttons.length; i++){
			addObject(m_Buttons[i]);
		}

	}
	
	
	@Override
	public void update(float deltaTime) {
		updateObjects(deltaTime);
		
		JSONObject msg = Network.getInstance().pollMessage();
		if(msg != null) {
			protocolProcess(msg);
		}
		
		mouseX = Input.getInstance().getCursorPosition().x;
		mouseY = Input.getInstance().getCursorPosition().y;
	}
	
	private void protocolProcess(JSONObject data){
		System.out.println(data);
		switch((String)data.get("Protocol")){
		case Protocol.LOGIN_RESULT:{
			if((boolean)data.get("Value")){
				
			}else{
				System.out.println("실패");
			}
		}break;
		
		default:System.out.println("unknownProtocol");
		}
	}
	
}
