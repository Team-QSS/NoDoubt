package qss.nodoubt.game.object;

public class Label extends GameObject{
	
	private String textureName = null;
	
	public Label(String textureName, float x, float y) {
		super(textureName, 0);
		setPosition(x, y);
		this.textureName = textureName;
	}

	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub
		
	}
}
