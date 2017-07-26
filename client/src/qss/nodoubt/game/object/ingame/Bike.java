package qss.nodoubt.game.object.ingame;

import org.joml.Vector2f;
import org.joml.Vector2i;

import qss.nodoubt.game.object.GameObject;

public class Bike extends GameObject {
	private static final Vector2f SPEED = new Vector2f(150, 150);
	private Vector2f m_Goal = null;
	private Vector2f m_Direction = new Vector2f();
	private boolean m_IsIdle = true;

	public Bike(char color) {
		super("IBike" + color, 3);
	}

	@Override
	public void update(float deltaTime) {
		if(!m_IsIdle) {
			if(m_Direction.x == 0 || m_Direction.y == 0) {
				move(new Vector2f(m_Direction).mul(SPEED).mul(deltaTime));
			}else if(m_Direction.x * m_Direction.y < 0) {
				move(new Vector2f(m_Direction.x, 0).mul(SPEED).mul(deltaTime));
			}else {
				move(new Vector2f(0, m_Direction.y).mul(SPEED).mul(deltaTime));
			}
			
			if(Math.abs(m_Goal.x - getPosition().x) < 5) {
				setPosition(m_Goal.x, getPosition().y);
				m_Direction.x = 0;
			}
			
			if(Math.abs(m_Goal.y - getPosition().y) < 5) {
				setPosition(getPosition().x, m_Goal.y);
				m_Direction.y = 0;
			}

			if(m_Direction.x == 0 && m_Direction.y == 0) {
				m_IsIdle = true;
			}
		}
		
	}
	
	public void move(Vector2f p) {
		setPosition(getPosition().x + p.x, getPosition().y + p.y);
	}
	
	public void setPosition(Vector2i pos) {
		setPosition(-480 + 160 * pos.x, 399 - 160 * pos.y);
	}
	
	public void go(Vector2i pos) {
		m_Goal = new Vector2f(-480 + 160 * pos.x, 399 - 160 * pos.y);
		Vector2f p = getPosition();
		m_Direction = new Vector2f(m_Goal).sub(p);
		
		if(Math.abs(m_Direction.x) < 80) {
			setPosition(new Vector2f(m_Goal.x, getPosition().y));
			m_Direction.x = 0;
		}
		
		if(Math.abs(m_Direction.y) < 80) {
			setPosition(new Vector2f(getPosition().x, m_Goal.y));
			m_Direction.y = 0;
		}
		
		if(m_Direction.x > 0) {
			m_Direction.x = 1;
		}else if(m_Direction.x < 0) {
			m_Direction.x = -1;
		}
		
		if(m_Direction.y > 0) {
			m_Direction.y = 1;
		}else if(m_Direction.y < 0) {
			m_Direction.y = -1;
		}
		m_IsIdle = false;
	}
	
	public boolean isIdle() {
		return m_IsIdle;
	}
}
