package qss.nodoubt.game.level;

/*
 * 로그인 화면을 다루는 레벨이다.
 * 실행파일을 실행했을 때 가장 처음 보게 될 레벨일 것임.
 * 일단 임시로 엔터를 눌렀을 때 Lobby 레벨로 가게 구현했지만
 * 로그인을 추후에 구현해야 함.
 * Sign up 이란 버튼을 누르면 회원가입이 됨
 */

import static org.lwjgl.glfw.GLFW.*;

import org.joml.Vector2f;
import org.joml.Vector3f;

import qss.nodoubt.game.*;
import qss.nodoubt.game.object.*;
import qss.nodoubt.input.Input;
import qss.nodoubt.network.Message;
import qss.nodoubt.network.Network;

public class LoginLevel extends GameLevel{
	private StringBuffer m_IDBuffer = null;
	private StringBuffer m_PWBuffer = null;
	private int m_ActiveBuffer = 0;
	private boolean m_isShiftPressed = false;
	
	private Message m_Message = null;
	private Network m_Network = null;
	//버튼
	private Button m_Signin = null;		//로그인
	private Button m_Signup = null;		//회원가입
	
	private Label m_IDLabel = null;
	private Label m_PWLabel = null;
	
	private float mouseX;
	private float mouseY;
	//배경
	private Background m_LoginBG = null;
	
