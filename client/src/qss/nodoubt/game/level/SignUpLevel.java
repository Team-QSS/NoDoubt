package qss.nodoubt.game.level;

import qss.nodoubt.game.Game;
import qss.nodoubt.game.object.*;
import qss.nodoubt.network.*;

import static org.lwjgl.glfw.GLFW.*;

/*
 * 회원가입을 하게 되는 단계이며
 * LoginLevel에서 SignUp 버튼을 눌렀을 시 오게 되는 단계이다.
 * 이 단계가 끝나면 setNextLevel 메서드를 통해 다시 LoginLevel로 가게 된다.
 * 아이디 규약은 다른 플레이어와 중복되지 않는 16자 이내의 영문과 숫자가 허용되는 문자열이다.
 */

public class SignUpLevel extends GameLevel{
	private String m_ID = "Test1";
	private String m_PW = "Test1";
	
	private Message m_Message = null;
	private Network m_Network = null;
	
	private Background m_SignUpBG = null;
	
	public SignUpLevel() {
		m_Network = Network.getInstance();
		m_Message = new Message();
		
		m_Message = m_Message.setProtocol("RegisterRequest");
	}
	
	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub
		m_Message = m_Message.addValue("ID", m_ID);
		m_Message = m_Message.addValue("Password", m_PW);
		m_Network.pushMessage(m_Message);
	}

}
