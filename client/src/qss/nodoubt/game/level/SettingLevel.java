package qss.nodoubt.game.level;

/*
 * 상점 레벨입니다.
 * 프로토타입에서는 미구현 예정입니다.
 * */

import qss.nodoubt.game.Game;
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
		
		for(int i = 0; i < buttons.length; i++){
	//		buttons[i] = new Button(m_ButtonTexturePath[i], 0, 0);
	//		addObject(buttons[i]);
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
