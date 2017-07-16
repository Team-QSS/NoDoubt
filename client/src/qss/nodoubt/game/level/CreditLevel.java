package qss.nodoubt.game.level;
import qss.nodoubt.game.Game;
import qss.nodoubt.game.object.*;
import qss.nodoubt.game.level.*;
import static org.lwjgl.glfw.GLFW.*;

public class CreditLevel extends GameLevel {
	//배경
	private Background m_CreditBG = null;
	
	//생성자
	public CreditLevel(){
		setEventListener((action,  key) -> { if(action == GLFW_PRESS) Game.getInstance().setNextLevel(new LobbyLevel());}, null);
		m_CreditBG = new Background("CreditBㅎ");		//크레딧 배경화면
		addObject(m_CreditBG);
	}
	
	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub
		//아무 키를 누르면 Lobby 단계로 돌아감	
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub
		drawObjects();		//오브젝트들 그리기
	}

}
