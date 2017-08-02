package qss.nodoubt.game.level;

import qss.nodoubt.game.Game;
import qss.nodoubt.game.GameState;
import qss.nodoubt.game.object.*;
import qss.nodoubt.input.Input;
import qss.nodoubt.network.*;

import static org.lwjgl.glfw.GLFW.*;

import org.joml.*;

/*
 * 회원가입을 하게 되는 단계이며
 * LoginLevel에서 SignUp 버튼을 눌렀을 시 오게 되는 단계이다.
 * 이 단계가 끝나면 setNextLevel 메서드를 통해 다시 LoginLevel로 가게 된다.
 * 아이디 규약은 다른 플레이어와 중복되지 않는 16자 이내의 영문과 숫자가 허용되는 문자열이다.
 */

public class SignUpLevel extends GameLevel{
	private StringBuffer m_IDBuffer = null;
	private StringBuffer m_PWBuffer = null;
	private StringBuffer m_PWRepeat = null;
	private StringBuffer m_Star1 = null;
	private StringBuffer m_Star2 = null;
	
	private Network m_Network = null;
	
	private Background m_SignUpBG = null;
	
	private Button m_Signup = null;
	
	private float mouseX;
	private float mouseY;
	private boolean m_isShiftPressed = false;
	private int m_ActiveBuffer = 0;
	
