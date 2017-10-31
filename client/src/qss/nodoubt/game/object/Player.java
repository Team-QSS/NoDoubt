package qss.nodoubt.game.object;

import org.joml.Vector2f;
import org.joml.Vector3f;


/**
 * WaitingRoomLevel에서 각 칸마다 출력할 객체
 * 대기실에 있는 대기자의 이름을 받아서 이 객체의 m_Name을 초기화한다
 * @author Splash
 *
 */
public class Player extends GameObject{
	private TextBox m_Name;
	private Icon m_MotorCycle;
	
	/**
	 * 각 Player 슬롯의 중점의 위치. 상수
	 */
	final static private Vector2f[] m_LOCS = {
			new Vector2f(-596, 174),
			new Vector2f(-596, -83),
			new Vector2f(-596, -340),
			new Vector2f(586, 174),
			new Vector2f(586, -83),
			new Vector2f(586, -340)};	
	
	/**
	 * 각 Player 슬롯의 위치에 따른 오토바이의 색깔. 상수
	 */
	final static private String[] m_TEXTURES = {
		new String("BikeR"),
		new String("BikeB"),
		new String("BikeG"),
		new String("BikeY"),
		new String("BikeW"),
		new String("BikeP")
	};
	
	/**
	 * TextBox의 m_Name에 들어갈 변수
	 * @param m_Name
	 * index에 따라 오토바이의 색깔과 위치 좌표가 달라짐
	 * @param index
	 */
	public Player(String m_Name, int index, String name) {
		super("Blank", 0);
		setPosition(m_LOCS[index]);
		
		this.m_Name = new TextBox(0, m_LOCS[index].x, m_LOCS[index].y + 63, m_LOCS[index].x, m_LOCS[index].y - 63, false, true, true, null, new Vector3f(0.0f, 0.0f, 0.0f));
		this.m_Name.m_Text.append(m_Name);
		
		this.m_MotorCycle.setTexture(m_TEXTURES[index]);
	}


	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub
		
	}
}
