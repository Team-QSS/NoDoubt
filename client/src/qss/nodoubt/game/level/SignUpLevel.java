package qss.nodoubt.game.level;

import qss.nodoubt.game.Game;
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
	
	private Message m_Message = null;
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
		m_Message = new Message();
		m_Message = m_Message.setProtocol("RegisterRequest");
		
		m_SignUpBG = new Background("SignupBG");
		m_Signup = new Button("SignupButton", null, 0, -395);
		
		m_Signup.setListener(
				(action, key) ->{
					if(action == GLFW_PRESS && key == GLFW_KEY_ENTER){
						if(m_PWBuffer.toString().equals(m_PWRepeat.toString())){
							m_Network.pushMessage(m_Message);
							Game.getInstance().setNextLevel(new LoginLevel());
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
								m_Network.pushMessage(m_Message);
								Game.getInstance().setNextLevel(new LoginLevel());
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
							if(key == GLFW_KEY_TAB){
								m_ActiveBuffer = 2;
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
									switch (key){
										case GLFW_KEY_A : m_PWRepeat.append("A"); break;
										case GLFW_KEY_B : m_PWRepeat.append("B"); break;
										case GLFW_KEY_C : m_PWRepeat.append("C"); break;
										case GLFW_KEY_D : m_PWRepeat.append("D"); break;
										case GLFW_KEY_E : m_PWRepeat.append("E"); break;
										case GLFW_KEY_F : m_PWRepeat.append("F"); break;
										case GLFW_KEY_G : m_PWRepeat.append("G"); break;
										case GLFW_KEY_H : m_PWRepeat.append("H"); break;
										case GLFW_KEY_I : m_PWRepeat.append("I"); break;
										case GLFW_KEY_J : m_PWRepeat.append("J"); break;
										case GLFW_KEY_K : m_PWRepeat.append("K"); break;
										case GLFW_KEY_L : m_PWRepeat.append("L"); break;
										case GLFW_KEY_M : m_PWRepeat.append("M"); break;
										case GLFW_KEY_N : m_PWRepeat.append("N"); break;
										case GLFW_KEY_O : m_PWRepeat.append("O"); break;
										case GLFW_KEY_P : m_PWRepeat.append("P"); break;
										case GLFW_KEY_Q : m_PWRepeat.append("Q"); break;
										case GLFW_KEY_R : m_PWRepeat.append("R"); break;
										case GLFW_KEY_S : m_PWRepeat.append("S"); break;
										case GLFW_KEY_T : m_PWRepeat.append("T"); break;
										case GLFW_KEY_U : m_PWRepeat.append("U"); break;
										case GLFW_KEY_V : m_PWRepeat.append("V"); break;
										case GLFW_KEY_W : m_PWRepeat.append("W"); break;
										case GLFW_KEY_X : m_PWRepeat.append("X"); break;
										case GLFW_KEY_Y : m_PWRepeat.append("Y"); break;
										case GLFW_KEY_Z : m_PWRepeat.append("Z"); break;
										case GLFW_KEY_1 : m_PWRepeat.append("1"); break;
										case GLFW_KEY_2 : m_PWRepeat.append("2"); break;
										case GLFW_KEY_3 : m_PWRepeat.append("3"); break;
										case GLFW_KEY_4 : m_PWRepeat.append("4"); break;
										case GLFW_KEY_5 : m_PWRepeat.append("5"); break;
										case GLFW_KEY_6 : m_PWRepeat.append("6"); break;
										case GLFW_KEY_7 : m_PWRepeat.append("7"); break;
										case GLFW_KEY_8 : m_PWRepeat.append("8"); break;
										case GLFW_KEY_9 : m_PWRepeat.append("9"); break;
										case GLFW_KEY_0 : m_PWRepeat.append("0"); break;
									}
								}
								else{
									switch (key){
										case GLFW_KEY_A : m_PWRepeat.append("a"); break;
										case GLFW_KEY_B : m_PWRepeat.append("b"); break;
										case GLFW_KEY_C : m_PWRepeat.append("c"); break;
										case GLFW_KEY_D : m_PWRepeat.append("d"); break;
										case GLFW_KEY_E : m_PWRepeat.append("e"); break;
										case GLFW_KEY_F : m_PWRepeat.append("f"); break;
										case GLFW_KEY_G : m_PWRepeat.append("g"); break;
										case GLFW_KEY_H : m_PWRepeat.append("h"); break;
										case GLFW_KEY_I : m_PWRepeat.append("i"); break;
										case GLFW_KEY_J : m_PWRepeat.append("j"); break;
										case GLFW_KEY_K : m_PWRepeat.append("k"); break;
										case GLFW_KEY_L : m_PWRepeat.append("l"); break;
										case GLFW_KEY_M : m_PWRepeat.append("m"); break;
										case GLFW_KEY_N : m_PWRepeat.append("n"); break;
										case GLFW_KEY_O : m_PWRepeat.append("o"); break;
										case GLFW_KEY_P : m_PWRepeat.append("p"); break;
										case GLFW_KEY_Q : m_PWRepeat.append("q"); break;
										case GLFW_KEY_R : m_PWRepeat.append("r"); break;
										case GLFW_KEY_S : m_PWRepeat.append("s"); break;
										case GLFW_KEY_T : m_PWRepeat.append("t"); break;
										case GLFW_KEY_U : m_PWRepeat.append("u"); break;
										case GLFW_KEY_V : m_PWRepeat.append("v"); break;
										case GLFW_KEY_W : m_PWRepeat.append("w"); break;
										case GLFW_KEY_X : m_PWRepeat.append("x"); break;
										case GLFW_KEY_Y : m_PWRepeat.append("y"); break;
										case GLFW_KEY_Z : m_PWRepeat.append("z"); break;
										case GLFW_KEY_1 : m_PWRepeat.append("1"); break;
										case GLFW_KEY_2 : m_PWRepeat.append("2"); break;
										case GLFW_KEY_3 : m_PWRepeat.append("3"); break;
										case GLFW_KEY_4 : m_PWRepeat.append("4"); break;
										case GLFW_KEY_5 : m_PWRepeat.append("5"); break;
										case GLFW_KEY_6 : m_PWRepeat.append("6"); break;
										case GLFW_KEY_7 : m_PWRepeat.append("7"); break;
										case GLFW_KEY_8 : m_PWRepeat.append("8"); break;
										case GLFW_KEY_9 : m_PWRepeat.append("9"); break;
										case GLFW_KEY_0 : m_PWRepeat.append("0"); break;
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
		m_Message = m_Message.addValue("ID", m_IDBuffer.toString());
		m_Message = m_Message.addValue("Password", m_PWBuffer.toString());
		
		if(m_IDBuffer.length()==0){
			drawTextCall("fontR11", "ID", new Vector2f(-313, 22), new Vector3f(0x82/255f, 0x82/255f, 0x82/255f));
		}
		if(m_PWBuffer.length()==0){
			drawTextCall("fontR11", "PW", new Vector2f(-313, -109), new Vector3f(0x82/255f, 0x82/255f, 0x82/255f));
		}
		if(m_PWRepeat.length()==0){
			drawTextCall("fontR11", "RetypePW", new Vector2f(-313, -233), new Vector3f(0x82/255f, 0x82/255f, 0x82/255f));
		}
		
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
		
		drawTextCall("fontR11", m_IDBuffer.toString(), new Vector2f(-313,15), new Vector3f(0,0,0));
		drawTextCall("fontR11", m_Star1.toString(), new Vector2f(-313,-116), new Vector3f(0,0,0));
		drawTextCall("fontR11", m_Star2.toString(), new Vector2f(-313,-240), new Vector3f(0,0,0));
		m_Network.pushMessage(m_Message);
	}

}
