package qss.nodoubt.game.object.ingame;

import org.joml.Vector2f;
import org.joml.Vector3f;

import qss.nodoubt.game.object.GameObject;

public class TabLabel extends GameObject{
	private static final Vector3f[] m_Colors = new Vector3f[]{
			new Vector3f(0xff / 255f, 0x39 / 255f, 0x39 / 255f), 
			new Vector3f(0x23 / 255f, 0x75 / 255f, 0xeb / 255f), 
			new Vector3f(0x36 / 255f, 0x8c / 255f, 0x49 / 255f), 
			new Vector3f(0xff / 255f, 0xf4 / 255f, 0x1f / 255f), 
			new Vector3f(0xff / 255f, 0xff / 255f, 0xff / 255f), 
			new Vector3f(0xa9 / 255f, 0x24 / 255f, 0xff / 255f)
	};
	
	private String m_Name;
	private int m_Color;
	private Vector2f m_TextPosition;

	public TabLabel(String name, char color) {
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
		m_Name = name;
	}

	@Override
	public void update(float deltaTime) {
		drawTextCall("fontR10", m_Name, m_TextPosition, m_Colors[m_Color]);
	}

}
