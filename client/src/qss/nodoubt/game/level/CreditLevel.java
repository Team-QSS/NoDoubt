package qss.nodoubt.game.level;
import qss.nodoubt.game.Game;
import qss.nodoubt.game.object.*;
import qss.nodoubt.game.level.*;
import static org.lwjgl.glfw.GLFW.*;

public class CreditLevel extends GameLevel {
	//踰꾪듉
	private Button m_DummyButton = null;
	//諛곌꼍
	private Background m_CreditBG = null;
	
	//�깮�꽦�옄
	public CreditLevel(){
		m_DummyButton = new Button("Blank", "Blank", 0, 0);
		m_DummyButton.setListner(
				(action, key) ->{
					if(action == GLFW_PRESS){
						if(key == GLFW_KEY_ENTER){
							Game.getInstance().setNextLevel(new LobbyLevel());
						}
					}
				},
				null
		);
		addObject(m_DummyButton);
		m_CreditBG = new Background("CreditBackground");		//�겕�젅�뵩 諛곌꼍�솕硫�
		addObject(m_CreditBG);
	}
	
	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub
		//�븘臾� �궎瑜� �늻瑜대㈃ Lobby �떒怨꾨줈 �룎�븘媛�	
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub
		drawObjects();		//諛곌꼍 洹몃━湲�
	}

}
