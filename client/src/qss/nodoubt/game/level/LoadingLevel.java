package qss.nodoubt.game.level;

import static org.lwjgl.glfw.GLFW.*;

import qss.nodoubt.game.Game;
import qss.nodoubt.game.object.*;

/*
 * 이 클래스는 대기실을 들어가기 전,
 * 들어갈 수 있는 다른 대기실을 골라 들어가거나
 * 대기실을 생성할 수 있는 레벨로 갈 수 있는 역할을 하는
 * 레벨이다.*/

public class LoadingLevel extends GameLevel{
	//배경
	private Background m_LoadingBG = null;
	
	//getCursor로 마우스의 좌표를 구함
	private float mouseX;
	private float mouseY;
	
	public LoadingLevel(){
		setEventListener((action,  key) -> { 
			if(key == GLFW_KEY_BACKSPACE){ 
				if(action == GLFW_PRESS) {
					Game.getInstance().setNextLevel(new LobbyLevel());
					}
				}
			}, 
		null);	
		m_LoadingBG = new Background("LoadBG");
		addObject(m_LoadingBG);
	}

	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub
		
	}

}
