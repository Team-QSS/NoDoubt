package qss.nodoubt.game.level;

import qss.nodoubt.game.object.*;

public class SettingLevel extends GameLevel{
	//버튼
	private Button m_NameChangeButton = null;			//닉네임 변경
	private Button m_DiceChangeButton = null;			//주사위 변경
	private Button m_CharacterChangeButton = null;		//캐릭터 변경
	private Button m_SoundSettingButton = null;			//소리 설정
	private Button m_FullScreenSettingButton = null;	//전체화면 설정
	private Button m_FriendManageButton = null;			//친구 관리
	private Button m_BackButton = null;					//뒤로가기
	//배경
	private Background m_GeneralBG = null;		//평범한 배경화면(초록색 풀 배경)
	//버튼 텍스처 경로
	private String[] m_ButtonTexturePath = {
		"NameChangeButton",
		"DiceChangeButton",
		"CharacterChangeButton",
		"SoundSettingButton",
		"FullScreenSettingButton",
		"FriendManageButton",
		"BackButton2"
	};
	
	//생성자
	public SettingLevel(){
		Button[] buttons = {
				m_NameChangeButton,
				m_DiceChangeButton,
				m_CharacterChangeButton,
				m_SoundSettingButton,
				m_FullScreenSettingButton,
				m_FriendManageButton,
				m_BackButton
		};
		
		m_GeneralBG = new Background("GeneralBackground");
		addObject(m_GeneralBG);
		
		for(int i = 0; i < buttons.length; i++){
			buttons[i] = new Button(m_ButtonTexturePath[i], 0, 0, 0);		//0,0,0 좌표는 임시값
			addObject(buttons[i]);											//경우에 따라 x좌표, y좌표 배열 만들수도?
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
