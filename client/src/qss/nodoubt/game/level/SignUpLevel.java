package qss.nodoubt.game.level;

import static org.lwjgl.glfw.GLFW.*;

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
	
	private Background m_SignUpBG = null;
	
	private Button m_Signup = null;
	
	private float mouseX;
	private float mouseY;
	private int m_ActiveBuffer = 0;
	
	public SignUpLevel() {
		m_ID = new TextBox(0, 0.0f, -3.0f, 680.0f, -313.0f, 15.0f, false, "ID", new Vector3f(0, 0, 0));
		m_PW = new TextBox(0, 0.0f, -3.0f, 680.0f, -313.0f, -116.0f, false, "PW", new Vector3f(0, 0, 0));
		m_PWRepeat = new TextBox(0, 0.0f, -3.0f, 680.0f, -313.0f, -240.0f, false, "Retype", new Vector3f(0, 0, 0));
		
		m_SignUpBG = new Background("SignupBG");
		m_Signup = new Button("SignupButton", null, 0, -395);
		
		m_Signup.setListener(
				(action, key) ->{
					if(action == GLFW_PRESS && key == GLFW_KEY_ENTER){
//						if( ((m_PW.m_Text.length() >= 4) && (m_ID.m_Text.length() >= 4)) ){
//							if(m_PW.m_Text.toString().equals(m_PWRepeat.toString())){
//								//메시지 전송
//								JSONObject signUpData=
//										Util.packetGenerator(Protocol.REGISTER_REQUEST,
//										new KeyValue("ID", m_ID.m_Text.toString()),
//										new KeyValue("Password", m_PW.m_Text.toString())
//										);
//								Network.getInstance().pushMessage(signUpData);
//							}else{
//								m_ID.m_Text.delete(0, m_ID.m_Text.length());
//								m_PW.m_Text.delete(0, m_PW.m_Text.length());
//								m_PWRepeat.m_Text.delete(0, m_PWRepeat.m_Text.length());
//								System.out.println("비번확인" + m_PW.m_Text.toString() + m_PWRepeat.m_Text.toString());
//								m_ActiveBuffer = 0;
//								m_ID.setActive();
//								m_PW.setInActive();
//								m_PWRepeat.setInActive();
//							}
//						}
//						else{
//							m_ID.m_Text.delete(0, m_ID.m_Text.length());
//							m_PW.m_Text.delete(0, m_PW.m_Text.length());
//							m_PWRepeat.m_Text.delete(0, m_PWRepeat.m_Text.length());
//							System.out.println("아이디 or 비번 4자리 이하" + m_PW.m_Text.toString() + m_PWRepeat.m_Text.toString());
//							m_ActiveBuffer = 0;
//							m_ID.setActive();
//							m_PW.setInActive();
//							m_PWRepeat.setInActive();
//						}
						if( ((m_PW.m_Text.length() >= 4) && (m_ID.m_Text.length() >= 4)) ){
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
								System.out.println("비번확인" + m_PW.m_Text.toString() + m_PWRepeat.m_Text.toString());
								m_ActiveBuffer = 0;
								m_ID.setActive();
								m_PW.setInActive();
								m_PWRepeat.setInActive();
							}
						}
						else{
							m_ID.m_Text.delete(0, m_ID.m_Text.length());
							m_PW.m_Text.delete(0, m_PW.m_Text.length());
							m_PWRepeat.m_Text.delete(0, m_PWRepeat.m_Text.length());
							System.out.println("아이디 or 비번 4자리 이하" + m_PW.m_Text.toString() + m_PWRepeat.m_Text.toString());
							m_ActiveBuffer = 0;
							m_ID.setActive();
							m_PW.setInActive();
							m_PWRepeat.setInActive();
						}
					}
				},
				(action, button) ->{
					if(action == GLFW_RELEASE && button == GLFW_MOUSE_BUTTON_LEFT){
						if(m_Signup.onButton(mouseX, mouseY)){
							if( ((m_PW.m_Text.length() >= 4) && (m_ID.m_Text.length() >= 4)) ){
								if(m_PW.m_Text.toString().equals(m_PWRepeat.m_Text.toString())){
									//메시지 전송
									JSONObject signUpData=
											Util.packetGenerator(Protocol.REGISTER_REQUEST,
											new KeyValue("ID", m_ID.m_Text.toString()),
											new KeyValue("Password", m_PW.m_Text.toString())
											);
									Network.getInstance().pushMessage(signUpData);
								}else{
//									m_ID.m_Text.delete(0, m_ID.m_Text.length());
//									m_PW.m_Text.delete(0, m_PW.m_Text.length());
//									m_PWRepeat.m_Text.delete(0, m_PWRepeat.m_Text.length());
									System.out.println("비번확인" + m_PW.m_Text.toString() + m_PWRepeat.m_Text.toString());
									m_ActiveBuffer = 0;
									m_ID.setActive();
									m_PW.setInActive();
									m_PWRepeat.setInActive();
								}
							}
							else{
//								m_ID.m_Text.delete(0, m_ID.m_Text.length());
//								m_PW.m_Text.delete(0, m_PW.m_Text.length());
//								m_PWRepeat.m_Text.delete(0, m_PWRepeat.m_Text.length());
								System.out.println("아이디 or 비번 4자리 이하" + m_PW.m_Text.toString() + m_PWRepeat.m_Text.toString());
								m_ActiveBuffer = 0;
								m_ID.setActive();
								m_PW.setInActive();
								m_PWRepeat.setInActive();
							}
						}
					}
				});
		
		
		setEventListener(
				(action, key) ->{
					if(action == GLFW_PRESS){
						if(key == GLFW_KEY_TAB){
							if(m_ActiveBuffer == 0){
								m_ActiveBuffer = 1;
								m_ID.setInActive();
								m_PW.setActive();
							}
							else if(m_ActiveBuffer == 1){
								m_ActiveBuffer = 2;
								m_PW.setInActive();
								m_PWRepeat.setActive();
							}
						}
					}
				},
				(action, button)->{
					if(action == GLFW_PRESS && button == GLFW_MOUSE_BUTTON_LEFT){
						if(mouseX>-340 && mouseX<340){
							if(mouseY<40 && mouseY>-46){
								m_ActiveBuffer = 0;
								m_ID.setActive();
								m_PW.setInActive();
								m_PWRepeat.setInActive();
							}
							else if(mouseY<-88 && mouseY>-176){
								m_ActiveBuffer = 1;
								m_ID.setInActive();
								m_PW.setActive();
								m_PWRepeat.setInActive();
							}
							else if(mouseY<-216 && mouseY>-304){
								m_ActiveBuffer = 2;
								m_ID.setInActive();
								m_PW.setInActive();
								m_PWRepeat.setActive();
							}
						}
					}
				});
		
		m_ID.setActive();
		m_PW.setInActive();
		m_PWRepeat.setInActive();
		
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
				m_ActiveBuffer = 0;
				m_ID.setActive();
				m_PW.setInActive();
				m_PWRepeat.setInActive();
			}
		}break;
		
		default:System.out.println("unknownProtocol");
		}
	}
}
