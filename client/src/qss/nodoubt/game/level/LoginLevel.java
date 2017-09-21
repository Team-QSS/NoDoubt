package qss.nodoubt.game.level;

/*
 * 로그인 화면을 다루는 레벨이다.
 * 실행파일을 실행했을 때 가장 처음 보게 될 레벨일 것임.
 * 일단 임시로 엔터를 눌렀을 때 Lobby 레벨로 가게 구현했지만
 * 로그인을 추후에 구현해야 함.
 * Sign up 이란 버튼을 누르면 회원가입이 됨
 */
import static org.lwjgl.glfw.GLFW.*;

import org.joml.Vector3f;
import org.json.simple.JSONObject;

import protocol.Protocol;
import qss.nodoubt.game.Game;
import qss.nodoubt.game.GameState;
import qss.nodoubt.game.object.*;
import qss.nodoubt.input.Input;
import qss.nodoubt.network.Network;
import util.KeyValue;
import util.Util;
import room.RoomManager;

public class LoginLevel extends GameLevel{
	private TextBox m_ID = null;
	private TextBox m_PW = null;

	private int m_ActiveBuffer = 0;
	
	//버튼
	private Button m_Signin = null;		//로그인
	private Button m_Signup = null;		//회원가입
	
	private float mouseX;
	private float mouseY;
	//배경
	private Background m_LoginBG = null;
	
	public LoginLevel() {
		m_Signin = new Button ("SigninButton", null, 0, -329, 680, 101);
		m_Signup = new Button ("RegisterButton", null, 0, -455, 117, 32);
		m_ID = new TextBox(0.0f, 0.0f, -21.5f, 680.0f, -313.0f, 3.0f, false, "ID", new Vector3f(0,0,0));
		m_PW = new TextBox(0.0f, 0.0f, -172.5f, 680.0f, -313.0f, -148.0f, true, "PW", new Vector3f(0,0,0));
		
		m_Signin.setListener(
				(action, key) -> {
					if(action == GLFW_PRESS && key == GLFW_KEY_ENTER) {
						Game.getInstance().setNextLevel(new InGameLevel(null));
					}
					
					if(action == GLFW_PRESS && key == GLFW_KEY_R) {
						Game.getInstance().setNextLevel(new LobbyLevel());
					}
				},
				(action, button) ->{
						if(action == GLFW_RELEASE && button == GLFW_MOUSE_BUTTON_LEFT){
							if(m_Signin.onButton(mouseX, mouseY)){
							//메시지 전송
							JSONObject loginData=
									Util.packetGenerator(Protocol.LOGIN_REQUEST,
									new KeyValue("ID", m_ID.m_Text.toString()),
									new KeyValue("Password", m_PW.m_Text.toString())
									);
							Network.getInstance().pushMessage(loginData);
						}
					}
				});
		m_Signup.setListener(
				null,
				(action, button) ->{
						if(action == GLFW_RELEASE && button == GLFW_MOUSE_BUTTON_LEFT){
							if(m_Signup.onButton(mouseX, mouseY)){
							Game.getInstance().setNextLevel(new SignUpLevel());
						}
					}
				});
		
		setEventListener(
				(action, key) ->{
					if(m_ActiveBuffer == 0){
						if(action == GLFW_PRESS){
							if(key == GLFW_KEY_TAB){
								m_ActiveBuffer = 1;
								m_ID.setInActive();
								m_PW.setActive();
							}
						}
					}
				},
				(action, button)->{
					if(action == GLFW_PRESS && button == GLFW_MOUSE_BUTTON_LEFT){
						if(mouseX>-340 && mouseX<340){
							if(mouseY<22 && mouseY>-65){
								m_ActiveBuffer = 0;
								m_ID.setActive();
								m_PW.setInActive();
							}
							else if(mouseY<-129 && mouseY>-216){
								m_ActiveBuffer = 1;
								m_ID.setInActive();
								m_PW.setActive();
							}
						}
					}
				});
		
		m_LoginBG = new Background("LoginBG");
		m_ID.setActive();
		
		addObject(m_Signin);
		addObject(m_Signup);
		addObject(m_LoginBG);
		addObject(m_ID);
		addObject(m_PW);
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
		m_ID.update(deltaTime);
		m_PW.update(deltaTime);
	}
	
	private void protocolProcess(JSONObject data){
		System.out.println(data);
		switch((String)data.get("Protocol")){
		case Protocol.LOGIN_RESULT:{
			if((boolean)data.get("Value")){
				Game.getInstance().setNextLevel(new LobbyLevel());
				System.out.println("로그인 성공");
				System.out.println(Util.printJSONLookSimple(data.get("User").toString()));
			}else{
				System.out.println("실패");
				m_ID.m_Text.delete(0, m_ID.m_Text.length());
				m_PW.m_Text.delete(0, m_PW.m_Text.length());
				m_ID.setActive();
				m_PW.setInActive();
				m_ActiveBuffer = 0;
			}
		}break;
		
		default:System.out.println("unknownProtocol");
		}
	}
}