	public SignUpLevel() {
		m_IDBuffer = new StringBuffer();
		m_PWBuffer = new StringBuffer();
		m_PWRepeat = new StringBuffer();
		m_Star1 = new StringBuffer();
		m_Star2 = new StringBuffer();
		
		m_Network = Network.getInstance();
		
		m_SignUpBG = new Background("SignupBG");
		m_Signup = new Button("SignupButton", null, 0, -395);
		
		m_Signup.setListener(
				(action, key) ->{
					if(action == GLFW_PRESS && key == GLFW_KEY_ENTER){
						if(m_PWBuffer.toString().equals(m_PWRepeat.toString())){
							//메시지 전송
						}else{
							m_IDBuffer.delete(0, m_IDBuffer.length());
							m_PWBuffer.delete(0, m_PWBuffer.length());
							m_PWRepeat.delete(0, m_PWRepeat.length());
							m_ActiveBuffer = 0;
						}
					}
				},
				(action, button) ->{
					if(action == GLFW_RELEASE && button == GLFW_MOUSE_BUTTON_LEFT){
						if(m_Signup.onButton(mouseX, mouseY)){
							if(m_PWBuffer.toString().equals(m_PWRepeat.toString())){
								//메시지 전송
							}else{
								m_IDBuffer.delete(0, m_IDBuffer.length());
								m_PWBuffer.delete(0, m_PWBuffer.length());
								m_PWRepeat.delete(0, m_PWRepeat.length());
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
							if(m_IDBuffer.length() < 16){
								if(m_isShiftPressed){
									if(key>=65 && key<=90){
										m_IDBuffer.append((char)key);
									}
								}
								else if (!m_isShiftPressed){
									if(key>=65 && key <= 90){
										m_IDBuffer.append((char)(key+32));
									}
								}
								else{
									if(key>=48 && key<= 57){
										m_IDBuffer.append((char)key);
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
							if(m_IDBuffer.length() < 16){
								if(m_isShiftPressed){
									if(key>=65 && key<=90){
										m_IDBuffer.append((char)key);
									}
								}
								else if (!m_isShiftPressed){
									if(key>=65 && key <= 90){
										m_IDBuffer.append((char)(key+32));
									}
								}
								else{
									if(key>=48 && key<= 57){
										m_IDBuffer.append((char)key);
									}
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
							if(key == GLFW_KEY_TAB){
								m_ActiveBuffer = 2;
							}
							if(m_PWBuffer.length() < 16){
								if(m_isShiftPressed){
									if(key>=65 && key<=90){
										m_PWBuffer.append((char)key);
									}
								}
								else if (!m_isShiftPressed){
									if(key>=65 && key <= 90){
										m_PWBuffer.append((char)(key+32));
									}
								}
								else{
									if(key>=48 && key<= 57){
										m_PWBuffer.append((char)key);
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
							if(m_PWBuffer.length() < 16){
								if(m_isShiftPressed){
									if(key>=65 && key<=90){
										m_PWBuffer.append((char)key);
									}
								}
								else if (!m_isShiftPressed){
									if(key>=65 && key <= 90){
										m_PWBuffer.append((char)(key+32));
									}
								}
								else{
									if(key>=48 && key<= 57){
										m_PWBuffer.append((char)key);
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
					
					else if(m_ActiveBuffer == 2){
						if(action == GLFW_PRESS){
							if(key == GLFW_KEY_LEFT_SHIFT){
								m_isShiftPressed = true;
							}
							if(key == GLFW_KEY_TAB){
								m_ActiveBuffer = 2;
							}
							if(m_PWRepeat.length() < 16){
								if(m_isShiftPressed){
									if(key>=65 && key<=90){
										m_PWRepeat.append((char)key);
									}
								}
								else if (!m_isShiftPressed){
									if(key>=65 && key <= 90){
										m_PWRepeat.append((char)(key+32));
									}
								}
								else{
									if(key>=48 && key<= 57){
										m_PWRepeat.append((char)key);
									}
								}
							}
							if(m_PWRepeat.length() > 0){
								if(key == GLFW_KEY_BACKSPACE){
									m_PWRepeat.deleteCharAt(m_PWRepeat.length()-1);
								}
							}
						}
						else if(action == GLFW_REPEAT){
							if(m_PWRepeat.length() < 16){
								if(m_isShiftPressed){
									if(key>=65 && key<=90){
										m_PWRepeat.append((char)key);
									}
								}
								else if (!m_isShiftPressed){
									if(key>=65 && key <= 90){
										m_PWRepeat.append((char)(key+32));
									}
								}
								else{
									if(key>=48 && key<= 57){
										m_PWRepeat.append((char)key);
									}
								}
							}
							
							if(m_PWRepeat.length() > 0){
								if(key == GLFW_KEY_BACKSPACE){
									m_PWRepeat.deleteCharAt(m_PWRepeat.length()-1);
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
	}
	
	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub
		mouseX = Input.getInstance().getCursorPosition().x;
		mouseY = Input.getInstance().getCursorPosition().y;
		
//		if(temp != null){
//			if(temp.getBoolValue("Value")){
//				Game.getInstance().setNextLevel(new LoginLevel());
//			}
//			else{
//				m_IDBuffer.delete(0, m_IDBuffer.length());
//				m_PWBuffer.delete(0, m_PWBuffer.length());
//				m_PWRepeat.delete(0, m_PWRepeat.length());
//				m_ActiveBuffer = 0;
//			}
//		}
		
		if(m_Star1.length() > m_PWBuffer.length()){
			for(int i = 0; i < m_Star1.length() - m_PWBuffer.length(); i++){
				m_Star1.deleteCharAt(m_Star1.length()-1);
			}
		}else if(m_Star1.length() < m_PWBuffer.length()){
			for(int i = 0; i < m_PWBuffer.length() - m_Star1.length(); i++){
				m_Star1.append("*");
			}
		}
		
		if(m_Star2.length() > m_PWRepeat.length()){
			for(int i = 0; i < m_Star2.length() - m_PWRepeat.length(); i++){
				m_Star2.deleteCharAt(m_Star2.length()-1);
			}
		}else if(m_Star2.length() < m_PWRepeat.length()){
			for(int i = 0; i < m_PWRepeat.length() - m_Star2.length(); i++){
				m_Star2.append("*");
			}
		}
		
		if(m_IDBuffer.length()==0){
			drawTextCall("fontR11", "ID", new Vector2f(-313, 22), new Vector3f(0x82/255f, 0x82/255f, 0x82/255f));
		}
		if(m_PWBuffer.length()==0){
			drawTextCall("fontR11", "PW", new Vector2f(-313, -109), new Vector3f(0x82/255f, 0x82/255f, 0x82/255f));
		}
		if(m_PWRepeat.length()==0){
			drawTextCall("fontR11", "RetypePW", new Vector2f(-313, -233), new Vector3f(0x82/255f, 0x82/255f, 0x82/255f));
		}
		
		drawTextCall("fontR11", m_IDBuffer.toString(), new Vector2f(-313,15), new Vector3f(0,0,0));
		drawTextCall("fontR11", m_Star1.toString(), new Vector2f(-313,-116), new Vector3f(0,0,0));
		drawTextCall("fontR11", m_Star2.toString(), new Vector2f(-313,-240), new Vector3f(0,0,0));
	}
}
