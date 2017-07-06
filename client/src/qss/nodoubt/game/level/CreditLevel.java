package qss.nodoubt.game.level;
import qss.nodoubt.game.object.*;

public class CreditLevel extends GameLevel {
	//배경
	private Background m_CreditBG = null;
	
	//생성자
	public CreditLevel(){
		m_CreditBG = new Background("CreditBackground");		//크레딧 배경화면
		addObject(m_CreditBG);
	}
	
	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub
		//아무 키를 누르면 Lobby 단계로 돌아감	
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub
		drawObjects();		//배경 그리기
	}

}
