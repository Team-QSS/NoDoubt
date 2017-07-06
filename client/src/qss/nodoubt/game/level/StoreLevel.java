package qss.nodoubt.game.level;

import qss.nodoubt.game.object.*;

public class StoreLevel extends GameLevel{

	//배경화면
	private Background m_GeneralBG = null;		//평범한 배경화면
	
	public StoreLevel(){
		m_GeneralBG = new Background("GeneralBackground");
		addObject(m_GeneralBG);
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
