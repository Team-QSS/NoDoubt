package qss.nodoubt.game.level;

import static org.lwjgl.glfw.GLFW.*;

import qss.nodoubt.game.Game;
import qss.nodoubt.game.object.Background;

public class HowToPlayLevel extends GameLevel{

	private Background m_HTPBG = null;
	private int m_BGIndex = 0;
	final private String[] BackgroundTexturePath = {
			"HTP1", 
			"HTP2", 
			"HTP3", 
			"HTP4", 
			"HTP5", 
			"HTP6", 
			"HTP7", 
			"HTP8", 
			"HTP9", 
	};
	
	HowToPlayLevel(){
		m_HTPBG = new Background(BackgroundTexturePath[0]);
		
		setEventListener(
				(action, key) ->{
					if(key != GLFW_KEY_LEFT_SHIFT || key != GLFW_KEY_RIGHT_SHIFT || key != GLFW_KEY_LEFT_ALT || key != GLFW_KEY_RIGHT_ALT
							|| key != GLFW_KEY_LEFT_CONTROL || key != GLFW_KEY_RIGHT_CONTROL
							|| key != GLFW_KEY_CAPS_LOCK || key != GLFW_KEY_NUM_LOCK || key != GLFW_KEY_SCROLL_LOCK)
					if(action == GLFW_PRESS){
						if(key == GLFW_KEY_BACKSPACE && m_BGIndex >= 1) {
							m_BGIndex--;
							m_HTPBG.changeBG(BackgroundTexturePath[m_BGIndex]);
						}
						else if(m_BGIndex <= 7){
							m_BGIndex++;
							m_HTPBG.changeBG(BackgroundTexturePath[m_BGIndex]);
						}
						else if(m_BGIndex <= 8) {
							Game.getInstance().setNextLevel(new LobbyLevel());
						}
					}
				}
				,(action, button) ->{
					if(action == GLFW_PRESS) {
						if(m_BGIndex <= 7){
							m_BGIndex++;
							m_HTPBG.changeBG(BackgroundTexturePath[m_BGIndex]);
						}
						else if(m_BGIndex <= 8) {
							Game.getInstance().setNextLevel(new LobbyLevel());
						}
					}
				});
		
		addObject(m_HTPBG);
	}
	
	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub
		
	}

}
