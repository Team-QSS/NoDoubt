package qss.nodoubt.game.object;

public class Button extends GameObject {

	public Button(String textureName,float x, float y, float z) {
		super(textureName, z);
		setPosition(x,y);
	}

	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub

	}
	

}
