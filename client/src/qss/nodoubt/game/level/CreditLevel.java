package qss.nodoubt.game.level;
import qss.nodoubt.game.Game;
import qss.nodoubt.game.object.*;
import static org.lwjgl.glfw.GLFW.*;

public class CreditLevel extends GameLevel {
	//배경
	private Background m_CreditBG = null;
	
	//생성자
	public CreditLevel(){
		setEventListener((action,  key) -> { 
			if(action == GLFW_PRESS) {
				Game.getInstance().setNextLevel(new LobbyLevel());
			}
		}, 
		(action, button) ->{
			if(action == GLFW_PRESS && button == GLFW_MOUSE_BUTTON_LEFT) {
				Game.getInstance().setNextLevel(new LobbyLevel());
			}
		}
	);
		m_CreditBG = new Background("CreditBG");		//크레딧 배경화면
		addObject(m_CreditBG);
	}
	
	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub
		//아무 키를 누르면 Lobby 단계로 돌아감	
	}

}
