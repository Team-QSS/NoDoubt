package qss.nodoubt.game.object.ingame;

import org.joml.Vector2f;

import qss.nodoubt.game.object.GameObject;

public class Bike extends GameObject{

	public Bike(char color, Vector2f pos) {
		super("IBike" + color, 3);
		setPosition(pos);
	}

	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub
		
	}
	
	public void move(Vector2f p) {
		setPosition(getPosition().x + p.x, getPosition().y + p.y);
	}
}
