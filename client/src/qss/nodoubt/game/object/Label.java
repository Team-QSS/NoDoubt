package qss.nodoubt.game.object;

import org.joml.Vector2f;

import qss.nodoubt.graphics.FontManager;

public class Label extends GameObject{
	public Label() {
		super(null, 0);
	}

	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void draw() {
		FontManager.getInstance().drawString(50, new Vector2f(0, 0), "Hello world!");
	}
}
