package qss.nodoubt.game.object.ingame;

import qss.nodoubt.game.object.GameObject;

public class DicePanel extends GameObject{
	private int m_DiceNum;
	private boolean m_IsAnimating;
	private int m_AnimateFrame;
	private float m_AcTime;
	public DicePanel() {
		super("Blank", 3);
		m_DiceNum = 0;
		m_IsAnimating = false;
		m_AnimateFrame = 0;
		m_AcTime = 0;
		setPosition(718, 44);
	}

	@Override
	public void update(float deltaTime) {
		m_AcTime += deltaTime;
		if(m_IsAnimating && m_AcTime > 1.0/15)
		{
			m_AnimateFrame += 1;
			m_AcTime -= 1.0f / 15;
			if(m_AnimateFrame == 15)
			{
				m_IsAnimating = false;
			}
			if(m_DiceNum > 0)
			{
				setTexture("Dice" + m_DiceNum + "" + m_AnimateFrame);
			}else
			{
				setTexture("DiceX" + m_AnimateFrame);
			}
		}
	}

	public int getDiceNum() {
		return m_DiceNum;
	}
	
	public void setBlank() {
		setTexture("Blank");
		m_DiceNum = 0;
	}
	
	public void setDiceResult(int n) {
		if(1 <= n && n <= 6) {
			setTexture("Dice" + n + "1");
			m_DiceNum = n;
		}else {
			setTexture("DiceX1");
			m_DiceNum = -1;
		}
		m_IsAnimating = true;
		m_AcTime = 0;
		m_AnimateFrame = 1;
	}
}
