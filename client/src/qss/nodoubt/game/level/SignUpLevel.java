package qss.nodoubt.game.level;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_BACKSPACE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ENTER;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_SHIFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_TAB;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.GLFW_REPEAT;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.json.simple.JSONObject;

import protocol.Protocol;
import qss.nodoubt.game.Game;
import qss.nodoubt.game.object.Background;
import qss.nodoubt.game.object.Button;
import qss.nodoubt.game.object.TextBox;
import qss.nodoubt.input.Input;
import qss.nodoubt.network.Network;
import util.KeyValue;
import util.Util;

/*
 * 회원가입을 하게 되는 단계이며
 * LoginLevel에서 SignUp 버튼을 눌렀을 시 오게 되는 단계이다.
 * 이 단계가 끝나면 setNextLevel 메서드를 통해 다시 LoginLevel로 가게 된다.
 * 아이디 규약은 다른 플레이어와 중복되지 않는 16자 이내의 영문과 숫자가 허용되는 문자열이다.
 */

public class SignUpLevel extends GameLevel{
	private TextBox m_ID = null;
	private TextBox m_PW = null;
	private TextBox m_PWRepeat = null;
	
	private Network m_Network = null;
	
	private Background m_SignUpBG = null;
	
	private Button m_Signup = null;
	
	private float mouseX;
	private float mouseY;
	private boolean m_isShiftPressed = false;
	private int m_ActiveBuffer = 0;
	
