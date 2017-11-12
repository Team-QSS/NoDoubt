package qss.nodoubt.game.object.ingame;

import qss.nodoubt.game.object.GameObject;

public class DeclarePanel extends GameObject{

	public DeclarePanel() {
		super("Blank", 1);
		setPosition(714, -431);
	}

	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub
		
	}
	
	public void setResult(int n) {
		setTexture("Result" + n);
	}
	
	public void setBlank() {
		setTexture("Blank");
	}

}
