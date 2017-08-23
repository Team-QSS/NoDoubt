package qss.nodoubt.game.object;

/*LoadingLevel 에서 출력될 방 목록들이 이 클래스의 객체들이다.*/

public class RoomObject extends GameObject{

	private StringBuffer m_Name = null;		//방제
	private StringBuffer m_Owner = null;		//방장
	private StringBuffer m_Players = null;		//인원수
	
	public RoomObject(String textureName, float depth) {
		super(textureName, depth);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void draw(){
		//글자를 그린다.
		
	}

}
