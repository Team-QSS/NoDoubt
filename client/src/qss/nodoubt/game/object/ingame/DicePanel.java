package qss.nodoubt.game.object.ingame;

import qss.nodoubt.game.object.GameObject;

public class DicePanel extends GameObject{
	private int m_DiceNum;

	public DicePanel() {
		super("Blank", 3);
		m_DiceNum = 0;
	}

	@Override
	public void update(float deltaTime) {
		
	}

	public int getDiceNum() {
		return m_DiceNum;
	}
	
	public void setBlank() {
		setTexture("Blank");
		m_DiceNum = 0;
	}
	
	public void setDiceResult(int n) {
		setTexture("" + n);
		m_DiceNum = n;
	}
}
