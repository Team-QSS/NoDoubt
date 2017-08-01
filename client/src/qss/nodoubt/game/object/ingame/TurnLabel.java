package qss.nodoubt.game.object.ingame;

import org.joml.Vector3f;

import qss.nodoubt.game.object.GameObject;

public class TurnLabel extends GameObject{
	private String[] m_IDs;
	private int m_PlayerCount;
	private int m_CurrentTurn;
	private static final Vector3f[] m_Colors = new Vector3f[]{
			new Vector3f(0xff / 255f, 0x39 / 255f, 0x39 / 255f), 
			new Vector3f(0x23 / 255f, 0x75 / 255f, 0xeb / 255f), 
			new Vector3f(0x36 / 255f, 0x8c / 255f, 0x49 / 255f), 
			new Vector3f(0xff / 255f, 0xf4 / 255f, 0x1f / 255f), 
			new Vector3f(0xff / 255f, 0xff / 255f, 0xff / 255f), 
			new Vector3f(0xa9 / 255f, 0x24 / 255f, 0xff / 255f)
	};
	
	public TurnLabel(String[] IDs, int playerCount) {
		super("Blank", 0);
		m_IDs = IDs;
		m_PlayerCount = playerCount;
		m_CurrentTurn = 0;
	}
	
	public void nextTurn() {
		m_CurrentTurn += 1;
		m_CurrentTurn = m_CurrentTurn % m_PlayerCount;
	}

	@Override
	public void update(float deltaTime) {
		
	}
	
	public Vector3f getColor() {
		return m_Colors[m_CurrentTurn];
	}
	
	public String getID() {
		return m_IDs[m_CurrentTurn];
	}
}
