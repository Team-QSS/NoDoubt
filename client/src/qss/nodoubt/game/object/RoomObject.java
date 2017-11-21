package qss.nodoubt.game.object;

import org.joml.Vector3f;

import qss.nodoubt.input.KeyListener;
import qss.nodoubt.input.MouseListener;

public class RoomObject extends GameObject {
	
	public TextBox m_GameName;
	public TextBox m_Owner;
	public TextBox m_Players;
	
	private final double m_ID;
	
	private static final float GAMENAMETEXT = -812;
	private static final float OWNERTEXT = -123;
	private static final float PLAYERSTEXT = 614;
	private static final float TEXTY = 154;
	
	private static final float GAMENAMEX = 467;
	private static final float OWNERX = 1137;
	private static final float PLAYERSX = 1627;
	
	private static final float Y = 410;
	
	private static final Vector3f COLOR = new Vector3f(0x80/255f, 0x43/255f, 0x1b/255f);
	
	private int index=0;
	private boolean isActived = false;
	
	public RoomObject(float depth, String gamename, String owner, int players, double id) {
		super("Blank", depth);
		
		m_GameName = new TextBox("fontB11", 0, GAMENAMEX, Y - 90 * index, GAMENAMETEXT, TEXTY - 90 * index, false, false, null, COLOR);
		m_Owner    = new TextBox("fontB11", 0, OWNERX,    Y - 90 * index, OWNERTEXT,    TEXTY - 90 * index, false, false, null, COLOR);
		m_Players  = new TextBox("fontB11", 0, PLAYERSX,  Y - 90 * index, PLAYERSTEXT,  TEXTY - 90 * index, false, false, null, COLOR);
	
		m_ID = id;
		
		m_GameName.m_Text.append(gamename);
		m_Owner.m_Text.append(owner);
		m_Players.m_Text.append(String.valueOf(players) + "  /  6" );
		
	}
	
	public void setPosition() {
		setPosition(-10, 130 - 90 * index);
	}
	
	public void setIndex(int index){
		this.index=index % 6;
		m_GameName.setPosition(GAMENAMEX, Y - 90 * index);
		m_GameName.setTextLocation(GAMENAMETEXT, TEXTY - 90 * index);
		m_Owner.setPosition(OWNERX, Y - 90 * index);
		m_Owner.setTextLocation(OWNERTEXT, TEXTY - 90 * index);
		m_Players.setPosition(PLAYERSX, Y - 90 * index);
		m_Players.setTextLocation(PLAYERSTEXT, TEXTY - 90 * index);
		setPosition();
	}
	
	public int getIndex(){
		return this.index;
	}
	
	/**
	 * player를 입력하면 m_Players의 StringBuffer를 변경할 수 있다.
	 * 예 : setPlayers(3) -> 결과 : "3 / 6"
	 * @param players
	 */
	public void setPlayers(int players) {
		m_Players.m_Text.deleteCharAt(0);
		m_Players.m_Text.insert(0, players);
	}
	public double getID() {
		return this.m_ID;
	}
	@Override
	public void update(float deltaTime) {
		m_GameName.update(deltaTime);
		m_Owner.update(deltaTime);
		m_Players.update(deltaTime);
	}
	
	public boolean onObject(float mouseX, float mouseY) {
		if((mouseX >= getPosition().x-828)&&(mouseX <= getPosition().x+828)){
			if((mouseY >= getPosition().y-43)&&(mouseY <= getPosition().y+43)){
				System.out.println(getPosition().x+", "+getPosition().y);
				return true;
			}
			else{
				return false;
			}
		}
		else{
			return false;
		}
	}
	public void setListener(KeyListener key, MouseListener mouse){
		setEventListener(key, mouse);
	}
	
	public void setActive(boolean b) {
		isActived = b;
	}
	public boolean getActive() {
		return isActived;
	}
}
