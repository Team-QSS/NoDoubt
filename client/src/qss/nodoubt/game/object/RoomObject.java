package qss.nodoubt.game.object;

import org.joml.Vector3f;

public class RoomObject extends GameObject {
	
	public TextBox m_GameName;
	public TextBox m_Owner;
	public TextBox m_Players;
	
	private static final float GAMENAMETEXT = 227;
	private static final float OWNERTEXT = 941;
	private static final float PLAYERSTEXT = 1574;
	
	private static final float GAMENAMEX = 467;
	private static final float OWNERX = 1137;
	private static final float PLAYERSX = 1627;

	private static final float Y = 410;
	private static final float TEXTY = 386;
	
	private static final Vector3f COLOR = new Vector3f(0x9a, 0x6f, 0x52);
	
	public RoomObject(String textureName, float depth, float index) {
		super(textureName, depth);
		
		m_GameName = new TextBox(0, GAMENAMEX, Y - 110 * index, 714, GAMENAMETEXT, TEXTY - 110 * index, false, null, COLOR);
		m_Owner    = new TextBox(0, OWNERX,    Y - 110 * index, 714, OWNERTEXT,    TEXTY - 110 * index, false, null, COLOR);
		m_Players  = new TextBox(0, PLAYERSX,  Y - 110 * index, 321, PLAYERSTEXT,  TEXTY - 110 * index, false, null, COLOR);
	}

	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub
		m_GameName.update(deltaTime);
		m_Owner.update(deltaTime);
		m_Players.update(deltaTime);
	}

}
