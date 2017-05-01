package qss.nodoubt.game.object;

import org.joml.*;

public abstract class GameObject {
	private Vector3f m_Position;
	private Vector2f m_Size;
	private float m_Angle;
	
	public GameObject() {
		m_Position = new Vector3f(0.0f, 0.0f, 0.0f);
		m_Size = new Vector2f(1.0f, 1.0f);
		m_Angle = 0.0f;
	}
	
	public abstract void update(float deltaTime);
	
	public final void draw(Matrix4f viewOrtho) {
		Matrix4f translate = new Matrix4f().translate(m_Position);
		Matrix4f rotate = new Matrix4f().rotate(m_Angle, new Vector3f(0.0f, 0.0f, 1.0f));
		Matrix4f scale = new Matrix4f().scale(new Vector3f(m_Size, 1.0f));
		
		Matrix4f world = scale.mul(rotate).mul(translate);
		Matrix4f worldViewOrtho = world.mul(viewOrtho);
	}

	protected final void setPosition(float x, float y, float z) {
		m_Position.set(x, y, z);
	}
	
	protected final void setPosition(Vector3f pos) {
		m_Position.set(pos);
	}
	
	protected final Vector3f getPosition() {
		return m_Position;
	}
	
	protected final void setAngle(float angle) {
		m_Angle = angle;
	}
	
	protected final float getAngle() {
		return m_Angle;
	}
	
	protected final void setSize(float xSize, float ySize) {
		m_Size.set(xSize, ySize);
	}
	
	protected final void setSize(Vector2f size) {
		m_Size.set(size);
	}
	
	protected final Vector2f getSize() {
		return m_Size;
	}
}
