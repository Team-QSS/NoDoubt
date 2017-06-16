package qss.nodoubt.game.level;
import qss.nodoubt.game.object.*;

public class LobbyLevel extends GameLevel{
	private Button m_GameJoinButton = null;
	private Button m_AccountSettingButton = null;
	private Button m_StoreButton = null;
	private Button m_CreditButton = null;
	private Button m_QuitButton = null;
	
	private Background m_LobbyBG = null;
	
	private String[] m_ButtonTexturePath = {
		"GameJoinButton2",
		"AccountSettingButton2",
		"StoreButton2",
		"CreditButton2",
		"QuitButton2"
	};	
	
	public LobbyLevel(){
		
		Button[] buttons = {
			m_GameJoinButton,
			m_AccountSettingButton,
			m_StoreButton,
			m_CreditButton,
			m_QuitButton
		};
		
		//배경 생성
		m_LobbyBG = new Background("LobbyBackground");
		addObject(m_LobbyBG);
		
		//버튼 생성
		for(int i = 0; i < buttons.length; i++){
			buttons[i] = new Button(m_ButtonTexturePath[0], (-960 + 200) + i*(480-100) , -270 , 0);
			addObject(buttons[i]);
		}
				
	}

	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub
		drawObjects();
	}
	
}
