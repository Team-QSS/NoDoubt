package qss.nodoubt.game.level;
import qss.nodoubt.game.object.*;

public class LobbyLevel extends GameLevel{
	private Button m_GameJoinButton;
	private Button m_AccountSettingButton;
	private Button m_StoreButton;
	private Button m_CreditButton;
	private Background m_LobbyBG;
	
	public LobbyLevel(){
		m_GameJoinButton = new Button("a");
		m_AccountSettingButton = new Button("a");
		m_StoreButton = new Button("a");
		m_CreditButton = new Button("a");
		m_LobbyBG = new Background("a");
		
		GameObject[] Objects = {
				m_GameJoinButton,
				m_AccountSettingButton,
				m_StoreButton,
				m_CreditButton,
				m_LobbyBG
		};
		
//		addObject(m_GameJoinButton);
//		addObject(m_AccountSettingButton);
//		addObject(m_StoreButton);
//		addObject(m_CreditButton);
		
		for(GameObject button : Objects){
			addObject(button);
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
