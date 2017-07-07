package qss.nodoubt.game.level;
import qss.nodoubt.game.Game;
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
			buttons[i] = new Button(m_ButtonTexturePath[i], (-960 + 200) + i*(480-100), -360);
			//일단 임시로 이렇게 넣어놈. 알아서 잘 바꾸셈
			//버튼 클릭하는거 인식은 버튼 내부의 생성자에서 입력 리스너 넣어주거나 하면 될듯.
			addObject(buttons[i]);
		}
		buttons[0].setListner(() -> {Game.getInstance().setNextLevel(new WaitingRoomLevel());});
		buttons[1].setListner(() -> {Game.getInstance().setNextLevel(new SettingLevel());});
		buttons[2].setListner(() -> {Game.getInstance().setNextLevel(new StoreLevel());});
		buttons[3].setListner(() -> {Game.getInstance().setNextLevel(new CreditLevel());});
		buttons[4].setListner(() -> {Game.getInstance().shutdown();});

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
