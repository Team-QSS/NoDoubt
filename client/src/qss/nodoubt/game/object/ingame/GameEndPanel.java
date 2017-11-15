package qss.nodoubt.game.object.ingame;

import org.joml.Vector2f;
import org.joml.Vector3f;

import qss.nodoubt.game.object.GameObject;
import qss.nodoubt.graphics.FontManager;

public class GameEndPanel extends GameObject{
	private float m_AcTime;
	private String m_String;
	private int m_Color;
	
	private static final Vector3f[] m_Colors = new Vector3f[]{
			new Vector3f(0xff / 255f, 0x39 / 255f, 0x39 / 255f), 
			new Vector3f(0x23 / 255f, 0x75 / 255f, 0xeb / 255f), 
			new Vector3f(0x36 / 255f, 0x8c / 255f, 0x49 / 255f), 
			new Vector3f(0xff / 255f, 0xf4 / 255f, 0x1f / 255f), 
			new Vector3f(0xff / 255f, 0xff / 255f, 0xff / 255f), 
			new Vector3f(0xa9 / 255f, 0x24 / 255f, 0xff / 255f)
	};

	public GameEndPanel(String name, int c) {
		super("VictoryPanel", 0);
		m_AcTime = 0;
		m_String = name;
		m_Color = c;
	}

	@Override
	public void update(float deltaTime) {
		m_AcTime += deltaTime;
		
		int width = FontManager.getInstance().getFont("fontB11").getStringWidth(m_String);
		drawTextCall("fontB11", m_String, new Vector2f(0 - width/2, 178), m_Colors[m_Color]);
	}

}
