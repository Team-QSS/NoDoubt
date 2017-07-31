package qss.nodoubt.game.level;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

import java.util.ArrayList;
import java.util.LinkedList;

import qss.nodoubt.game.Game;
import qss.nodoubt.game.object.*;
import qss.nodoubt.input.Input;
import qss.nodoubt.network.Message;
import qss.nodoubt.network.Network;

public class WaitingRoomLevel extends GameLevel{

	private String m_GameName;
	private String m_Owner;
	private int m_Players;
	
	private float mouseX;
	private float mouseY;
	
	private String[] m_PlayerList;
	
	private Button m_StartButton;
	private Button m_BackButton;
	
//	private Button[] m_PlusButtons; 초대버튼
	private Background m_WaitingRoomBG;
	
	private Network m_Network;
	private Message m_Message;
	
	public WaitingRoomLevel(){
		m_StartButton = new Button("GameJoinButton1", "GameJoinButton2", 320, 414);
		m_StartButton.setListener(null,
				(action, button) -> {
					if(button == GLFW_MOUSE_BUTTON_LEFT){
						if(m_StartButton.onButton(mouseX, mouseY)){
							if(action == GLFW_PRESS){
								m_StartButton.focus();
							}
							if(action == GLFW_RELEASE){
								Game.getInstance().setNextLevel(new InGameLevel());
							}
						}
						else{
							if(action == GLFW_RELEASE){
								m_StartButton.unfocus();
							}
						}
						
					}
				});
		
		m_BackButton = new Button("BackButton1", "BackButton2", 667, 414);
		m_BackButton.setListener(null,
				(action, button) -> {
					if(button == GLFW_MOUSE_BUTTON_LEFT){
						if(m_BackButton.onButton(mouseX, mouseY)){
							if(action == GLFW_PRESS){
								m_BackButton.focus();
							}
							if(action == GLFW_RELEASE){
								Game.getInstance().setNextLevel(new LoadingLevel());
							}
						}
						else{
							if(action == GLFW_RELEASE){
								m_BackButton.unfocus();
							}
						}
						
					}
				});
		
		m_WaitingRoomBG = new Background("WaitingBG");
		
		
		addObject(m_StartButton);
		addObject(m_BackButton);
		addObject(m_WaitingRoomBG);
	}
	
	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub
		mouseX = Input.getInstance().getCursorPosition().x;
		mouseY = Input.getInstance().getCursorPosition().y;
		
	}

}
