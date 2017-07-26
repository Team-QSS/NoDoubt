package qss.nodoubt.game.object.ingame;

import org.joml.Vector2f;
import org.joml.Vector2i;

import qss.nodoubt.game.object.GameObject;

public class Bike extends GameObject{

	public Bike(char color) {
		super("IBike" + color, 3);
	}

	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub
		
	}
	
	public void move(Vector2f p) {
		setPosition(getPosition().x + p.x, getPosition().y + p.y);
	}
	
	public void setPosition(Vector2i pos) {
		setPosition(-480 + 160 * pos.x, 399 - 160 * pos.y);
	}
}
