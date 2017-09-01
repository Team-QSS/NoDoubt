package qss.nodoubt.game.object;

import org.joml.Vector3f;

public class RoomObject extends GameObject {
	
	public TextBox m_GameName;
	public TextBox m_Owner;
	public TextBox m_Players;
	
	private static final float GAMENAMETEXT = -812;
	private static final float OWNERTEXT = -123;
	private static final float PLAYERSTEXT = 614;
	private static final float TEXTY = 154;
	
	private static final float GAMENAMEX = 467;
	private static final float OWNERX = 1137;
	private static final float PLAYERSX = 1627;
	
	private static final float Y = 410;
	
	private static final Vector3f COLOR = new Vector3f(0x9a/255f, 0x6f/255f, 0x52/255f);
	
	private int index=0;
	
	public RoomObject(float depth, String gamename, String owner, int players) {
		super("Blank", depth);
		
		m_GameName = new TextBox(0, GAMENAMEX, Y - 110 * index, 714, GAMENAMETEXT, TEXTY - 110 * index, false, null, COLOR);
		m_Owner    = new TextBox(0, OWNERX,    Y - 110 * index, 714, OWNERTEXT,    TEXTY - 110 * index, false, null, COLOR);
		m_Players  = new TextBox(0, PLAYERSX,  Y - 110 * index, 321, PLAYERSTEXT,  TEXTY - 110 * index, false, null, COLOR);
		
		m_GameName.m_Text.append(gamename);
		m_Owner.m_Text.append(owner);
		m_Players.m_Text.append(String.valueOf(players) + "  /  6" );
	}
	
	public void setIndex(int index){
		this.index=index;
		m_GameName.setPosition(GAMENAMEX, Y - 110 * this.index);
		m_Owner.setPosition(OWNERX, Y - 110 * this.index);
		m_Players.setPosition(PLAYERSX, Y - 110 * this.index);
	}
	
	public int getIndex(){
		return this.index;
	}

	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub
		m_GameName.update(deltaTime);
		m_Owner.update(deltaTime);
		m_Players.update(deltaTime);
	}

}
