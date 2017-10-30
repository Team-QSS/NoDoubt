package qss.nodoubt.game.object;

import org.joml.Vector2f;

/**
 * WaitingRoomLevel에서 각 칸마다 출력할 객체
 * 대기실에 있는 대기자
 * @author Splash
 *
 */
public class Player extends GameObject{
	private TextBox m_Name;
	private Icon m_MotorCycle;
	
	final private Vector2f[] m_Locs = {
			new Vector2f(-596, 174),
			new Vector2f(-596, -83),
			new Vector2f(-596, -340),
			new Vector2f(586, 174),
			new Vector2f(586, -83),
			new Vector2f(586, -340)};	
	
	final private String[] m_Textures = {
		new String("BikeR"),
		new String("BikeB"),
		new String("BikeG"),
		new String("BikeY"),
		new String("BikeW"),
		new String("BikeP")
	};
	
	/**
	 * TextBox m_Name에 들어갈 변수
	 * @param m_Name
	 * index에 따라 오토바이의 색깔과 위치 좌표가 달라짐
	 * @param index
	 * 
	 */
	public Player(String m_Name, int index) {
		super("Blank", 0);
		setPosition(m_Locs[index]);
		
		this.m_Name.setPosition(m_Locs[index].x - 156, m_Locs[index].y + 67);
		this.m_Name.m_Text.append(m_Name);
		
		this.m_MotorCycle.setTexture(m_Textures[index]);
	}

	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub
		
	}
}
