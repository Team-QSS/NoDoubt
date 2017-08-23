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
import org.json.simple.JSONObject;

import protocol.Protocol;
import qss.nodoubt.game.*;
import qss.nodoubt.game.object.*;
import qss.nodoubt.input.Input;
import qss.nodoubt.network.Network;
import util.KeyValue;
import util.Util;

public class LoginLevel extends GameLevel{
	private StringBuffer m_IDBuffer = null;
	private StringBuffer m_PWBuffer = null;
	private StringBuffer m_Star = null;
	private int m_ActiveBuffer = 0;
	private boolean m_isShiftPressed = false;
	
	private Network m_Network = null;
	//버튼
	private Button m_Signin = null;		//로그인
	private Button m_Signup = null;		//회원가입
	
	private float mouseX;
	private float mouseY;
	//배경
	private Background m_LoginBG = null;
	
	public LoginLevel() {
		m_Network = Network.getInstance();
		m_Signin = new Button ("SigninButton", null, 0, -329, 680, 101);
		m_Signup = new Button ("RegisterButton", null, 0, -455, 117, 32);
		m_IDBuffer = new StringBuffer();
		m_PWBuffer = new StringBuffer();
		m_Star = new StringBuffer();
		
		m_Signin.setListener(
				(action, key) -> {
					if(action == GLFW_PRESS && key == GLFW_KEY_ENTER){
						//메시지 전송
						JSONObject loginData=
								Util.packetGenerator(Protocol.LOGIN_REQUEST,
								new KeyValue("ID", m_IDBuffer.toString()),
								new KeyValue("Password", m_PWBuffer.toString())
								);
						Network.getInstance().pushMessage(loginData);
					}else if(action == GLFW_PRESS && key == GLFW_KEY_Q) {
						Game.getInstance().setNextLevel(new LobbyLevel());
					}
				},
				(action, button) ->{
						if(action == GLFW_RELEASE && button == GLFW_MOUSE_BUTTON_LEFT){
							if(m_Signin.onButton(mouseX, mouseY)){
							//메시지 전송
							JSONObject loginData=
									Util.packetGenerator(Protocol.LOGIN_REQUEST,
									new KeyValue("ID", m_IDBuffer.toString()),
									new KeyValue("Password", m_PWBuffer.toString())
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
							if(key == GLFW_KEY_LEFT_SHIFT){
								m_isShiftPressed = true;
							}
							if(key == GLFW_KEY_TAB){
								m_ActiveBuffer = 1;
							}
							if(m_IDBuffer.length() < 16){
								if(key>=65 && key<=90){
									if(m_isShiftPressed){
									m_IDBuffer.append((char)key);
									}
									else if (!m_isShiftPressed){
										m_IDBuffer.append((char)(key+32));
									}
								}
								else if(key>=48 && key<= 57){
									m_IDBuffer.append((char)key);
								}
								else if(key>=320 && key<=329){
									m_IDBuffer.append((char)(key-272));
								}
							}
							if(m_IDBuffer.length() > 0){
								if(key == GLFW_KEY_BACKSPACE){
									m_IDBuffer.deleteCharAt(m_IDBuffer.length()-1);
								}
							}
						}
						else if(action == GLFW_REPEAT){
							if(m_IDBuffer.length() < 16){
								if(key>=65 && key<=90){
									if(m_isShiftPressed){
									m_IDBuffer.append((char)key);
									}
									else if (!m_isShiftPressed){
										m_IDBuffer.append((char)(key+32));
									}
								}
								else if(key>=48 && key<= 57){
									m_IDBuffer.append((char)key);
								}
								else if(key>=320 && key<=329){
									m_IDBuffer.append((char)(key-272));
								}
							}
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
								if(key>=65 && key<=90){
									if(m_isShiftPressed){
									m_PWBuffer.append((char)key);
									}
									else if (!m_isShiftPressed){
										m_PWBuffer.append((char)(key+32));
									}
								}
								else if(key>=48 && key<= 57){
									m_PWBuffer.append((char)key);
								}
								else if(key>=320 && key<=329){
									m_PWBuffer.append((char)(key-272));
								}
							}
							
							if(m_PWBuffer.length() > 0){
								if(key == GLFW_KEY_BACKSPACE){
									m_PWBuffer.deleteCharAt(m_PWBuffer.length()-1);
								}
							}
						}
						else if(action == GLFW_REPEAT){
							if(m_PWBuffer.length() < 16){
								if(m_PWBuffer.length() < 16){
									if(key>=65 && key<=90){
										if(m_isShiftPressed){
											m_PWBuffer.append((char)key);
										}
										else if (!m_isShiftPressed){
											m_PWBuffer.append((char)(key+32));
										}
									}
									else if(key>=48 && key<= 57){
										m_PWBuffer.append((char)key);
									}
									else if(key>=320 && key<=329){
										m_PWBuffer.append((char)(key-272));
									}
								}
							}
							if(m_PWBuffer.length() > 0){
								if(key == GLFW_KEY_BACKSPACE){
									m_PWBuffer.deleteCharAt(m_PWBuffer.length()-1);
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
		addObject(m_Signin);
		addObject(m_Signup);
		addObject(m_LoginBG);
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
		if(m_Star.length() > m_PWBuffer.length()){
			for(int i = 0; i < m_Star.length() - m_PWBuffer.length(); i++){
				m_Star.deleteCharAt(m_Star.length()-1);
			}
		}else if(m_Star.length() < m_PWBuffer.length()){
			for(int i = 0; i < m_PWBuffer.length() - m_Star.length(); i++){
				m_Star.append("*");
			}
		}
		
		if(m_IDBuffer.length()==0){
			drawTextCall("fontR11", "ID", new Vector2f(-313, 3), new Vector3f(0x82/255f, 0x82/255f, 0x82/255f));
		}
		if(m_PWBuffer.length()==0){
			drawTextCall("fontR11", "PW", new Vector2f(-313, -148), new Vector3f(0x82/255f, 0x82/255f, 0x82/255f));
		}
		
		drawTextCall("fontR11", m_IDBuffer.toString(), new Vector2f(-313,3), new Vector3f(0,0,0));
		drawTextCall("fontR11", m_Star.toString(), new Vector2f(-313,-148), new Vector3f(0,0,0));
	}
	
	private void protocolProcess(JSONObject data){
		System.out.println(data);
		switch((String)data.get("Protocol")){
		case Protocol.LOGIN_RESULT:{
			if((boolean)data.get("Value")){
				Game.getInstance().setNextLevel(new LobbyLevel());
				System.out.println("로그인 성공");
				Util.printJSONLookSimple(data.get("User").toString());
				Util.printJSONLookSimple(data.get("RoomManager").toString());
			}else{
				System.out.println("실패");
				m_IDBuffer.delete(0, m_IDBuffer.length());
				m_PWBuffer.delete(0, m_PWBuffer.length());
			}
		}break;
		
		default:System.out.println("unknownProtocol");
		}
	}
}

