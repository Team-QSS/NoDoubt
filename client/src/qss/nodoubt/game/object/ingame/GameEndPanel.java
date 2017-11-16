package qss.nodoubt.game.object.ingame;

import org.joml.Vector2f;
import org.joml.Vector3f;

import qss.nodoubt.game.GameConstants;
import qss.nodoubt.game.object.GameObject;
import qss.nodoubt.graphics.FontManager;

public class GameEndPanel extends GameObject{
	private float m_AcTime;
	private String m_String;
	private int m_Color;
	
	public GameEndPanel(String name, int c) {
		super("VictoryPanel", 0);
		m_AcTime = 0;
		m_String = name;
		m_Color = c;
	}

	@Override
	public void update(float deltaTime) {
		m_AcTime += deltaTime;
		
		int width = FontManager.getInstance().getFont("fontB21").getStringWidth(m_String);
		drawTextCall("fontB21", m_String, new Vector2f(0 - width/2, 178), GameConstants.BIKE_COLORS[m_Color]);
	}

}
