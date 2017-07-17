package qss.nodoubt.game.object;

/*
 * LoadingLevel에서 대기실 목록을 표현할 때 보여질 오브젝트이다.
 */

public class WaitingRoom extends GameObject{

	private String m_GameName = null;	//게임이름
	private String m_Owner = null;		//방장
	private String m_Players = null;	//인원
	
	public WaitingRoom(String textureName, float depth) {
		super(textureName, depth);
		
	}

	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub
		
	}

}
