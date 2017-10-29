package qss.nodoubt.game.object;

/**
 * 장식용으로 출력할 객체
 * @author Splash
 *
 */
public class Icon extends GameObject{
	
	public Icon(String textureName, float x, float y) {
		super(textureName, 0);
		setPosition(x, y);
		setTexture(textureName);
	}

	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub
		
	}
}
