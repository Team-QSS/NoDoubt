package qss.nodoubt.game.level;

/*
 * 로그인 화면을 다루는 레벨이다.
 * 실행파일을 실행했을 때 가장 처음 보게 될 레벨일 것임.
 * 일단 임시로 엔터를 눌렀을 때 Lobby 레벨로 가게 구현했지만
 * 로그인을 추후에 구현해야 함.
 * Sign up 이란 버튼을 누르면 회원가입이 됨
 */

import static org.lwjgl.glfw.GLFW.*;

import qss.nodoubt.game.Game;
import qss.nodoubt.game.object.*;

public class LoginLevel extends GameLevel{
	//버튼
	private Button m_Signin = null;		//로그인
	private Button m_Signup = null;		//회원가입
	//배경
	private Background m_LoginBG = null;
	
	public LoginLevel() {
		setEventListener((action,  key) -> { 
			if(key == GLFW_KEY_ENTER){
				if(action == GLFW_PRESS){
					Game.getInstance().setNextLevel(new LobbyLevel());
				}
			}
		},
		null);
		
		m_LoginBG = new Background("LoginBG");
		addObject(m_LoginBG);
	}

	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub
		
	}

}
