package qss.nodoubt.game.level;
import qss.nodoubt.game.object.*;

public class LobbyLevel extends GameLevel{
	//버튼
	private Button m_GameJoinButton = null;
	private Button m_SettingButton = null;
	private Button m_StoreButton = null;
	private Button m_CreditButton = null;
	private Button m_QuitButton = null;
	//배경
	private Background m_LobbyBG = null;
	//버튼 텍스처 경로
	private String[] m_ButtonTexturePath = {
		"GameJoinButton2",
		"SettingButton2",
		"StoreButton2",
		"CreditButton2",
		"QuitButton2"
	};	
	
	//생성자
	public LobbyLevel(){
		
		Button[] buttons = {
			m_GameJoinButton,
			m_SettingButton,
			m_StoreButton,
			m_CreditButton,
			m_QuitButton
		};
		
		//배경 생성
		m_LobbyBG = new Background("LobbyBackground");
		addObject(m_LobbyBG);
		
		//버튼 생성
		for(int i = 0; i < buttons.length; i++){
			buttons[i] = new Button(m_ButtonTexturePath[4], (-960 + 200) + i*(480-100), -270 , 0);
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