	public SignUpLevel() {
		m_ID = new TextBox(0, 0.0f, -3.0f, 680.0f, -313.0f, 15.0f, false, "ID", new Vector3f(0, 0, 0));
		m_PW = new TextBox(0, 0.0f, -3.0f, 680.0f, -313.0f, -116.0f, true, "PW", new Vector3f(0, 0, 0));
		m_PWRepeat = new TextBox(0, 0.0f, -3.0f, 680.0f, -313.0f, -240.0f, true, "Retype", new Vector3f(0, 0, 0));
		
		m_Network = Network.getInstance();
		
		m_SignUpBG = new Background("SignupBG");
		m_Signup = new Button("SignupButton", null, 0, -395);
		
		m_Signup.setListener(
				(action, key) ->{
					if(action == GLFW_PRESS && key == GLFW_KEY_ENTER){
						if(m_PW.m_Text.toString().equals(m_PWRepeat.toString())){
							//메시지 전송
							JSONObject signUpData=
									Util.packetGenerator(Protocol.REGISTER_REQUEST,
									new KeyValue("ID", m_ID.m_Text.toString()),
									new KeyValue("Password", m_PW.m_Text.toString())
									);
							Network.getInstance().pushMessage(signUpData);
						}else{
							m_ID.m_Text.delete(0, m_ID.m_Text.length());
							m_PW.m_Text.delete(0, m_PW.m_Text.length());
							m_PWRepeat.m_Text.delete(0, m_PW.m_Text.length());
							m_ActiveBuffer = 0;
						}
					}
				},
				(action, button) ->{
					if(action == GLFW_RELEASE && button == GLFW_MOUSE_BUTTON_LEFT){
						if(m_Signup.onButton(mouseX, mouseY)){
							if(m_PW.m_Text.toString().equals(m_PWRepeat.m_Text.toString())){
								//메시지 전송
								JSONObject signUpData=
										Util.packetGenerator(Protocol.REGISTER_REQUEST,
										new KeyValue("ID", m_ID.m_Text.toString()),
										new KeyValue("Password", m_PW.m_Text.toString())
										);
								Network.getInstance().pushMessage(signUpData);
							}else{
								m_ID.m_Text.delete(0, m_ID.m_Text.length());
								m_PW.m_Text.delete(0, m_PW.m_Text.length());
								m_PWRepeat.m_Text.delete(0, m_PWRepeat.m_Text.length());
								m_ActiveBuffer = 0;
							}
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
							if(m_ID.m_Text.length() < 16){
								if(key>=65 && key<=90){
									if(m_isShiftPressed){
									m_ID.m_Text.append((char)key);
									}
									else if (!m_isShiftPressed){
										m_ID.m_Text.append((char)(key+32));
									}
								}
								else if(key>=48 && key<= 57){
									m_ID.m_Text.append((char)key);
								}
								else if(key>=320 && key<=329){
									m_ID.m_Text.append((char)(key-272));
								}
							}
							if(m_ID.m_Text.length() > 0){
								if(key == GLFW_KEY_BACKSPACE){
									m_ID.m_Text.deleteCharAt(m_ID.m_Text.length()-1);
								}
							}
						}
						else if(action == GLFW_REPEAT){
							if(m_ID.m_Text.length() < 16){
								if(key>=65 && key<=90){
									if(m_isShiftPressed){
									m_ID.m_Text.append((char)key);
									}
									else if (!m_isShiftPressed){
										m_ID.m_Text.append((char)(key+32));
									}
								}
								else if(key>=48 && key<= 57){
									m_ID.m_Text.append((char)key);
								}
								else if(key>=320 && key<=329){
									m_ID.m_Text.append((char)(key-272));
								}
							}
							if(m_ID.m_Text.length() > 0){
								if(key == GLFW_KEY_BACKSPACE){
									m_ID.m_Text.deleteCharAt(m_ID.m_Text.length()-1);
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
							if(key == GLFW_KEY_TAB){
								m_ActiveBuffer = 2;
							}
							if(m_PW.m_Text.length() < 16){
								if(key>=65 && key<=90){
									if(m_isShiftPressed){
										m_PW.m_Text.append((char)key);
									}
									else if (!m_isShiftPressed){
										m_PW.m_Text.append((char)(key+32));
									}
								}
								else if(key>=48 && key<= 57){
									m_PW.m_Text.append((char)key);
								}
								else if(key>=320 && key<=329){
									m_PW.m_Text.append((char)(key-272));
								}
							}
							if(m_PW.m_Text.length() > 0){
								if(key == GLFW_KEY_BACKSPACE){
									m_PW.m_Text.deleteCharAt(m_PW.m_Text.length()-1);
								}
							}
						}
						else if(action == GLFW_REPEAT){
							if(m_PW.m_Text.length() < 16){
								if(m_PW.m_Text.length() < 16){
									if(key>=65 && key<=90){
										if(m_isShiftPressed){
											m_PW.m_Text.append((char)key);
										}
										else if (!m_isShiftPressed){
											m_PW.m_Text.append((char)(key+32));
										}
									}
									else if(key>=48 && key<= 57){
										m_PW.m_Text.append((char)key);
									}
									else if(key>=320 && key<=329){
										m_PW.m_Text.append((char)(key-272));
									}
								}
							}
							if(m_PW.m_Text.length() > 0){
								if(key == GLFW_KEY_BACKSPACE){
									m_PW.m_Text.deleteCharAt(m_PW.m_Text.length()-1);
								}
							}
						}
						else if(action == GLFW_RELEASE){
							if(key == GLFW_KEY_LEFT_SHIFT){
								m_isShiftPressed = false;
							}
						}
					}
					
					else if(m_ActiveBuffer == 2){
						if(action == GLFW_PRESS){
							if(key == GLFW_KEY_LEFT_SHIFT){
								m_isShiftPressed = true;
							}
							if(key == GLFW_KEY_TAB){
								m_ActiveBuffer = 2;
							}
							if(m_PWRepeat.m_Text.length() < 16){
								if(key>=65 && key<=90){
									if(m_isShiftPressed){
										m_PWRepeat.m_Text.append((char)key);
									}
									else if (!m_isShiftPressed){
										m_PWRepeat.m_Text.append((char)(key+32));
									}
								}
								else if(key>=48 && key<= 57){
									m_PWRepeat.m_Text.append((char)key);
								}
								else if(key>=320 && key<=329){
									m_PWRepeat.m_Text.append((char)(key-272));
								}
							}
							if(m_PWRepeat.m_Text.length() > 0){
								if(key == GLFW_KEY_BACKSPACE){
									m_PWRepeat.m_Text.deleteCharAt(m_PWRepeat.m_Text.length()-1);
								}
							}
						}
						else if(action == GLFW_REPEAT){
							if(m_PWRepeat.m_Text.length() < 16){
								if(key>=65 && key<=90){
									if(m_isShiftPressed){
										m_PWRepeat.m_Text.append((char)key);
									}
									else if (!m_isShiftPressed){
										m_PWRepeat.m_Text.append((char)(key+32));
									}
								}
								else if(key>=48 && key<= 57){
									m_PWRepeat.m_Text.append((char)key);
								}
								else if(key>=320 && key<=329){
									m_PWRepeat.m_Text.append((char)(key-272));
								}
							}
							
							if(m_PWRepeat.m_Text.length() > 0){
								if(key == GLFW_KEY_BACKSPACE){
									m_PWRepeat.m_Text.deleteCharAt(m_PWRepeat.m_Text.length()-1);
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
							if(mouseY<40 && mouseY>-46){
								m_ActiveBuffer = 0;
							}
							else if(mouseY<-88 && mouseY>-176){
								m_ActiveBuffer = 1;
							}
							else if(mouseY<-216 && mouseY>-304){
								m_ActiveBuffer = 2;
							}
						}
					}
				});
		addObject(m_SignUpBG);
		addObject(m_Signup);
		addObject(m_ID);
		addObject(m_PW);
		addObject(m_PWRepeat);
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
		m_PWRepeat.update(deltaTime);
	}
	
	private void protocolProcess(JSONObject data){
		System.out.println(data);
		switch((String)data.get("Protocol")){
		case Protocol.REGISTER_RESULT:{
			if((boolean)data.get("Value")){
				Game.getInstance().setNextLevel(new LoginLevel());
			}else{
				System.out.println("실패");
				m_ID.m_Text.delete(0, m_ID.m_Text.length());
				m_PW.m_Text.delete(0, m_PW.m_Text.length());
				m_PWRepeat.m_Text.delete(0, m_PWRepeat.m_Text.length());
			}
		}break;
		
		default:System.out.println("unknownProtocol");
		}
	}
}
