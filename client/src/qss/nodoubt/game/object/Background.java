package qss.nodoubt.game.object;

public class Background extends GameObject {

	public Background(String textureName) {
		super(textureName, 10);
		setPosition(0, 0);
	}

	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub
	}
	
	public void changeBG(String textureName) {
		setTexture(textureName);
	}

}
