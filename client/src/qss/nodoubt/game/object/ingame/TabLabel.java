package qss.nodoubt.game.object.ingame;

import org.joml.Vector2f;
import org.joml.Vector3f;

import qss.nodoubt.game.GameConstants;
import qss.nodoubt.game.object.GameObject;
import qss.nodoubt.graphics.FontManager;

public class TabLabel extends GameObject{
	
	private String m_Name;
	private int m_Score;
	private int m_Color;
	private Vector2f m_TextPosition;
	private Vector2f m_ScorePosition;

	public TabLabel(String name, int score, char color) {
		super("TabDot" + color, 0);
		
		switch(color)
		{
		case 'R': setPosition(new Vector2f(-888, -64)); m_Color = 0; break;
		case 'B': setPosition(new Vector2f(-888, -150)); m_Color = 1; break;
		case 'G': setPosition(new Vector2f(-888, -236)); m_Color = 2; break;
		case 'Y': setPosition(new Vector2f(-888, -322)); m_Color = 3; break;
		case 'W': setPosition(new Vector2f(-888, -408)); m_Color = 4; break;
		case 'P': setPosition(new Vector2f(-888, -494)); m_Color = 5; break;
		}
		
		m_TextPosition = new Vector2f(getPosition()).add(new Vector2f(77, 16));
		m_ScorePosition = new Vector2f(-420 - FontManager.getInstance().getFont("fontR10").getStringWidth(Integer.toString(m_Score)), m_TextPosition.y);
		m_Name = name;
		m_Score = score;
	}

	@Override
	public void update(float deltaTime) {
		drawTextCall("fontR10", m_Name, m_TextPosition, GameConstants.BIKE_COLORS[m_Color]);
		drawTextCall("fontR10", Integer.toString(m_Score), m_ScorePosition, GameConstants.BIKE_COLORS[m_Color]);
	}

}