	public LoginLevel() {
		
		m_Signin = new Button ("SigninButton", null, 0, -329);
		m_Signup = new Button ("SignupButton", null, 0, -455);
		m_IDLabel = new Label ("IDLabel", -370, -20);
		m_PWLabel = new Label ("PWLabel", -380, -170);
		m_IDBuffer = new StringBuffer();
		m_PWBuffer = new StringBuffer();		
		
		m_Signin.setListener(
				(action, key) -> {
					if(action == GLFW_PRESS && key == GLFW_KEY_ENTER){
						m_Network.pushMessage(m_Message);
						Game.getInstance().setNextLevel(new LobbyLevel());
					}
				},
				(action, button) ->{
						if(action == GLFW_RELEASE && button == GLFW_MOUSE_BUTTON_LEFT){
							if(m_Signin.onButton(mouseX, mouseY)){
							m_Network.pushMessage(m_Message);
							Game.getInstance().setNextLevel(new LobbyLevel());
						}
					}
				});
		m_Signup.setListener(
				null,
				(action, button) ->{
						if(action == GLFW_RELEASE && button == GLFW_MOUSE_BUTTON_LEFT){
							if(m_Signup.onButton(mouseX, mouseY)){
						//	Game.getInstance().setNextLevel(new SignUpLevel());
						}
					}
				});
		setEventListener((action,  key) -> { 
			if(key == GLFW_KEY_ENTER){
				if(action == GLFW_PRESS){
					Game.getInstance().setNextLevel(new LobbyLevel());
				}
			}
		},
		null);
		
		m_Network = Network.getInstance();
		m_Message = new Message();
		m_Message = m_Message.setProtocol("LoginRequest");

		
		setEventListener(
				(action, key) ->{
					if(m_ActiveBuffer == 0){
						if(action == GLFW_PRESS){
							if(key == GLFW_KEY_LEFT_SHIFT){
								m_isShiftPressed = true;
							}
							if(key == GLFW_KEY_TAB){
								m_ActiveBuffer = 1;
							}
							if(m_IDBuffer.length() < 16){
								if(m_isShiftPressed){
									switch (key){
									case GLFW_KEY_A : m_IDBuffer.append("A"); break;
									case GLFW_KEY_B : m_IDBuffer.append("B"); break;
									case GLFW_KEY_C : m_IDBuffer.append("C"); break;
									case GLFW_KEY_D : m_IDBuffer.append("D"); break;
									case GLFW_KEY_E : m_IDBuffer.append("E"); break;
									case GLFW_KEY_F : m_IDBuffer.append("F"); break;
									case GLFW_KEY_G : m_IDBuffer.append("G"); break;
									case GLFW_KEY_H : m_IDBuffer.append("H"); break;
									case GLFW_KEY_I : m_IDBuffer.append("I"); break;
									case GLFW_KEY_J : m_IDBuffer.append("J"); break;
									case GLFW_KEY_K : m_IDBuffer.append("K"); break;
									case GLFW_KEY_L : m_IDBuffer.append("L"); break;
									case GLFW_KEY_M : m_IDBuffer.append("M"); break;
									case GLFW_KEY_N : m_IDBuffer.append("N"); break;
									case GLFW_KEY_O : m_IDBuffer.append("O"); break;
									case GLFW_KEY_P : m_IDBuffer.append("P"); break;
									case GLFW_KEY_Q : m_IDBuffer.append("Q"); break;
									case GLFW_KEY_R : m_IDBuffer.append("R"); break;
									case GLFW_KEY_S : m_IDBuffer.append("S"); break;
									case GLFW_KEY_T : m_IDBuffer.append("T"); break;
									case GLFW_KEY_U : m_IDBuffer.append("U"); break;
									case GLFW_KEY_V : m_IDBuffer.append("V"); break;
									case GLFW_KEY_W : m_IDBuffer.append("W"); break;
									case GLFW_KEY_X : m_IDBuffer.append("X"); break;
									case GLFW_KEY_Y : m_IDBuffer.append("Y"); break;
									case GLFW_KEY_Z : m_IDBuffer.append("Z"); break;
									case GLFW_KEY_1 : m_IDBuffer.append("1"); break;
									case GLFW_KEY_2 : m_IDBuffer.append("2"); break;
									case GLFW_KEY_3 : m_IDBuffer.append("3"); break;
									case GLFW_KEY_4 : m_IDBuffer.append("4"); break;
									case GLFW_KEY_5 : m_IDBuffer.append("5"); break;
									case GLFW_KEY_6 : m_IDBuffer.append("6"); break;
									case GLFW_KEY_7 : m_IDBuffer.append("7"); break;
									case GLFW_KEY_8 : m_IDBuffer.append("8"); break;
									case GLFW_KEY_9 : m_IDBuffer.append("9"); break;
									case GLFW_KEY_0 : m_IDBuffer.append("0"); break;
									}
								}
								else if (!m_isShiftPressed){
									switch (key){
									case GLFW_KEY_A : m_IDBuffer.append("a"); break;
									case GLFW_KEY_B : m_IDBuffer.append("b"); break;
									case GLFW_KEY_C : m_IDBuffer.append("c"); break;
									case GLFW_KEY_D : m_IDBuffer.append("d"); break;
									case GLFW_KEY_E : m_IDBuffer.append("e"); break;
									case GLFW_KEY_F : m_IDBuffer.append("f"); break;
									case GLFW_KEY_G : m_IDBuffer.append("g"); break;
									case GLFW_KEY_H : m_IDBuffer.append("h"); break;
									case GLFW_KEY_I : m_IDBuffer.append("i"); break;
									case GLFW_KEY_J : m_IDBuffer.append("j"); break;
									case GLFW_KEY_K : m_IDBuffer.append("k"); break;
									case GLFW_KEY_L : m_IDBuffer.append("l"); break;
									case GLFW_KEY_M : m_IDBuffer.append("m"); break;
									case GLFW_KEY_N : m_IDBuffer.append("n"); break;
									case GLFW_KEY_O : m_IDBuffer.append("o"); break;
									case GLFW_KEY_P : m_IDBuffer.append("p"); break;
									case GLFW_KEY_Q : m_IDBuffer.append("q"); break;
									case GLFW_KEY_R : m_IDBuffer.append("r"); break;
									case GLFW_KEY_S : m_IDBuffer.append("s"); break;
									case GLFW_KEY_T : m_IDBuffer.append("t"); break;
									case GLFW_KEY_U : m_IDBuffer.append("u"); break;
									case GLFW_KEY_V : m_IDBuffer.append("v"); break;
									case GLFW_KEY_W : m_IDBuffer.append("w"); break;
									case GLFW_KEY_X : m_IDBuffer.append("x"); break;
									case GLFW_KEY_Y : m_IDBuffer.append("y"); break;
									case GLFW_KEY_Z : m_IDBuffer.append("z"); break;
									case GLFW_KEY_1 : m_IDBuffer.append("1"); break;
									case GLFW_KEY_2 : m_IDBuffer.append("2"); break;
									case GLFW_KEY_3 : m_IDBuffer.append("3"); break;
									case GLFW_KEY_4 : m_IDBuffer.append("4"); break;
									case GLFW_KEY_5 : m_IDBuffer.append("5"); break;
									case GLFW_KEY_6 : m_IDBuffer.append("6"); break;
									case GLFW_KEY_7 : m_IDBuffer.append("7"); break;
									case GLFW_KEY_8 : m_IDBuffer.append("8"); break;
									case GLFW_KEY_9 : m_IDBuffer.append("9"); break;
									case GLFW_KEY_0 : m_IDBuffer.append("0"); break;
								}
								}
							}
							if(m_IDBuffer.length() > 0){
								if(key == GLFW_KEY_BACKSPACE){
									m_IDBuffer.deleteCharAt(m_IDBuffer.length()-1);
								}
							}
						}
						else if(action == GLFW_REPEAT){
							if(m_IDBuffer.length() > 0){
								if(key == GLFW_KEY_BACKSPACE){
									m_IDBuffer.deleteCharAt(m_IDBuffer.length()-1);
								}
							}
						}
						else if(action == GLFW_RELEASE && key == GLFW_KEY_LEFT_SHIFT){
								m_isShiftPressed = false;
						}
					}
					else if(m_ActiveBuffer == 1){
						if(action == GLFW_PRESS){
							if(key == GLFW_KEY_LEFT_SHIFT){
								m_isShiftPressed = true;
							}
							
							if(m_PWBuffer.length() < 16){
								if(m_isShiftPressed){
									switch (key){
										case GLFW_KEY_A : m_PWBuffer.append("A"); break;
										case GLFW_KEY_B : m_PWBuffer.append("B"); break;
										case GLFW_KEY_C : m_PWBuffer.append("C"); break;
										case GLFW_KEY_D : m_PWBuffer.append("D"); break;
										case GLFW_KEY_E : m_PWBuffer.append("E"); break;
										case GLFW_KEY_F : m_PWBuffer.append("F"); break;
										case GLFW_KEY_G : m_PWBuffer.append("G"); break;
										case GLFW_KEY_H : m_PWBuffer.append("H"); break;
										case GLFW_KEY_I : m_PWBuffer.append("I"); break;
										case GLFW_KEY_J : m_PWBuffer.append("J"); break;
										case GLFW_KEY_K : m_PWBuffer.append("K"); break;
										case GLFW_KEY_L : m_PWBuffer.append("L"); break;
										case GLFW_KEY_M : m_PWBuffer.append("M"); break;
										case GLFW_KEY_N : m_PWBuffer.append("N"); break;
										case GLFW_KEY_O : m_PWBuffer.append("O"); break;
										case GLFW_KEY_P : m_PWBuffer.append("P"); break;
										case GLFW_KEY_Q : m_PWBuffer.append("Q"); break;
										case GLFW_KEY_R : m_PWBuffer.append("R"); break;
										case GLFW_KEY_S : m_PWBuffer.append("S"); break;
										case GLFW_KEY_T : m_PWBuffer.append("T"); break;
										case GLFW_KEY_U : m_PWBuffer.append("U"); break;
										case GLFW_KEY_V : m_PWBuffer.append("V"); break;
										case GLFW_KEY_W : m_PWBuffer.append("W"); break;
										case GLFW_KEY_X : m_PWBuffer.append("X"); break;
										case GLFW_KEY_Y : m_PWBuffer.append("Y"); break;
										case GLFW_KEY_Z : m_PWBuffer.append("Z"); break;
										case GLFW_KEY_1 : m_PWBuffer.append("1"); break;
										case GLFW_KEY_2 : m_PWBuffer.append("2"); break;
										case GLFW_KEY_3 : m_PWBuffer.append("3"); break;
										case GLFW_KEY_4 : m_PWBuffer.append("4"); break;
										case GLFW_KEY_5 : m_PWBuffer.append("5"); break;
										case GLFW_KEY_6 : m_PWBuffer.append("6"); break;
										case GLFW_KEY_7 : m_PWBuffer.append("7"); break;
										case GLFW_KEY_8 : m_PWBuffer.append("8"); break;
										case GLFW_KEY_9 : m_PWBuffer.append("9"); break;
										case GLFW_KEY_0 : m_PWBuffer.append("0"); break;
									}
								}
								else{
									switch (key){
										case GLFW_KEY_A : m_PWBuffer.append("a"); break;
										case GLFW_KEY_B : m_PWBuffer.append("b"); break;
										case GLFW_KEY_C : m_PWBuffer.append("c"); break;
										case GLFW_KEY_D : m_PWBuffer.append("d"); break;
										case GLFW_KEY_E : m_PWBuffer.append("e"); break;
										case GLFW_KEY_F : m_PWBuffer.append("f"); break;
										case GLFW_KEY_G : m_PWBuffer.append("g"); break;
										case GLFW_KEY_H : m_PWBuffer.append("h"); break;
										case GLFW_KEY_I : m_PWBuffer.append("i"); break;
										case GLFW_KEY_J : m_PWBuffer.append("j"); break;
										case GLFW_KEY_K : m_PWBuffer.append("k"); break;
										case GLFW_KEY_L : m_PWBuffer.append("l"); break;
										case GLFW_KEY_M : m_PWBuffer.append("m"); break;
										case GLFW_KEY_N : m_PWBuffer.append("n"); break;
										case GLFW_KEY_O : m_PWBuffer.append("o"); break;
										case GLFW_KEY_P : m_PWBuffer.append("p"); break;
										case GLFW_KEY_Q : m_PWBuffer.append("q"); break;
										case GLFW_KEY_R : m_PWBuffer.append("r"); break;
										case GLFW_KEY_S : m_PWBuffer.append("s"); break;
										case GLFW_KEY_T : m_PWBuffer.append("t"); break;
										case GLFW_KEY_U : m_PWBuffer.append("u"); break;
										case GLFW_KEY_V : m_PWBuffer.append("v"); break;
										case GLFW_KEY_W : m_PWBuffer.append("w"); break;
										case GLFW_KEY_X : m_PWBuffer.append("x"); break;
										case GLFW_KEY_Y : m_PWBuffer.append("y"); break;
										case GLFW_KEY_Z : m_PWBuffer.append("z"); break;
										case GLFW_KEY_1 : m_PWBuffer.append("1"); break;
										case GLFW_KEY_2 : m_PWBuffer.append("2"); break;
										case GLFW_KEY_3 : m_PWBuffer.append("3"); break;
										case GLFW_KEY_4 : m_PWBuffer.append("4"); break;
										case GLFW_KEY_5 : m_PWBuffer.append("5"); break;
										case GLFW_KEY_6 : m_PWBuffer.append("6"); break;
										case GLFW_KEY_7 : m_PWBuffer.append("7"); break;
										case GLFW_KEY_8 : m_PWBuffer.append("8"); break;
										case GLFW_KEY_9 : m_PWBuffer.append("9"); break;
										case GLFW_KEY_0 : m_PWBuffer.append("0"); break;
									}
								}
							}
							if(m_PWBuffer.length() > 0){
								if(key == GLFW_KEY_BACKSPACE){
									m_PWBuffer.deleteCharAt(m_PWBuffer.length()-1);
								}
							}
						}
						else if(action == GLFW_REPEAT){
							if(m_IDBuffer.length() > 0){
								if(key == GLFW_KEY_BACKSPACE){
									m_IDBuffer.deleteCharAt(m_IDBuffer.length()-1);
								}
							}
						}
						else if(action == GLFW_RELEASE){
							if(key == GLFW_KEY_LEFT_SHIFT){
								m_isShiftPressed = false;
							}
						}
					}
				},
				(action, button)->{
					if(action == GLFW_PRESS && button == GLFW_MOUSE_BUTTON_LEFT){
						if(mouseX>-340 && mouseX<340){
							if(mouseY<22 && mouseY>-65){
								m_ActiveBuffer = 0;
							}
							else if(mouseY<-129 && mouseY>-216){
								m_ActiveBuffer = 1;
							}
						}
					}
				});
		
		m_LoginBG = new Background("LoginBG");
		addObject(m_IDLabel);
		addObject(m_PWLabel);
		addObject(m_Signin);
		addObject(m_Signup);
		addObject(m_LoginBG);
	}

	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub
		mouseX = Input.getInstance().getCursorPosition().x;
		mouseY = Input.getInstance().getCursorPosition().y;
		m_Message = m_Message.addValue("ID", m_IDBuffer.toString());
		m_Message = m_Message.addValue("Password", m_PWBuffer.toString());
				
		drawTextCall("fontB", m_IDBuffer.toString(), new Vector2f(-325,25), new Vector3f(0,0,0));
		drawTextCall("fontB", m_PWBuffer.toString(), new Vector2f(-325,-125), new Vector3f(0,0,0));
	}
}

