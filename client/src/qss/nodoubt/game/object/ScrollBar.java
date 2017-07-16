package qss.nodoubt.game.object;

/*
 * LoadingLevel 에서 대기실 목록에 표시될 스크롤바이다.
 */


public class ScrollBar extends GameObject{

	//MAX			스크롤바 최대 y좌표
	//MIN			스크롤바 최소 y좌표
	//m_Length		스크롤바 길이
	
	public ScrollBar(String textureName, float depth) {
		super(textureName, depth);
	}

	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub
		//방 갯수에 따른 스크롤바 길이 변환
	}
	

}
