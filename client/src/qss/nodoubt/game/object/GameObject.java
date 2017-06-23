package qss.nodoubt.game.object;

import org.joml.*;

import qss.nodoubt.graphics.Texture;
import qss.nodoubt.graphics.TextureManager;
import qss.nodoubt.graphics.VertexArray;
import qss.nodoubt.input.Input;
import qss.nodoubt.input.KeyListener;
import qss.nodoubt.input.MouseListener;

public abstract class GameObject {
	private Vector3f m_Position;
	private Vector2f m_Size;
	private float m_Angle;
	private VertexArray m_VertexArray;
	private Texture m_Texture;
	private KeyListener m_KeyListener = null;
	private MouseListener m_MouseListener = null;
	
	/**
	 * 오브젝트 생성
	 * @param textureName 초기 텍스쳐 이름
	 */
	public GameObject(String textureName) {
		m_Position = new Vector3f(0.0f, 0.0f, 0.0f);
		m_Size = new Vector2f(1.0f, 1.0f);
		m_Angle = 0.0f;
		m_Texture = TextureManager.getInstance().getTexture(textureName);
		m_VertexArray = new VertexArray(m_Texture.getWidth(), m_Texture.getHeight(), 0.0f, 1.0f, 0.0f, 1.0f);
	}
	
	public abstract void update(float deltaTime);
	
	public final void draw() {
		Matrix4f translate = new Matrix4f().translate(m_Position);
		Matrix4f rotate = new Matrix4f().rotate(m_Angle, new Vector3f(0.0f, 0.0f, 1.0f));
		Matrix4f scale = new Matrix4f().scale(new Vector3f(m_Size, 1.0f));
		
		Matrix4f world = scale.mul(rotate).mul(translate);
		
		
		
		m_Texture.bind();
		
		m_VertexArray.draw(world);
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
	
	/**
	 * 오브젝트 삭제되기 직전에 호출되어야 할 함수
	 * 엔진이 알아서 호출하니 호출하지 말것
	 */
	public final void destroyObject() {
		Input input = Input.getInstance();
		if(m_KeyListener != null) {
			input.removeKeyListener(m_KeyListener);
		}
		if(m_MouseListener != null) {
			input.removeMouseListener(m_MouseListener);
		}
	}
	
	/**
	 * 이벤트 리스너 설정
	 * 현재 두번호출하면 상태 이상하니 한번만 호출할것
	 * @param key 키보드 리스너, 없으면 null넣으셈
	 * @param mouse 마우스 버튼 리스터, 없으면 null넣으셈
	 */
	protected final void setEventListener(KeyListener key, MouseListener mouse) {
		Input input = Input.getInstance();
		if(key != null) {
			input.addKeyListener(key);
			m_KeyListener = key;
		}
		if(mouse != null) {
			input.addMouseListener(mouse);
			m_MouseListener = mouse;
		}
	}
	
	/**
	 * 텍스쳐 설정
	 * @param textureName 텍스쳐 이름
	 */
	protected final void setTexture(String textureName) {
		m_Texture = TextureManager.getInstance().getTexture(textureName);
		m_VertexArray.setSize(m_Texture.getWidth(), m_Texture.getHeight());
	}
}
