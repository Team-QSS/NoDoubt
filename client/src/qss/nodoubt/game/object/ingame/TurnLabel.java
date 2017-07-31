package qss.nodoubt.game.object.ingame;

import org.joml.Vector3f;

import qss.nodoubt.game.object.GameObject;

public class TurnLabel extends GameObject{
	private String[] m_IDs;
	private int m_PlayerCount;
	private int m_CurrentTurn;
	private Vector3f[] m_Colors;
	
	public TurnLabel(String[] IDs, Vector3f[] colors, int playerCount) {
		super("Blank", 0);
		m_IDs = IDs;
		m_PlayerCount = playerCount;
		m_CurrentTurn = 0;
		m_Colors = colors;
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
